package sop_corba;


/**
* sop_corba/interfazJefeDepartamentoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 05:26:42 AM COT
*/

abstract public class interfazJefeDepartamentoHelper
{
  private static String  _id = "IDL:sop_corba/interfazJefeDepartamento:1.0";

  public static void insert (org.omg.CORBA.Any a, sop_corba.interfazJefeDepartamento that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static sop_corba.interfazJefeDepartamento extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (sop_corba.interfazJefeDepartamentoHelper.id (), "interfazJefeDepartamento");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static sop_corba.interfazJefeDepartamento read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_interfazJefeDepartamentoStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, sop_corba.interfazJefeDepartamento value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static sop_corba.interfazJefeDepartamento narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof sop_corba.interfazJefeDepartamento)
      return (sop_corba.interfazJefeDepartamento)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      sop_corba._interfazJefeDepartamentoStub stub = new sop_corba._interfazJefeDepartamentoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static sop_corba.interfazJefeDepartamento unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof sop_corba.interfazJefeDepartamento)
      return (sop_corba.interfazJefeDepartamento)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      sop_corba._interfazJefeDepartamentoStub stub = new sop_corba._interfazJefeDepartamentoStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
