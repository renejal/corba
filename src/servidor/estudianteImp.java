
package servidor;

import Datos.anteproyectoDAO;
import java.util.ArrayList;
import sop_corba.anteproyectoDTO;
import sop_corba.interfazEstudiantesDirectorPOA;

public class estudianteImp extends interfazEstudiantesDirectorPOA {

    @Override
    public anteproyectoDTO[] listarAnteProyectos() {
        System.out.println("listnado anteproyectos");
        anteproyectoDAO objAnteproyecto = new anteproyectoDAO();
        ArrayList<anteproyectoDTO> temp = objAnteproyecto.listarAnteproyectos();
         anteproyectoDTO[] varColAnteproyectos = new anteproyectoDTO[temp.size()];
        for (int i = 0; i <= temp.size()-1; i++){
             System.out.println("listnado anddteproyectos");
            varColAnteproyectos[i]=temp.get(i);
            
            
        }
        for (int i = 0; i < varColAnteproyectos.length; i++) {
            varColAnteproyectos[i].atrIdCodirector="vacio";
            varColAnteproyectos[i].atrIdDirector="vacio";
            varColAnteproyectos[i].atrIdEstudiante1="vacio";
            varColAnteproyectos[i].atrIdEstudiante2="vacio";
            varColAnteproyectos[i].atrModalidad = "vacio";
            varColAnteproyectos[i].atrNumeroRevision=0;
        }
        System.out.println("listnado anteproyectos");
      return varColAnteproyectos;
    }
    

    @Override
    public anteproyectoDTO buscarAnteproyecto(int parCodigo) {
       System.out.println("listnado anteproyectos");
        anteproyectoDAO objAnteproyecto = new anteproyectoDAO();
        anteproyectoDTO obj =  objAnteproyecto.buscarAnteproyecto(parCodigo);
            obj.atrIdCodirector="vacio";
            obj.atrIdDirector="vacio";
            obj.atrIdEstudiante1="vacio";
            obj.atrIdEstudiante2="vacio";
            obj.atrModalidad = "vacio";
            obj.atrNumeroRevision=0;
        
        System.out.println("listnado anteproyectos");
        
        
      return obj;
    
    
    }
    
    
}
