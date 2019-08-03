package sop_corba;


/**
* sop_corba/_interfazJefeDepartamentoStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:03 PM COT
*/

public class _interfazJefeDepartamentoStub extends org.omg.CORBA.portable.ObjectImpl implements sop_corba.interfazJefeDepartamento
{

  public boolean RegistarAnteProyectos (sop_corba.anteproyectoDTO parObjAnteproyectoDTO)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("RegistarAnteProyectos", true);
                sop_corba.anteproyectoDTOHelper.write ($out, parObjAnteproyectoDTO);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return RegistarAnteProyectos (parObjAnteproyectoDTO        );
            } finally {
                _releaseReply ($in);
            }
  } // RegistarAnteProyectos

  public boolean modificarEstadoAnteproyecto (int parCodigo, int parEstado)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("modificarEstadoAnteproyecto", true);
                $out.write_long (parCodigo);
                $out.write_long (parEstado);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return modificarEstadoAnteproyecto (parCodigo, parEstado        );
            } finally {
                _releaseReply ($in);
            }
  } // modificarEstadoAnteproyecto

  public boolean RegistrarUsuario (sop_corba.UsuarioDTO parObjUsuario)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("RegistrarUsuario", true);
                sop_corba.UsuarioDTOHelper.write ($out, parObjUsuario);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return RegistrarUsuario (parObjUsuario        );
            } finally {
                _releaseReply ($in);
            }
  } // RegistrarUsuario

  public boolean AsignarEvaluadores (sop_corba.clsAsigEvaluadoresDTO objAsigEvaluadores)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("AsignarEvaluadores", true);
                sop_corba.clsAsigEvaluadoresDTOHelper.write ($out, objAsigEvaluadores);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return AsignarEvaluadores (objAsigEvaluadores        );
            } finally {
                _releaseReply ($in);
            }
  } // AsignarEvaluadores

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/interfazJefeDepartamento:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _interfazJefeDepartamentoStub
