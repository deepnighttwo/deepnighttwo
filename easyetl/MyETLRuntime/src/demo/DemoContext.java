/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package demo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DemoContext {

    private static final String SOURCE_DB_URL = "jdbc:mysql://localhost/test";
    private static final String TARGET_DB_URL = "jdbc:mysql://localhost/test";

    public static Properties SOURCE_DB_PROPS = new Properties();
    public static Properties TARGET_DB_PROPS = new Properties();

    public static final String SOURCE_FILE_URL = "C:" + File.pathSeparator
            + "sourceFile";
    public static final String TARGET_FILE_URL = "C:" + File.pathSeparator
            + "targetFile";;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SOURCE_DB_PROPS.setProperty("user", "root");
        SOURCE_DB_PROPS.setProperty("password", "sql");

        TARGET_DB_PROPS.setProperty("user", "root");
        TARGET_DB_PROPS.setProperty("password", "sql");
    }

    public static Connection getSourceConnection() throws SQLException {
        return DriverManager.getConnection(SOURCE_DB_URL, SOURCE_DB_PROPS);
    }

    public static Connection getTargetConnection() throws SQLException {
        return DriverManager.getConnection(TARGET_DB_URL, TARGET_DB_PROPS);
    }

}
