package cliente;
import sop_corba.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;


public class ClienteDeObjetos{
    //*** Atributo estático ***
    //static interfazAnteProyectosOperations ref;
    static interfazJefeDepartamentoOperations ref_servicios_jefe;
    static interfazUsuarioOperations ref_servicios_IniciarSesion;
    public static void main(String args[]){
        try{
            // crea e inicia el ORB
            // crea e iniciia el ORB
            String[] vector = new String[]{"-ORBInitialHost", "localhost", "-ORBInitialPort", "2020"};
            ORB orb = ORB.init(vector, null);

            // obtiene la base del naming context
            org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
            
            //->>>>>>>>>>>>>>>>>>>>>>>>>>>para REgistra usuario>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            ref_servicios_jefe = interfazJefeDepartamentoHelper.narrow(ncRef.resolve_str("ServiciosJefe"));
            UsuarioDTO obj = new UsuarioDTO("100", "rene","jalvin", "rene","1299",1);
            //ref_servicios_jefe.RegistrarUsuario(obj);             //modalidad//titulo//idanterpoyecto//idcoordiandor//
            //->>>>>>>>>>>>>>>>>>>>>>>>>>>>>para registar anteproyecto>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            //anteproyectoDTO objAnteproyecto = new anteproyectoDTO(_atrModalidad, _atrTitulo,codigoAnteproyecto, _atrIdEstudiante1, _atrIdEstudiante2, _atrIdDirector, _atrIdCodirector, _atrFechaRegistro, _atrFechaAprobacion,concepto,estado,numRevicion);
            anteproyectoDTO objAnteproyecto = new anteproyectoDTO("mod",              "cor",          85,               "15",               "2",               "3",           "21",           "12/12/12",         "12/12/12",         2,       2,    145);
            ref_servicios_jefe.RegistarAnteProyectos(objAnteproyecto);
            //->
           
            //->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>para iniciar sesion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
             org.omg.CORBA.Object objRefIniciarSesion = orb.resolve_initial_references("NameService");
            NamingContextExt ncRefIniciarSesion = NamingContextExtHelper.narrow(objRefIniciarSesion);
            ref_servicios_IniciarSesion = interfazUsuarioHelper.narrow(ncRefIniciarSesion.resolve_str("IniciarSesion"));
            //int a = ref_servicios_IniciarSesion.IniciarSesion("rene", "1299");
            //System.out.println("tipo usuario : "+a);
            //->>>>>>>>>>>>>>>>>>>>>>>>>>>>>para registrar asignar evaluadores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            
            
            }catch (Exception e) {
          System.out.println("ERROR : " + e) ;
          e.printStackTrace(System.out);
        }
    }
}

           
/*
            int opcion = 0, estado = 0, consultarEstado = 0;
            boolean bandera = false;
            String titulo = "", estudiante = "", consultarTitulo = "", consultarNombreEstudiante = "";
            do{
                System.out.println("==Menu==");
                System.out.println(" 1. Registrar anteproyecto.");
                System.out.println(" 2. Consultar anteproyecto.");
                System.out.println(" 3. Salir");

                opcion = UtilidadesConsola.leerEntero();

                switch (opcion) {
                    case 1:
                        System.out.println("Digite el titulo del anteproyecto: ");
                        titulo = UtilidadesConsola.leerCadena();
                        System.out.println("Digite el nombre del estudiante: ");
                        estudiante = UtilidadesConsola.leerCadena();
                        do{
                          System.out.println("Digite el estado del anteproyecto: ");
                          System.out.println("1. Sin asignacion.");
                          System.out.println("2. Evaluadores asignados.");
                          System.out.println("3. En revision.");
                          System.out.println("4. Evaluado.");
                          estado = UtilidadesConsola.leerEntero();
                        }while(estado == 0 || estado < 1 || estado > 4);


                        if (ref.registrarAnteproyecto(titulo, estudiante, estado)) {
                            System.out.println("Registro realizado con éxito");
                        }else{
                            System.out.println("no se realizó el registro");
                        }
                        break;
                    case 2:
                        consultarTitulo = ref.consultarTitulo();
                        consultarNombreEstudiante = ref.consultarEstudiante();
                        consultarEstado = ref.consultarEstado();
                        String cadenaConsultarEstado = "";
                        switch(consultarEstado){
                          case 1:
                            cadenaConsultarEstado = "Sin asignacion.";
                          break;
                          case 2:
                            cadenaConsultarEstado = "Evaluadores asignados.";
                          break;
                          case 3:
                            cadenaConsultarEstado = "En revision.";
                          break;
                          case 4:
                            cadenaConsultarEstado = "Evaluado";
                          break;
                          default:
                            System.out.println("Opción incorrecta");
                        }
                          System.out.println("Datos del anteproyecto ");
                          System.out.println("\n TITULO: " + consultarTitulo);
                          System.out.println(" ESTUDIANTE: " + consultarNombreEstudiante);
                          System.out.println(" ESTADO: " + cadenaConsultarEstado);
                        break;
                    case 3:
                        System.out.println("Salir...");
                        break;
                    default:
                        System.out.println("Opción incorrecta");
                }
            }while (opcion != 4);

        }catch (Exception e) {
          System.out.println("ERROR : " + e) ;
          e.printStackTrace(System.out);
        }
    }
}
*/
