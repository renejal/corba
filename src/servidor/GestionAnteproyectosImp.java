
package servidor;

import sop_corba.interfazAnteProyectosPOA;

public class GestionAnteproyectosImp extends interfazAnteProyectosPOA{

    @Override
    public boolean registrarAnteproyecto(sop_corba.anteproyectoDTO objAnteproyecto) {
        System.out.println("registrando anteproyecto");
        return true;
    }

    @Override
    public sop_corba.anteproyectoDTO buscarAnteproyecto(String titulo) {
        System.out.println("buscando anteproyecto"+ titulo + "hola");
        return null;
    }

   
    
}
