/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.MantenimientoEquipoDAO;
import com.mycompany.empresadetransportehirata.Logica.MantenimientoEquipo;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class HistorialMantenimientoEquipos extends javax.swing.JInternalFrame {
private static final String ROL_TECNICO_IT = "tecnico_it";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final MantenimientoEquipoDAO historialDAO = new MantenimientoEquipoDAO();
    private final String rolUsuarioSesion;
    private final DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private TableRowSorter<DefaultTableModel> sorterTabla;
    
    
    public HistorialMantenimientoEquipos() {
        this(null);
    }

    public HistorialMantenimientoEquipos(String rolUsuarioSesion) {
        this.rolUsuarioSesion = rolUsuarioSesion;
        initComponents(); 
        setTitle("Historial de Mantenimiento de Equipos");
        configurarCierreVentana();  
        aplicarEstiloVisual(); 

        if (!validarAccesoPorRol()) return;

        configurarTabla();
        configurarFiltros();
        cargarTabla();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buscar_txt = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        registrosVisibles_lb = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaHistorial = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("Buscar");

        buscar_txt.addActionListener(this::buscar_txtActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        registrosVisibles_lb.setText("Registros visibles: 0");

        btnActualizar.setText("Refrescar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnActualizar)
                            .addComponent(btnLimpiar))
                        .addGap(33, 33, 33)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buscar_txt)
                    .addComponent(registrosVisibles_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(registrosVisibles_lb))
                .addGap(27, 27, 27)
                .addComponent(btnActualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaHistorial.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaHistorial);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscar_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscar_txtActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        buscar_txt.setText("");
        buscar_txt.requestFocusInWindow();
    }//GEN-LAST:event_btnLimpiarActionPerformed
private boolean validarAccesoPorRol() {
        if (ROL_TECNICO_IT.equals(rolUsuarioSesion)) return true;
        JOptionPane.showMessageDialog(this, "Solo el Técnico IT puede acceder al historial de equipos.", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        java.awt.EventQueue.invokeLater(this::dispose);
        return false;
    }

    private void configurarTabla() {
        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Equipo", "Tipo Mant.", "Fecha", "Técnico", "Descripción", "Estado"});
        tablaHistorial.setModel(modeloTabla);
        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaHistorial.setRowSorter(sorterTabla);
        tablaHistorial.setRowHeight(22);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
    }

    private void configurarFiltros() {
        buscar_txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
            @Override public void removeUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
            @Override public void changedUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
        });
    }

    private void aplicarFiltrosTabla() {
        if (sorterTabla == null) return;
        List<RowFilter<Object, Object>> filtros = new ArrayList<>();
        String texto = buscar_txt.getText().trim();
        if (!texto.isEmpty()) filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1, 2, 4, 5));
        sorterTabla.setRowFilter(filtros.isEmpty() ? null : RowFilter.andFilter(filtros));
        registrosVisibles_lb.setText("Registros visibles: " + tablaHistorial.getRowCount());
    }

    private void cargarTabla() {
        try {
            modeloTabla.setRowCount(0);
            for (MantenimientoEquipo m : historialDAO.listarHistorial()) {
                modeloTabla.addRow(new Object[]{
                    m.getId(), m.getEquipo(), m.getTipoMantenimiento(),
                    m.getFecha() != null ? m.getFecha().toLocalDate().format(FORMATO_FECHA) : "Sin fecha",
                    m.getTecnico(), m.getDescripcion(), m.getEstado()
                });
            }
            aplicarFiltrosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void aplicarEstiloVisual() {
        // Colores sacados del diseño de tu compañero
        java.awt.Color fondoCeleste = new java.awt.Color(218, 238, 255); 
        java.awt.Color azulTexto = new java.awt.Color(22, 90, 148);
        java.awt.Color azulBoton = new java.awt.Color(80, 140, 200);
        java.awt.Color verdeBoton = new java.awt.Color(70, 170, 70);

        // Pintar el fondo de la ventana
        this.getContentPane().setBackground(fondoCeleste);
        jPanel2.setBackground(fondoCeleste); // Asumiendo que tu panel de arriba se llama jPanel2

        // Ponerle el borde azul oscuro al panel
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(azulTexto, 1),
            "Búsqueda en Historial",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
            azulTexto));

        // Letras azules
        jLabel1.setForeground(azulTexto);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        registrosVisibles_lb.setForeground(azulTexto);

        // Estilizar botón Limpiar (Azulito)
        btnLimpiar.setBackground(azulBoton);
        btnLimpiar.setForeground(java.awt.Color.WHITE);
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnLimpiar.setFocusPainted(false);

        // Estilizar botón Refrescar (Verdecito)
        btnActualizar.setBackground(verdeBoton);
        btnActualizar.setForeground(java.awt.Color.WHITE);
        btnActualizar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);
    }
    private void configurarCierreVentana() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                manejarCierreVentana();
            }
        });
    }

    private void manejarCierreVentana() {
        Object[] opciones = {"Cerrar sesión", "Solo cerrar ventana", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,
                "¿Qué desea hacer con esta ventana?",
                "Sesión",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]);

        if (opcion == 0) { // Cerrar sesión
            java.awt.Window ventanaPadre = javax.swing.SwingUtilities.getWindowAncestor(this);
            java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            } else {
                dispose();
            }
        } else if (opcion == 1) { // Solo cerrar ventana
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel registrosVisibles_lb;
    private javax.swing.JTable tablaHistorial;
    // End of variables declaration//GEN-END:variables
}
