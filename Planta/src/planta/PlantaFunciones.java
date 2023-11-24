package planta;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import static planta.MySQLConnection.getConnection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author XPC
 */
public class PlantaFunciones {
    
    public static boolean VerificarIdEmpleadoEmpleados(int IdEmpleado){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Call TraerIdEmpleado();");
            boolean Aviso = false;
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                if(rs.getInt(1) == IdEmpleado){
                    Aviso = true;
                    return Aviso;
                }
            }
            
            con.close();
            return Aviso;
        } catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }
    
    
    public static String Marcar(int IdEmpleado, String FechaActual){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call Marcar(?, ?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdEmpleado);
            stmt.setString(2, FechaActual);
            
            stmt.registerOutParameter(3, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(3);
            System.out.println("1");
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
        
    }
}
