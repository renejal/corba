/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vistas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servidor.dto.AnteproyectoDTO;
import servidor.dto.UsuarioDTO;
import servidor.dto.clsAsigEvaluadoresDTO;
import servidor.sop_rmi.clsEstudianteDirector;
import servidor.sop_rmi.clsJefeDepartamento;
import servidor.sop_rmi.interfazEstudianteDirector;
import servidor.sop_rmi.interfazJefeDepartamento;

/**
 *
 * @author Angélica
 */
public final class FuncionesAdministrador extends javax.swing.JFrame {
    /**
     * Creates new form MenuAdministrador
     */
  
    // <editor-fold defaultstate="collapsed" desc="Constantes">
    private final static String APROBADO = "1. Aprobado";
    private final static String NO_APROBADO = "2. No aprobado";
    private final static String SIN_ASIGNACION = "1. Sin asignación";
    private final static String EVALUADORES_ASIGNADOS = "2. Evaluadores asignados";
    private final static String EN_REVISION = "3. En revisión";
    private final static String EVALUADO = "4. Evaluado";
    private final static String ESTUDIANTE = "1. Estudiante";
    private final static String DIRECTOR_CODIRECTOR = "2. Director - Codirector";
    private final static String EVALUADOR = "3. Evaluador";
    private final static String JEFE_DEPARTAMENTO = "4. Jefe de departamento";
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final interfazJefeDepartamento objJefeDepartamento;
    private final interfazEstudianteDirector objEstudianteDirector;
    
    DefaultTableModel modeloTablaCodigoTituloB;
    DefaultTableModel modeloTablaCodigoTituloL;
    DefaultTableModel modeloDatosAnteproyecto;
    
    String direccionIpRMIRegistry = "localhost";
    int numPuertoRMIRegistry = 3032;
    
    InterfazIniciarSesion objInterLogin = null;
    
