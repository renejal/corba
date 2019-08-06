/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class evaluadorDAO {
    private final ConexionBD conexionABaseDeDatos;
    public evaluadorDAO(){
        conexionABaseDeDatos = new ConexionBD();
}
     public boolean ingresarCocepto(int parIdUsuario, int parCodAnteproyecto, int parConcepto) 
    {
      
        conexionABaseDeDatos.conectar();
        int resultado = -1;
       
        try {            
            PreparedStatement sentencia = null;
            String consulta = "UPDATE usuarios_anteproyecto set usuarios_anteproyecto.CONCEPTO = ? WHERE usuarios_anteproyecto.ID_ANTEPROYECTO = ? and usuarios_anteproyecto.ID_USUARIO = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, parConcepto);
            sentencia.setInt(2, parCodAnteproyecto);
            sentencia.setInt(3, parIdUsuario);
            resultado = sentencia.executeUpdate();
             conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    
    
    
}
