package sop_corba;


/**
* sop_corba/interfazEstudiantesDirectorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 04:57:33 AM COT
*/

public abstract class interfazEstudiantesDirectorPOA extends org.omg.PortableServer.Servant
 implements sop_corba.interfazEstudiantesDirectorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("listarAnteProyectos", new java.lang.Integer (0));
    _methods.put ("buscarAnteproyecto", new java.lang.Integer (1));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // sop_corba/interfazEstudiantesDirector/listarAnteProyectos
       {
         sop_corba.anteproyectoDTO $result[] = null;
         $result = this.listarAnteProyectos ();
         out = $rh.createReply();
         sop_corba.interfazEstudiantesDirectorPackage.ListAnteproyectosHelper.write (out, $result);
         break;
       }

       case 1:  // sop_corba/interfazEstudiantesDirector/buscarAnteproyecto
       {
         int parCodigo = in.read_long ();
         sop_corba.anteproyectoDTO $result = null;
         $result = this.buscarAnteproyecto (parCodigo);
         out = $rh.createReply();
         sop_corba.anteproyectoDTOHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/interfazEstudiantesDirector:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public interfazEstudiantesDirector _this() 
  {
    return interfazEstudiantesDirectorHelper.narrow(
    super._this_object());
  }

  public interfazEstudiantesDirector _this(org.omg.CORBA.ORB orb) 
  {
    return interfazEstudiantesDirectorHelper.narrow(
    super._this_object(orb));
  }


} // class interfazEstudiantesDirectorPOA
