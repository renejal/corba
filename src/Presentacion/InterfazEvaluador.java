/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

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
import sop_rmi.AnteproyectoDTO;
import sop_rmi.clsEstudianteDirector;
import sop_rmi.clsEvaluador;
import sop_rmi.interfazEstudianteDirector;
import sop_rmi.interfazEvaluador;

/**
 *
 * @author Angélica
 */
public class InterfazEvaluador extends javax.swing.JFrame {

    /**
     * Creates new form InterfazEvaluador
     */
    
    DefaultTableModel modeloTablaCodigoTitulo;
    DefaultTableModel modeloDatosAnteproyectoB;
    DefaultTableModel modeloDatosAnteproyectoL;
    String nombreColumnasAnteproyecto[] = {"MODALIDAD", "TITULO", "CÓDIGO", "ESTUDIANTE 1", 
                                           "ESTUDIANTE 2", "DIRECTOR", "CODIRECTOR", 
                                           "FECHA DE REGISTRO", "FECHA DE APROBACIÓN", 
                                           "CONCEPTO", "ESTADO", "NUMERO DE REVISIÓN"};
    String nombreColumnasCodigoTitutlo[] = {"CÓDIGO", "TITULO"};
    String rutaAnteproyecto = "src/Datos/anteProyectos.txt";
    String rutaAnteproyectoCodigoTitulo = "src/Datos/infoAnteproyectosCodigoTitulo.txt";
    interfazEvaluador objEvaluador = null;
    interfazEstudianteDirector objEstudianteDirector = null;
    
    public InterfazEvaluador() throws RemoteException {
        this.modeloTablaCodigoTitulo = new DefaultTableModel();        
        this.modeloDatosAnteproyectoB = new DefaultTableModel();
        this.modeloDatosAnteproyectoL = new DefaultTableModel();
        initComponents();
        this.lblInfoResultado1.setVisible(false);
        this.objEvaluador = new clsEvaluador();
        objEstudianteDirector = new clsEstudianteDirector();
    }
    
