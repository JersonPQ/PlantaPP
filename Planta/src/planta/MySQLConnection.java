/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planta;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQLConnection {
    private static final String url = "jdbc:mysql://localhost:3306/plantan";
    private static final String user = "root";
    private static final String password = "CR07mrl02.";

    private MySQLConnection() {
    }
    
    public static Connection getConnection(){
        try {
            Connection connection;
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            System.out.println("Error al conectar");
        }
        return null;
    }
}