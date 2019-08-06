
package servidor;
import Datos.anteproyectoDAO;
import Datos.clsUsuarioDAO;
import sop_corba.*;
public class jefeDepartamentoImp extends interfazJefeDepartamentoPOA {

    @Override
    public boolean RegistarAnteProyectos(anteproyectoDTO parObjAnteproyectoDTO) {
        System.out.println("registrando anteproyectos");
        anteproyectoDAO objAnteproyectoDAO = new anteproyectoDAO();
        objAnteproyectoDAO.registrarAnteproyecto(parObjAnteproyectoDTO);     
        objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(parObjAnteproyectoDTO.atrCodigo,4, parObjAnteproyectoDTO.atrIdCodirector,parObjAnteproyectoDTO.atrFechaAprobacion);
        objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(parObjAnteproyectoDTO.atrCodigo,3, parObjAnteproyectoDTO.atrIdDirector,parObjAnteproyectoDTO.atrFechaAprobacion);
        objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(parObjAnteproyectoDTO.atrCodigo,1, parObjAnteproyectoDTO.atrIdEstudiante1,parObjAnteproyectoDTO.atrFechaAprobacion);
        objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(parObjAnteproyectoDTO.atrCodigo,1, parObjAnteproyectoDTO.atrIdEstudiante2,parObjAnteproyectoDTO.atrFechaAprobacion);
        return true;
    }

    @Override
    public boolean modificarEstadoAnteproyecto(int parCodigo, int parEstado) {
         System.out.println("modificadno estado del protecto con codigo: "+parCodigo + "a estado :" +parEstado);
           ///anteproyectoDAO objAnteproyectoDAO = new anteproyectoDAO();
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
         anteproyectoDAO objAnteproyectoDAO = new anteproyectoDAO();
           boolean res1 =  objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(objAsigEvaluadores.cod_anteproyecto,2, objAsigEvaluadores.IdEvaluador1,objAsigEvaluadores.FechaRevision1);
           boolean res2 =  objAnteproyectoDAO.llenarDatosUsuario_anteproyecto(objAsigEvaluadores.cod_anteproyecto,2, objAsigEvaluadores.idEvaluador2,objAsigEvaluadores.FechaRevision2);
           return (res1 = true && res2 == true);
        
    }
    
}
