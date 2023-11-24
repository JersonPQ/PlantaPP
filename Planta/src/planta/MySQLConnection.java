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
    private static final String url = "jdbc:mysql://localhost:3306/PlantaN";
    private static final String user = "root";
    private static final String password = "MneJDPQ077.";

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
    
    
     public static void main(String[] args) {
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Call TraerIdEmpleado();");
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
            
            con.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
