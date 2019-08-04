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
            /*
            sentencia.close();
            //otra sentencia tomando como usuario principal el codigo de CODIRECTOR
            sentencia = null;
            consulta = "INSERT into usuarios_anteproyecto(usuarios_anteproyecto.ID_ANTEPROYECTO,usuarios_anteproyecto.ROL_ANTEPROYECTO,\n" +
"                                        usuarios_anteproyecto.ID_USUARIO,usuarios_anteproyecto.FECHAREGISTRO,usuarios_anteproyecto.FECHAAPROBACION)VALUES(?,?,?,?,?);";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parUsuario.atrCodigo);
             sentencia.setInt(2,4);
            sentencia.setString(3, parUsuario.atrIdCodirector);
            sentencia.setString(4, parUsuario.atrFechaAprobacion); 
            sentencia.setString(5, parUsuario.atrFechaRegistro);
           conexionABaseDeDatos.desconectar();
           resultado2 = sentencia.executeUpdate(); 
            this.llenarDatosUsuario_anteproyecto(parUsuario.atrCodigo,parUsuario.atrRolAnteproyecto,parUsuario.atrIdDirector,parUsuario.atrFechaRegistro,parUsuario.atrFechaAprobacion);
            /*
            
            resultado2 = sentencia.executeUpdate();
            //otra sentencia tomando como usuario principal el codigo de director
            sentencia.setString(3, parUsuario.atrIdDirector);
            resultado3 = sentencia.executeUpdate();
            System.out.println("paso1");
            //otra sentencia tomando como usuario principal el codigo de estudiante1
            sentencia.setString(1, parUsuario.atrIdEstudiante1);
            resultado4 = sentencia.executeUpdate();
            System.out.println("paso1");
            //otra sentencia tomando como usuario principal el codigo de estudiante2
            sentencia.setString(1, parUsuario.atrIdEstudiante2);
            resultado4 = sentencia.executeUpdate();
            System.out.println("paso1");
            //otra sentencia tomando como usuario principal el codigo de evaluador
            sentencia.setString(2, parUsuario.atrIdEstudiante2);
            resultado5 = sentencia.executeUpdate();
            System.out.println("paso1");
            */
            

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    public boolean llenarDatosUsuario_anteproyecto(int parCodigoAnteproyecto,int parCodigoRol, String parCodigoUsuario,String parFechaRevision, String parFecharRegistro){
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
}
