package sop_corba;

/**
* sop_corba/interfazJefeDepartamentoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:03 PM COT
*/

public final class interfazJefeDepartamentoHolder implements org.omg.CORBA.portable.Streamable
{
  public sop_corba.interfazJefeDepartamento value = null;

  public interfazJefeDepartamentoHolder ()
  {
  }

  public interfazJefeDepartamentoHolder (sop_corba.interfazJefeDepartamento initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = sop_corba.interfazJefeDepartamentoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    sop_corba.interfazJefeDepartamentoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return sop_corba.interfazJefeDepartamentoHelper.type ();
  }

}