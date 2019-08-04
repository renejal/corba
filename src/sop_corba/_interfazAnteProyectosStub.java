package sop_corba;


/**
* sop_corba/_interfazAnteProyectosStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 11:41:26 PM COT
*/

public class _interfazAnteProyectosStub extends org.omg.CORBA.portable.ObjectImpl implements sop_corba.interfazAnteProyectos
{

  public boolean registrarAnteproyecto (sop_corba.anteproyectoDTO objAnteproyecto)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("registrarAnteproyecto", true);
                sop_corba.anteproyectoDTOHelper.write ($out, objAnteproyecto);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return registrarAnteproyecto (objAnteproyecto        );
            } finally {
                _releaseReply ($in);
            }
  } // registrarAnteproyecto

  public sop_corba.anteproyectoDTO buscarAnteproyecto (String titulo)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("buscarAnteproyecto", true);
                $out.write_string (titulo);
                $in = _invoke ($out);
                sop_corba.anteproyectoDTO $result = sop_corba.anteproyectoDTOHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return buscarAnteproyecto (titulo        );
            } finally {
                _releaseReply ($in);
            }
  } // buscarAnteproyecto

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:sop_corba/interfazAnteProyectos:1.0"};

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
} // class _interfazAnteProyectosStub
