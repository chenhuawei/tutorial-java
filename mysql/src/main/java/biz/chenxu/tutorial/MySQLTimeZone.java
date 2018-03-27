package biz.chenxu.tutorial;

import org.apache.commons.cli.CommandLine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MySQLTimeZone {

    /**
     * 先以本地时区写入一条时间记录，然后分别以本地时区和UTC时区读取写入的时间
     * 再以UTC时区写入一条时间记录，然后分别以UTC时区和本地时区读取写入的时间
     *
     * 测试数据库时区
     *  'system_time_zone', 'CST'
     *  'time_zone', 'SYSTEM'
     * 数据库服务器时区
     *  /etc/localtime -> /usr/share/zoneinfo/Asia/Shanghai
     * 本地(JVM)时区
     *  Asia/Shanghai
     *
     * 测试结果
     *  本地(JVM)时区       数据库值                JDBC返回值
     *  Asia/Shanghai   2018-03-27 07:00:17     2018-03-27 15:00:17
     *  UTC             2018-03-27 07:00:17     2018-03-27 07:00:17
     *
     *  UTC             2018-03-27 07:00:18     2018-03-27 07:00:18
     *  Asia/Shanghai   2018-03-27 07:00:18     2018-03-27 15:00:18
     *
     * @param cmd
     */
    public void run(CommandLine cmd) {

        TimeZone local = TimeZone.getDefault();
        ConfigBuilder builder = ConfigBuilder.parse(cmd);

        Config config = builder
                .characterEncoding("utf8")
                .useTimezone(true)
                .serverTimezone("UTC")
                .cacheDefaultTimezone(false)    //Driver默认会缓存时区信息导致切换时区没有生效，这里为了测试所以告诉Driver不要缓存
                .build();

        String ownerCode = "UTC";
        Date now = new Date();
        System.out.println("insert local time " + format(now));
        writeTime(config, ownerCode, now);

        System.out.println("\tcurrent time is " + format(new Date()));
        readTime(config, ownerCode);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("\tcurrent time is " + format(new Date()));
        readTime(config, ownerCode);

        now = new Date();
        System.out.println("insert UTC time " + format(now));
        writeTime(config, ownerCode, now);
        System.out.println("\tcurrent time is " + format(new Date()));
        readTime(config, ownerCode);

        TimeZone.setDefault(local);
        System.out.println("\tcurrent time is " + format(new Date()));

        readTime(config, ownerCode);
    }

    private String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return formatter.format(date);
    }

    private void writeTime(Config config, String ownerCode, Date time) {
        Connection connection = connect(config);
        try {
            createTable(connection);
            insert(connection, ownerCode, time);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(connection);
    }

    private void readTime(Config config, String ownerCode) {
        Connection connection = connect(config);
        try {
            Date createTime = queryCreateTime(connection, ownerCode);
            System.out.println(String.format("\tlast insert datetime is %s", createTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(connection);
    }

    private void insert(Connection connection, String ownerCode, Date time) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `test_mysql_time_zone_info`" +
                "(" +
                "`create_time`," +
                "`update_time`," +
                "`owner_code`)" +
                "VALUES" +
                "(?,?,?)");
        pstmt.setObject(1, time);
        pstmt.setObject(2, new Date());
        pstmt.setObject(3, ownerCode);
        pstmt.executeUpdate();
    }

    private void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `test_mysql_time_zone_info` (" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID'," +
                "  `create_time` datetime NOT NULL COMMENT '创建时间'," +
                "  `update_time` datetime NOT NULL COMMENT '更新时间'," +
                "  `owner_code` varchar(100) NOT NULL COMMENT '数据拥有者编码'," +
                "  PRIMARY KEY (`id`)" +
                ") DEFAULT CHARSET=utf8 COMMENT='测试时区记录表'";
        execute(connection, sql);
    }

    private Date queryCreateTime(Connection connection, String ownerCode) throws SQLException {
        Date createTime = null;
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM `test_mysql_time_zone_info` WHERE owner_code = ? ORDER BY id DESC limit 1");
        pstmt.setObject(1, ownerCode);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            createTime = resultSet.getTimestamp("create_time");
        }
        resultSet.close();
        return createTime;
    }

    private void execute(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    private Connection connect(Config config) {
        try {
//            System.out.println("connect to " + config.getJdbcUrl());
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword());
            connection.setAutoCommit(true);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
