package sop_corba;


/**
* sop_corba/interfazAnteProyectosPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 04:57:33 AM COT
*/

public abstract class interfazAnteProyectosPOA extends org.omg.PortableServer.Servant
 implements sop_corba.interfazAnteProyectosOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("registrarAnteproyecto", new java.lang.Integer (0));
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
       case 0:  // sop_corba/interfazAnteProyectos/registrarAnteproyecto
       {
         sop_corba.anteproyectoDTO objAnteproyecto = sop_corba.anteproyectoDTOHelper.read (in);
         boolean $result = false;
         $result = this.registrarAnteproyecto (objAnteproyecto);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // sop_corba/interfazAnteProyectos/buscarAnteproyecto
       {
         String titulo = in.read_string ();
         sop_corba.anteproyectoDTO $result = null;
         $result = this.buscarAnteproyecto (titulo);
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
    "IDL:sop_corba/interfazAnteProyectos:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public interfazAnteProyectos _this() 
  {
    return interfazAnteProyectosHelper.narrow(
    super._this_object());
  }

  public interfazAnteProyectos _this(org.omg.CORBA.ORB orb) 
  {
    return interfazAnteProyectosHelper.narrow(
    super._this_object(orb));
  }


} // class interfazAnteProyectosPOA
