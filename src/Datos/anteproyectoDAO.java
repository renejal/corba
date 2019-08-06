package Datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sop_corba.anteproyectoDTO;

public class anteproyectoDAO {
     private final ConexionBD conexionABaseDeDatos;
     public anteproyectoDAO(){
        conexionABaseDeDatos = new ConexionBD();
}
    public boolean registrarAnteproyecto(anteproyectoDTO  parUsuario){
       System.out.println("codigo usuario-DAO: "+parUsuario.atrCodigo);
        conexionABaseDeDatos.conectar();
        int resultado = -1;
       
        try {            
            PreparedStatement sentencia = null;
            String consulta = "INSERT into anteproyecto(anteproyecto.ID_ANTEPROYECTO,anteproyecto.TITULO,anteproyecto.MODALIDAD,anteproyecto.FECHAREGISTRO)VALUES(?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parUsuario.atrCodigo);
            sentencia.setString(2, parUsuario.atrTitulo);
            sentencia.setString(3, parUsuario.atrModalidad);
            sentencia.setString(4, parUsuario.atrFechaRegistro);
            resultado = sentencia.executeUpdate();
             conexionABaseDeDatos.desconectar();
            

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    public boolean llenarDatosUsuario_anteproyecto(int parCodigoAnteproyecto,int parCodigoRol, String parCodigoUsuario,String parFechaRevision){
        System.out.println("codigoAnteproyecto-DAO2:"+parCodigoAnteproyecto);  
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "INSERT into usuarios_anteproyecto(usuarios_anteproyecto.ID_ANTEPROYECTO,usuarios_anteproyecto.ROL_ANTEPROYECTO,\n" +
"                                        usuarios_anteproyecto.ID_USUARIO,usuarios_anteproyecto.FECHAREVISION)VALUES(?,?,?,?);";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1,parCodigoAnteproyecto);
             sentencia.setInt(2,parCodigoRol);
            sentencia.setString(3,parCodigoUsuario);
            sentencia.setString(4,parFechaRevision); 
 
            
            resultado = sentencia.executeUpdate(); 
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
        
        
    }
    public boolean modificarConcepotAnteproyecto(String parIdAnteproyecto, int parConceto_anteproyecto ){
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "UPDATE usuarios_anteproyecto SET usuarios_anteproyecto.CONCEPTO = ? where usuarios_anteproyecto.ID_ANTEPROYECTO = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parConceto_anteproyecto);
            sentencia.setString(2, parIdAnteproyecto);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        return resultado == 1;
    }
    public ArrayList<anteproyectoDTO> listarAnteproyectos(){
         System.out.println("buscando en la base de datos");
         anteproyectoDTO objAnteproyecto=null;
         ArrayList<anteproyectoDTO> varVector  = new ArrayList();
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select * from anteproyecto";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta); 
            
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
                      
                objAnteproyecto= new anteproyectoDTO();
                objAnteproyecto.atrCodigo = (res.getInt("id_anteproyecto"));
                objAnteproyecto.atrTitulo =(res.getString("titulo"));
                objAnteproyecto.atrModalidad = (res.getString("modalidad"));
                objAnteproyecto.atrEstado = (res.getInt("estado"));
                objAnteproyecto.atrFechaRegistro = (res.getString("fecharegistro"));
                objAnteproyecto.atrFechaAprobacion =(res.getString("fechaaprobacion"));
                objAnteproyecto.atrConcepto = (res.getInt("concepto"));
                varVector.add(objAnteproyecto);
                
            }
            System.out.println("hola"+objAnteproyecto.atrConcepto);
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la consulta de un empleado: "+e.getMessage());         
        }
        
        return varVector;
    }
    public anteproyectoDTO buscarAnteproyecto(int parCodigo){
        System.out.println("buscando en la base de datos");
        anteproyectoDTO objAnteproyecto=null;
 
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select * from anteproyecto WHERE anteproyecto.ID_ANTEPROYECTO = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta); 
            sentencia.setInt(1, parCodigo);
            ResultSet res = sentencia.executeQuery();
             objAnteproyecto= new anteproyectoDTO();
            while(res.next()){
                      
               
                objAnteproyecto.atrCodigo = (res.getInt("id_anteproyecto"));
                objAnteproyecto.atrTitulo =(res.getString("titulo"));
                objAnteproyecto.atrModalidad = (res.getString("modalidad"));
                objAnteproyecto.atrEstado = (res.getInt("estado"));
                objAnteproyecto.atrFechaRegistro = (res.getString("fecharegistro"));
                objAnteproyecto.atrFechaAprobacion =(res.getString("fechaaprobacion"));
                objAnteproyecto.atrConcepto = (res.getInt("concepto"));
                
                
            }
            System.out.println("hola"+objAnteproyecto.atrConcepto);
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la consulta de un empleado: "+e.getMessage());         
        }
        
        return objAnteproyecto;
    } 
}
