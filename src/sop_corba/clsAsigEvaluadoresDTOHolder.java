package sop_corba;

/**
* sop_corba/clsAsigEvaluadoresDTOHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 08:20:03 PM COT
*/

public final class clsAsigEvaluadoresDTOHolder implements org.omg.CORBA.portable.Streamable
{
  public sop_corba.clsAsigEvaluadoresDTO value = null;

  public clsAsigEvaluadoresDTOHolder ()
  {
  }

  public clsAsigEvaluadoresDTOHolder (sop_corba.clsAsigEvaluadoresDTO initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = sop_corba.clsAsigEvaluadoresDTOHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    sop_corba.clsAsigEvaluadoresDTOHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return sop_corba.clsAsigEvaluadoresDTOHelper.type ();
  }

}
