package Controlador;

import Datos.clsUsuarioDAO;
import sop_corba.UsuarioDTO;

public class clsControlador {
    public static void main(String args[]){
        UsuarioDTO obj = new UsuarioDTO("4","rene","jalvin","rene","r3n3",0);
        clsUsuarioDAO objDao = new clsUsuarioDAO();
        objDao.registrarUsuario(obj);
                
    }
}
