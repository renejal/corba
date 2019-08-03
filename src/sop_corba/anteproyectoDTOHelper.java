package sop_corba;


/**
* sop_corba/anteproyectoDTOHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* viernes 2 de agosto de 2019 11:25:30 PM COT
*/

abstract public class anteproyectoDTOHelper
{
  private static String  _id = "IDL:sop_corba/anteproyectoDTO:1.0";

  public static void insert (org.omg.CORBA.Any a, sop_corba.anteproyectoDTO that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static sop_corba.anteproyectoDTO extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [13];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "atrModalidad",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "atrTitulo",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "atrCodigo",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "atrIdEstudiante1",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "atrIdEstudiante2",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[5] = new org.omg.CORBA.StructMember (
            "atrIdDirector",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[6] = new org.omg.CORBA.StructMember (
            "atrIdCodirector",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[7] = new org.omg.CORBA.StructMember (
            "atrFechaRegistro",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[8] = new org.omg.CORBA.StructMember (
            "atrFechaAprobacion",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[9] = new org.omg.CORBA.StructMember (
            "atrConcepto",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[10] = new org.omg.CORBA.StructMember (
            "atrEstado",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[11] = new org.omg.CORBA.StructMember (
            "atrNumeroRevision",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[12] = new org.omg.CORBA.StructMember (
            "atrRolAnteproyecto",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (sop_corba.anteproyectoDTOHelper.id (), "anteproyectoDTO", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static sop_corba.anteproyectoDTO read (org.omg.CORBA.portable.InputStream istream)
  {
    sop_corba.anteproyectoDTO value = new sop_corba.anteproyectoDTO ();
    value.atrModalidad = istream.read_string ();
    value.atrTitulo = istream.read_string ();
    value.atrCodigo = istream.read_long ();
    value.atrIdEstudiante1 = istream.read_string ();
    value.atrIdEstudiante2 = istream.read_string ();
    value.atrIdDirector = istream.read_string ();
    value.atrIdCodirector = istream.read_string ();
    value.atrFechaRegistro = istream.read_string ();
    value.atrFechaAprobacion = istream.read_string ();
    value.atrConcepto = istream.read_long ();
    value.atrEstado = istream.read_long ();
    value.atrNumeroRevision = istream.read_long ();
    value.atrRolAnteproyecto = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, sop_corba.anteproyectoDTO value)
  {
    ostream.write_string (value.atrModalidad);
    ostream.write_string (value.atrTitulo);
    ostream.write_long (value.atrCodigo);
    ostream.write_string (value.atrIdEstudiante1);
    ostream.write_string (value.atrIdEstudiante2);
    ostream.write_string (value.atrIdDirector);
    ostream.write_string (value.atrIdCodirector);
    ostream.write_string (value.atrFechaRegistro);
    ostream.write_string (value.atrFechaAprobacion);
    ostream.write_long (value.atrConcepto);
    ostream.write_long (value.atrEstado);
    ostream.write_long (value.atrNumeroRevision);
    ostream.write_long (value.atrRolAnteproyecto);
  }

}
