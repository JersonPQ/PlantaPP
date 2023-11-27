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
    
    public static String MostrarEmpleados(){
        Connection con = getConnection();
        Statement st;
        ResultSet resultSet;
        try {
            st = con.createStatement();
            resultSet = st.executeQuery("Call MostrarEmpleados();");
            String str = "";
            
            // while para imprimir todos los datos por tupla de el select
            while (resultSet.next()) {
                str += resultSet.getInt(1) + "\t|" +
                        resultSet.getString(2) + "\t|" +
                        resultSet.getString(3) + "\t|" +
                        resultSet.getDate(4) + "\t|" +
                        resultSet.getDate(5) + "\t|" +
                        resultSet.getInt(6) + "\t|" +
                        resultSet.getInt(7) + "\t|" +
                        resultSet.getString(8) + "\t|" +
                        resultSet.getInt(9) + "\t|" +
                        resultSet.getInt(10) + "\t|" +
                        resultSet.getInt(11) + "\t|" + "\n";

            }
            con.close();
            return str;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return "";
    }
    
    // CALENDARIOS    
    public static String[] getNombresCalendarios(){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        String ids = "";
        String resultado = "";
        String[] resultadoFinal;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Call traernombrecalendario();");
            
            while (rs.next()) {      
                ids += rs.getInt(1) + ",";
                resultado += rs.getString(2) + ",";
            }
            
            resultadoFinal = new String[]{ids, resultado};
            con.close();
            return resultadoFinal;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return null;
    }
    
    public static String consultarDiasTrabajadosPorCalendario(int idCalendarioConsulta){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call consultarDiasTrabajadosPorCalendario(?)}");
            ResultSet rs;
            stmt.setInt(1, idCalendarioConsulta);
            rs = stmt.executeQuery();
            
            String resultado = "Día            Hora Entrada      Hora Salida         Horas Laborables\n";
            while (rs.next()) {                
                resultado += rs.getString(1) + "\t" + rs.getTime(2) + "\t" +
                        rs.getTime(3) + "\t\t" + rs.getInt(4) + "\n";
            }
            
            con.close();
            stmt.close();
            return resultado;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al obtener los datos";
        }
    }
    
    public static String consultarDiasFeriadosPorCalendario(int idCalendarioConsulta){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call consultarDiasFeriadosPorCalendario(?)}");
            ResultSet rs;
            stmt.setInt(1, idCalendarioConsulta);
            rs = stmt.executeQuery();
            
            String resultado = "";
            while (rs.next()) {                
                resultado += rs.getInt(1) + "\t" + rs.getString(2) + "\t";
                if (rs.getString(3).equals("D")) {
                    resultado += "Pago Doble\n";
                } else {
                    resultado += "Pago Normal\n";
                }
            }
            
            con.close();
            stmt.close();
            return resultado;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al obtener los datos";
        }
    }
    
    public static String consultarTipoDPagoPorCalendario(int idCalendarioConsulta){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call consultarTipoDPagoPorCalendario(?)}");
            ResultSet rs;
            stmt.setInt(1, idCalendarioConsulta);
            rs = stmt.executeQuery();
            
            String resultado = "";
            while (rs.next()) {                
                resultado += rs.getString(1) + "\n";
            }
            
            con.close();
            stmt.close();
            return resultado;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al obtener los datos";
        }
    }
    
    public static String anadirCalendario(String nombreCalendario, int tipoDPago){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call anadirCalendario(?, ?, ?)}");
            stmt.setString(1, nombreCalendario);
            stmt.setInt(2, tipoDPago);
            
            stmt.registerOutParameter(3, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(3);
            
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al añadir el calendario";
        }
        
    }
    
    public static String modificarCalendario(int idCalendario, int idTipoPago){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call modificarCalendario(?, ?, ?)}");
            stmt.setInt(1, idCalendario);
            stmt.setInt(2, idTipoPago);
            
            stmt.registerOutParameter(3, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(3);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al modificar el calendario";
        }
    }
    
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    public static boolean FechaCSentido(String FechaIngreso, String FechaSalida){
        int AneoIngreso = Integer.parseInt(FechaIngreso.substring(0, 4));
        int AneoSalida = Integer.parseInt(FechaSalida.substring(0, 4));
        if(AneoIngreso>AneoSalida){
            return false;
        }
        else if(AneoIngreso<AneoSalida){
            return true;
        }
        int MesIngreso = Integer.parseInt(FechaIngreso.substring(5, 7));
        int MesSalida = Integer.parseInt(FechaSalida.substring(5, 7));
        if(MesIngreso>MesSalida){
            return false;
        }
        else if(MesIngreso<MesSalida){
            return true;
        }
        int DiaIngreso = Integer.parseInt(FechaIngreso.substring(8));
        int DiaSalida = Integer.parseInt(FechaSalida.substring(8));
        if(DiaIngreso>DiaSalida){
            return false;
        }
        return true;
    }
    
    public static boolean VerificarIdDepartamentoDPO(int IdDepartamento){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("call MostrarIdDepartamento();");
            boolean Aviso = false;
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                if(rs.getInt(1) == IdDepartamento){
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
    
    public static boolean VerificarIdTipoEmpleadoTE(int IdTipoEmpleado){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("call MostrarIdTipoEmpleado();");
            boolean Aviso = false;
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                if(rs.getInt(1) == IdTipoEmpleado){
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
    
    
    public static int ObtenerSBruto(int IdTipoEmpleado){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call ObtenerSalarioBruto(?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipoEmpleado);
            
            stmt.registerOutParameter(2, Types.INTEGER);
            
            stmt.execute();
            
            int SBruto = stmt.getInt(2);
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return SBruto;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        
    }
    
    public static int ObtenerSHora(int IdTipoEmpleado){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call ObtenerSalarioHora(?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipoEmpleado);
            
            stmt.registerOutParameter(2, Types.INTEGER);
            
            stmt.execute();
            
            int SHora = stmt.getInt(2);
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return SHora;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }
    
    public static boolean InsertarEmpleado( String Nombre, String Apellidos, String FechaIngreso, String FechaSalida, int SalarioBruto, 
            int SalarioHora, String Planta, int IdDepartamento, int IdSupervisor, int IdTipoE){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call InsertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Statement st;
            ResultSet rs;
            if(FechaSalida.length() == 0){
                stmt.setString(1, Nombre);
                stmt.setString(2, Apellidos);
                stmt.setString(3, FechaIngreso);
                stmt.setString(4, null);
                stmt.setInt(5, SalarioBruto);
                stmt.setInt(6, SalarioHora);
                stmt.setString(7, Planta);
                stmt.setInt(8, IdDepartamento);
                stmt.setInt(9, IdSupervisor);
                stmt.setInt(10, IdTipoE);
                stmt.execute();
            
            
                // while para imprimir todos los datos por tupla de el select
                con.close();
                return true;
            }
            stmt.setString(1, Nombre);
            stmt.setString(2, Apellidos);
            stmt.setString(3, FechaIngreso);
            stmt.setString(4, FechaSalida);
            stmt.setInt(5, SalarioBruto);
            stmt.setInt(6, SalarioHora);
            stmt.setString(7, Planta);
            stmt.setInt(8, IdDepartamento);
            stmt.setInt(9, IdSupervisor);
            stmt.setInt(10, IdTipoE);
            stmt.execute();
            

            
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    public static String eliminarCalendario(int idCalendario){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call eliminarCalendario(?, ?)}");
            stmt.setInt(1, idCalendario);
            
            stmt.registerOutParameter(2, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(2);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al eliminar el calendario";
        }
        
    }
    
    public static String MostrarTiposEmpleados(){
        Connection con = getConnection();
        Statement st;
        ResultSet resultSet;
        try {
            st = con.createStatement();
            resultSet = st.executeQuery("Call MostrarTiposEmpleados();");
            String str = "IdTipo" + "\t|" + "NombreTipo" + "\t|" + "IdCalendario" + "\t|" + "CostoHora" + "\t|" + "CostoHoraExtra" + "\t|" + "CostoHoraDoble" + "\t|" + "\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (resultSet.next()) {
                str += resultSet.getInt(1) + "\t|" +
                        resultSet.getString(2) + "\t|" +
                        resultSet.getInt(3) + "\t|" +
                        resultSet.getBigDecimal(4) + "\t|" +
                        resultSet.getBigDecimal(5) + "\t|" +
                        resultSet.getBigDecimal(6) + "\t|" + "\n";

            }
            con.close();
            return str;
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        return "";
    }
    
    // DEPARTAMENTOS
    public static String[] getNombresDepartamentos(){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        String ids = "";
        String resultado = "";
        String[] resultadoFinal;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Call traernombredepartamento();");
            
            while (rs.next()) {      
                ids += rs.getInt(1) + ",";
                resultado += rs.getString(2) + ",";
            }
            
            resultadoFinal = new String[]{ids, resultado};
            con.close();
            return resultadoFinal;
        } catch (Exception e) {
            System.out.println("Error");
        }
      
        return null;
    }
    
    
    public static boolean EditarEmpleado( int IdEmpleado, String Nombre, String Apellidos, String FechaIngreso, String FechaSalida, int SalarioBruto, 
            int SalarioHora, int IdDepartamento, int IdSupervisor, int IdTipoE){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call EditarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Statement st;
            ResultSet rs;
            if(FechaSalida.length() == 0){
                stmt.setInt(1, IdEmpleado);
                stmt.setString(2, Nombre);
                stmt.setString(3, Apellidos);
                stmt.setString(4, FechaIngreso);
                stmt.setString(5, null);
                stmt.setInt(6, SalarioBruto);
                stmt.setInt(7, SalarioHora);
                stmt.setInt(8, IdDepartamento);
                stmt.setInt(9, IdSupervisor);
                stmt.setInt(10, IdTipoE);
                stmt.execute();
            
            
                // while para imprimir todos los datos por tupla de el select
                con.close();
                return true;
            }
            stmt.setInt(1, IdEmpleado);
                stmt.setString(2, Nombre);
                stmt.setString(3, Apellidos);
                stmt.setString(4, FechaIngreso);
                stmt.setString(5, FechaSalida);
                stmt.setInt(6, SalarioBruto);
                stmt.setInt(7, SalarioHora);
                stmt.setInt(8, IdDepartamento);
                stmt.setInt(9, IdSupervisor);
                stmt.setInt(10, IdTipoE);
                stmt.execute();
            

            
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    public static boolean EliminarMarcasDEmpleado( int IdEmpleado){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call EliminarMarcasDEmpleado(?)}");
            Statement st;
            ResultSet rs;
            
            stmt.setInt(1, IdEmpleado);
            stmt.execute();
            

            
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static String anadirDepartamento(String nombreDep){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call anadirDepartamento(?, ?)}");
            stmt.setString(1, nombreDep);
            
            stmt.registerOutParameter(2, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(2);
            
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al añadir el departamento";
        }
    }
    
    public static String modificarDepartamento(int idDepModificar, String nuevoNombre){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call modificarDepartamento(?, ?, ?)}");
            stmt.setInt(1, idDepModificar);
            stmt.setString(2, nuevoNombre);
            
            stmt.registerOutParameter(3, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(3);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al modificar el departamento";
        }
        
    }
    
    public static String eliminarDepartamento(int idCalendario){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call eliminarDepartamento(?, ?)}");
            stmt.setInt(1, idCalendario);
            
            stmt.registerOutParameter(2, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(2);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al eliminar el calendario";
        }
        
    }
    
    // DIAS FERIADOS
    
    public static String[] consultarDiasFeriadosPorCalendarioID(int idCalendarioConsulta){
        Connection con = getConnection();
        String ids = "";
        String resultado = "";
        String[] resultadoFinal;
        try {
            CallableStatement stmt = con.prepareCall("{call consultarDiasFeriadosPorCalendarioID(?)}");
            ResultSet rs;
            stmt.setInt(1, idCalendarioConsulta);
            rs = stmt.executeQuery();
            
            while (rs.next()) {   
                ids += rs.getInt(1) + ",";
                resultado += rs.getInt(2) + "-" + rs.getString(3) + ",";
            }
            
            resultadoFinal = new String[]{ids, resultado};
            con.close();
            stmt.close();
            return resultadoFinal;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public static String anadirDiaFeriado(int idCalendario, int numDia, int numMes, String tipoPago){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call anadirDiaFeriado(?, ?, ?, ?, ?)}");
            stmt.setInt(1, idCalendario);
            stmt.setInt(2, numDia);
            stmt.setInt(3, numMes);
            stmt.setString(4, tipoPago);
            
            stmt.registerOutParameter(5, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(5);
            
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al añadir el dia feriado";
        }
        
    }
    
    public static String modificarDiaFeriado(int idCalendario, int idDiaCambiar, int numDia, int numMes, String tipoPaga){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call modificarDiaFeriado(?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, idCalendario);
            stmt.setInt(2, idDiaCambiar);
            stmt.setInt(3, numDia);
            stmt.setInt(4, numMes);
            stmt.setString(5, tipoPaga);
            
            stmt.registerOutParameter(6, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(6);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al modificar el dia feriado";
        }
        
    }
    
    public static String eliminarDiaFeriado(int idDiaBorrar){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{call eliminarDiaFeriado(?, ?)}");
            stmt.setInt(1, idDiaBorrar);
            
            stmt.registerOutParameter(2, Types.NVARCHAR);
            
            stmt.execute();
            
            String Salida = stmt.getString(2);
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al eliminar el dia feriado";
        }
    }
    
    public static boolean EliminarEmpleado( int IdEmpleado){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call EliminarEmpleado(?)}");
            Statement st;
            ResultSet rs;
            
            stmt.setInt(1, IdEmpleado);
            stmt.execute();
            

            
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    public static boolean VerificarIdCalendarios(int IdCalendario){
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Call MostrarIdCalendarios();");
            boolean Aviso = false;
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                if(rs.getInt(1) == IdCalendario){
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
    
    public static int InsertarNombreTipo(String NombreTipo){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call InsertarNombreTipo(?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setString(1, NombreTipo);
            
            stmt.registerOutParameter(2, Types.INTEGER);
            
            stmt.execute();
            
            int Salida = stmt.getInt(2);
            System.out.println("1");
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        
    }
    
    
    public static boolean InsertarTipoEmpleado(int IdTipo, int IdCalendario, int CostoHora, int CostoHoraE, int CostoHoraDN){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call InsertarTipoEmpleado(?, ?, ?,?,?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipo);
            stmt.setInt(2, IdCalendario);
            stmt.setInt(3, CostoHora);
            stmt.setInt(4, CostoHoraE);
            stmt.setInt(5, CostoHoraDN);
            
            
            stmt.execute();
            System.out.println("1");
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    public static boolean EditarTipoEmpleado(int IdTipo, int IdCalendario, int CostoHora, int CostoHoraE, int CostoHoraDN, String NombreTipo){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call EditarTipoEmpleado(?, ?, ?, ?, ?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipo);
            stmt.setInt(2, IdCalendario);
            stmt.setInt(3, CostoHora);
            stmt.setInt(4, CostoHoraE);
            stmt.setInt(5, CostoHoraDN);
            stmt.setString(6, NombreTipo);
            
            
            stmt.execute();
            System.out.println("1");
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    
    public static int ObtenerEPorTE(int IdTipo){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call ObtenerCEmpleadosPT(?, ?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipo);
            
            
            stmt.registerOutParameter(2, Types.INTEGER);
            
            stmt.execute();
            
            int Salida = stmt.getInt(2);
            System.out.println("1");
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        
    }
    
    
    public static boolean EliminarTipoEmpleado(int IdTipo){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{cALL EliminarTipoEmpleado(?)}");
            Statement st;
            ResultSet rs;
            stmt.setInt(1, IdTipo);
            
            
            stmt.execute();
            
            
            // while para imprimir todos los datos por tupla de el select
            
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    
    public static String MostrarInfDepartamentos(){
        Connection con = getConnection();
        Statement st;
        ResultSet resultSet;
        try {
            st = con.createStatement();
            resultSet = st.executeQuery("Call traernombredepartamento();");
            String str = "IdDepartamento" + "\t|" + "NombreDepartamento" + "\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (resultSet.next()) {
                str += resultSet.getInt(1) + "\t\t|" +
                        resultSet.getString(2) + "\t\t|" + "\n";

            }
            con.close();
            return str;
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        return "";
    }
    
    public static String MostrarInfTiposEmpledos(){
        Connection con = getConnection();
        Statement st;
        ResultSet resultSet;
        try {
            st = con.createStatement();
            resultSet = st.executeQuery("Call MostrarTiposEmpleado();");
            String str = "IdTipo" + "\t\t|" + "NombreTipo" + "\t\t|" + "IdCalendario" + "\t\t|" + "CostoHora" + "\t\t|" + "CostoHoraExtra" + "\t\t|" + "CostoHoraDoble" + "\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (resultSet.next()) {
                str += resultSet.getInt(1) + "\t\t|" +
                        resultSet.getString(2) + "\t\t|" +
                        resultSet.getInt(3) + "\t\t|" +
                        resultSet.getInt(4) + "\t\t|" +
                        resultSet.getInt(5) + "\t\t|" +
                        resultSet.getInt(6) + "\t\t|" + "\n";

            }
            con.close();
            return str;
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        return "";
    }
    
    
    public static String MostrarInfCalendarios(){
        Connection con = getConnection();
        Statement st;
        ResultSet resultSet;
        try {
            st = con.createStatement();
            resultSet = st.executeQuery("Call MostrarCalendarios();");
            String str = "IdCalendario" + "\t\t\t|" + "NombreCalendario" + "\t\t\t|" + "TipoDPago" + "\t\t\t|\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (resultSet.next()) {
                str += resultSet.getInt(1) + "\t\t\t|" +
                        resultSet.getString(2) + "\t\t\t|" +
                        resultSet.getString(3) + "\t\t\t|" + "\n";

            }
            con.close();
            return str;
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        return "";
    }
    
    public static String Consulta3(String FechaI, String FechaF){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call Consulta3(?,?)}");
            stmt.setString(1, FechaI);
            stmt.setString(2, FechaF);
            ResultSet rs = null;
            
            
            stmt.execute();
            rs = stmt.getResultSet();
            String str = "IdEmpleado" + "\t\t\t|" + "Nombre Empleado" + "\t\t\t|\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                str += rs.getInt(1) + "\t\t\t|" +
                        rs.getString(2) + "\t\t\t|" + "\n";

            }
            
            con.close();
            stmt.close();
            return str;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al realizar Consulta 3";
        }
        
    }
    
    public static String Consulta5(int IdEmpleado){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call Consulta5(?)}");
            stmt.setInt(1, IdEmpleado);
            ResultSet rs = null;
            
            
            stmt.execute();
            rs = stmt.getResultSet();
            String str = "IdEmpleado" + "\t\t\t\t|" + "Nombre Empleado" + "\t\t\t\t|" + "Apellidos Empleado" + "\t\t\t\t|" + "Fecha Ingreso" 
                    + "\t\t\t\t|" + "FechaSalida" + "\t\t\t\t|" + "Salario Bruto" + "\t\t\t\t|" + "Salario Hora" + "\t\t\t\t|" + "Planta" 
                    + "\t\t\t\t|" + "Departamento" + "\t\t\t\t|" + "Supervisor" + "\t\t\t\t|" + "Puesto" + "\t\t\t\t|\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                str += rs.getInt(1) + "\t\t\t\t|" +
                        rs.getString(2) + "\t\t\t\t|" +
                        rs.getString(3) + "\t\t\t\t|" +
                        rs.getDate(4) + "\t\t\t\t|" +
                        rs.getDate(5) + "\t\t\t\t|" +
                        rs.getInt(6) + "\t\t\t\t|" +
                        rs.getInt(7) + "\t\t\t\t|" +
                        rs.getString(8) + "\t\t\t\t|" +
                        rs.getString(9) + "\t\t\t\t|" +
                        rs.getInt(10) + "\t\t\t\t|" +
                        rs.getString(11) + "\t\t\t\t|" + "\n";

            }
            
            con.close();
            stmt.close();
            return str;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al realizar Consulta 3";
        }
        
    }
    
    public static String Consulta7(int IdSupervisor){
        Connection con = getConnection();
        
        try {
            CallableStatement stmt = con.prepareCall("{Call Consulta7(?)}");
            stmt.setInt(1, IdSupervisor);
            ResultSet rs = null;
            
            
            stmt.execute();
            rs = stmt.getResultSet();
            String str = "IdEmpleado" + "\t|" + "Nombre Empleado" + "\t|" + "Apellidos Empleado" + "\t|" + "Fecha Ingreso" 
                    + "\t|" + "FechaSalida" + "\t|" + "Salario Bruto" + "\t|" + "Salario Hora" + "\t|" + "Planta" 
                    + "\t|" + "Departamento" + "\t|" + "Puesto" + "\t|\n\n";
            
            // while para imprimir todos los datos por tupla de el select
            while (rs.next()) {
                str += rs.getInt(1) + "\t|" +
                        rs.getString(2) + "\t|" +
                        rs.getString(3) + "\t|" +
                        rs.getDate(4) + "\t|" +
                        rs.getDate(5) + "\t|" +
                        rs.getInt(6) + "\t|" +
                        rs.getInt(7) + "\t|" +
                        rs.getString(8) + "\t|" +
                        rs.getString(9) + "\t|" +
                        rs.getString(11) + "\t|" + "\n";

            }
            
            con.close();
            stmt.close();
            return str;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al realizar Consulta 3";
        }
        
    }
    
    
    public static String consultaTardiasPorPeriodoTiempo(String fechaInicial, String fechaFinal){
        Connection con = getConnection();
        ResultSet rs;
        String Salida = "";
        
        try {
            CallableStatement stmt = con.prepareCall("{call tardiasPorPeriodoTiempo(?, ?)}");
            stmt.setString(1, fechaInicial);
            stmt.setString(2, fechaFinal);
            
            rs = stmt.executeQuery();
            
            Salida += "Id Empleado \t Nombre \t Apellidos \t\t Hora Entrada \t Hora llegada \t Dia marca \n";
            
            while (rs.next()) {                
                Salida += rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getTime(4) + "\t" +
                        rs.getTime(5) + "\t" + rs.getDate(6) + "\n";
            }
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al consultar tardias";
        }
    }
    
    public static String consultaEmpleadosDeBajaPorPeriodo(String fechaInicial, String fechaFinal){
        Connection con = getConnection();
        ResultSet rs;
        String Salida = "";
        
        try {
            CallableStatement stmt = con.prepareCall("{call empleadosDeBajaPorPeriodo(?, ?)}");
            stmt.setString(1, fechaInicial);
            stmt.setString(2, fechaFinal);
            
            rs = stmt.executeQuery();
            
            Salida += "Nombre \t\t Apellidos \t\t\t Fecha ingreso \t\t Fecha salida\n";
            
            while (rs.next()) {                
                Salida += rs.getString(1) + "\t\t" + rs.getString(2) + "\t\t\t" +
                        rs.getDate(3) + "\t\t" + rs.getDate(4) + "\n";
            }
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al consultar empleados de baja";
        }
    }
    
    public static String consultaInfoEmpleadosPorDepartamento(int idDepartamento){
        Connection con = getConnection();
        ResultSet rs;
        String Salida = "";
        
        try {
            CallableStatement stmt = con.prepareCall("{call infoEmpleadosPorDepartamento(?)}");
            stmt.setInt(1, idDepartamento);
            
            rs = stmt.executeQuery();
            
            Salida += "Id \t Nombre \t\t Apellidos \t\t Fecha ingreso \t Salario Bruto, \t Salario H \t Planta \t Supervisor\n";
            
            while (rs.next()) {                
                Salida += rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" +
                        rs.getDate(4) + "\t" + rs.getFloat(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\n";
            }
            
            con.close();
            stmt.close();
            return Salida;
        } catch (Exception e) {
            System.out.println(e);
            return "Error al consultar empleados de baja";
        }
    }
    
    public static void main(String[] args) {
        System.out.println(ObtenerSBruto(1));
        System.out.println(ObtenerSHora(1));
    }
}
