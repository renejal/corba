package sop_corba;


/**
* sop_corba/interfazAnteProyectosOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interfazAnteproyecto.idl
* s�bado 3 de agosto de 2019 04:57:33 AM COT
*/

public interface interfazAnteProyectosOperations 
{
  boolean registrarAnteproyecto (sop_corba.anteproyectoDTO objAnteproyecto);
  sop_corba.anteproyectoDTO buscarAnteproyecto (String titulo);
} // interface interfazAnteProyectosOperations