    public void cargarInfoAnteproyectos(DefaultTableModel parModelo, String ruta, int nroColumnas, String[] vectorColumnas) 
                                        throws FileNotFoundException, IOException {
        int i = 0;
        String linea = "";
        ArrayList<String> varColAnteproyectos = new ArrayList<>();
        Object[] LineaAnteproyecto = new Object[nroColumnas];
        FileReader f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);
        for(int j = 0; j<nroColumnas; j++){
            parModelo.addColumn(vectorColumnas[j]);
        }
        while(( linea = b.readLine() ) != null) {
            varColAnteproyectos.add(linea);
            System.out.println(varColAnteproyectos.get(i));
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
    
    public void ingresarConcepto() throws RemoteException{
        boolean resultado = false;
        int codigoAnteproyecto = Integer.parseInt(this.jtfCodigoAI.getText());
        String concepto = this.cbConceptosI.getSelectedItem().toString().trim();
        resultado = this.objEvaluador.ingresarConcepto(codigoAnteproyecto, concepto);
        this.mostrarResultadoDeIngresarConcepto(resultado, codigoAnteproyecto);
    }
    public void mostrarResultadoDeIngresarConcepto(boolean pResultado, int pCodigoAnteproyecto){
        this.lblInfoResultado1.setVisible(true);
        if(pResultado){
            this.lblInfoResultado1.setText("EXITO. Se ingresó el concepto al anteproyecto : " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
        }else{
            this.lblInfoResultado1.setText("FALLO. No se ingresó el concepto al anteproyecto: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
        }
    }
    public void limpiarCamposEnIngresarConcepto(){
        this.jtfCodigoAI.setText("");
        this.cbConceptosI.setSelectedIndex(1);
    }
    public boolean hayCamposVaciosEnIngresarConcepto(){
        boolean resultado = false;
        if(this.jtfCodigoAI.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor seleccione o ingrese el código del anteproyecto.");
            resultado = true;
        }
        return resultado;
    }
    private void buscarAnteproyecto() throws RemoteException{
        boolean resultado = false;
        int codigoAnteproyecto = Integer.parseInt(this.jtfCodigoAB.getText());
        AnteproyectoDTO objAnteproyecto = this.objEstudianteDirector.buscarAnteproyecto(codigoAnteproyecto);
        if(objAnteproyecto != null){
            this.mostrarDatosEnTabla(objAnteproyecto);
            resultado = true;
        }
        mostrarResultadoDeBuscar(resultado, codigoAnteproyecto);
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
    public boolean hayCamposVaciosEnBuscarAnteproyecto(){
        boolean resultado = false;
        if(this.jtfCodigoAB.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor ingrese o seleccione un código.");
            resultado = true;
        }
        return resultado;
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
        this.modeloDatosAnteproyectoB.addRow(objDatosAnteproyecto);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulariosEvaluador = new javax.swing.JTabbedPane();
        pnlBuscarAnteproyecto = new javax.swing.JPanel();
        pnlInfoBuscarAnteproyecto = new javax.swing.JPanel();
        lblInfoBuscarAnteproyecto = new javax.swing.JLabel();
        panelCodigoTituloAnteproyectos = new javax.swing.JScrollPane();
        tblCodigoTituloAnteproyectos = new javax.swing.JTable();
        pnlMostrarDatosAnteproyectos = new javax.swing.JScrollPane();
        tblDatosAnteproyectos = new javax.swing.JTable();
        pnlInfoResultado2 = new javax.swing.JPanel();
        lblInfoResultado2 = new javax.swing.JLabel();
        pnlCodigoAnteproyecto = new javax.swing.JPanel();
        lblCodigoAB = new javax.swing.JLabel();
        jtfCodigoAB = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnVerCodigosB = new javax.swing.JButton();
        pnlFormularioIngresarConcepto = new javax.swing.JPanel();
        pnlInfoIngresarConcepto = new javax.swing.JPanel();
        lblInfoIngresarConcepto = new javax.swing.JLabel();
        pnlDatosIngresarConcepto = new javax.swing.JPanel();
        lblCodigoAI = new javax.swing.JLabel();
        jtfCodigoAI = new javax.swing.JTextField();
        lblInfoConceptoI = new javax.swing.JLabel();
        cbConceptosI = new javax.swing.JComboBox<>();
        btnVerCodigosAI = new javax.swing.JButton();
        btnGuardarI = new javax.swing.JButton();
        btnCancelarI = new javax.swing.JButton();
        pnlCodigoTituloAntI = new javax.swing.JScrollPane();
        tblCodigoTituloAnteproyectosI = new javax.swing.JTable();
        pnlInfoResultado1 = new javax.swing.JPanel();
        lblInfoResultado1 = new javax.swing.JLabel();
        pnlListarAnteproyectos = new javax.swing.JPanel();
        pnlInfoListarAnteproyectos = new javax.swing.JPanel();
        lblInfoListarAnteproyectos = new javax.swing.JLabel();
        pnlListadoAnteproyectos = new javax.swing.JScrollPane();
        tblListadoAnteproyectos = new javax.swing.JTable();
        btnListar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlFormulariosEvaluador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 15), new java.awt.Color(0, 0, 153))); // NOI18N
        pnlFormulariosEvaluador.setForeground(new java.awt.Color(0, 0, 153));
        pnlFormulariosEvaluador.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlFormulariosEvaluador.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlFormulariosEvaluador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnlFormulariosEvaluador.setMinimumSize(new java.awt.Dimension(140, 23));
        pnlFormulariosEvaluador.setName(""); // NOI18N
        pnlFormulariosEvaluador.setPreferredSize(new java.awt.Dimension(1120, 575));

        pnlBuscarAnteproyecto.setEnabled(false);

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
                .addContainerGap(742, Short.MAX_VALUE))
        );
        pnlInfoBuscarAnteproyectoLayout.setVerticalGroup(
            pnlInfoBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoBuscarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoBuscarAnteproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCodigoTituloAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Códigos y titulos de los anteproyectos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblCodigoTituloAnteproyectos.setModel(modeloTablaCodigoTitulo);
        tblCodigoTituloAnteproyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCodigoTituloAnteproyectosMousePressed(evt);
            }
        });
        panelCodigoTituloAnteproyectos.setViewportView(tblCodigoTituloAnteproyectos);

        pnlMostrarDatosAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del anteproyecto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblDatosAnteproyectos.setModel(modeloDatosAnteproyectoB);
        pnlMostrarDatosAnteproyectos.setViewportView(tblDatosAnteproyectos);

        lblInfoResultado2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado2.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado2.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado2Layout = new javax.swing.GroupLayout(pnlInfoResultado2);
        pnlInfoResultado2.setLayout(pnlInfoResultado2Layout);
        pnlInfoResultado2Layout.setHorizontalGroup(
            pnlInfoResultado2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlInfoResultado2Layout.setVerticalGroup(
            pnlInfoResultado2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlCodigoAnteproyecto.setBackground(new java.awt.Color(251, 248, 248));
        pnlCodigoAnteproyecto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Código para buscar anteproyecto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

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

        javax.swing.GroupLayout pnlBuscarAnteproyectoLayout = new javax.swing.GroupLayout(pnlBuscarAnteproyecto);
        pnlBuscarAnteproyecto.setLayout(pnlBuscarAnteproyectoLayout);
        pnlBuscarAnteproyectoLayout.setHorizontalGroup(
            pnlBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoBuscarAnteproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addComponent(panelCodigoTituloAnteproyectos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlCodigoAnteproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMostrarDatosAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addGroup(pnlBuscarAnteproyectoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlBuscarAnteproyectoLayout.setVerticalGroup(
            pnlBuscarAnteproyectoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarAnteproyectoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoBuscarAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCodigoAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(pnlMostrarDatosAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCodigoTituloAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pnlFormulariosEvaluador.addTab("Buscar un anteproyecto", pnlBuscarAnteproyecto);

        pnlInfoIngresarConcepto.setBackground(new java.awt.Color(102, 153, 255));
        pnlInfoIngresarConcepto.setPreferredSize(new java.awt.Dimension(186, 56));

        lblInfoIngresarConcepto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInfoIngresarConcepto.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoIngresarConcepto.setText("Ingresar Concepto");

        javax.swing.GroupLayout pnlInfoIngresarConceptoLayout = new javax.swing.GroupLayout(pnlInfoIngresarConcepto);
        pnlInfoIngresarConcepto.setLayout(pnlInfoIngresarConceptoLayout);
        pnlInfoIngresarConceptoLayout.setHorizontalGroup(
            pnlInfoIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoIngresarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoIngresarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoIngresarConceptoLayout.setVerticalGroup(
            pnlInfoIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoIngresarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoIngresarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlDatosIngresarConcepto.setBackground(new java.awt.Color(251, 248, 248));
        pnlDatosIngresarConcepto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos para ingresar un concepto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        lblCodigoAI.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigoAI.setText("Código del anteproyecto");

        lblInfoConceptoI.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblInfoConceptoI.setText("Seleccione un concepto");

        cbConceptosI.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cbConceptosI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Aprobado", "2. No aprobado" }));
        cbConceptosI.setSelectedIndex(1);
        cbConceptosI.setToolTipText("");

        btnVerCodigosAI.setText("Ver códigos");
        btnVerCodigosAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCodigosAIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosIngresarConceptoLayout = new javax.swing.GroupLayout(pnlDatosIngresarConcepto);
        pnlDatosIngresarConcepto.setLayout(pnlDatosIngresarConceptoLayout);
        pnlDatosIngresarConceptoLayout.setHorizontalGroup(
            pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosIngresarConceptoLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCodigoAI)
                    .addComponent(lblInfoConceptoI))
                .addGap(10, 10, 10)
                .addGroup(pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbConceptosI, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfCodigoAI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerCodigosAI)
                .addContainerGap(436, Short.MAX_VALUE))
        );
        pnlDatosIngresarConceptoLayout.setVerticalGroup(
            pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosIngresarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosIngresarConceptoLayout.createSequentialGroup()
                        .addComponent(lblCodigoAI)
                        .addGap(48, 48, 48)
                        .addGroup(pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblInfoConceptoI)
                            .addComponent(cbConceptosI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDatosIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfCodigoAI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVerCodigosAI)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarI.setText("GUARDAR");
        btnGuardarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarIActionPerformed(evt);
            }
        });

        btnCancelarI.setText("CANCELAR");
        btnCancelarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarIActionPerformed(evt);
            }
        });

        pnlCodigoTituloAntI.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccione un código:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblCodigoTituloAnteproyectosI.setModel(modeloTablaCodigoTitulo);
        tblCodigoTituloAnteproyectosI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCodigoTituloAnteproyectosIMousePressed(evt);
            }
        });
        pnlCodigoTituloAntI.setViewportView(tblCodigoTituloAnteproyectosI);

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

        javax.swing.GroupLayout pnlFormularioIngresarConceptoLayout = new javax.swing.GroupLayout(pnlFormularioIngresarConcepto);
        pnlFormularioIngresarConcepto.setLayout(pnlFormularioIngresarConceptoLayout);
        pnlFormularioIngresarConceptoLayout.setHorizontalGroup(
            pnlFormularioIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioIngresarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosIngresarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfoIngresarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioIngresarConceptoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardarI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarI))
                    .addComponent(pnlCodigoTituloAntI)
                    .addGroup(pnlFormularioIngresarConceptoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlFormularioIngresarConceptoLayout.setVerticalGroup(
            pnlFormularioIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioIngresarConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoIngresarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatosIngresarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCodigoTituloAntI, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(pnlFormularioIngresarConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarI)
                    .addComponent(btnCancelarI))
                .addContainerGap())
        );

        pnlDatosIngresarConcepto.getAccessibleContext().setAccessibleName("Datos para ingresar concepto");

        pnlFormulariosEvaluador.addTab("Ingresar un concepto", pnlFormularioIngresarConcepto);

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
                .addContainerGap(742, Short.MAX_VALUE))
        );
        pnlInfoListarAnteproyectosLayout.setVerticalGroup(
            pnlInfoListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoListarAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlListadoAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado de anteproyectos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblListadoAnteproyectos.setModel(modeloDatosAnteproyectoL);
        pnlListadoAnteproyectos.setViewportView(tblListadoAnteproyectos);

        btnListar.setText("LISTAR");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListarAnteproyectosLayout = new javax.swing.GroupLayout(pnlListarAnteproyectos);
        pnlListarAnteproyectos.setLayout(pnlListarAnteproyectosLayout);
        pnlListarAnteproyectosLayout.setHorizontalGroup(
            pnlListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addComponent(pnlListadoAnteproyectos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListarAnteproyectosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnListar)))
                .addContainerGap())
        );
        pnlListarAnteproyectosLayout.setVerticalGroup(
            pnlListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlListadoAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(btnListar)
                .addContainerGap())
        );

        pnlFormulariosEvaluador.addTab("Listar los anteproyectos", pnlListarAnteproyectos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulariosEvaluador, javax.swing.GroupLayout.DEFAULT_SIZE, 1130, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulariosEvaluador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerCodigosAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCodigosAIActionPerformed
        if(this.tblCodigoTituloAnteproyectosI.getColumnCount() == 0){
            try {
                cargarInfoAnteproyectos(modeloTablaCodigoTitulo, rutaAnteproyectoCodigoTitulo, 2, nombreColumnasCodigoTitutlo);
            } catch (IOException ex) {
                Logger.getLogger(InterfazEvaluador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("AVISO. Ya se cargaron los datos en la tabla.");
        }
    }//GEN-LAST:event_btnVerCodigosAIActionPerformed

    private void btnGuardarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarIActionPerformed
        if(!hayCamposVaciosEnIngresarConcepto()){
            try{
                ingresarConcepto();
                limpiarCamposEnIngresarConcepto();
            } catch (RemoteException ex) {
                Logger.getLogger(InterfazAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarIActionPerformed

    private void btnCancelarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarIActionPerformed
        limpiarCamposEnIngresarConcepto();
        this.lblInfoResultado1.setText("");
    }//GEN-LAST:event_btnCancelarIActionPerformed

    private void tblCodigoTituloAnteproyectosIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigoTituloAnteproyectosIMousePressed
        String dato = String.valueOf(this.modeloTablaCodigoTitulo.getValueAt(this.tblCodigoTituloAnteproyectosI.getSelectedRow(),0));
        limpiarCamposEnIngresarConcepto();
        System.out.println("DATOO "+ dato);
        this.jtfCodigoAI.setText(dato);
        this.lblInfoResultado1.setText("");
    }//GEN-LAST:event_tblCodigoTituloAnteproyectosIMousePressed

    private void tblCodigoTituloAnteproyectosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigoTituloAnteproyectosMousePressed
        String dato = String.valueOf(this.modeloTablaCodigoTitulo.getValueAt(this.tblCodigoTituloAnteproyectos.getSelectedRow(),0));
        this.jtfCodigoAB.setText(dato);
        this.lblInfoResultado2.setText("");
    }//GEN-LAST:event_tblCodigoTituloAnteproyectosMousePressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if(tblCodigoTituloAnteproyectos.getColumnCount() == 0){
            if(!hayCamposVaciosEnBuscarAnteproyecto()){
                try {
                    buscarAnteproyecto();
                    this.jtfCodigoAB.setText("");
                } catch (RemoteException ex) {
                    Logger.getLogger(InterfazAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            System.out.println("AVISO. Ya se cargaron los datos en la tabla.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnVerCodigosBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCodigosBActionPerformed
        if(tblCodigoTituloAnteproyectos.getColumnCount() == 0){
            try {
                cargarInfoAnteproyectos(modeloTablaCodigoTitulo, rutaAnteproyectoCodigoTitulo, 2, nombreColumnasCodigoTitutlo);
            } catch (IOException ex) {
                Logger.getLogger(InterfazAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("AVISO. Ya se cargaron los datos en la tabla.");
        }
    }//GEN-LAST:event_btnVerCodigosBActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        if(tblListadoAnteproyectos.getColumnCount() == 0){    
            try {
                cargarInfoAnteproyectos(modeloDatosAnteproyectoL, rutaAnteproyecto, 12, nombreColumnasAnteproyecto);
            } catch (IOException ex) {
                Logger.getLogger(InterfazAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("AVISO. Ya se cargaron los datos en la tabla.");
        }
    }//GEN-LAST:event_btnListarActionPerformed

    /**
     * @param args the command line arguments
     */
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
    //            java.util.logging.Logger.getLogger(InterfazEvaluador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //        } catch (InstantiationException ex) {
    //            java.util.logging.Logger.getLogger(InterfazEvaluador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //        } catch (IllegalAccessException ex) {
    //            java.util.logging.Logger.getLogger(InterfazEvaluador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
    //            java.util.logging.Logger.getLogger(InterfazEvaluador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    //        }
    //        //</editor-fold>
    //
    //        /* Create and display the form */
    //        java.awt.EventQueue.invokeLater(new Runnable() {
    //            public void run() {
    //                try {
    //                    new InterfazEvaluador().setVisible(true);
    //                } catch (RemoteException ex) {
    //                    Logger.getLogger(InterfazEvaluador.class.getName()).log(Level.SEVERE, null, ex);
    //                }
    //            }
    //        });
    //    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarI;
    private javax.swing.JButton btnGuardarI;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnVerCodigosAI;
    private javax.swing.JButton btnVerCodigosB;
    private javax.swing.JComboBox<String> cbConceptosI;
    private javax.swing.JTextField jtfCodigoAB;
    private javax.swing.JTextField jtfCodigoAI;
    private javax.swing.JLabel lblCodigoAB;
    private javax.swing.JLabel lblCodigoAI;
    private javax.swing.JLabel lblInfoBuscarAnteproyecto;
    private javax.swing.JLabel lblInfoConceptoI;
    private javax.swing.JLabel lblInfoIngresarConcepto;
    private javax.swing.JLabel lblInfoListarAnteproyectos;
    private javax.swing.JLabel lblInfoResultado1;
    private javax.swing.JLabel lblInfoResultado2;
    private javax.swing.JScrollPane panelCodigoTituloAnteproyectos;
    private javax.swing.JPanel pnlBuscarAnteproyecto;
    private javax.swing.JPanel pnlCodigoAnteproyecto;
    private javax.swing.JScrollPane pnlCodigoTituloAntI;
    private javax.swing.JPanel pnlDatosIngresarConcepto;
    private javax.swing.JPanel pnlFormularioIngresarConcepto;
    private javax.swing.JTabbedPane pnlFormulariosEvaluador;
    private javax.swing.JPanel pnlInfoBuscarAnteproyecto;
    private javax.swing.JPanel pnlInfoIngresarConcepto;
    private javax.swing.JPanel pnlInfoListarAnteproyectos;
    private javax.swing.JPanel pnlInfoResultado1;
    private javax.swing.JPanel pnlInfoResultado2;
    private javax.swing.JScrollPane pnlListadoAnteproyectos;
    private javax.swing.JPanel pnlListarAnteproyectos;
    private javax.swing.JScrollPane pnlMostrarDatosAnteproyectos;
    private javax.swing.JTable tblCodigoTituloAnteproyectos;
    private javax.swing.JTable tblCodigoTituloAnteproyectosI;
    private javax.swing.JTable tblDatosAnteproyectos;
    private javax.swing.JTable tblListadoAnteproyectos;
    // End of variables declaration//GEN-END:variables
}
