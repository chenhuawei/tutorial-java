package biz.chenxu.tutorial;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.StringUtils;

public class ConfigBuilder {

    private String host;
    private int port;
    private String schema;
    private String username;
    private String password;
    private boolean useUnicode = true;
    private String characterEncoding = "autodetect";
    private String serverTimezone;
    private boolean useFastDateParsing = true;
    private boolean cacheDefaultTimezone = true;
    private boolean noTimezoneConversionForDateType = true;
    private boolean noTimezoneConversionForTimeType = false;
    private boolean useGmtMillisForDatetimes = false;
    private boolean useJDBCCompliantTimezoneShift = false;
    private boolean useLegacyDatetimeCode = true;
    private boolean useSSPSCompatibleTimezoneShift = false;
    private boolean useTimezone = false;

    private ConfigBuilder() {

    }

    public ConfigBuilder host(String host) {
        this.host = host;
        return this;
    }

    public ConfigBuilder port(int port) {
        this.port = port;
        return this;
    }

    public ConfigBuilder schema(String schema) {
        this.schema = schema;
        return this;
    }

    public ConfigBuilder username(String username) {
        this.username = username;
        return this;
    }

    public ConfigBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * useUnicode
     *
     * Should the driver use Unicode character encodings when handling strings? Should only be used when
     * the driver can't determine the character set mapping, or you are trying to 'force' the driver to
     * use a character set that MySQL either doesn't natively support (such as UTF-8), true/false, defaults to 'true'
     *
     * Default: true
     *
     * Since version: 1.1g
     *
     * @param useUnicode
     * @return
     */
    public ConfigBuilder useUnicode(boolean useUnicode) {
        this.useUnicode = useUnicode;
        return this;
    }

