/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import com.mycompany.empresadetransportehirata.Data.ConductorDAO;
import com.mycompany.empresadetransportehirata.Data.consultas;
import com.mycompany.empresadetransportehirata.Logica.Conductor;
import com.mycompany.empresadetransportehirata.Logica.ValidarRut;

/**
 * Pantalla de gestión de conductores.
 *
 * <p>Permite registrar, editar, eliminar y consultar conductores, además de
 * sincronizar la tabla de usuarios para conductores.</p>
 */
public class almacenarInfoConductor extends javax.swing.JInternalFrame {

    private int idEditando = -1;

    /**
     * Inicializa la ventana, aplica estilo y configura tabla, temporizador y
     * menú contextual.
     */
    public almacenarInfoConductor() {
        initComponents();
        setLocation(600, 200);
        setTitle("Gestión de Conductores");

         // ── Paleta general ──────────────────────────────────────────
        java.awt.Color fondoPrincipal = new java.awt.Color(242, 248, 255);
        java.awt.Color fondoPanel = new java.awt.Color(230, 241, 252);
        java.awt.Color colorTitulo = new java.awt.Color(22, 90, 148);
        java.awt.Color colorCampo = new java.awt.Color(250, 253, 255);
        java.awt.Color colorBordeCampo = new java.awt.Color(160, 192, 224);

        getContentPane().setBackground(fondoPrincipal);
        jPanel1.setBackground(fondoPanel);
        jPanel2.setBackground(fondoPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2),
            "Datos Personales",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
            colorTitulo));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2),
            "ID",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
            colorTitulo));

        jLabel5.setForeground(colorTitulo);
        jLabel6.setForeground(colorTitulo);
        jLabel7.setForeground(colorTitulo);
        jLabel8.setForeground(colorTitulo);
        jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel6.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel7.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel8.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        lblId.setForeground(new java.awt.Color(39, 76, 119));
        lblId.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        txtRut.setBackground(colorCampo);
        txtNombre.setBackground(colorCampo);
        txtApellido.setBackground(colorCampo);
        txtRut.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        txtApellido.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));

        cmbLicencia.setBackground(colorCampo);
        cmbLicencia.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        java.awt.Component editorCombo = cmbLicencia.getEditor().getEditorComponent();
        if (editorCombo instanceof javax.swing.JComponent editorComponente) {
            editorComponente.setBackground(colorCampo);
        }

        // ── Botones ──────────────────────────────────────────────────
        btnGuardar.setBackground(new java.awt.Color(34, 139, 34));
        btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setFont(btnGuardar.getFont().deriveFont(java.awt.Font.BOLD));
        btnGuardar.setToolTipText("Guardar el conductor en la base de datos");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 100, 20), 2, true));
        btnGuardar.setFocusPainted(false);

        btnEditar.setBackground(new java.awt.Color(255, 140, 0));
        btnEditar.setForeground(java.awt.Color.WHITE);
        btnEditar.setFont(btnEditar.getFont().deriveFont(java.awt.Font.BOLD));
        btnEditar.setToolTipText("Seleccione un conductor de la tabla para editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 95, 0), 2, true));
        btnEditar.setFocusPainted(false);

        btnEliminar.setBackground(new java.awt.Color(178, 34, 34));
        btnEliminar.setForeground(java.awt.Color.WHITE);
        btnEliminar.setFont(btnEliminar.getFont().deriveFont(java.awt.Font.BOLD));
        btnEliminar.setToolTipText("Eliminar el conductor seleccionado");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 20, 20), 2, true));
        btnEliminar.setFocusPainted(false);

        // ── Estilo de la tabla ───────────────────────────────────────
        TablaConductores.setRowHeight(26);
        TablaConductores.setGridColor(new java.awt.Color(210, 210, 210));
        TablaConductores.setShowHorizontalLines(true);
        TablaConductores.setShowVerticalLines(false);
        TablaConductores.setSelectionBackground(new java.awt.Color(100, 149, 237));
        TablaConductores.setSelectionForeground(java.awt.Color.WHITE);
        TablaConductores.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        TablaConductores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        // Encabezado de la tabla
        TablaConductores.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        TablaConductores.getTableHeader().setBackground(new java.awt.Color(70, 130, 180));
        TablaConductores.getTableHeader().setForeground(java.awt.Color.WHITE);
        TablaConductores.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
        TablaConductores.getTableHeader().setReorderingAllowed(false);
        TablaConductores.setFillsViewportHeight(true);
        jScrollPane2.getViewport().setBackground(java.awt.Color.WHITE);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2));
        
        // Filas con colores alternos
        TablaConductores.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? java.awt.Color.WHITE : new java.awt.Color(235, 245, 255));
                    setForeground(java.awt.Color.DARK_GRAY);
                }
                return this;
            }
        });

         // ── Cargar datos ─────────────────────────────────────────────
        cargarTablaConductores();

        try {
            ConductorDAO dao = new ConductorDAO();
            int proximoId = dao.obtenerProximoIdDisponible();
            lblId.setText("Se asignará el ID número " + proximoId + " al próximo conductor");
        } catch (Exception ex) {
            lblId.setText("No se pudo obtener el próximo ID");
        }

        Timer timer = new Timer(5000, e -> {
            if (idEditando == -1) {
                cargarTablaConductores();
            }
        });
        timer.start();

        // ── Menú emergente de la tabla ───────────────────────────────
        JPopupMenu menuTabla = new JPopupMenu();

        JMenuItem opcionVer = new JMenuItem("Ver detalles");
        opcionVer.addActionListener(e -> mostrarDetallesConductor());
        menuTabla.add(opcionVer);

        menuTabla.addSeparator();

        // ── Botones Editar y Eliminar ────────────────────────────────
        JMenuItem opcionEditar = new JMenuItem("Editar");
        opcionEditar.addActionListener(e -> editarConductor());
        menuTabla.add(opcionEditar);

        JMenuItem opcionEliminar = new JMenuItem("Eliminar");
        opcionEliminar.addActionListener(e -> eliminarConductorPopup());
        menuTabla.add(opcionEliminar);

        TablaConductores.setComponentPopupMenu(menuTabla);
    }
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtRut = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbLicencia = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaConductores = new javax.swing.JTable();

        setClosable(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        lblId.setText("--------");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblId)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblId)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        jLabel5.setText("Rut");

        jLabel6.setText("Nombre");

        jLabel7.setText("Apellido");

        jLabel8.setText("Licencia");

        cmbLicencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A4", "A5" }));

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(51, 51, 51)
                                    .addComponent(btnGuardar))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(22, 22, 22)
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(19, 19, 19)
                                                    .addComponent(jLabel6)))
                                            .addGap(51, 51, 51)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnEditar)
                                        .addComponent(btnEliminar))))
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel8)))
                .addGap(0, 24, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(13, 13, 13)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEditar)
                        .addGap(25, 25, 25)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TablaConductores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TablaConductores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Guarda un conductor nuevo o confirma la edición del conductor en curso.
     *
     * @param evt evento del botón guardar
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

    String rut = txtRut.getText().trim().replace(".", "").toUpperCase();
    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String licencia = (String) cmbLicencia.getSelectedItem();

    if (rut.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!ValidarRut.validar(rut)) {
        JOptionPane.showMessageDialog(this, "RUT inválido. Use formato 12345678-9.", "RUT inválido", JOptionPane.WARNING_MESSAGE);
        txtRut.requestFocus();
        txtRut.selectAll();
        return;
    }

    txtRut.setText(rut);

    try {
        ConductorDAO dao = new ConductorDAO();

        if (idEditando != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirma los cambios en el conductor con ID " + idEditando + "?",
                "Confirmar edición", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            boolean actualizado = dao.editarConductor(new Conductor(idEditando, rut, nombre, apellido, licencia));
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Conductor actualizado correctamente.", "Edición exitosa", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaConductores();
                cancelarEdicion();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el conductor a editar.", "Edición no aplicada", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }

        int proximoId = dao.obtenerProximoIdDisponible();
        int idAsignado = dao.guardarConductor(new Conductor(proximoId, rut, nombre, apellido, licencia), proximoId);

        if (idAsignado == -1) {
            JOptionPane.showMessageDialog(this,
                "Error: el RUT está mal escrito o ya existe.\nEl conductor no se registró.",
                "Error al guardar", JOptionPane.ERROR_MESSAGE);
        } else {
            // Crear usuario en tabla users
            String nombreCompleto = nombre + " " + apellido;
            String passInicial = rut.replace("-", "").substring(0, 4);
            consultas q = new consultas();
            boolean usuarioCreado = q.crearUsuarioConductor(rut, nombreCompleto, passInicial);

            if (usuarioCreado) {
                JOptionPane.showMessageDialog(this,
                    "Conductor registrado con ID: " + idAsignado + "\n"
                    + "Contraseña inicial: " + passInicial,
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Conductor registrado con ID: " + idAsignado + "\n"
                    + "⚠ No se pudo crear el usuario de acceso.",
                    "Registro parcial", JOptionPane.WARNING_MESSAGE);
            }

            txtRut.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            cmbLicencia.setSelectedIndex(0);
            cargarTablaConductores();
            lblId.setText("Se asignará el ID número " + dao.obtenerProximoIdDisponible() + " al próximo conductor");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Elimina el conductor actualmente seleccionado.
     *
     * @param evt evento del botón eliminar
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarConductorSeleccionado();
    }//GEN-LAST:event_btnEliminarActionPerformed

    /**
     * Alterna entre edición y cancelación según el estado actual del
     * formulario.
     *
     * @param evt evento del botón editar
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (idEditando != -1) {
            cancelarEdicion();
        } else {
            editarConductor();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

/**
 * Recarga la tabla con todos los conductores persistidos.
 */
private void cargarTablaConductores() {
    String[] columnas = {"ID", "RUT", "Nombre", "Apellido", "Licencia"};
    DefaultTableModel modelo = new DefaultTableModel(new Object[0][0], columnas);

    try {
        ConductorDAO dao = new ConductorDAO();
        ArrayList<Conductor> lista = dao.listarConductores();

        for (Conductor c : lista) {
            Object[] fila = {
                c.getId(),
                c.getRut(),
                c.getNombre(),
                c.getApellido(),
                c.getLicencia()
            };
            modelo.addRow(fila);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Error al cargar conductores: " + ex.getMessage(),
            "Error de base de datos",
            JOptionPane.ERROR_MESSAGE
        );
    }

    TablaConductores.setModel(modelo);
}


/**
 * Muestra en un diálogo el detalle del conductor seleccionado en la tabla.
 */
private void mostrarDetallesConductor() {
    int fila = TablaConductores.getSelectedRow();
    if (fila != -1) {
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════\n");
        sb.append("   INFORMACIÓN DEL CONDUCTOR\n");
        sb.append("══════════════════════════════\n\n");
        sb.append("ID:        ").append(TablaConductores.getValueAt(fila, 0)).append("\n");
        sb.append("RUT:       ").append(TablaConductores.getValueAt(fila, 1)).append("\n");
        sb.append("Nombre:    ").append(TablaConductores.getValueAt(fila, 2))
          .append(" ").append(TablaConductores.getValueAt(fila, 3)).append("\n");
        sb.append("Licencia:  ").append(TablaConductores.getValueAt(fila, 4)).append("\n");

        JOptionPane.showMessageDialog(
            this,
            sb.toString(),
            "Detalles del Conductor",
            JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        JOptionPane.showMessageDialog(
            this,
            "No hay ninguna fila seleccionada.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
    }
}


/**
 * Lleva el conductor seleccionado al formulario para editarlo.
 */
private void editarConductor() {
    int fila = TablaConductores.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this,
            "Debe seleccionar un conductor de la tabla para editar.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    idEditando = Integer.parseInt(TablaConductores.getValueAt(fila, 0).toString());
    txtRut.setText(TablaConductores.getValueAt(fila, 1).toString());
    txtNombre.setText(TablaConductores.getValueAt(fila, 2).toString());
    txtApellido.setText(TablaConductores.getValueAt(fila, 3).toString());
    cmbLicencia.setSelectedItem(TablaConductores.getValueAt(fila, 4).toString());

    lblId.setText("Editando ID: " + idEditando + " - Presione Guardar para confirmar o Editar para cancelar");
    lblId.setForeground(new java.awt.Color(178, 34, 34));
    btnGuardar.setText("Actualizar");
    btnGuardar.setBackground(new java.awt.Color(255, 140, 0));
    btnGuardar.setToolTipText("Guardar los cambios del conductor con ID " + idEditando);
    btnEditar.setText("Cancelar");
    btnEditar.setBackground(new java.awt.Color(105, 105, 105));
    btnEditar.setToolTipText("Cancelar la edición y volver al modo inserción");
    txtRut.requestFocus();
    txtRut.selectAll();
}


/**
 * Restablece el formulario al modo alta y limpia la edición en curso.
 */
private void cancelarEdicion() {
    idEditando = -1;
    txtRut.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    cmbLicencia.setSelectedIndex(0);
    btnGuardar.setText("Guardar");
    btnGuardar.setBackground(new java.awt.Color(34, 139, 34));
    btnGuardar.setToolTipText("Guardar el conductor en la base de datos");
    btnEditar.setText("Editar");
    btnEditar.setBackground(new java.awt.Color(255, 140, 0));
    btnEditar.setToolTipText("Seleccione un conductor de la tabla para editar");
    lblId.setForeground(java.awt.Color.DARK_GRAY);

    try {
        ConductorDAO dao = new ConductorDAO();
        lblId.setText("Se asignará el ID número " + dao.obtenerProximoIdDisponible() + " al próximo conductor");
    } catch (Exception ex) {
        lblId.setText("--------");
    }
}


/**
 * Dispara la eliminación desde el menú contextual.
 */
private void eliminarConductorPopup() {
    eliminarConductorSeleccionado();
}


/**
 * Elimina el conductor seleccionado y, si corresponde, lo desasigna de sus
 * camiones y elimina su usuario de acceso.
 */
private void eliminarConductorSeleccionado() {
    int filaSeleccionada = TablaConductores.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un conductor en la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(TablaConductores.getValueAt(filaSeleccionada, 0).toString());
    String rutEliminado = TablaConductores.getValueAt(filaSeleccionada, 1).toString();
    String nombreCompleto = TablaConductores.getValueAt(filaSeleccionada, 2).toString() + " "
                          + TablaConductores.getValueAt(filaSeleccionada, 3).toString();

    int confirm = JOptionPane.showConfirmDialog(this,
        "¿Está seguro de eliminar al conductor:\n" + nombreCompleto + "?\n\nEsta acción no se puede deshacer.",
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        ConductorDAO dao = new ConductorDAO();
        int camionesLigados = dao.contarCamionesAsignados(id);
        boolean confirmarDesasignacion = false;

        if (camionesLigados > 0) {
            int confirmRelacion = JOptionPane.showConfirmDialog(this,
                "El conductor está ligado a " + camionesLigados + " camión(es).\n"
                + "Si confirma, se desasignará de esos camiones y luego se eliminará.\n"
                + "¿Desea continuar?",
                "Conductor ligado a camión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmRelacion != JOptionPane.YES_OPTION) return;
            confirmarDesasignacion = true;
        }

        boolean eliminado = dao.eliminarConductor(id, confirmarDesasignacion);
        if (eliminado) {
            // Eliminar también de users
            new consultas().eliminarUsuario(rutEliminado);

            JOptionPane.showMessageDialog(this,
                camionesLigados > 0
                    ? "El conductor fue eliminado y desasignado de sus camiones."
                    : "El conductor fue eliminado correctamente.",
                "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);

            cargarTablaConductores();
            lblId.setText("Se asignará el ID número " + dao.obtenerProximoIdDisponible() + " al próximo conductor");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el conductor a eliminar.", "Eliminación no aplicada", JOptionPane.WARNING_MESSAGE);
        }
    } catch (RuntimeException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
    }
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaConductores;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbLicencia;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblId;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
