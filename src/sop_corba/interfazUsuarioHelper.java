package sop_corba;


/**
* sop_corba/interfazUsuarioHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:03 PM COT
*/

abstract public class interfazUsuarioHelper
{
  private static String  _id = "IDL:sop_corba/interfazUsuario:1.0";

  public static void insert (org.omg.CORBA.Any a, sop_corba.interfazUsuario that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static sop_corba.interfazUsuario extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (sop_corba.interfazUsuarioHelper.id (), "interfazUsuario");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static sop_corba.interfazUsuario read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_interfazUsuarioStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, sop_corba.interfazUsuario value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static sop_corba.interfazUsuario narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof sop_corba.interfazUsuario)
      return (sop_corba.interfazUsuario)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      sop_corba._interfazUsuarioStub stub = new sop_corba._interfazUsuarioStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static sop_corba.interfazUsuario unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof sop_corba.interfazUsuario)
      return (sop_corba.interfazUsuario)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      sop_corba._interfazUsuarioStub stub = new sop_corba._interfazUsuarioStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
