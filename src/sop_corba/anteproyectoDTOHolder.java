package sop_corba;

/**
* sop_corba/anteproyectoDTOHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:02 PM COT
*/

public final class anteproyectoDTOHolder implements org.omg.CORBA.portable.Streamable
{
  public sop_corba.anteproyectoDTO value = null;

  public anteproyectoDTOHolder ()
  {
  }

  public anteproyectoDTOHolder (sop_corba.anteproyectoDTO initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = sop_corba.anteproyectoDTOHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    sop_corba.anteproyectoDTOHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return sop_corba.anteproyectoDTOHelper.type ();
  }

}