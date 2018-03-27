package biz.chenxu.tutorial;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MySQLMain {

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "MySQLMain", options );
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("H").type(String.class).hasArg(false).required(false).longOpt("help").desc("print help message").build());
        options.addOption(Option.builder("h").type(String.class).hasArg(true).required(false).argName("host").longOpt("host").desc("database host name or address, default is 127.0.0.1").build());
        options.addOption(Option.builder("p").type(String.class).hasArg(true).required(false).argName("port").longOpt("port").desc("database port, default is 3306").build());
        options.addOption(Option.builder("s").type(String.class).hasArg(true).required(false).argName("schema").longOpt("schema").desc("schema name").build());
        options.addOption(Option.builder("u").type(String.class).hasArg(true).required(false).argName("username").longOpt("username").desc("database username, default is root").build());
        options.addOption(Option.builder("P").type(String.class).hasArg(true).required(false).argName("password").longOpt("password").desc("database password, default is root").build());

        options.addOption(Option.builder().required(false).longOpt("test-time-zone").desc("test time zone").build());

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse( options, args);

            int counter = 0;
//            if (cmd.hasOption("help")) {
//                printHelp(options);
//                return;
//            }

            if (cmd.hasOption("test-time-zone")) {
                counter ++;
                MySQLTimeZone mySQLTimeZone = new MySQLTimeZone();
                mySQLTimeZone.run(cmd);
            }

            if (counter == 0) {
                printHelp(options);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            printHelp(options);
        }
    }
}
