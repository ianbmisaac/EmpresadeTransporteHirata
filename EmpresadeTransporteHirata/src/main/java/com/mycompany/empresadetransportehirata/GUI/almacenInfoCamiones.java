/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.CamionDAO;
import com.mycompany.empresadetransportehirata.Data.ConductorDAO;
import com.mycompany.empresadetransportehirata.Logica.Camion;
import com.mycompany.empresadetransportehirata.Logica.Conductor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de gestión de camiones.
 *
 * <p>Permite registrar, editar, eliminar y consultar camiones junto con su
 * conductor asignado.</p>
 */
public class almacenInfoCamiones extends javax.swing.JInternalFrame {

    private int idEditando = -1; // -1 = modo inserción, >0 = modo edición

/**
 * Inicializa la ventana, aplica estilo visual, carga datos y configura el
 * menú contextual de la tabla.
 */
public almacenInfoCamiones() {
    initComponents();
    setLocation(100, 50);
    setTitle("Gestión de Camiones");

    // ── Paleta general ──────────────────────────────────────────
    java.awt.Color fondoPrincipal = new java.awt.Color(246, 252, 255);
    java.awt.Color fondoPanel = new java.awt.Color(233, 245, 255);
    java.awt.Color bordePanel = new java.awt.Color(120, 170, 210);
    java.awt.Color textoTitulo = new java.awt.Color(35, 86, 138);
    java.awt.Color fondoCampo = new java.awt.Color(255, 255, 255);

    getContentPane().setBackground(fondoPrincipal);

    jPanel1.setBackground(fondoPanel);
    jPanel2.setBackground(fondoPanel);
    jPanel3.setBackground(fondoPanel);

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
        javax.swing.BorderFactory.createLineBorder(bordePanel, 2),
        "Camiones",
        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
        textoTitulo
    ));

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
        javax.swing.BorderFactory.createLineBorder(bordePanel, 2),
        "ID",
        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
        textoTitulo
    ));

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
        javax.swing.BorderFactory.createLineBorder(bordePanel, 2),
        "Conductor Asignado",
        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        javax.swing.border.TitledBorder.DEFAULT_POSITION,
        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
        textoTitulo
    ));

    jLabel1.setForeground(textoTitulo);
    jLabel2.setForeground(textoTitulo);
    jLabel3.setForeground(textoTitulo);
    jLabel4.setForeground(textoTitulo);
    jLabel5.setForeground(textoTitulo);
    jLabel6.setForeground(textoTitulo);
    lblIdCamion.setForeground(new java.awt.Color(45, 90, 130));
    lblIdCamion.setFont(lblIdCamion.getFont().deriveFont(java.awt.Font.BOLD));

    txtPatente.setBackground(fondoCampo);
    txtPatente.setForeground(new java.awt.Color(35, 35, 35));
    txtPatente.setCaretColor(new java.awt.Color(40, 120, 180));

    comboMarca.setBackground(fondoCampo);
    comboModelo.setBackground(fondoCampo);
    comboConductor.setBackground(fondoCampo);
    spinnerKmInicial.getEditor().getComponent(0).setBackground(fondoCampo);

    jScrollPane1.getViewport().setBackground(new java.awt.Color(248, 252, 255));

    // ── Botones ──────────────────────────────────────────────────
    btGuardar.setBackground(new java.awt.Color(34, 139, 34));
    btGuardar.setForeground(java.awt.Color.WHITE);
    btGuardar.setFont(btGuardar.getFont().deriveFont(java.awt.Font.BOLD));
    btGuardar.setToolTipText("Guardar el camión en la base de datos");

    jButton2.setBackground(new java.awt.Color(255, 140, 0));
    jButton2.setForeground(java.awt.Color.WHITE);
    jButton2.setFont(jButton2.getFont().deriveFont(java.awt.Font.BOLD));
    jButton2.setToolTipText("Editar el camión seleccionado en la tabla");

    jButton3.setBackground(new java.awt.Color(178, 34, 34));
    jButton3.setForeground(java.awt.Color.WHITE);
    jButton3.setFont(jButton3.getFont().deriveFont(java.awt.Font.BOLD));
    jButton3.setToolTipText("Eliminar el camión seleccionado de la base de datos");

    btGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 100, 20), 2));
    jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 120, 0), 2));
    jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 30, 30), 2));

    // ── Tooltips de campos ───────────────────────────────────────
    txtPatente.setToolTipText("Ingrese la patente del camión (ej: AB1234 o BBBB-12)");
    comboMarca.setToolTipText("Seleccione la marca del camión");
    comboModelo.setToolTipText("Seleccione el modelo según la marca elegida");
    yearChooser.setToolTipText("Seleccione el año de fabricación del camión");
    spinnerKmInicial.setToolTipText("Ingrese el kilometraje inicial del camión");
    comboConductor.setToolTipText("Seleccione el conductor asignado (opcional)");

    // ── Spinner con rango válido ─────────────────────────────────
    spinnerKmInicial.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9_999_999, 1));

    // ── Estilo de la tabla ───────────────────────────────────────
    tablaCamiones.setRowHeight(26);
    tablaCamiones.setGridColor(new java.awt.Color(210, 210, 210));
    tablaCamiones.setShowHorizontalLines(true);
    tablaCamiones.setShowVerticalLines(false);
    tablaCamiones.setSelectionBackground(new java.awt.Color(100, 149, 237));
    tablaCamiones.setSelectionForeground(java.awt.Color.WHITE);
    tablaCamiones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    tablaCamiones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tablaCamiones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    tablaCamiones.setFillsViewportHeight(true);

    // Encabezado de la tabla
    tablaCamiones.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    tablaCamiones.getTableHeader().setBackground(new java.awt.Color(70, 130, 180));
    tablaCamiones.getTableHeader().setForeground(java.awt.Color.WHITE);
    tablaCamiones.getTableHeader().setReorderingAllowed(false);
    tablaCamiones.getTableHeader().setPreferredSize(new java.awt.Dimension(tablaCamiones.getTableHeader().getWidth(), 28));

    // Filas con colores alternos
    tablaCamiones.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                setBackground(row % 2 == 0
                    ? java.awt.Color.WHITE
                    : new java.awt.Color(235, 245, 255));
                setForeground(java.awt.Color.DARK_GRAY);
            }
            return this;
        }
    });

    // ── Cargar datos ─────────────────────────────────────────────
    cargarConductoresEnCombo();
    cargarTablaCamiones();

    try {
        CamionDAO dao = new CamionDAO();
        int proximoId = dao.obtenerProximoIdDisponible();
        lblIdCamion.setText("Se asignará el ID número " + proximoId + " al próximo camión");
    } catch (Exception e) {
        lblIdCamion.setText("No se pudo obtener el próximo ID");
    }

    // ── Menú emergente de la tabla ───────────────────────────────
    JPopupMenu menuTabla = new JPopupMenu();

    JMenuItem opcionVer = new JMenuItem("Ver detalles");
    opcionVer.addActionListener(e -> mostrarDetallesCamion());
    menuTabla.add(opcionVer);

    menuTabla.addSeparator();

    JMenuItem opcionEditar = new JMenuItem("Editar");
    opcionEditar.addActionListener(e -> editarCamion());
    menuTabla.add(opcionEditar);

    JMenuItem opcionEliminar = new JMenuItem("Eliminar");
    opcionEliminar.addActionListener(e -> eliminarCamionPopup());
    menuTabla.add(opcionEliminar);

    tablaCamiones.setComponentPopupMenu(menuTabla);

    // ── Botones Editar y Eliminar ────────────────────────────────
    jButton2.addActionListener(e -> {
        if (idEditando != -1) cancelarEdicion();
        else editarCamion();
    });
    jButton3.addActionListener(e -> eliminarCamionPopup());
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtPatente = new javax.swing.JTextField();
        comboModelo = new javax.swing.JComboBox();
        yearChooser = new com.toedter.calendar.JYearChooser();
        jLabel6 = new javax.swing.JLabel();
        spinnerKmInicial = new javax.swing.JSpinner();
        comboMarca = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCamiones = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        comboConductor = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        lblIdCamion = new javax.swing.JLabel();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Camiones"));

        jLabel1.setText("Patente");

        jLabel2.setText("Marca");

        jLabel3.setText("Año");

        jLabel4.setText("Modelo");

        btGuardar.setText("Guardar");
        btGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarActionPerformed(evt);
            }
        });

        jButton2.setText("Editar");

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtPatente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatenteActionPerformed(evt);
            }
        });

        jLabel6.setText("Kilometro Inicial");

        comboMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toyota", "Mercedes-Benz", "Volvo", "Scania", "MAN", "Iveco" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinnerKmInicial, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6))
                            .addComponent(yearChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(comboModelo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPatente)
                            .addComponent(comboMarca, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPatente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerKmInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        // Listener para actualizar modelos según la marca seleccionada
        comboMarca.addActionListener(e -> {
            comboModelo.removeAllItems(); // limpiar modelos anteriores
            String marcaSeleccionada = comboMarca.getSelectedItem().toString();

            switch (marcaSeleccionada) {
                case "Toyota":
                comboModelo.addItem("Hilux");
                comboModelo.addItem("Hino 500");
                comboModelo.addItem("Dyna");
                break;
                case "Mercedes-Benz":
                comboModelo.addItem("Actros");
                comboModelo.addItem("Atego");
                comboModelo.addItem("Axor");
                break;
                case "Volvo":
                comboModelo.addItem("FH16");
                comboModelo.addItem("FMX");
                comboModelo.addItem("VNL");
                break;
                case "Scania":
                comboModelo.addItem("R-Series");
                comboModelo.addItem("S-Series");
                comboModelo.addItem("P-Series");
                break;
                case "MAN":
                comboModelo.addItem("TGX");
                comboModelo.addItem("TGS");
                comboModelo.addItem("CLA");
                break;
                case "Iveco":
                comboModelo.addItem("Stralis");
                comboModelo.addItem("Eurocargo");
                comboModelo.addItem("Daily");
                break;
            }
        });

        tablaCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaCamiones);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Conductor Asignado"));

        jLabel5.setText("Conductor");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboConductor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        lblIdCamion.setText("--------");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIdCamion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblIdCamion)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Guarda un camión nuevo o actualiza uno existente según el modo actual del
     * formulario.
     *
     * @param evt evento del botón guardar
     */
    private void btGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarActionPerformed
    // Validaciones comunes
    String patente = txtPatente.getText().trim();
    if (patente.isEmpty()) {
        JOptionPane.showMessageDialog(this, "La patente no puede estar vacía.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!patente.matches("[A-Za-z0-9\\-]{4,8}")) {
        JOptionPane.showMessageDialog(this, "La patente tiene un formato inválido (ej: AB1234 o BBBB-12).", "Patente inválida", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int anio = yearChooser.getYear();
    int anioActual = Calendar.getInstance().get(Calendar.YEAR);
    if (anio < 1980 || anio > anioActual) {
        JOptionPane.showMessageDialog(this, "El año debe estar entre 1980 y " + anioActual + ".", "Año inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int km = (Integer) spinnerKmInicial.getValue();
    if (km < 0) {
        JOptionPane.showMessageDialog(this, "El kilometraje inicial no puede ser negativo.", "Km inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Construir el objeto Camion
    Camion camion = new Camion();
    camion.setPatente(patente.toUpperCase());
    camion.setMarca((String) comboMarca.getSelectedItem());
    camion.setModelo((String) comboModelo.getSelectedItem());
    camion.setAno(java.sql.Date.valueOf(anio + "-01-01"));
    camion.setKmInicial(km);
    Object seleccionado = comboConductor.getSelectedItem();
    camion.setConductor((seleccionado instanceof Conductor) ? (Conductor) seleccionado : null);

    if (idEditando != -1) {
        // ── MODO EDICIÓN ─────────────────────────────────────────
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Confirma los cambios en el camión con ID " + idEditando + "?",
            "Confirmar edición", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            camion.setId(idEditando);
            CamionDAO dao = new CamionDAO();
            boolean ok = dao.actualizarCamion(camion);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Camión actualizado correctamente.", "Edición exitosa", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaCamiones();
                cancelarEdicion();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el camión.", "Error al editar", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // ── MODO INSERCIÓN ────────────────────────────────────────
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea guardar el camión con los datos ingresados?",
            "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            CamionDAO dao = new CamionDAO();
            int nuevoId = dao.obtenerProximoIdDisponible();
            int resultado = dao.guardarCamion(camion, nuevoId);
            if (resultado != -1) {
                JOptionPane.showMessageDialog(this, "Camión guardado correctamente con ID: " + resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaCamiones();
                txtPatente.setText("");
                comboMarca.setSelectedIndex(0);
                spinnerKmInicial.setValue(0);
                comboConductor.setSelectedItem(null);
                lblIdCamion.setText("Se asignará el ID número " + dao.obtenerProximoIdDisponible() + " al próximo camión");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar. Verifique que la patente no esté duplicada.", "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        spinnerKmInicial.setEnabled(true);
        spinnerKmInicial.setValue(0);

    }
    }//GEN-LAST:event_btGuardarActionPerformed

    /**
     * Evento asociado al campo patente.
     *
     * @param evt evento de acción del campo
     */
    private void txtPatenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPatenteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

/**
 * Carga los conductores disponibles en el combo de asignación.
 */
private void cargarConductoresEnCombo() {    try {
        ConductorDAO dao = new ConductorDAO();
        ArrayList<Conductor> lista = dao.listarConductores();

        comboConductor.removeAllItems();
        comboConductor.addItem(null); // opción "sin conductor"

        for (Conductor c : lista) {
            comboConductor.addItem(c);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "No se pudo cargar la lista de conductores: " + ex.getMessage(),
            "Error de conexión",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


/**
 * Recarga la tabla principal con todos los camiones registrados.
 */
private void cargarTablaCamiones() {
    DefaultTableModel modelo = new DefaultTableModel(
        new Object[0][0],
        new String[]{"ID", "Patente", "Marca", "Modelo", "Año", "Km Inicial", "Conductor"}
    );

    try {
        CamionDAO dao = new CamionDAO();
        List<Camion> listaCamiones = dao.listarCamiones();

        for (Camion c : listaCamiones) {
            String conductorNombre = (c.getConductor() != null)
                ? c.getConductor().getNombre() + " " + c.getConductor().getApellido()
                : "Ninguno";

            modelo.addRow(new Object[]{
                c.getId(),
                c.getPatente(),
                c.getMarca(),
                c.getModelo(),
                c.getAno().toString(),
                c.getKmInicial(),
                conductorNombre
            });
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "No se pudo cargar la tabla de camiones: " + ex.getMessage(),
            "Error de conexión",
            JOptionPane.ERROR_MESSAGE
        );
    }

    tablaCamiones.setModel(modelo);
    tablaCamiones.getColumnModel().getColumn(0).setPreferredWidth(50);
    tablaCamiones.getColumnModel().getColumn(1).setPreferredWidth(100);
    tablaCamiones.getColumnModel().getColumn(6).setPreferredWidth(150);
}


/**
 * Muestra un cuadro con el detalle del camión seleccionado en la tabla.
 */
private void mostrarDetallesCamion() {
    int fila = tablaCamiones.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un camión de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("══════════════════════════════\n");
    sb.append("     INFORMACIÓN DEL CAMIÓN\n");
    sb.append("══════════════════════════════\n\n");
    sb.append("ID:         ").append(tablaCamiones.getValueAt(fila, 0)).append("\n");
    sb.append("Patente:    ").append(tablaCamiones.getValueAt(fila, 1)).append("\n");
    sb.append("Marca:      ").append(tablaCamiones.getValueAt(fila, 2)).append("\n");
    sb.append("Modelo:     ").append(tablaCamiones.getValueAt(fila, 3)).append("\n");
    sb.append("Año:        ").append(tablaCamiones.getValueAt(fila, 4)).append("\n");
    sb.append("Km Inicial: ").append(tablaCamiones.getValueAt(fila, 5)).append("\n");
    sb.append("Conductor:  ").append(tablaCamiones.getValueAt(fila, 6)).append("\n");
    JOptionPane.showMessageDialog(this, sb.toString(), "Detalles del Camión", JOptionPane.INFORMATION_MESSAGE);
}


/**
 * Carga en el formulario los datos del camión seleccionado para editarlo.
 */
private void editarCamion() {
    int fila = tablaCamiones.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un camión de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    idEditando = Integer.parseInt(tablaCamiones.getValueAt(fila, 0).toString());
    txtPatente.setText(tablaCamiones.getValueAt(fila, 1).toString());
    comboMarca.setSelectedItem(tablaCamiones.getValueAt(fila, 2).toString());
    comboModelo.setSelectedItem(tablaCamiones.getValueAt(fila, 3).toString());
    try {
        int anio = Integer.parseInt(tablaCamiones.getValueAt(fila, 4).toString().substring(0, 4));
        yearChooser.setYear(anio);
    } catch (Exception ex) { /* ignorar si el formato del año falla */ }
    try {
    spinnerKmInicial.setValue(Integer.parseInt(tablaCamiones.getValueAt(fila, 5).toString()));
    // Bloquear edición del spinner en modo edición
    spinnerKmInicial.setEnabled(false);
} catch (Exception ex) { /* ignorar */ }

    String conductorTabla = tablaCamiones.getValueAt(fila, 6) != null
        ? tablaCamiones.getValueAt(fila, 6).toString()
        : "Ninguno";
    if ("Ninguno".equalsIgnoreCase(conductorTabla)) {
        comboConductor.setSelectedItem(null);
    } else {
        boolean asignado = false;
        for (int i = 0; i < comboConductor.getItemCount(); i++) {
            Object item = comboConductor.getItemAt(i);
            if (item instanceof Conductor c) {
                String nombreCompleto = c.getNombre() + " " + c.getApellido();
                if (nombreCompleto.equalsIgnoreCase(conductorTabla)) {
                    comboConductor.setSelectedIndex(i);
                    asignado = true;
                    break;
                }
            }
        }
        if (!asignado) {
            comboConductor.setSelectedItem(null);
        }
    }

    // Indicadores visuales de modo edición
    lblIdCamion.setText("✏  Editando ID: " + idEditando + "  —  Presione [Guardar] para confirmar o [Editar] para cancelar");
    lblIdCamion.setForeground(new java.awt.Color(178, 34, 34));
    btGuardar.setText("Actualizar");
    btGuardar.setBackground(new java.awt.Color(255, 140, 0));
    btGuardar.setToolTipText("Guardar los cambios del camión con ID " + idEditando);
    jButton2.setText("Cancelar");
    jButton2.setBackground(new java.awt.Color(105, 105, 105));
    txtPatente.requestFocus();
    txtPatente.selectAll();
}


/**
 * Restablece el formulario al modo inserción.
 */
private void cancelarEdicion() {
    idEditando = -1;
    txtPatente.setText("");
    comboMarca.setSelectedIndex(0);
    spinnerKmInicial.setEnabled(true);
    spinnerKmInicial.setValue(0);
    comboConductor.setSelectedItem(null);
    btGuardar.setText("Guardar");
    btGuardar.setBackground(new java.awt.Color(34, 139, 34));
    btGuardar.setToolTipText("Guardar el camión en la base de datos");
    jButton2.setText("Editar");
    jButton2.setBackground(new java.awt.Color(255, 140, 0));
    lblIdCamion.setForeground(java.awt.Color.DARK_GRAY);
    try {
        CamionDAO dao = new CamionDAO();
        lblIdCamion.setText("Se asignará el ID número " + dao.obtenerProximoIdDisponible() + " al próximo camión");
    } catch (Exception ex) {
        lblIdCamion.setText("--------");
    }
}


/**
 * Elimina el camión seleccionado previa confirmación del usuario.
 */
private void eliminarCamionPopup() {
    int fila = tablaCamiones.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un camión de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int id = Integer.parseInt(tablaCamiones.getValueAt(fila, 0).toString());
    String patente = tablaCamiones.getValueAt(fila, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "¿Está seguro de eliminar el camión con patente: " + patente + "?\nEsta acción no se puede deshacer.",
        "Confirmar eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            CamionDAO dao = new CamionDAO();
            boolean ok = dao.eliminarCamion(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Camión \"" + patente + "\" eliminado correctamente.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaCamiones();
                int proximoId = dao.obtenerProximoIdDisponible();
                lblIdCamion.setText("Se asignará el ID número " + proximoId + " al próximo camión");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el camión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGuardar;
    private javax.swing.JComboBox comboConductor;
    private javax.swing.JComboBox<String> comboMarca;
    private javax.swing.JComboBox<String> comboModelo;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdCamion;
    private javax.swing.JSpinner spinnerKmInicial;
    private javax.swing.JTable tablaCamiones;
    private javax.swing.JTextField txtPatente;
    private com.toedter.calendar.JYearChooser yearChooser;
    // End of variables declaration//GEN-END:variables
}
