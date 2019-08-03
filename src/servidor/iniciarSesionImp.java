/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import Datos.clsUsuarioDAO;
import sop_corba.*;
/**
 *
 * @author rene
 */
public class iniciarSesionImp extends interfazUsuarioPOA{

    @Override
    public int IniciarSesion(String parUser, String parPassword) {
            System.out.println("iniciando sesion usuario-> user: "+parUser + " pass: "+parPassword);
            clsUsuarioDAO objDao = new clsUsuarioDAO();
            return objDao.iniciarSesion(parUser, parPassword);
            
    }
    
}
