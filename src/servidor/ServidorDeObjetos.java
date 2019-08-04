package servidor;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import sop_corba.*;

public class ServidorDeObjetos {

  public static void main(String args[]) {
    try{
      // crea e iniciia el ORB
       String[] vector = new String[]{"-ORBInitialHost", "localhost", "-ORBInitialPort", "2020"};
      ORB orb = ORB.init(vector, null);

      // obtiene la referencia del rootpoa y activa el POAManager
      POA rootpoa = 
      POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      //*** crea una instancia del servant y lo registra con el ORB ***
      jefeDepartamentoImp convref = new jefeDepartamentoImp();
      //***
      iniciarSesionImp IniciarSesionRef = new iniciarSesionImp();
      //**
     
       
      //*** obtiene la referencia del objeto desde el servant ***
      org.omg.CORBA.Object obj = rootpoa.servant_to_reference(convref);
      interfazJefeDepartamento href = interfazJefeDepartamentoHelper.narrow(obj);
      //
      org.omg.CORBA.Object objIniciarSesion = rootpoa.servant_to_reference(IniciarSesionRef);
      interfazUsuario hrefIniciarSesion = interfazUsuarioHelper.narrow(objIniciarSesion);
      // obtiene la base del contexto de nombrado
      org.omg.CORBA.Object objref =
          orb.resolve_initial_references("NameService");
      //_>
       org.omg.CORBA.Object objrefIniciarSesion =
          orb.resolve_initial_references("NameService");
      // Usa NamingContextExt el cual es parte de la especificacion 
      // del servicio de nombrado interoperable (INS).
      NamingContextExt ncref = NamingContextExtHelper.narrow(objref);
      NamingContextExt ncrefIniciarSesion = NamingContextExtHelper.narrow(objrefIniciarSesion);

      // *** Realiza el binding de la referencia de objeto en el N_S ***
      String name = "ServiciosJefe";
      NameComponent path[] = ncref.to_name( name );
      ncref.rebind(path, href);
      //->
      String name2 = "IniciarSesion";
      NameComponent path2[] = ncrefIniciarSesion.to_name( name2 );
      ncref.rebind(path2, hrefIniciarSesion);
    

      // esperan por las invocaciones desde los clientes
      //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>para modificar concepto>>>>>>>>>>>>>>>
      evaluadorImp convrefModificarConceptor = new evaluadorImp();
     
       org.omg.CORBA.Object objModConcepto = rootpoa.servant_to_reference(convrefModificarConceptor);
      interfazEvaluador hrefEvaluador = interfazEvaluadorHelper.narrow(objModConcepto);
      
      org.omg.CORBA.Object objrefEvaluador =
          orb.resolve_initial_references("NameService");
      
       NamingContextExt ncrefEvaludor = NamingContextExtHelper.narrow(objrefEvaluador);
      
      String nameEvaluador = "serviciosEvaluador";
      NameComponent path3[] = ncrefEvaludor.to_name( nameEvaluador );
      ncref.rebind(path3, hrefEvaluador);
      
      
     System.out.println("El Servidor esta listo y esperandosh ...");
      orb.run();
      
    } 
	
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
    
    
	  
      System.out.println("Servidor: Saliendo ...");
	
  }
}
