package sop_corba;

/**
* sop_corba/UsuarioDTOHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 11:41:26 PM COT
*/

public final class UsuarioDTOHolder implements org.omg.CORBA.portable.Streamable
{
  public sop_corba.UsuarioDTO value = null;

  public UsuarioDTOHolder ()
  {
  }

  public UsuarioDTOHolder (sop_corba.UsuarioDTO initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = sop_corba.UsuarioDTOHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    sop_corba.UsuarioDTOHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return sop_corba.UsuarioDTOHelper.type ();
  }

}
