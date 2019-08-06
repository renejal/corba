package cliente.utilidades;

import sop_corba.interfazEvaluador;
import sop_corba.interfazJefeDepartamento;
import sop_corba.interfazJefeDepartamentoHelper;
import sop_corba.interfazEvaluadorHelper;
import sop_corba.interfazUsuarioHelper;
import sop_corba.interfazUsuario;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import src.cliente.vistas.Login;

public class ClienteDeObjetos{
    //*** Atributo estático ***
    //static interfazAnteProyectosOperations ref;
    static interfazJefeDepartamento ref_servicios_jefe;
    static interfazUsuario ref_servicios_iniciarSesion;
    static interfazEvaluador ref_asignar_evaluadores;
    
    public static void main(String args[]){
        try{
            // crea e iniciia el ORB
            String[] vector = new String[]{"-ORBInitialHost", "localhost", "-ORBInitialPort", "2020"};
            ORB orb = ORB.init(vector, null);

            // obtiene la base del naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            // *** Resuelve la referencia del objeto en el N_S ***
            ref_servicios_jefe = interfazJefeDepartamentoHelper.narrow(ncRef.resolve_str("serviciosJefe"));
            
            //org.omg.CORBA.Object objRefIniciarSesion = orb.resolve_initial_references("NameService");
            //NamingContextExt ncRefIniciarSesion = NamingContextExtHelper.narrow(objRefIniciarSesion);
            ref_servicios_iniciarSesion = interfazUsuarioHelper.narrow(ncRef.resolve_str("IniciarSesion"));

            org.omg.CORBA.Object objRefAsignarEvaluadores = orb.resolve_initial_references("NameService");
            NamingContextExt ncRefAsignarEvaluadores = NamingContextExtHelper.narrow(objRefAsignarEvaluadores);
            ref_asignar_evaluadores = interfazEvaluadorHelper.narrow(ncRef.resolve_str("serviciosEvaluador"));
            
            Login objPrincipal = new Login(ref_servicios_iniciarSesion);
            objPrincipal.setVisible(true);
            
        }catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
