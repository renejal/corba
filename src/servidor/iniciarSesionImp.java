
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
