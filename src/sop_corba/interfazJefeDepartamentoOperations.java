package sop_corba;


/**
* sop_corba/interfazJefeDepartamentoOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 11:41:27 PM COT
*/

public interface interfazJefeDepartamentoOperations 
{
  boolean RegistarAnteProyectos (sop_corba.anteproyectoDTO parObjAnteproyectoDTO);
  boolean modificarEstadoAnteproyecto (int parCodigo, int parEstado);
  boolean RegistrarUsuario (sop_corba.UsuarioDTO parObjUsuario);
  boolean AsignarEvaluadores (sop_corba.clsAsigEvaluadoresDTO objAsigEvaluadores);
} // interface interfazJefeDepartamentoOperations
