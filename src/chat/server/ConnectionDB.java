package chat.server;

import java.sql.*;

public class ConnectionDB {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Dima10910000";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static Statement statement;
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connection successful");
        }catch (SQLException exc){
            exc.printStackTrace(System.out);
        }
    }
    static {
        try{
            statement = connection.createStatement();
            System.out.println("Statement successful");
        }catch (SQLException exc){
            exc.printStackTrace();
        }
    }
}
