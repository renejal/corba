package sop_corba;

/**
* sop_corba/interfazAnteProyectosHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 11:41:26 PM COT
*/

public final class interfazAnteProyectosHolder implements org.omg.CORBA.portable.Streamable
{
  public sop_corba.interfazAnteProyectos value = null;

  public interfazAnteProyectosHolder ()
  {
  }

  public interfazAnteProyectosHolder (sop_corba.interfazAnteProyectos initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = sop_corba.interfazAnteProyectosHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    sop_corba.interfazAnteProyectosHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return sop_corba.interfazAnteProyectosHelper.type ();
  }

}