    String nombreColumnasAnteproyecto[] = {"MODALIDAD", "TITULO", "CÓDIGO", "ESTUDIANTE 1", 
                                           "ESTUDIANTE 2", "DIRECTOR", "CODIRECTOR", 
                                           "FECHA DE REGISTRO", "FECHA DE APROBACIÓN", 
                                           "CONCEPTO", "ESTADO", "NUMERO DE REVISIÓN"};
    String nombreColumnasCodigoTitutlo[] = {"CÓDIGO", "TITULO"};
    String rutaAnteproyecto = "src/cliente/utilidades/archivos/anteProyectos.txt";
    String rutaAnteproyectoCodigoTitulo = "src/cliente/utilidades/archivos/infoAnteproyectosCodigoTitulo.txt";
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public FuncionesAdministrador() throws RemoteException, IOException {
        this.modeloTablaCodigoTituloB = new DefaultTableModel();
        this.modeloTablaCodigoTituloL = new DefaultTableModel();
        this.modeloDatosAnteproyecto = new DefaultTableModel();
        agregarColumnasAModelo(modeloDatosAnteproyecto, 12, nombreColumnasAnteproyecto);
        agregarColumnasAModelo(modeloTablaCodigoTituloL, 2, nombreColumnasCodigoTitutlo);
        cargarInfoAnteproyectos(modeloTablaCodigoTituloB, rutaAnteproyectoCodigoTitulo, 2, nombreColumnasCodigoTitutlo);
        initComponents();
        this.objJefeDepartamento = new clsJefeDepartamento();
        this.objEstudianteDirector = new clsEstudianteDirector();
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Métodos del administrador">
    private void asignarEvaluadores() throws RemoteException{
        boolean resultado = false;
        int codigoAnteproyecto = Integer.parseInt(this.jtfCodigoAA.getText());
        String nombreEvaluador1 = this.jtfNombrePrEvaluador.getText();
        String concepto1 = (String)this.cbTiposConceptos1.getSelectedItem();
        String fechaRevision1 = this.jtfFechaPrRevision.getText();
        String nombreEvaluador2 = this.jtfNombreSeEvaluador.getText();
        String concepto2 = (String)this.cbTiposConceptos2.getSelectedItem();
        String fechaRevision2 = this.jtfFechaSeRevision.getText();
        clsAsigEvaluadoresDTO objAsignarEvaluador = new clsAsigEvaluadoresDTO(codigoAnteproyecto, 
                                                                               nombreEvaluador1, concepto1, fechaRevision1,
                                                                               nombreEvaluador2, concepto2, fechaRevision2);
        resultado = this.objJefeDepartamento.AsignarEvaluadores(objAsignarEvaluador);
        this.mostrarResultadoDeAsignar(resultado, codigoAnteproyecto);
    }
    private void buscarAnteproyecto() throws RemoteException{
        boolean resultado = false;
        int codigoAnteproyecto = Integer.parseInt(this.jtfCodigoAB.getText());
        AnteproyectoDTO objAnteproyecto = new AnteproyectoDTO();
        objAnteproyecto = this.objEstudianteDirector.buscarAnteproyecto(codigoAnteproyecto);
        if(objAnteproyecto != null){
            this.mostrarDatosEnTabla(objAnteproyecto);
            resultado = true;
        }
        mostrarResultadoDeBuscar(resultado, codigoAnteproyecto);
    }
    private void modificarConcepto() throws RemoteException{
        boolean resultado = false;
        int codigo, concepto;
        codigo = Integer.parseInt(this.jtfCodigoAM.getText());
        String cadenaConcepto = this.cbConceptosM.getSelectedItem().toString().trim();
        concepto = Integer.parseInt("" +cadenaConcepto.charAt(0));
        resultado = this.objJefeDepartamento.modificarConceptoAnteproyecto(codigo, concepto);
        mostrarResultadoDeModificar(resultado, codigo);
    }
    private void registrarAnteproyecto() throws RemoteException{
        String modalidad = "", titulo = "", nombreEstudiante1 = "", nombreEstudiante2 = "", 
               nombreDirector = "", nombreCodirector = "", fechaRegistro = "", fechaAprobacion = "";
        int codigo = 0, concepto = 0, estado = 0, numeroRevision = 0;
        boolean resultado = false;
        modalidad = this.cbTiposModalidades.getSelectedItem().toString().trim();
        titulo = this.jtfTitulo.getText().trim();
        codigo = Integer.parseInt(this.jtfCodigoAR.getText().trim());
        nombreEstudiante1 = this.jtfNombrePrEstudiante.getText().trim();
        nombreEstudiante2 = this.jtfNombreSeEstudiante.getText().trim();
        nombreDirector = this.jtfNombreDirector.getText().trim();
        nombreCodirector = this.jtfNombreCodirector.getText().trim();
        fechaRegistro = this.jtfFechaRegistro.getText().trim();
        fechaAprobacion = this.jtfFechaAprobacion.getText().trim();
        concepto = this.obtenerCampoConcepto();
        estado = this.obtenerCampoEstado();
        numeroRevision = Integer.parseInt(this.jtfNroRevision.getText().trim());
        AnteproyectoDTO objAnteproyecto = new AnteproyectoDTO(modalidad, titulo, codigo, nombreEstudiante1,
                                                              nombreEstudiante2, nombreDirector, nombreCodirector,
                                                              fechaRegistro, fechaAprobacion, concepto, estado, numeroRevision);
        resultado = this.objJefeDepartamento.RegistarAnteProyectos(objAnteproyecto);
        mostrarResultadoDeRegistrarAnt(resultado, codigo);
    }
    private void registrarUsuario() throws RemoteException{
        String nombre = "", apellido = "", usuario = "", contrasenia = "";
        int ID = 0, tipoDeUsuario = 0;
        boolean resultado = false;
        
        ID = Integer.parseInt(this.jtfID.getText().trim());
        nombre = this.jtfNombre.getText().trim();
        apellido = this.jtfApellido.getText().trim();
        usuario = this.jtfUsuario.getText().trim();
        contrasenia = this.jtfContrasenia.getText().trim();
        tipoDeUsuario = obtenerCampoTipoUsuario();
        
        UsuarioDTO objUsuario = new UsuarioDTO(ID, nombre, apellido, usuario,
                                                    contrasenia, tipoDeUsuario);
        resultado = this.objJefeDepartamento.RegistrarUsuario(objUsuario);
        mostrarResultadoDeRegistrarUsuario(resultado, ID);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos para mostrar un mensaje del resultado de las operaciones">
    private void mostrarResultadoDeAsignar(boolean pResultado, int pCodigoAnteproyecto) {
        if(pResultado){
            this.lblInfoResultado1.setText("EXITO. Se asignaron los evaluadores al proyecto " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
        }else{
            this.lblInfoResultado1.setText("FALLO. No se asignaron los evaluadores al proyecto " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
        }
    }
    private void mostrarResultadoDeBuscar(boolean pResultado, int pCodigoAnteproyecto) {
        if(pResultado){
            this.lblInfoResultado2.setText("EXITO. Anteproyecto encontrado con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado2.setVisible(true);
        }else{
            this.lblInfoResultado2.setText("FALLO. No se encontró el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado2.setVisible(true);
        }
    }
    private void mostrarResultadoDeModificar(boolean pResultado, int pCodigoAnteproyecto) {
        if(pResultado){
            this.lblInfoResultado9.setText("EXITO. Se modificó el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado9.setVisible(true);
        }else{
            this.lblInfoResultado9.setText("FALLO. No se modificó el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado9.setVisible(true);
        }
    }
    private void mostrarResultadoDeRegistrarAnt(boolean pResultado, int pCodigoAnteproyecto){
        if(pResultado){
            this.lblInfoResultado10.setText("EXITO. Se registró el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado10.setVisible(true);
        }else{
            this.lblInfoResultado10.setText("FALLO. No se registró el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado10.setVisible(true);
        }
    }
    public void mostrarResultadoDeRegistrarUsuario(boolean pResultado, int pIDUsuario){
        if(pResultado){
            this.lblInfoResultado11.setText("EXITO. Se registró el usuario con ID " + pIDUsuario + ".");
            this.lblInfoResultado11.setVisible(true);
        }else{
            this.lblInfoResultado11.setText("FALLO. No se registró el usuario con ID " + pIDUsuario + ".");
            this.lblInfoResultado11.setVisible(true);
        }
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Métodos sobre las tablas de datos.">
    
    public void agregarColumnasAModelo(DefaultTableModel parModelo, int nroColumnas, String[] vectorColumnas){
            for(int j = 0; j<nroColumnas; j++){
            parModelo.addColumn(vectorColumnas[j]);
        }
    }
    
    public void cargarInfoAnteproyectos(DefaultTableModel parModelo, String ruta, int nroColumnas, String[] vectorColumnas) 
                                        throws FileNotFoundException, IOException {
        int i = 0;
        String linea = "";
        ArrayList<String> varColAnteproyectos = new ArrayList<>();
        Object[] LineaAnteproyecto = new Object[nroColumnas];
        FileReader f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);
        while(( linea = b.readLine() ) != null) {
            varColAnteproyectos.add(linea);
            for(String objLineaAnteproyecto : varColAnteproyectos){
                LineaAnteproyecto = objLineaAnteproyecto.split(";");
            }
            parModelo.addRow(LineaAnteproyecto);
            i++;
        }
        if(varColAnteproyectos.isEmpty()){
            System.out.println("Archivo Vacio");
        }
        b.close();   
    }

    private void mostrarDatosEnTabla(AnteproyectoDTO pObjAnteproyecto) {
        Object[] objDatosAnteproyecto;
        objDatosAnteproyecto = new Object[]{
            pObjAnteproyecto.getAtrModalidad(), pObjAnteproyecto.getAtrTitulo(),
            pObjAnteproyecto.getAtrCodigo(), pObjAnteproyecto.getAtrNombreEstudiante1(),
            pObjAnteproyecto.getAtrNombreEstudiante2(), pObjAnteproyecto.getAtrNombreDirector(),
            pObjAnteproyecto.getAtrNombreCodirector(), pObjAnteproyecto.getAtrFechaRegistro(),
            pObjAnteproyecto.getAtrFechaAprobacion(), pObjAnteproyecto.getAtrConcepto(),
            pObjAnteproyecto.getAtrEstado(), pObjAnteproyecto.getAtrNumeroRevision()
        };
        this.modeloDatosAnteproyecto.addRow(objDatosAnteproyecto);
    }
    private void abrirVentanaLogin(){
         try {
            this.objInterLogin = new InterfazIniciarSesion();
        } catch (RemoteException ex) {
            Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        this.dispose();
        this.objInterLogin.setVisible(true);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Métodos sobre los campos de texto.">
    private void habilitarCamposEnAsignarEvaluadores(){
        this.jtfNombrePrEvaluador.setEditable(true);
        this.cbTiposConceptos1.setEnabled(true);
        this.jtfFechaPrRevision.setEditable(true);
        this.jtfNombreSeEvaluador.setEditable(true);
        this.cbTiposConceptos2.setEnabled(true);
        this.jtfFechaSeRevision.setEditable(true);
    }
    private void limpiarCamposEnAsignarEvaluadores(){
        this.jtfCodigoAA.setText("");
        this.jtfNombrePrEvaluador.setText("");
        this.cbTiposConceptos1.setSelectedIndex(1);
        this.jtfFechaPrRevision.setText("");
        this.jtfNombreSeEvaluador.setText("");
        this.cbTiposConceptos2.setSelectedIndex(1);
        this.jtfFechaSeRevision.setText("");
         this.jtfNombrePrEvaluador.setEditable(false);
        this.cbTiposConceptos1.setEnabled(false);
        this.jtfFechaPrRevision.setEditable(false);
        this.jtfNombreSeEvaluador.setEditable(false);
        this.cbTiposConceptos2.setEnabled(false);
        this.jtfFechaSeRevision.setEditable(false);
    }
    public void limpiarCamposEnRegistrarAnt(){
        this.jtfCodigoAR.setText("");
        this.cbTiposModalidades.setSelectedIndex(0);
        this.jtfTitulo.setText("");
        this.jtfNombrePrEstudiante.setText("");
        this.jtfNombreSeEstudiante.setText("");
        this.jtfNombreDirector.setText("");
        this.jtfNombreCodirector.setText("");
        this.jtfFechaRegistro.setText("");
        this.jtfFechaAprobacion.setText("");
        this.cbTiposConceptos.setSelectedIndex(1);
        this.cbTiposEstados.setSelectedIndex(0);      
        this.jtfNroRevision.setText("");
    }
    
    public void limpiarCamposEnRegistrarUsuario(){
        this.jtfID.setText("");
        this.jtfNombre.setText("");
        this.jtfApellido.setText("");
        this.jtfUsuario.setText("");
        this.jtfContrasenia.setText("");
        this.cbTiposUsuarios.setSelectedIndex(0);
    }
    public boolean hayCamposVaciosEnAsignarEvaluadores(){
        boolean resultado = false;
        if(this.jtfCodigoAA.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor seleccione un código en la tabla de datos.");
            resultado = true;
        }
        if( this.jtfCodigoAA.getText().isEmpty() || this.jtfNombrePrEvaluador.getText().isEmpty() ||
            this.jtfFechaPrRevision.getText().isEmpty() || this.jtfNombreSeEvaluador.getText().isEmpty()||
            this.jtfFechaSeRevision.getText().isEmpty()  
          ){
            resultado = true;
        }
        return resultado;
    }
    public boolean hayCamposVaciosEnBuscarAnteproyecto(){
        boolean resultado = false;
        if(this.jtfCodigoAB.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor ingrese o seleccione un código.");
            resultado = true;
        }
        return resultado;
    }
    public boolean hayCamposVaciosEnRegistrarAnt(){
        boolean resultado = false;
        if(this.jtfCodigoAR.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor seleccione o ingrese un código en la tabla de datos.");
            resultado = true;
        }
        if( this.jtfCodigoAR.getText().isEmpty() || this.jtfTitulo.getText().isEmpty() ||
            this.jtfNombrePrEstudiante.getText().isEmpty() || this.jtfNombreSeEstudiante.getText().isEmpty()||
            this.jtfNombreDirector.getText().isEmpty() || this.jtfNombreCodirector.getText().isEmpty() ||
            this.jtfFechaRegistro.getText().isEmpty() || this.jtfFechaAprobacion.getText().isEmpty() || this.jtfNroRevision.getText().isEmpty() 
          ){
            resultado = true;
        }
        return resultado;
    }
    public boolean hayCamposVaciosEnRegistrarUsuarios(){
        boolean resultado = false;
        if(this.jtfID.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor ingrese el número de identificación del usuario.");
            resultado = true;
        }
        if( this.jtfID.getText().isEmpty() || this.jtfNombre.getText().isEmpty() || this.jtfApellido.getText().isEmpty() 
                || this.jtfUsuario.getText().isEmpty() || this.jtfContrasenia.getText().isEmpty()  
          ){
            resultado = true;
        }
        return resultado;
    }
    private int obtenerCampoConcepto(){
        int concepto = 0;
        String cadenaConcepto = this.cbTiposConceptos.getSelectedItem().toString().trim();
        switch(cadenaConcepto){
            case APROBADO:
                concepto = 1;
                break;
            case NO_APROBADO:
                concepto = 2;
                break;
            default:
                System.out.println("ERROR. El concepto del anteproyecto no se distingue.");
                break;
        }
        return concepto;
    }
    private int obtenerCampoEstado(){
        int estado = 0;
        String cadenaEstado = this.cbTiposEstados.getSelectedItem().toString().trim();
        switch(cadenaEstado){
            case SIN_ASIGNACION:
                estado = 1;
                break;
            case EVALUADORES_ASIGNADOS:
                estado = 2;
                break;
            case EN_REVISION:
                estado = 3;
                break;
            case EVALUADO:
                estado = 4;
                break;
            default:
                System.out.println("ERROR. El estado del anteproyecto no se distingue.");
                break;
        }
        return estado;
    }
    public int obtenerCampoTipoUsuario(){
        int tipoDeUsuario = 0;
        String cadenaTipoDeUsuario = this.cbTiposUsuarios.getSelectedItem().toString().trim();
        switch(cadenaTipoDeUsuario){
            case ESTUDIANTE:
                tipoDeUsuario = 1;
                break;
            case DIRECTOR_CODIRECTOR:
                tipoDeUsuario = 1;
                break;
            case EVALUADOR:
                tipoDeUsuario = 2;
                break;
            case JEFE_DEPARTAMENTO:
                tipoDeUsuario = 3;
                break;
            default:
                System.out.println("ERROR. El tipo de usuario no se distingue.");
                break;
        }
        return tipoDeUsuario;
    }
    // </editor-fold> 
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulariosAdmin = new javax.swing.JTabbedPane();
        pnlFormularioAsignarEvaluadores = new javax.swing.JPanel();
        pnlDatosEvaluadores = new javax.swing.JPanel();
        lblConceptos2 = new javax.swing.JLabel();
        lblNombreSeEvaluador = new javax.swing.JLabel();
        lblCodigoA = new javax.swing.JLabel();
        lblConceptos1 = new javax.swing.JLabel();
        lblNombrePrEvaluador = new javax.swing.JLabel();
        lblFechaPrRevision = new javax.swing.JLabel();
        lblFechaSeRevision = new javax.swing.JLabel();
        jtfNombrePrEvaluador = new javax.swing.JTextField();
        jtfCodigoAA = new javax.swing.JTextField();
        jtfNombreSeEvaluador = new javax.swing.JTextField();
        jtfFechaPrRevision = new javax.swing.JTextField();
        jtfFechaSeRevision = new javax.swing.JTextField();
        cbTiposConceptos1 = new javax.swing.JComboBox<>();
        cbTiposConceptos2 = new javax.swing.JComboBox<>();
        pnlInfoAsignarEvaluadores = new javax.swing.JPanel();
        lblInfoAsignarEvaluadores = new javax.swing.JLabel();
        btnGuardarE = new javax.swing.JButton();
        btnCancelarE = new javax.swing.JButton();
        pnlCodigosTitulosAnteproyectosA = new javax.swing.JScrollPane();
        tblInfoAnteproyectos = new javax.swing.JTable();
        pnlInfoResultado1 = new javax.swing.JPanel();
        lblInfoResultado1 = new javax.swing.JLabel();
        pnlMostrarAnteproyecto = new javax.swing.JPanel();
        pnlMostrarDatosAnteproyectos = new javax.swing.JScrollPane();
        tblDatosAnteproyectos = new javax.swing.JTable();
        panelCodigoTituloAnteproyectos = new javax.swing.JScrollPane();
        tblCodigoTituloAnteproyectos = new javax.swing.JTable();
        pnlInfoBuscarAnteproyecto = new javax.swing.JPanel();
        lblInfoBuscarAnteproyecto = new javax.swing.JLabel();
        pnlInfoResultado3 = new javax.swing.JPanel();
        lblInfoResultado3 = new javax.swing.JLabel();
        pnlCodigoAnteproyecto = new javax.swing.JPanel();
        lblCodigoAB = new javax.swing.JLabel();
        jtfCodigoAB = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnVerCodigosB = new javax.swing.JButton();
        pnlListadoDeAnteproyectos = new javax.swing.JPanel();
        pnlInfoListarAnteproyectos = new javax.swing.JPanel();
        lblInfoListarAnteproyectos = new javax.swing.JLabel();
        btnListar = new javax.swing.JButton();
        pnlListadoAnteproyectos = new javax.swing.JScrollPane();
        tblListadoAnteproyectos = new javax.swing.JTable();
        pnlFormularioModificarConcepto = new javax.swing.JPanel();
        pnlInfoModificarConcepto = new javax.swing.JPanel();
        lblInfoModificarConcepto = new javax.swing.JLabel();
        pnlDatosModificarConcepto = new javax.swing.JPanel();
        lblCodigoAM = new javax.swing.JLabel();
        jtfCodigoAM = new javax.swing.JTextField();
        cbConceptosM = new javax.swing.JComboBox<>();
        lblConceptosM = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        btnCancelarM = new javax.swing.JButton();
        pnlCodigosTitulosAnteproyectosM = new javax.swing.JScrollPane();
        tblInfoAnteproyectosM = new javax.swing.JTable();
        pnlInfoResultado9 = new javax.swing.JPanel();
        lblInfoResultado9 = new javax.swing.JLabel();
        pnlFormularioAnteproyecto = new javax.swing.JPanel();
        pnlDatosAnteproyecto = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        lblModalidad = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblNombrePrEstudiante = new javax.swing.JLabel();
        lblNombreSeEstudiante = new javax.swing.JLabel();
        lblNombreDirector = new javax.swing.JLabel();
        lblNombreCodirector = new javax.swing.JLabel();
        lblConcepto = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblNroRevision = new javax.swing.JLabel();
        lblFechaRegistro = new javax.swing.JLabel();
        lblFechaAprobacion = new javax.swing.JLabel();
        jtfCodigoAR = new javax.swing.JTextField();
        jtfTitulo = new javax.swing.JTextField();
        jtfNombrePrEstudiante = new javax.swing.JTextField();
        jtfNombreSeEstudiante = new javax.swing.JTextField();
        jtfNombreDirector = new javax.swing.JTextField();
        jtfNombreCodirector = new javax.swing.JTextField();
        jtfFechaRegistro = new javax.swing.JTextField();
        jtfFechaAprobacion = new javax.swing.JTextField();
        jtfNroRevision = new javax.swing.JTextField();
        cbTiposConceptos = new javax.swing.JComboBox<>();
        cbTiposEstados = new javax.swing.JComboBox<>();
        cbTiposModalidades = new javax.swing.JComboBox<>();
        pnlInfoRegAnteproyectos = new javax.swing.JPanel();
        lblInfoRegAnteproyectos = new javax.swing.JLabel();
        btnGuardarA = new javax.swing.JButton();
        btnCancelarA = new javax.swing.JButton();
        pnlInfoResultado10 = new javax.swing.JPanel();
        lblInfoResultado10 = new javax.swing.JLabel();
        pnlFormularioUsuarios = new javax.swing.JPanel();
        pnlDatosUsuario = new javax.swing.JPanel();
        lblContrasenia = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jtfApellido = new javax.swing.JTextField();
        jtfID = new javax.swing.JTextField();
        jtfUsuario = new javax.swing.JTextField();
        jtfContrasenia = new javax.swing.JTextField();
        lblTiposUsuarios = new javax.swing.JLabel();
        cbTiposUsuarios = new javax.swing.JComboBox<>();
        pnlInfoRegUsuarios = new javax.swing.JPanel();
        lblInfoRegUsuarios = new javax.swing.JLabel();
        btnGuardarU = new javax.swing.JButton();
        btnCancelarU = new javax.swing.JButton();
        pnlInfoResultado11 = new javax.swing.JPanel();
        lblInfoResultado11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCerrarSesionED = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Anteproyectos");

        pnlFormulariosAdmin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones administrador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N
        pnlFormulariosAdmin.setForeground(new java.awt.Color(0, 0, 153));
        pnlFormulariosAdmin.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlFormulariosAdmin.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlFormulariosAdmin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnlFormulariosAdmin.setMinimumSize(new java.awt.Dimension(140, 23));
        pnlFormulariosAdmin.setName(""); // NOI18N
        pnlFormulariosAdmin.setPreferredSize(new java.awt.Dimension(1120, 575));

        pnlFormularioAsignarEvaluadores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pnlDatosEvaluadores.setBackground(new java.awt.Color(251, 248, 248));
        pnlDatosEvaluadores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de los evaluadores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblConceptos2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConceptos2.setText("Concepto del segundo evaluador");

        lblNombreSeEvaluador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombreSeEvaluador.setText("Nombre del segundo evaluador");

        lblCodigoA.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigoA.setText("Código del anteproyecto");

        lblConceptos1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConceptos1.setText("Concepto del primer evaluador");

        lblNombrePrEvaluador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombrePrEvaluador.setText("Nombre del primer evaluador");

        lblFechaPrRevision.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaPrRevision.setText("Fecha de primer revisión");

        lblFechaSeRevision.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaSeRevision.setText("Fecha de segundo revisión");

        jtfNombrePrEvaluador.setEditable(false);

        jtfCodigoAA.setEditable(false);

        jtfNombreSeEvaluador.setEditable(false);

        jtfFechaPrRevision.setEditable(false);

        jtfFechaSeRevision.setEditable(false);

        cbTiposConceptos1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Aprobado", "2. No aprobado" }));
        cbTiposConceptos1.setSelectedIndex(1);
        cbTiposConceptos1.setEnabled(false);

        cbTiposConceptos2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Aprobado", "2. No aprobado" }));
        cbTiposConceptos2.setSelectedIndex(1);
        cbTiposConceptos2.setEnabled(false);

        javax.swing.GroupLayout pnlDatosEvaluadoresLayout = new javax.swing.GroupLayout(pnlDatosEvaluadores);
        pnlDatosEvaluadores.setLayout(pnlDatosEvaluadoresLayout);
        pnlDatosEvaluadoresLayout.setHorizontalGroup(
            pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNombrePrEvaluador)
                    .addComponent(lblCodigoA)
                    .addComponent(lblConceptos1)
                    .addComponent(lblFechaPrRevision))
                .addGap(10, 10, 10)
                .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfCodigoAA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDatosEvaluadoresLayout.createSequentialGroup()
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNombrePrEvaluador, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbTiposConceptos1, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE)
                                .addComponent(jtfFechaPrRevision, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFechaSeRevision)
                            .addComponent(lblConceptos2)
                            .addComponent(lblNombreSeEvaluador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNombreSeEvaluador, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbTiposConceptos2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtfFechaSeRevision, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDatosEvaluadoresLayout.setVerticalGroup(
            pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfCodigoAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDatosEvaluadoresLayout.createSequentialGroup()
                        .addComponent(lblCodigoA)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombreSeEvaluador)
                                .addComponent(jtfNombreSeEvaluador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombrePrEvaluador)
                                .addComponent(jtfNombrePrEvaluador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblConceptos2)
                                .addComponent(cbTiposConceptos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblConceptos1)
                                .addComponent(cbTiposConceptos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaPrRevision)
                            .addComponent(lblFechaSeRevision)
                            .addComponent(jtfFechaSeRevision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFechaPrRevision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInfoAsignarEvaluadores.setBackground(new java.awt.Color(102, 153, 255));

        lblInfoAsignarEvaluadores.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoAsignarEvaluadores.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoAsignarEvaluadores.setText("Asignar Evaluadores");

        javax.swing.GroupLayout pnlInfoAsignarEvaluadoresLayout = new javax.swing.GroupLayout(pnlInfoAsignarEvaluadores);
        pnlInfoAsignarEvaluadores.setLayout(pnlInfoAsignarEvaluadoresLayout);
        pnlInfoAsignarEvaluadoresLayout.setHorizontalGroup(
            pnlInfoAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoAsignarEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoAsignarEvaluadores, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoAsignarEvaluadoresLayout.setVerticalGroup(
            pnlInfoAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoAsignarEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoAsignarEvaluadores, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnGuardarE.setText("GUARDAR");
        btnGuardarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEActionPerformed(evt);
            }
        });

        btnCancelarE.setText("CANCELAR");
        btnCancelarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarEActionPerformed(evt);
            }
        });

        pnlCodigosTitulosAnteproyectosA.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de anteproyectos, seleccione un código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N
        pnlCodigosTitulosAnteproyectosA.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        tblInfoAnteproyectos.setModel(modeloTablaCodigoTituloB);
        tblInfoAnteproyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblInfoAnteproyectosMousePressed(evt);
            }
        });
        pnlCodigosTitulosAnteproyectosA.setViewportView(tblInfoAnteproyectos);

        lblInfoResultado1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado1.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado1.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado1Layout = new javax.swing.GroupLayout(pnlInfoResultado1);
        pnlInfoResultado1.setLayout(pnlInfoResultado1Layout);
        pnlInfoResultado1Layout.setHorizontalGroup(
            pnlInfoResultado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlInfoResultado1Layout.setVerticalGroup(
            pnlInfoResultado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFormularioAsignarEvaluadoresLayout = new javax.swing.GroupLayout(pnlFormularioAsignarEvaluadores);
        pnlFormularioAsignarEvaluadores.setLayout(pnlFormularioAsignarEvaluadoresLayout);
        pnlFormularioAsignarEvaluadoresLayout.setHorizontalGroup(
            pnlFormularioAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioAsignarEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoAsignarEvaluadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCodigosTitulosAnteproyectosA)
                    .addGroup(pnlFormularioAsignarEvaluadoresLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioAsignarEvaluadoresLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarE))
                    .addComponent(pnlDatosEvaluadores, javax.swing.GroupLayout.PREFERRED_SIZE, 841, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFormularioAsignarEvaluadoresLayout.setVerticalGroup(
            pnlFormularioAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioAsignarEvaluadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoAsignarEvaluadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCodigosTitulosAnteproyectosA, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDatosEvaluadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(pnlFormularioAsignarEvaluadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarE)
                    .addComponent(btnCancelarE))
                .addContainerGap())
        );

        pnlFormulariosAdmin.addTab("Asignar evaluadores", pnlFormularioAsignarEvaluadores);

        pnlMostrarAnteproyecto.setEnabled(false);

        pnlMostrarDatosAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del anteproyecto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblDatosAnteproyectos.setModel(modeloBuscarListarDatos);
        pnlMostrarDatosAnteproyectos.setViewportView(tblDatosAnteproyectos);

        panelCodigoTituloAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblCodigoTituloAnteproyectos.setModel(modeloVerCodigoTitulo);
        tblCodigoTituloAnteproyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCodigoTituloAnteproyectosMousePressed(evt);
            }
        });
        panelCodigoTituloAnteproyectos.setViewportView(tblCodigoTituloAnteproyectos);

        pnlInfoBuscarAnteproyecto.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoBuscarAnteproyecto.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoBuscarAnteproyecto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoBuscarAnteproyecto.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoBuscarAnteproyecto.setText("Buscar Anteproyecto");
        lblInfoBuscarAnteproyecto.setMaximumSize(new java.awt.Dimension(158, 22));
        lblInfoBuscarAnteproyecto.setMinimumSize(new java.awt.Dimension(158, 22));
        lblInfoBuscarAnteproyecto.setPreferredSize(new java.awt.Dimension(158, 22));

        javax.swing.GroupLayout pnlInfoBuscarAnteproyectoLayout = new javax.swing.GroupLayout(pnlInfoBuscarAnteproyecto);
        pnlInfoBuscarAnteproyecto.setLayout(pnlInfoBuscarAnteproyectoLayout);
        pnlInfoBuscarAnteproyectoLayout.setHorizontalGroup(
            pnlInfoBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoBuscarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoBuscarAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoBuscarAnteproyectoLayout.setVerticalGroup(
            pnlInfoBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoBuscarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoBuscarAnteproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblInfoResultado3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado3.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado3.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado3Layout = new javax.swing.GroupLayout(pnlInfoResultado3);
        pnlInfoResultado3.setLayout(pnlInfoResultado3Layout);
        pnlInfoResultado3Layout.setHorizontalGroup(
            pnlInfoResultado3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado3, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlInfoResultado3Layout.setVerticalGroup(
            pnlInfoResultado3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlCodigoAnteproyecto.setBackground(new java.awt.Color(251, 248, 248));
        pnlCodigoAnteproyecto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese o seleccione un código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblCodigoAB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigoAB.setText("Código del anteproyecto");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnVerCodigosB.setText("Ver códigos");
        btnVerCodigosB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCodigosBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCodigoAnteproyectoLayout = new javax.swing.GroupLayout(pnlCodigoAnteproyecto);
        pnlCodigoAnteproyecto.setLayout(pnlCodigoAnteproyectoLayout);
        pnlCodigoAnteproyectoLayout.setHorizontalGroup(
            pnlCodigoAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCodigoAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigoAB)
                .addGap(10, 10, 10)
                .addComponent(jtfCodigoAB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerCodigosB)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCodigoAnteproyectoLayout.setVerticalGroup(
            pnlCodigoAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCodigoAnteproyectoLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(pnlCodigoAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCodigoAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfCodigoAB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar)
                        .addComponent(btnVerCodigosB))
                    .addComponent(lblCodigoAB))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMostrarAnteproyectoLayout = new javax.swing.GroupLayout(pnlMostrarAnteproyecto);
        pnlMostrarAnteproyecto.setLayout(pnlMostrarAnteproyectoLayout);
        pnlMostrarAnteproyectoLayout.setHorizontalGroup(
            pnlMostrarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMostrarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMostrarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlMostrarDatosAnteproyectos)
                    .addComponent(pnlInfoBuscarAnteproyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                    .addComponent(panelCodigoTituloAnteproyectos)
                    .addComponent(pnlCodigoAnteproyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMostrarAnteproyectoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMostrarAnteproyectoLayout.setVerticalGroup(
            pnlMostrarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMostrarAnteproyectoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlInfoBuscarAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCodigoAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCodigoTituloAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlMostrarDatosAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pnlFormulariosAdmin.addTab("Buscar un anteproyecto", pnlMostrarAnteproyecto);

        pnlInfoListarAnteproyectos.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoListarAnteproyectos.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoListarAnteproyectos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoListarAnteproyectos.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoListarAnteproyectos.setText("Listar Anteproyectos");

        javax.swing.GroupLayout pnlInfoListarAnteproyectosLayout = new javax.swing.GroupLayout(pnlInfoListarAnteproyectos);
        pnlInfoListarAnteproyectos.setLayout(pnlInfoListarAnteproyectosLayout);
        pnlInfoListarAnteproyectosLayout.setHorizontalGroup(
            pnlInfoListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoListarAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(651, Short.MAX_VALUE))
        );
        pnlInfoListarAnteproyectosLayout.setVerticalGroup(
            pnlInfoListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoListarAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnListar.setText("LISTAR");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        pnlListadoAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado de anteproyectos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblListadoAnteproyectos.setModel(modeloDatosAnteproyecto);
        pnlListadoAnteproyectos.setViewportView(tblListadoAnteproyectos);

        javax.swing.GroupLayout pnlListadoDeAnteproyectosLayout = new javax.swing.GroupLayout(pnlListadoDeAnteproyectos);
        pnlListadoDeAnteproyectos.setLayout(pnlListadoDeAnteproyectosLayout);
        pnlListadoDeAnteproyectosLayout.setHorizontalGroup(
            pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlListadoAnteproyectos)
                    .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                    .addGroup(pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnListar)))
                .addContainerGap())
        );
        pnlListadoDeAnteproyectosLayout.setVerticalGroup(
            pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlListadoAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(btnListar)
                .addContainerGap())
        );

        pnlFormulariosAdmin.addTab("Listar los anteproyectos", pnlListadoDeAnteproyectos);

        pnlInfoModificarConcepto.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoModificarConcepto.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoModificarConcepto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoModificarConcepto.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoModificarConcepto.setText("Modificar Concepto");

        javax.swing.GroupLayout pnlInfoModificarConceptoLayout = new javax.swing.GroupLayout(pnlInfoModificarConcepto);
        pnlInfoModificarConcepto.setLayout(pnlInfoModificarConceptoLayout);
        pnlInfoModificarConceptoLayout.setHorizontalGroup(
            pnlInfoModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoModificarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoModificarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoModificarConceptoLayout.setVerticalGroup(
            pnlInfoModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoModificarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoModificarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlDatosModificarConcepto.setBackground(new java.awt.Color(251, 248, 248));
        pnlDatosModificarConcepto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos para modificar concepto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblCodigoAM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigoAM.setText("Código del anteproyecto");

        cbConceptosM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Aprobado", "2. No aprobado" }));
        cbConceptosM.setSelectedIndex(1);

        lblConceptosM.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblConceptosM.setText("Concepto");

        javax.swing.GroupLayout pnlDatosModificarConceptoLayout = new javax.swing.GroupLayout(pnlDatosModificarConcepto);
        pnlDatosModificarConcepto.setLayout(pnlDatosModificarConceptoLayout);
        pnlDatosModificarConceptoLayout.setHorizontalGroup(
            pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosModificarConceptoLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCodigoAM)
                    .addComponent(lblConceptosM))
                .addGap(10, 10, 10)
                .addGroup(pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbConceptosM, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCodigoAM, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDatosModificarConceptoLayout.setVerticalGroup(
            pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosModificarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfCodigoAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDatosModificarConceptoLayout.createSequentialGroup()
                        .addComponent(lblCodigoAM)
                        .addGap(29, 29, 29)
                        .addGroup(pnlDatosModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbConceptosM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConceptosM))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnCancelarM.setText("CANCELAR");
        btnCancelarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMActionPerformed(evt);
            }
        });

        pnlCodigosTitulosAnteproyectosM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de anteproyectos, seleccione un código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N
        pnlCodigosTitulosAnteproyectosM.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        tblInfoAnteproyectosM.setModel(modeloTablaCodigoTituloB);
        tblInfoAnteproyectosM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblInfoAnteproyectosMMousePressed(evt);
            }
        });
        pnlCodigosTitulosAnteproyectosM.setViewportView(tblInfoAnteproyectosM);

        lblInfoResultado9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado9.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado9.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado9Layout = new javax.swing.GroupLayout(pnlInfoResultado9);
        pnlInfoResultado9.setLayout(pnlInfoResultado9Layout);
        pnlInfoResultado9Layout.setHorizontalGroup(
            pnlInfoResultado9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado9, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlInfoResultado9Layout.setVerticalGroup(
            pnlInfoResultado9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFormularioModificarConceptoLayout = new javax.swing.GroupLayout(pnlFormularioModificarConcepto);
        pnlFormularioModificarConcepto.setLayout(pnlFormularioModificarConceptoLayout);
        pnlFormularioModificarConceptoLayout.setHorizontalGroup(
            pnlFormularioModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioModificarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoModificarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                    .addComponent(pnlDatosModificarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioModificarConceptoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarM))
                    .addGroup(pnlFormularioModificarConceptoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE))
                    .addComponent(pnlCodigosTitulosAnteproyectosM))
                .addContainerGap())
        );
        pnlFormularioModificarConceptoLayout.setVerticalGroup(
            pnlFormularioModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioModificarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoModificarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatosModificarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCodigosTitulosAnteproyectosM, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(pnlFormularioModificarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnCancelarM))
                .addContainerGap())
        );

        pnlFormulariosAdmin.addTab("Modificar un concepto", pnlFormularioModificarConcepto);

        pnlDatosAnteproyecto.setBackground(new java.awt.Color(251, 248, 248));
        pnlDatosAnteproyecto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del anteproyecto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigo.setText("Código");

        lblModalidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblModalidad.setText("Modalidad");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTitulo.setText("Titulo");

        lblNombrePrEstudiante.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombrePrEstudiante.setText("Nombre del primer estudiante");

        lblNombreSeEstudiante.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombreSeEstudiante.setText("Nombre del segundo estudiante");

        lblNombreDirector.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombreDirector.setText("Nombre del director");

        lblNombreCodirector.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombreCodirector.setText("Nombre del codirector");

        lblConcepto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConcepto.setText("Concepto");

        lblEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEstado.setText("Estado");

        lblNroRevision.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNroRevision.setText("Número de revisión");

        lblFechaRegistro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaRegistro.setText("Fecha de registro");

        lblFechaAprobacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaAprobacion.setText("Fecha de aprobación");

        cbTiposConceptos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Aprobado", "2. No aprobado" }));
        cbTiposConceptos.setSelectedIndex(1);

        cbTiposEstados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Sin asignación", "2. Evaluadores asignados", "3. En revisión", "4. Evaluado" }));

        cbTiposModalidades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(TI) - Trabajo de Investigación", "(PP) - Práctica Profesional" }));

        javax.swing.GroupLayout pnlDatosAnteproyectoLayout = new javax.swing.GroupLayout(pnlDatosAnteproyecto);
        pnlDatosAnteproyecto.setLayout(pnlDatosAnteproyectoLayout);
        pnlDatosAnteproyectoLayout.setHorizontalGroup(
            pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosAnteproyectoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNombreCodirector)
                    .addComponent(lblNombreDirector)
                    .addComponent(lblNombreSeEstudiante)
                    .addComponent(lblNombrePrEstudiante)
                    .addComponent(lblTitulo)
                    .addComponent(lblModalidad)
                    .addComponent(lblCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfNombreDirector)
                    .addComponent(jtfNombreSeEstudiante)
                    .addComponent(jtfTitulo)
                    .addComponent(cbTiposModalidades, 0, 220, Short.MAX_VALUE)
                    .addComponent(jtfNombreCodirector)
                    .addComponent(jtfNombrePrEstudiante)
                    .addComponent(jtfCodigoAR, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaRegistro)
                    .addComponent(lblConcepto)
                    .addComponent(lblFechaAprobacion)
                    .addComponent(lblEstado)
                    .addComponent(lblNroRevision))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbTiposEstados, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTiposConceptos, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfFechaAprobacion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtfFechaRegistro, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jtfNroRevision, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDatosAnteproyectoLayout.setVerticalGroup(
            pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(jtfCodigoAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosAnteproyectoLayout.createSequentialGroup()
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblModalidad)
                            .addComponent(cbTiposModalidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTitulo)
                            .addComponent(jtfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombrePrEstudiante)
                            .addComponent(jtfNombrePrEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreSeEstudiante)
                            .addComponent(jtfNombreSeEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreDirector)
                            .addComponent(jtfNombreDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombreCodirector)
                            .addComponent(jtfNombreCodirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDatosAnteproyectoLayout.createSequentialGroup()
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaRegistro)
                            .addComponent(jtfFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaAprobacion)
                            .addComponent(jtfFechaAprobacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblConcepto)
                            .addComponent(cbTiposConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(cbTiposEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNroRevision)
                            .addComponent(jtfNroRevision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInfoRegAnteproyectos.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoRegAnteproyectos.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoRegAnteproyectos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoRegAnteproyectos.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoRegAnteproyectos.setText("Registrar Anteproyectos");

        javax.swing.GroupLayout pnlInfoRegAnteproyectosLayout = new javax.swing.GroupLayout(pnlInfoRegAnteproyectos);
        pnlInfoRegAnteproyectos.setLayout(pnlInfoRegAnteproyectosLayout);
        pnlInfoRegAnteproyectosLayout.setHorizontalGroup(
            pnlInfoRegAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoRegAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoRegAnteproyectos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoRegAnteproyectosLayout.setVerticalGroup(
            pnlInfoRegAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoRegAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoRegAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnGuardarA.setText("GUARDAR");
        btnGuardarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAActionPerformed(evt);
            }
        });

        btnCancelarA.setText("CANCELAR");
        btnCancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAActionPerformed(evt);
            }
        });

        lblInfoResultado10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado10.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado10.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado10Layout = new javax.swing.GroupLayout(pnlInfoResultado10);
        pnlInfoResultado10.setLayout(pnlInfoResultado10Layout);
        pnlInfoResultado10Layout.setHorizontalGroup(
            pnlInfoResultado10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado10, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlInfoResultado10Layout.setVerticalGroup(
            pnlInfoResultado10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFormularioAnteproyectoLayout = new javax.swing.GroupLayout(pnlFormularioAnteproyecto);
        pnlFormularioAnteproyecto.setLayout(pnlFormularioAnteproyectoLayout);
        pnlFormularioAnteproyectoLayout.setHorizontalGroup(
            pnlFormularioAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosAnteproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfoRegAnteproyectos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioAnteproyectoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarA))
                    .addGroup(pnlFormularioAnteproyectoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlFormularioAnteproyectoLayout.setVerticalGroup(
            pnlFormularioAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoRegAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatosAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addGroup(pnlFormularioAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarA)
                    .addComponent(btnCancelarA))
                .addContainerGap())
        );

        pnlFormulariosAdmin.addTab("Registrar anteproyectos", pnlFormularioAnteproyecto);

        pnlDatosUsuario.setBackground(new java.awt.Color(251, 248, 248));
        pnlDatosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblContrasenia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblContrasenia.setText("Contraseña");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUsuario.setText("Usuario de Unicauca");

        lblID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblID.setText("Identificacion");

        lblApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblApellido.setText("Apellido");

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNombre.setText("Nombre");

        lblTiposUsuarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTiposUsuarios.setText("Tipo de usuario");

        cbTiposUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Estudiante", "2. Director - Codirector", "3. Evaluador", "4. Jefe de departamento" }));

        javax.swing.GroupLayout pnlDatosUsuarioLayout = new javax.swing.GroupLayout(pnlDatosUsuario);
        pnlDatosUsuario.setLayout(pnlDatosUsuarioLayout);
        pnlDatosUsuarioLayout.setHorizontalGroup(
            pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                        .addComponent(lblID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfID, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addComponent(lblApellido)
                                .addGap(10, 10, 10)
                                .addComponent(jtfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(112, 112, 112)
                        .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblContrasenia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosUsuarioLayout.createSequentialGroup()
                                .addComponent(lblUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblTiposUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbTiposUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlDatosUsuarioLayout.setVerticalGroup(
            pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(jtfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUsuario)
                        .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContrasenia)
                    .addComponent(jtfContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblApellido)
                        .addComponent(jtfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTiposUsuarios)
                    .addComponent(cbTiposUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlInfoRegUsuarios.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoRegUsuarios.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoRegUsuarios.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoRegUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoRegUsuarios.setText("Registrar Usuarios");

        javax.swing.GroupLayout pnlInfoRegUsuariosLayout = new javax.swing.GroupLayout(pnlInfoRegUsuarios);
        pnlInfoRegUsuarios.setLayout(pnlInfoRegUsuariosLayout);
        pnlInfoRegUsuariosLayout.setHorizontalGroup(
            pnlInfoRegUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoRegUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoRegUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoRegUsuariosLayout.setVerticalGroup(
            pnlInfoRegUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoRegUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoRegUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnGuardarU.setText("GUARDAR");
        btnGuardarU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUActionPerformed(evt);
            }
        });

        btnCancelarU.setText("CANCELAR");
        btnCancelarU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUActionPerformed(evt);
            }
        });

        lblInfoResultado11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado11.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado11.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado11Layout = new javax.swing.GroupLayout(pnlInfoResultado11);
        pnlInfoResultado11.setLayout(pnlInfoResultado11Layout);
        pnlInfoResultado11Layout.setHorizontalGroup(
            pnlInfoResultado11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado11, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlInfoResultado11Layout.setVerticalGroup(
            pnlInfoResultado11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFormularioUsuariosLayout = new javax.swing.GroupLayout(pnlFormularioUsuarios);
        pnlFormularioUsuarios.setLayout(pnlFormularioUsuariosLayout);
        pnlFormularioUsuariosLayout.setHorizontalGroup(
            pnlFormularioUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfoRegUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioUsuariosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarU))
                    .addGroup(pnlFormularioUsuariosLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlFormularioUsuariosLayout.setVerticalGroup(
            pnlFormularioUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoRegUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addGroup(pnlFormularioUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarU)
                    .addComponent(btnCancelarU))
                .addContainerGap())
        );

        pnlFormulariosAdmin.addTab("Registrar usuarios", pnlFormularioUsuarios);

        btnCerrarSesionED.setText("CERRAR SESIÓN");
        btnCerrarSesionED.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionEDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(btnCerrarSesionED)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(btnCerrarSesionED, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulariosAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFormulariosAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
        );

        pnlFormulariosAdmin.getAccessibleContext().setAccessibleName("Registrar anteproyectos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Métodos que capturan los eventos generados en la interfaz">  
    private void btnCancelarUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarUActionPerformed
        limpiarCamposEnRegistrarUsuario();
        this.lblInfoResultado11.setText("");
    }//GEN-LAST:event_btnCancelarUActionPerformed

    private void btnGuardarUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUActionPerformed
        if(!hayCamposVaciosEnRegistrarUsuarios()){
            try {
                registrarUsuario();
            } catch (RemoteException ex) {
                Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
            limpiarCamposEnRegistrarUsuario();
        }else{
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarUActionPerformed

    private void btnCancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarAActionPerformed
        limpiarCamposEnRegistrarAnt();
        this.lblInfoResultado10.setText("");
    }//GEN-LAST:event_btnCancelarAActionPerformed

    private void btnGuardarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAActionPerformed
        if(!hayCamposVaciosEnRegistrarAnt()){
            try{
                registrarAnteproyecto();
                limpiarCamposEnRegistrarAnt();
            } catch (RemoteException ex) {
                Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarAActionPerformed

    private void tblInfoAnteproyectosMMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInfoAnteproyectosMMousePressed
        String dato = String.valueOf(this.modeloTablaCodigoTituloB.getValueAt(this.tblInfoAnteproyectosM.getSelectedRow(),0));
        this.jtfCodigoAM.setText(dato);
    }//GEN-LAST:event_tblInfoAnteproyectosMMousePressed

    private void btnCancelarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMActionPerformed
        this.jtfCodigoAM.setText("");
        this.cbConceptosM.setSelectedIndex(1);
        this.lblInfoResultado9.setText("");
    }//GEN-LAST:event_btnCancelarMActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if(!this.jtfCodigoAM.getText().isEmpty()){
            try {
                modificarConcepto();
            } catch (RemoteException ex) {
                Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor ingrese o seleccione un código.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
            try {
                cargarInfoAnteproyectos(modeloDatosAnteproyecto, rutaAnteproyecto, 12, nombreColumnasAnteproyecto);
            } catch (IOException ex) {
                Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_btnListarActionPerformed

    private void tblInfoAnteproyectosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInfoAnteproyectosMousePressed
        String dato = String.valueOf(this.modeloTablaCodigoTituloB.getValueAt(this.tblInfoAnteproyectos.getSelectedRow(),0));
        this.jtfCodigoAA.setText(dato);
        habilitarCamposEnAsignarEvaluadores();
        this.lblInfoResultado1.setText("");
    }//GEN-LAST:event_tblInfoAnteproyectosMousePressed

    private void btnCancelarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarEActionPerformed
        limpiarCamposEnAsignarEvaluadores();
    }//GEN-LAST:event_btnCancelarEActionPerformed

    // <editor-fold defaultstate="collapsed" desc="Métodos que capturan los eventos realizados en la interfaz por el usuario"> 
    private void btnGuardarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEActionPerformed
        if(!hayCamposVaciosEnAsignarEvaluadores()){
            try{
                asignarEvaluadores();
                limpiarCamposEnAsignarEvaluadores();
            } catch (RemoteException ex) {
                Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarEActionPerformed

    private void btnCerrarSesionEDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionEDActionPerformed
        abrirVentanaLogin();
    }//GEN-LAST:event_btnCerrarSesionEDActionPerformed

    private void tblCodigoTituloAnteproyectosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigoTituloAnteproyectosMousePressed
        String dato = String.valueOf(this.modeloVerCodigoTitulo.getValueAt(this.tblCodigoTituloAnteproyectos.getSelectedRow(),0));
        this.jtfCodigoAB.setText(dato);
        this.lblInfoResultado1.setText("");
    }//GEN-LAST:event_tblCodigoTituloAnteproyectosMousePressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        agregarColumnasAModelo(modeloBuscarListarDatos, 9, nombreCamposAnteproyecto);
        buscarAnteproyecto();
        this.jtfCodigoAB.setText("");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnVerCodigosBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCodigosBActionPerformed
        try {
            cargarInfoAnteproyectos(modeloVerCodigoTitulo, rutaAnteproyectoCodigoTitulo, 2, camposBasicosAnteproyecto);
        } catch (IOException ex) {
            Logger.getLogger(InterfazAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerCodigosBActionPerformed
    // </editor-fold> 
    
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(InterfazEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(InterfazEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(InterfazEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(InterfazEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new FuncionesAdministrador().setVisible(true);
//                } catch (RemoteException ex) {
//                    Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarA;
    private javax.swing.JButton btnCancelarE;
    private javax.swing.JButton btnCancelarM;
    private javax.swing.JButton btnCancelarU;
    private javax.swing.JButton btnCerrarSesionED;
    private javax.swing.JButton btnGuardarA;
    private javax.swing.JButton btnGuardarE;
    private javax.swing.JButton btnGuardarU;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVerCodigosB;
    private javax.swing.JComboBox<String> cbConceptosM;
    private javax.swing.JComboBox<String> cbTiposConceptos;
    private javax.swing.JComboBox<String> cbTiposConceptos1;
    private javax.swing.JComboBox<String> cbTiposConceptos2;
    private javax.swing.JComboBox<String> cbTiposEstados;
    private javax.swing.JComboBox<String> cbTiposModalidades;
    private javax.swing.JComboBox<String> cbTiposUsuarios;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jtfApellido;
    private javax.swing.JTextField jtfCodigoAA;
    private javax.swing.JTextField jtfCodigoAB;
    private javax.swing.JTextField jtfCodigoAM;
    private javax.swing.JTextField jtfCodigoAR;
    private javax.swing.JTextField jtfContrasenia;
    private javax.swing.JTextField jtfFechaAprobacion;
    private javax.swing.JTextField jtfFechaPrRevision;
    private javax.swing.JTextField jtfFechaRegistro;
    private javax.swing.JTextField jtfFechaSeRevision;
    private javax.swing.JTextField jtfID;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfNombreCodirector;
    private javax.swing.JTextField jtfNombreDirector;
    private javax.swing.JTextField jtfNombrePrEstudiante;
    private javax.swing.JTextField jtfNombrePrEvaluador;
    private javax.swing.JTextField jtfNombreSeEstudiante;
    private javax.swing.JTextField jtfNombreSeEvaluador;
    private javax.swing.JTextField jtfNroRevision;
    private javax.swing.JTextField jtfTitulo;
    private javax.swing.JTextField jtfUsuario;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoA;
    private javax.swing.JLabel lblCodigoAB;
    private javax.swing.JLabel lblCodigoAM;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel lblConceptos1;
    private javax.swing.JLabel lblConceptos2;
    private javax.swing.JLabel lblConceptosM;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaAprobacion;
    private javax.swing.JLabel lblFechaPrRevision;
    private javax.swing.JLabel lblFechaRegistro;
    private javax.swing.JLabel lblFechaSeRevision;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblInfoAsignarEvaluadores;
    private javax.swing.JLabel lblInfoBuscarAnteproyecto;
    private javax.swing.JLabel lblInfoListarAnteproyectos;
    private javax.swing.JLabel lblInfoModificarConcepto;
    private javax.swing.JLabel lblInfoRegAnteproyectos;
    private javax.swing.JLabel lblInfoRegUsuarios;
    private javax.swing.JLabel lblInfoResultado1;
    private javax.swing.JLabel lblInfoResultado10;
    private javax.swing.JLabel lblInfoResultado11;
    private javax.swing.JLabel lblInfoResultado3;
    private javax.swing.JLabel lblInfoResultado9;
    private javax.swing.JLabel lblModalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreCodirector;
    private javax.swing.JLabel lblNombreDirector;
    private javax.swing.JLabel lblNombrePrEstudiante;
    private javax.swing.JLabel lblNombrePrEvaluador;
    private javax.swing.JLabel lblNombreSeEstudiante;
    private javax.swing.JLabel lblNombreSeEvaluador;
    private javax.swing.JLabel lblNroRevision;
    private javax.swing.JLabel lblTiposUsuarios;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JScrollPane panelCodigoTituloAnteproyectos;
    private javax.swing.JPanel pnlCodigoAnteproyecto;
    private javax.swing.JScrollPane pnlCodigosTitulosAnteproyectosA;
    private javax.swing.JScrollPane pnlCodigosTitulosAnteproyectosM;
    private javax.swing.JPanel pnlDatosAnteproyecto;
    private javax.swing.JPanel pnlDatosEvaluadores;
    private javax.swing.JPanel pnlDatosModificarConcepto;
    private javax.swing.JPanel pnlDatosUsuario;
    private javax.swing.JPanel pnlFormularioAnteproyecto;
    private javax.swing.JPanel pnlFormularioAsignarEvaluadores;
    private javax.swing.JPanel pnlFormularioModificarConcepto;
    private javax.swing.JPanel pnlFormularioUsuarios;
    private javax.swing.JTabbedPane pnlFormulariosAdmin;
    private javax.swing.JPanel pnlInfoAsignarEvaluadores;
    private javax.swing.JPanel pnlInfoBuscarAnteproyecto;
    private javax.swing.JPanel pnlInfoListarAnteproyectos;
    private javax.swing.JPanel pnlInfoModificarConcepto;
    private javax.swing.JPanel pnlInfoRegAnteproyectos;
    private javax.swing.JPanel pnlInfoRegUsuarios;
    private javax.swing.JPanel pnlInfoResultado1;
    private javax.swing.JPanel pnlInfoResultado10;
    private javax.swing.JPanel pnlInfoResultado11;
    private javax.swing.JPanel pnlInfoResultado3;
    private javax.swing.JPanel pnlInfoResultado9;
    private javax.swing.JScrollPane pnlListadoAnteproyectos;
    private javax.swing.JPanel pnlListadoDeAnteproyectos;
    private javax.swing.JPanel pnlMostrarAnteproyecto;
    private javax.swing.JScrollPane pnlMostrarDatosAnteproyectos;
    private javax.swing.JTable tblCodigoTituloAnteproyectos;
    private javax.swing.JTable tblDatosAnteproyectos;
    public javax.swing.JTable tblInfoAnteproyectos;
    public javax.swing.JTable tblInfoAnteproyectosM;
    private javax.swing.JTable tblListadoAnteproyectos;
    // End of variables declaration//GEN-END:variables
}
