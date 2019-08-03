
package Datos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sop_corba.UsuarioDTO;

public class clsUsuarioDAO {
     private final ConexionBD conexionABaseDeDatos;
     
     public clsUsuarioDAO(){
         conexionABaseDeDatos = new ConexionBD();
     }
     public boolean registrarUsuario(UsuarioDTO  parUsuario) 
    {
       
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "insert into usuario(usuario.ID_USUARIO,usuario.NOMBRE,usuario.APELLIDO,usuario.user,usuario.PASSWORD,usuario.ROL)VALUES(?,?,?,?,?,?);";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setString(1, parUsuario.id);
            sentencia.setString(2, parUsuario.nombre);
            sentencia.setString(3, parUsuario.apellido);
            sentencia.setString(4, parUsuario.usuario);
            sentencia.setString(5, parUsuario.password);
            sentencia.setInt(6,parUsuario.tipo);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    public int iniciarSesion(String parUsuario, String parPassword) {
        int varTipUsuario = -1;
       System.out.println("usuario : "+parUsuario +" password: "+parPassword);
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "SELECT ROL FROM usuario where usuario.USER =? and usuario.PASSWORD=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
            sentencia.setString(1, parUsuario);
            sentencia.setString(2, parPassword);
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
            System.out.println("usuario tipo: " +res.getString("ROL"));
            varTipUsuario = Integer.parseInt(res.getString("ROL"));
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la consulta de un usuario: "+e.getMessage());         
        }
        
        return varTipUsuario;
    }
}