    /**
     * characterEncoding
     *
     * If 'useUnicode' is set to true, what character encoding should the driver use when dealing with strings?
     * (defaults is to 'autodetect')
     *
     * Since version: 1.1g
     *
     * @param characterEncoding
     * @return
     */
    public ConfigBuilder characterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        return this;
    }

    /**
     * serverTimezone
     *
     * Override detection/mapping of time zone. Used when time zone from server doesn't map to Java time zone
     *
     * Since version: 3.0.2
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param serverTimezone
     * @return
     */
    public ConfigBuilder serverTimezone(String serverTimezone) {
        this.serverTimezone = serverTimezone;
        return this;
    }

    /**
     * useFastDateParsing
     *
     * Use internal String->Date/Time/Timestamp conversion routines to avoid excessive object creation?
     * This is part of the legacy date-time code, thus the property has an effect only when "useLegacyDatetimeCode=true."
     *
     * Default: true
     *
     * Since version: 5.0.5
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useFastDateParsing
     * @return
     */
    public ConfigBuilder useFastDateParsing(boolean useFastDateParsing) {
        this.useFastDateParsing = useFastDateParsing;
        return this;
    }

    /**
     * cacheDefaultTimezone
     *
     * Caches client's default time zone. This results in better performance when dealing with time zone
     * conversions in Date and Time data types, however it won't be aware of time zone changes if they happen at runtime.
     *
     * Default: true
     *
     * Since version: 5.1.35
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param cacheDefaultTimezone
     * @return
     */
    public ConfigBuilder cacheDefaultTimezone(boolean cacheDefaultTimezone) {
        this.cacheDefaultTimezone = cacheDefaultTimezone;
        return this;
    }

    /**
     * noTimezoneConversionForDateType
     *
     * Don't convert DATE values using the server time zone if 'useTimezone'='true' or 'useLegacyDatetimeCode'='false'
     *
     * Default: true
     *
     * Since version: 5.1.35
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param noTimezoneConversionForDateType
     * @return
     */
    public ConfigBuilder noTimezoneConversionForDateType(boolean noTimezoneConversionForDateType) {
        this.noTimezoneConversionForDateType = noTimezoneConversionForDateType;
        return this;
    }

    /**
     * noTimezoneConversionForTimeType
     *
     * Don't convert TIME values using the server time zone if 'useTimezone'='true'
     *
     * Default: false
     *
     * Since version: 5.0.0
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param noTimezoneConversionForTimeType
     * @return
     */
    public ConfigBuilder noTimezoneConversionForTimeType(boolean noTimezoneConversionForTimeType) {
        this.noTimezoneConversionForTimeType = noTimezoneConversionForTimeType;
        return this;
    }

    /**
     * useGmtMillisForDatetimes
     *
     * Convert between session time zone and GMT before creating Date and Timestamp instances
     * (value of 'false' leads to legacy behavior, 'true' leads to more JDBC-compliant behavior)?
     * This is part of the legacy date-time code, thus the property has an effect only when "useLegacyDatetimeCode=true."
     *
     * Default: false
     *
     * Since version: 3.1.12
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useGmtMillisForDatetimes
     * @return
     */
    public ConfigBuilder useGmtMillisForDatetimes(boolean useGmtMillisForDatetimes) {
        this.useGmtMillisForDatetimes = useGmtMillisForDatetimes;
        return this;
    }

    /**
     * useJDBCCompliantTimezoneShift
     *
     * Should the driver use JDBC-compliant rules when converting TIME/TIMESTAMP/DATETIME values' time zone information
     * for those JDBC arguments which take a java.util.Calendar argument? This is part of the legacy date-time code,
     * thus the property has an effect only when "useLegacyDatetimeCode=true."
     *
     * Default: false
     *
     * Since version: 5.0.0
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useJDBCCompliantTimezoneShift
     * @return
     */
    public ConfigBuilder useJDBCCompliantTimezoneShift(boolean useJDBCCompliantTimezoneShift) {
        this.useJDBCCompliantTimezoneShift = useJDBCCompliantTimezoneShift;
        return this;
    }

    /**
     * useLegacyDatetimeCode
     *
     * Use code for DATE/TIME/DATETIME/TIMESTAMP handling in result sets and statements that consistently handles
     * time zone conversions from client to server and back again, or use the legacy code for these datatypes that
     * has been in the driver for backwards-compatibility? Setting this property to 'false' voids the effects
     * of "useTimezone," "useJDBCCompliantTimezoneShift," "useGmtMillisForDatetimes," and "useFastDateParsing."
     *
     * Default: true
     *
     * Since version: 5.1.6
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useLegacyDatetimeCode
     * @return
     */
    public ConfigBuilder useLegacyDatetimeCode(boolean useLegacyDatetimeCode) {
        this.useLegacyDatetimeCode = useLegacyDatetimeCode;
        return this;
    }

    /**
     * useSSPSCompatibleTimezoneShift
     *
     * If migrating from an environment that was using server-side prepared statements, and the configuration
     * property "useJDBCCompliantTimeZoneShift" set to "true", use compatible behavior when not using server-side
     * prepared statements when sending TIMESTAMP values to the MySQL server.
     *
     * Default: false
     *
     * Since version: 5.0.5
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useSSPSCompatibleTimezoneShift
     * @return
     */
    public ConfigBuilder useSSPSCompatibleTimezoneShift(boolean useSSPSCompatibleTimezoneShift) {
        this.useSSPSCompatibleTimezoneShift = useSSPSCompatibleTimezoneShift;
        return this;
    }

    /**
     * useTimezone
     *
     * Convert time/date types between client and server time zones (true/false, defaults to 'false')?
     * This is part of the legacy date-time code, thus the property has an effect only when "useLegacyDatetimeCode=true."
     *
     * Default: false
     *
     * Since version: 3.0.2
     *
     * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
     *
     * @param useTimezone
     * @return
     */
    public ConfigBuilder useTimezone(boolean useTimezone) {
        this.useTimezone = useTimezone;
        return this;
    }

    public static ConfigBuilder parse(CommandLine cmd) {
        return new ConfigBuilder().host(cmd.getOptionValue("host", "127.0.0.1"))
                .port(Integer.parseInt(cmd.getOptionValue("port", "3306")))
                .schema(cmd.getOptionValue("schema", "test"))
                .username(cmd.getOptionValue("username", "root"))
                .password(cmd.getOptionValue("password", "root"));
    }

    public Config build() {
        Config config = new Config();
        config.setJdbcUrl(toJdbcUrl());
        config.setUsername(username);
        config.setPassword(password);
        return config;
    }

    private String toJdbcUrl() {
        String protocol = String.format("jdbc:mysql://%s:%s/%s", host, port, schema);
        StringBuilder arguments = new StringBuilder();

        arguments.append("?").append("useUnicode").append("=").append(useUnicode);
        arguments.append("&").append("useTimezone").append("=").append(useTimezone);
        arguments.append("&").append("cacheDefaultTimezone").append("=").append(cacheDefaultTimezone);

        if (StringUtils.isNotBlank(characterEncoding)) {
            arguments.append("&").append("characterEncoding").append("=").append(characterEncoding);
        }
        if (StringUtils.isNotBlank(serverTimezone)) {
            arguments.append("&").append("serverTimezone").append("=").append(serverTimezone);
        }

        return String.format("%s%s", protocol, arguments);
    }
}
