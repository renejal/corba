package Datos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import sop_corba.anteproyectoDTO;

public class anteproyectoDAO {
     private final ConexionBD conexionABaseDeDatos;
     public anteproyectoDAO(){
        conexionABaseDeDatos = new ConexionBD();
}
     public boolean registrarAnteproyecto(anteproyectoDTO  parUsuario) 
    {
       
        conexionABaseDeDatos.conectar();
        int resultado1 = -1;
        int resultado2 = -1;
        int resultado3 = -1;
        int resultado4 = -1;
        int resultado5 = -1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "INSERT into anteproyecto(anteproyecto.ID_ANTEPROYECTO,anteproyecto.TITULO,anteproyecto.MODALIDAD)VALUES(?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parUsuario.getAtrCodigo());
            sentencia.setString(2, parUsuario.getAtrTitulo());
            sentencia.setString(3, parUsuario.getAtrModalidad());
            
            resultado1 = sentencia.executeUpdate(); 
            sentencia.close();
            //otra sentencia
            sentencia = null;
            consulta = "INSERT into usuarios_anteproyecto(usuarios_anteproyecto.ID_ANTEPROYECTO,usuarios_anteproyecto.ID_USUARIO,usuarios_anteproyecto.ROL_ANTEPROYECTO,\n" +
"                                        usuarios_anteproyecto.FECHAREGISTRO,usuarios_anteproyecto.FECHAAPROBACION)VALUES(?,?,4,?,?);";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parUsuario.getAtrCodigo());
            sentencia.setString(2, parUsuario.getAtrNombreCodirector());
            sentencia.setString(4, parUsuario.getAtrFechaRegistro());
            sentencia.setString(5, parUsuario.getAtrFechaAprobacion());
            
            resultado1 = sentencia.executeUpdate(); 
            
            
            
            
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado1 == 1 && resultado2 == 1;
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
}
