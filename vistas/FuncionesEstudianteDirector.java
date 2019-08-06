/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vistas;

import cliente.vistas.FuncionesAdministrador;
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

/**
 *
 * @author Angélica
 */
public final class FuncionesEstudianteDirector extends javax.swing.JFrame {

    /**
     * Creates new form MenuEstudiante
     */
    
    Login objInterfazLogin = null;
    
    DefaultTableModel modeloVerCodigoTitulo;
    DefaultTableModel modeloBuscarListarDatos;
    DefaultTableModel modeloListarDatosBasicos;
    String[] nombreCamposAnteproyecto = {"CÓDIGO", "TITULO", "ESTUDIANTE 1", 
                                           "ESTUDIANTE 2", "EVALUADOR 1", "EVALUADOR 2",
                                           "FECHA DE REVISIÓN", "CONCEPTO", "ESTADO"};
    String[] camposBasicosAnteproyecto = {"CÓDIGO", "TITULO"};
    //obtener datos?
    
    FuncionesEstudianteDirector objEstudianteDirector = null;
    String direccionIpRMIRegistry = "localhost";
    int numPuertoRMIRegistry = 3032;
    
    public FuncionesEstudianteDirector() throws RemoteException {
        this.modeloVerCodigoTitulo = new DefaultTableModel();        
        this.modeloBuscarListarDatos = new DefaultTableModel();
        this.modeloListarDatosBasicos = new DefaultTableModel();
        agregarColumnasAModelo(modeloVerCodigoTitulo, 2, camposBasicosAnteproyecto);
        
        agregarColumnasAModelo(modeloListarDatosBasicos, 2, camposBasicosAnteproyecto);
        initComponents();
        this.lblInfoResultado1.setVisible(false);
        
        objEstudianteDirector = (FuncionesEstudianteDirector)UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry, "ObjetoRemotoEstudianteDirector");
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
            this.lblInfoResultado1.setText("EXITO. Anteproyecto encontrado con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
        }else{
            this.lblInfoResultado1.setText("FALLO. No se encontró el anteproyecto con código: " + pCodigoAnteproyecto + ".");
            this.lblInfoResultado1.setVisible(true);
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
            pObjAnteproyecto.getAtrCodigo(), pObjAnteproyecto.getAtrTitulo(),
            pObjAnteproyecto.getAtrNombreEstudiante1(), pObjAnteproyecto.getAtrNombreEstudiante2(),
            pObjAnteproyecto.getAtrFechaAprobacion(), pObjAnteproyecto.getAtrConcepto(),
            pObjAnteproyecto.getAtrEstado()
        };
        this.modeloBuscarListarDatos.addRow(objDatosAnteproyecto);
    }
        
    public void agregarColumnasAModelo(DefaultTableModel parModelo, int nroColumnas, String[] vectorColumnas){
        for(int j = 0; j<nroColumnas; j++){
            parModelo.addColumn(vectorColumnas[j]);
        }
    }
    
    private void abrirVentanaLogin(){
         try {
            this.objInterfazLogin = new Login();
        } catch (RemoteException ex) {
            Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        this.dispose();
        this.objInterfazLogin.setVisible(true);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulariosEstudianteDirector = new javax.swing.JTabbedPane();
        pnlMostrarAnteproyecto = new javax.swing.JPanel();
        pnlInfoBuscarAnteproyecto = new javax.swing.JPanel();
        lblInfoBuscarAnteproyecto = new javax.swing.JLabel();
        pnlCodigoAnteproyecto = new javax.swing.JPanel();
        lblCodigoAB = new javax.swing.JLabel();
        jtfCodigoAB = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnVerCodigosB = new javax.swing.JButton();
        pnlMostrarDatosAnteproyectos = new javax.swing.JScrollPane();
        tblDatosAnteproyectos = new javax.swing.JTable();
        panelCodigoTituloAnteproyectos = new javax.swing.JScrollPane();
        tblCodigoTituloAnteproyectos = new javax.swing.JTable();
        pnlInfoResultado1 = new javax.swing.JPanel();
        lblInfoResultado1 = new javax.swing.JLabel();
        pnlListadoDeAnteproyectos = new javax.swing.JPanel();
        pnlInfoListarAnteproyectos = new javax.swing.JPanel();
        lblInfoListarAnteproyectos = new javax.swing.JLabel();
        pnlListadoAnteproyectos = new javax.swing.JScrollPane();
        tblListadoAnteproyectos = new javax.swing.JTable();
        btnListar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnCerrarSesionED = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Anteproyectos");

        pnlFormulariosEstudianteDirector.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones Estudiante - Director", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N
        pnlFormulariosEstudianteDirector.setForeground(new java.awt.Color(0, 0, 153));
        pnlFormulariosEstudianteDirector.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlFormulariosEstudianteDirector.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlFormulariosEstudianteDirector.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnlFormulariosEstudianteDirector.setMinimumSize(new java.awt.Dimension(140, 23));
        pnlFormulariosEstudianteDirector.setName(""); // NOI18N
        pnlFormulariosEstudianteDirector.setPreferredSize(new java.awt.Dimension(1120, 575));

        pnlMostrarAnteproyecto.setEnabled(false);

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

        lblInfoResultado1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInfoResultado1.setForeground(new java.awt.Color(0, 0, 153));
        lblInfoResultado1.setText("Mensaje con el resultado de la operación");

        javax.swing.GroupLayout pnlInfoResultado1Layout = new javax.swing.GroupLayout(pnlInfoResultado1);
        pnlInfoResultado1.setLayout(pnlInfoResultado1Layout);
        pnlInfoResultado1Layout.setHorizontalGroup(
            pnlInfoResultado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoResultado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlInfoResultado1Layout.setVerticalGroup(
            pnlInfoResultado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoResultado1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(pnlInfoBuscarAnteproyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addComponent(panelCodigoTituloAnteproyectos)
                    .addComponent(pnlCodigoAnteproyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMostrarAnteproyectoLayout.createSequentialGroup()
                        .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 168, Short.MAX_VALUE)))
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
                .addComponent(pnlInfoResultado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pnlFormulariosEstudianteDirector.addTab("Buscar un anteproyecto", pnlMostrarAnteproyecto);

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
                .addContainerGap(652, Short.MAX_VALUE))
        );
        pnlInfoListarAnteproyectosLayout.setVerticalGroup(
            pnlInfoListarAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoListarAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfoListarAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlListadoAnteproyectos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado de anteproyectos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 153))); // NOI18N

        tblListadoAnteproyectos.setModel(modeloListarDatosBasicos);
        pnlListadoAnteproyectos.setViewportView(tblListadoAnteproyectos);

        btnListar.setText("LISTAR");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListadoDeAnteproyectosLayout = new javax.swing.GroupLayout(pnlListadoDeAnteproyectos);
        pnlListadoDeAnteproyectos.setLayout(pnlListadoDeAnteproyectosLayout);
        pnlListadoDeAnteproyectosLayout.setHorizontalGroup(
            pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnListar))
                    .addComponent(pnlListadoAnteproyectos, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlListadoDeAnteproyectosLayout.setVerticalGroup(
            pnlListadoDeAnteproyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoDeAnteproyectosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInfoListarAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlListadoAnteproyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(btnListar)
                .addContainerGap())
        );

        pnlFormulariosEstudianteDirector.addTab("Listar los anteproyectos", pnlListadoDeAnteproyectos);

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
            .addComponent(pnlFormulariosEstudianteDirector, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFormulariosEstudianteDirector, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
                agregarColumnasAModelo(modeloBuscarListarDatos, 9, nombreCamposAnteproyecto);
                buscarAnteproyecto();
                this.jtfCodigoAB.setText("");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnVerCodigosBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCodigosBActionPerformed
        try {
            cargarInfoAnteproyectos(modeloVerCodigoTitulo, rutaAnteproyectoCodigoTitulo, 2, camposBasicosAnteproyecto);
        } catch (IOException ex) {
            Logger.getLogger(FuncionesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerCodigosBActionPerformed

    private void tblCodigoTituloAnteproyectosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigoTituloAnteproyectosMousePressed
        String dato = String.valueOf(this.modeloVerCodigoTitulo.getValueAt(this.tblCodigoTituloAnteproyectos.getSelectedRow(),0));
        this.jtfCodigoAB.setText(dato);
        this.lblInfoResultado1.setText("");
    }//GEN-LAST:event_tblCodigoTituloAnteproyectosMousePressed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        try {
            cargarInfoAnteproyectos(modeloListarDatosBasicos, rutaAnteproyecto, 12, nombreCamposAnteproyecto);
        } catch (IOException ex) {
            Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnCerrarSesionEDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionEDActionPerformed
        abrirVentanaLogin();
    }//GEN-LAST:event_btnCerrarSesionEDActionPerformed

    /**
     * @param args the command line arguments
     */
//        public static void main(String args[]) {
//            /* Set the Nimbus look and feel */
//            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//             */
//            try {
//                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                    if ("Nimbus".equals(info.getName())) {
//                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                        break;
//                    }
//                }
//            } catch (ClassNotFoundException ex) {
//                java.util.logging.Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//            } catch (InstantiationException ex) {
//                java.util.logging.Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//            } catch (IllegalAccessException ex) {
//                java.util.logging.Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//                java.util.logging.Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//            }
//            //</editor-fold>
//            //</editor-fold>
//    
//            /* Create and display the form */
//            java.awt.EventQueue.invokeLater(new Runnable() {
//                public void run() {
//                    try {
//                        new FuncionesEstudianteDirector().setVisible(true);
//                    } catch (RemoteException ex) {
//                        Logger.getLogger(FuncionesEstudianteDirector.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            });
//        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrarSesionED;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnVerCodigosB;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jtfCodigoAB;
    private javax.swing.JLabel lblCodigoAB;
    private javax.swing.JLabel lblInfoBuscarAnteproyecto;
    private javax.swing.JLabel lblInfoListarAnteproyectos;
    private javax.swing.JLabel lblInfoResultado1;
    private javax.swing.JScrollPane panelCodigoTituloAnteproyectos;
    private javax.swing.JPanel pnlCodigoAnteproyecto;
    private javax.swing.JTabbedPane pnlFormulariosEstudianteDirector;
    private javax.swing.JPanel pnlInfoBuscarAnteproyecto;
    private javax.swing.JPanel pnlInfoListarAnteproyectos;
    private javax.swing.JPanel pnlInfoResultado1;
    private javax.swing.JScrollPane pnlListadoAnteproyectos;
    private javax.swing.JPanel pnlListadoDeAnteproyectos;
    private javax.swing.JPanel pnlMostrarAnteproyecto;
    private javax.swing.JScrollPane pnlMostrarDatosAnteproyectos;
    private javax.swing.JTable tblCodigoTituloAnteproyectos;
    private javax.swing.JTable tblDatosAnteproyectos;
    private javax.swing.JTable tblListadoAnteproyectos;
    // End of variables declaration//GEN-END:variables
}
