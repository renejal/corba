
package servidor;
import Datos.anteproyectoDAO;
import Datos.clsUsuarioDAO;
import sop_corba.*;
public class jefeDepartamentoImp extends interfazJefeDepartamentoPOA {

    @Override
    public boolean RegistarAnteProyectos(anteproyectoDTO parObjAnteproyectoDTO) {
        System.out.println("registrando anteproyectos");
        anteproyectoDAO objAnteproyectoDAO = new anteproyectoDAO();
       return objAnteproyectoDAO.registrarAnteproyecto(parObjAnteproyectoDTO);
    }

    @Override
    public boolean modificarEstadoAnteproyecto(int parCodigo, int parEstado) {
         System.out.println("modificadno estado del protecto con codigo: "+parCodigo + "a estado :" +parEstado);
           anteproyectoDAO objAnteproyectoDAO = new anteproyectoDAO();
           //objAnteproyectoDAO.
    return true;        
    }

    @Override
    public boolean RegistrarUsuario(UsuarioDTO parObjUsuario) {
        System.out.println("registrando usuario");
        clsUsuarioDAO objUsuairo = new clsUsuarioDAO();
        objUsuairo.registrarUsuario(parObjUsuario);
        return true;
    }

    @Override
    public boolean AsignarEvaluadores(clsAsigEvaluadoresDTO objAsigEvaluadores) {
         System.out.println("asignadon evaluadores");
         return true;
        
    }
    
}
