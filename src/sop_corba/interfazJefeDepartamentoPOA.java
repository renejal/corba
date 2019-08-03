package sop_corba;


/**
* sop_corba/interfazJefeDepartamentoPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 11:25:31 PM COT
*/

public abstract class interfazJefeDepartamentoPOA extends org.omg.PortableServer.Servant
 implements sop_corba.interfazJefeDepartamentoOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("RegistarAnteProyectos", new java.lang.Integer (0));
    _methods.put ("modificarEstadoAnteproyecto", new java.lang.Integer (1));
    _methods.put ("RegistrarUsuario", new java.lang.Integer (2));
    _methods.put ("AsignarEvaluadores", new java.lang.Integer (3));
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
       case 0:  // sop_corba/interfazJefeDepartamento/RegistarAnteProyectos
       {
         sop_corba.anteproyectoDTO parObjAnteproyectoDTO = sop_corba.anteproyectoDTOHelper.read (in);
         boolean $result = false;
         $result = this.RegistarAnteProyectos (parObjAnteproyectoDTO);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // sop_corba/interfazJefeDepartamento/modificarEstadoAnteproyecto
       {
         int parCodigo = in.read_long ();
         int parEstado = in.read_long ();
         boolean $result = false;
         $result = this.modificarEstadoAnteproyecto (parCodigo, parEstado);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // sop_corba/interfazJefeDepartamento/RegistrarUsuario
       {
         sop_corba.UsuarioDTO parObjUsuario = sop_corba.UsuarioDTOHelper.read (in);
         boolean $result = false;
         $result = this.RegistrarUsuario (parObjUsuario);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // sop_corba/interfazJefeDepartamento/AsignarEvaluadores
       {
         sop_corba.clsAsigEvaluadoresDTO objAsigEvaluadores = sop_corba.clsAsigEvaluadoresDTOHelper.read (in);
         boolean $result = false;
         $result = this.AsignarEvaluadores (objAsigEvaluadores);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/interfazJefeDepartamento:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public interfazJefeDepartamento _this() 
  {
    return interfazJefeDepartamentoHelper.narrow(
    super._this_object());
  }

  public interfazJefeDepartamento _this(org.omg.CORBA.ORB orb) 
  {
    return interfazJefeDepartamentoHelper.narrow(
    super._this_object(orb));
  }


} // class interfazJefeDepartamentoPOA
