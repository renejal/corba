package sop_corba;


/**
* sop_corba/_interfazEstudiantesDirectorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:03 PM COT
*/

public class _interfazEstudiantesDirectorStub extends org.omg.CORBA.portable.ObjectImpl implements sop_corba.interfazEstudiantesDirector
{

  public sop_corba.anteproyectoDTO[] listarAnteProyectos ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listarAnteProyectos", true);
                $in = _invoke ($out);
                sop_corba.anteproyectoDTO $result[] = sop_corba.interfazEstudiantesDirectorPackage.ListAnteproyectosHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return listarAnteProyectos (        );
            } finally {
                _releaseReply ($in);
            }
  } // listarAnteProyectos

  public sop_corba.anteproyectoDTO buscarAnteproyecto (int parCodigo)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("buscarAnteproyecto", true);
                $out.write_long (parCodigo);
                $in = _invoke ($out);
                sop_corba.anteproyectoDTO $result = sop_corba.anteproyectoDTOHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return buscarAnteproyecto (parCodigo        );
            } finally {
                _releaseReply ($in);
            }
  } // buscarAnteproyecto

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/interfazEstudiantesDirector:1.0"};

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
} // class _interfazEstudiantesDirectorStub