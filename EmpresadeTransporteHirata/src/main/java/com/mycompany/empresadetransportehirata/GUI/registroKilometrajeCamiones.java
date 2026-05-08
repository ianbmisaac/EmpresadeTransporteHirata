/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.CamionDAO;
import com.mycompany.empresadetransportehirata.Data.ConductorDAO;
import com.mycompany.empresadetransportehirata.Data.MantenimientoDAO;
import com.mycompany.empresadetransportehirata.Data.RegKmCamionesDAO;
import com.mycompany.empresadetransportehirata.Logica.Camion;
import com.mycompany.empresadetransportehirata.Logica.Conductor;
import com.mycompany.empresadetransportehirata.Logica.Mantenimiento;
import com.mycompany.empresadetransportehirata.Logica.regKmCamiones;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de registro de kilometraje por camión.
 *
 * Actualmente contiene estructura visual base para capturar identificación,
 * datos de recorrido y visualizar registros en tabla.
 */
public class registroKilometrajeCamiones extends javax.swing.JInternalFrame {

    private static final DateTimeFormatter FORMATO_FECHA_REGISTRO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String rutConductorSesion;

    /**
     * Crea la ventana sin contexto de conductor en sesión.
     */
    public registroKilometrajeCamiones() {
        this(null);
    }

    /**
     * Crea la ventana y carga solo la información asociada al conductor de la
     * sesión.
     *
     * @param rutConductorSesion rut del conductor autenticado
     */
    public registroKilometrajeCamiones(String rutConductorSesion) {
        this.rutConductorSesion = rutConductorSesion;
        initComponents();
        this.setClosable(true);
        this.setTitle("Registro de Kilometraje");
        this.setLocation(180, 90);
        aplicarEstiloVisual();
        cargarConductorSesion();
        cargarCamionesEnCombo();
        cargarTabla();
        configurarMenuEmergenteTabla();
        actualizarSpinnerConCamionSeleccionado();
    }

    /**
     * Aplica la configuración visual principal de la pantalla.
     */
    private void aplicarEstiloVisual() {
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
                "Identificación",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
                colorTitulo));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2),
                "Datos del Recorrido",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
                colorTitulo));

        jLabel1.setForeground(colorTitulo);
        jLabel2.setForeground(colorTitulo);
        jLabel3.setForeground(colorTitulo);
        jLabel4.setForeground(colorTitulo);

        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        estilizarCombo(camion_cb, colorCampo, colorBordeCampo);
        estilizarEtiquetaCampo(conductor_lb, colorCampo, colorBordeCampo);
        estilizarCampoTexto(observacion_txt, colorCampo, colorBordeCampo);

        kmagregado_spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9_999_999, 1));
        kmagregado_spinner.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));

        if (kmagregado_spinner.getEditor() instanceof javax.swing.JSpinner.DefaultEditor) {
            javax.swing.JFormattedTextField spinnerField = ((javax.swing.JSpinner.DefaultEditor) kmagregado_spinner.getEditor()).getTextField();
            spinnerField.setBackground(colorCampo);
            spinnerField.setForeground(new java.awt.Color(35, 35, 35));
            spinnerField.setCaretColor(new java.awt.Color(40, 120, 180));
            spinnerField.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
            spinnerField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        }

        guardar_bt.setToolTipText("Actualizar el kilometraje del camión seleccionado");
        cancelar_bt.setToolTipText("Limpiar los campos del formulario");
        camion_cb.setToolTipText("Seleccione el camión cuyo kilometraje desea registrar");
        conductor_lb.setToolTipText("Conductor que tiene la sesión activa");
        observacion_txt.setToolTipText("Ingrese observaciones sobre el recorrido realizado");
        kmagregado_spinner.setToolTipText("Ingrese el nuevo kilometraje actual del camión");

        estilizarBoton(guardar_bt, new java.awt.Color(34, 139, 34), new java.awt.Color(20, 100, 20));
        estilizarBoton(cancelar_bt, new java.awt.Color(70, 130, 180), new java.awt.Color(40, 90, 140));

        estilizarTabla();
    }

    /**
     * Aplica estilo a un combo box del formulario.
     *
     * @param combo combo a estilizar
     * @param colorCampo color de fondo del campo
     * @param colorBordeCampo color del borde del campo
     */
    private void estilizarCombo(javax.swing.JComboBox<String> combo, java.awt.Color colorCampo, java.awt.Color colorBordeCampo) {
        combo.setBackground(colorCampo);
        combo.setForeground(new java.awt.Color(39, 76, 119));
        combo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        combo.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
    }

    /**
     * Aplica estilo visual a un campo de texto.
     *
     * @param campo campo a estilizar
     * @param colorCampo color de fondo del campo
     * @param colorBordeCampo color del borde del campo
     */
    private void estilizarCampoTexto(javax.swing.JTextField campo, java.awt.Color colorCampo, java.awt.Color colorBordeCampo) {
        campo.setBackground(colorCampo);
        campo.setForeground(new java.awt.Color(35, 35, 35));
        campo.setCaretColor(new java.awt.Color(40, 120, 180));
        campo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        campo.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
    }

    /**
     * Aplica estilo visual a la etiqueta que muestra el conductor de la sesión.
     *
     * @param etiqueta etiqueta a estilizar
     * @param colorCampo color de fondo
     * @param colorBordeCampo color del borde
     */
    private void estilizarEtiquetaCampo(javax.swing.JLabel etiqueta, java.awt.Color colorCampo, java.awt.Color colorBordeCampo) {
        etiqueta.setOpaque(true);
        etiqueta.setBackground(colorCampo);
        etiqueta.setForeground(new java.awt.Color(35, 35, 35));
        etiqueta.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        etiqueta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    /**
     * Aplica estilo a un botón de la pantalla.
     *
     * @param boton botón a estilizar
     * @param colorFondo color principal del botón
     * @param colorBorde color del borde
     */
    private void estilizarBoton(javax.swing.JButton boton, java.awt.Color colorFondo, java.awt.Color colorBorde) {
        boton.setBackground(colorFondo);
        boton.setForeground(java.awt.Color.WHITE);
        boton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        boton.setBorder(javax.swing.BorderFactory.createLineBorder(colorBorde, 2, true));
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * Configura la apariencia y el comportamiento visual de la tabla.
     */
    private void estilizarTabla() {
        jTable1.setRowHeight(26);
        jTable1.setGridColor(new java.awt.Color(210, 210, 210));
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(false);
        jTable1.setSelectionBackground(new java.awt.Color(100, 149, 237));
        jTable1.setSelectionForeground(java.awt.Color.WHITE);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setFillsViewportHeight(true);
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jTable1.getTableHeader().setBackground(new java.awt.Color(70, 130, 180));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2));

        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
    }

    /**
     * Configura el menú contextual de la tabla con acciones de detalle,
     * selección y recarga.
     */
    private void configurarMenuEmergenteTabla() {
        JPopupMenu menuTabla = new JPopupMenu();

        JMenuItem opcionVer = new JMenuItem("Ver detalles");
        opcionVer.addActionListener(e -> mostrarDetallesRegistroSeleccionado());
        menuTabla.add(opcionVer);

        menuTabla.addSeparator();

        JMenuItem opcionSeleccionar = new JMenuItem("Seleccionar camión");
        opcionSeleccionar.addActionListener(e -> seleccionarCamionDesdeTabla());
        menuTabla.add(opcionSeleccionar);

        JMenuItem opcionRecargar = new JMenuItem("Recargar tabla");
        opcionRecargar.addActionListener(e -> recargarDatosDesdeMenu());
        menuTabla.add(opcionRecargar);

        jTable1.setComponentPopupMenu(menuTabla);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                seleccionarFilaEnPopup(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                seleccionarFilaEnPopup(e);
            }
        });
    }

    /**
     * Selecciona la fila apuntada por el click derecho antes de mostrar el menú
     * contextual.
     *
     * @param evento evento del mouse sobre la tabla
     */
    private void seleccionarFilaEnPopup(java.awt.event.MouseEvent evento) {
        if (!evento.isPopupTrigger()) {
            return;
        }

        int fila = jTable1.rowAtPoint(evento.getPoint());
        if (fila >= 0) {
            jTable1.setRowSelectionInterval(fila, fila);
        }
    }

    /**
     * Muestra un diálogo con los datos del registro seleccionado.
     */
    private void mostrarDetallesRegistroSeleccionado() {
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro en la tabla.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patente = String.valueOf(jTable1.getValueAt(fila, 0));
        String conductor = String.valueOf(jTable1.getValueAt(fila, 1));
        String kilometraje = String.valueOf(jTable1.getValueAt(fila, 2));
        String observacion = String.valueOf(jTable1.getValueAt(fila, 3));
        String fecha = String.valueOf(jTable1.getValueAt(fila, 4));

        StringBuilder detalles = new StringBuilder();
        detalles.append("Camión: ").append(patente).append("\n");
        detalles.append("Conductor: ").append(conductor).append("\n");
        detalles.append("Kilometraje: ").append(kilometraje).append(" km\n");
        detalles.append("Observación: ").append(observacion == null || observacion.isBlank() ? "Sin observaciones" : observacion).append("\n");
        detalles.append("Fecha registro: ").append(fecha == null || fecha.isBlank() ? "Sin fecha registrada" : fecha);

        JOptionPane.showMessageDialog(this, detalles.toString(), "Detalle del registro", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sincroniza la selección del formulario con la fila elegida en la tabla.
     */
    private void seleccionarCamionDesdeTabla() {
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro en la tabla.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patente = String.valueOf(jTable1.getValueAt(fila, 0));
        for (int i = 0; i < camion_cb.getItemCount(); i++) {
            String item = camion_cb.getItemAt(i);
            if (patente.equals(item)) {
                camion_cb.setSelectedIndex(i);
                kmagregado_spinner.requestFocusInWindow();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "No se pudo ubicar el camión seleccionado en el formulario.", "Camión no encontrado", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Recarga combo, tabla y spinner desde los datos persistidos.
     */
    private void recargarDatosDesdeMenu() {
        cargarCamionesEnCombo();
        cargarTabla();
        actualizarSpinnerConCamionSeleccionado();
    }

    /**
     * Ajusta el spinner para reflejar el kilometraje del camión actualmente
     * seleccionado.
     */
    private void actualizarSpinnerConCamionSeleccionado() {
        if (camionSeleccionado != null) {
            kmagregado_spinner.setValue(camionSeleccionado.getKmInicial());
        } else {
            kmagregado_spinner.setValue(0);
        }
    }

    /**
     * Carga el conductor autenticado y actualiza la etiqueta visible.
     */
    private void cargarConductorSesion() {
        conductorSesion = obtenerConductorSesion();
        conductor_lb.setText(obtenerNombreConductorSesion());
    }

    /**
     * Busca la entidad del conductor autenticado en base de datos.
     *
     * @return conductor de la sesión o null si no se pudo resolver
     */
    private Conductor obtenerConductorSesion() {
        if (rutConductorSesion == null || rutConductorSesion.isBlank()) {
            return null;
        }

        ConductorDAO conductorDAO = new ConductorDAO();
        return conductorDAO.buscarConductorPorRut(rutConductorSesion);
    }

    /**
     * Obtiene el nombre completo del conductor autenticado para mostrarlo en la
     * interfaz.
     *
     * @return nombre completo o un texto por defecto si no hay sesión válida
     */
    private String obtenerNombreConductorSesion() {
        if (conductorSesion != null) {
            return conductorSesion.getNombre() + " " + conductorSesion.getApellido();
        }

        return "Conductor no identificado";
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
        camion_cb = new javax.swing.JComboBox<>();
        conductor_lb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        observacion_txt = new javax.swing.JTextField();
        kmagregado_spinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        guardar_bt = new javax.swing.JButton();
        cancelar_bt = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Identificacion"));

        jLabel1.setText("Camion");

        jLabel2.setText("Conductor");

        camion_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camion_cbActionPerformed(evt);
            }
        });

        conductor_lb.setText("Conductor no identificado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(camion_cb, 0, 180, Short.MAX_VALUE)
                    .addComponent(conductor_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(camion_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(conductor_lb))
                .addGap(40, 40, 40))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Recorrido"));

        jLabel3.setText("Kilometraje");

        jLabel4.setText("Observaciones");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(observacion_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(kmagregado_spinner))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(kmagregado_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(observacion_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        guardar_bt.setText("Guardar");
        guardar_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_btActionPerformed(evt);
            }
        });

        cancelar_bt.setText("Cancelar");
        cancelar_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar_btActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(guardar_bt)
                        .addGap(90, 90, 90)
                        .addComponent(cancelar_bt)
                        .addGap(253, 253, 253))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar_bt)
                    .addComponent(cancelar_bt))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Registra un nuevo kilometraje para el camión seleccionado y refresca la
     * vista.
     *
     * @param evt evento del botón guardar
     */
    private void guardar_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_btActionPerformed
        // TODO add your handling code here:
        if (conductorSesion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo identificar al conductor de la sesión activa.", "Sesión inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que haya un camión seleccionado
        if (camion_cb.getSelectedIndex() < 0 || camion_cb.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un camión.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (camionSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener el camión seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int kmNuevo = (Integer) kmagregado_spinner.getValue();
        if (kmNuevo < 0 || kmNuevo <= camionSeleccionado.getKmInicial()) {
            JOptionPane.showMessageDialog(this, "El kilometraje no puede ser menor o igual al actual ("
                    + camionSeleccionado.getKmInicial() + " km).", "Km inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int kmAnterior = camionSeleccionado.getKmInicial();
        String observacion = observacion_txt.getText().trim();
        RegKmCamionesDAO registroDAO = new RegKmCamionesDAO();
        boolean exito = registroDAO.registrarKilometraje(camionSeleccionado, conductorSesion, kmNuevo, observacion);

        if (exito) {
            camionSeleccionado.setKmInicial(kmNuevo);
            JOptionPane.showMessageDialog(this, "Kilometraje actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            verificarMantenimientoPorKm(kmAnterior, kmNuevo, camionSeleccionado.getPatente());

            cargarCamionesEnCombo();
            cargarTabla();
            observacion_txt.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el kilometraje.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_guardar_btActionPerformed

    /**
     * Actualiza el camión seleccionado cuando cambia el valor del combo.
     *
     * @param evt evento del combo de camiones
     */
    private void camion_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camion_cbActionPerformed
        // TODO add your handling code here:
        if (listaCamiones == null) {
            return;
        }
        int idx = camion_cb.getSelectedIndex();
        if (idx >= 0 && listaCamiones != null && idx < listaCamiones.size()) {
            camionSeleccionado = listaCamiones.get(idx);
            actualizarSpinnerConCamionSeleccionado();
            return;
        }
        camionSeleccionado = null;
        actualizarSpinnerConCamionSeleccionado();
    }//GEN-LAST:event_camion_cbActionPerformed

    /**
     * Limpia el formulario y restablece la selección inicial.
     *
     * @param evt evento del botón cancelar
     */
    private void cancelar_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelar_btActionPerformed
        // TODO add your handling code here:
        if (camion_cb.getItemCount() > 0) {
            camion_cb.setSelectedIndex(0);
        }
        actualizarSpinnerConCamionSeleccionado();
        observacion_txt.setText("");
    }//GEN-LAST:event_cancelar_btActionPerformed

    /**
     * Obtiene desde la capa de datos los camiones visibles para el conductor en
     * sesión.
     *
     * @return lista de camiones disponibles en la pantalla
     */
    private List<com.mycompany.empresadetransportehirata.Logica.Camion> obtenerListaCamiones() {
        CamionDAO camionDAO = new CamionDAO();
        return camionDAO.listarCamionesPorRutConductor(rutConductorSesion);
    }

    /**
     * Verifica si el cambio de kilometraje exige generar una revisión general.
     *
     * @param kmAnterior kilometraje previo del camión
     * @param kmNuevo nuevo kilometraje registrado
     * @param patente patente del camión afectado
     */
    private void verificarMantenimientoPorKm(int kmAnterior, int kmNuevo, String patente) {
        // Detecta si se cruzó algún múltiplo de 5000 entre el km anterior y el nuevo
        int umbralAnterior = (kmAnterior / KM_INTERVALO_MANTENIMIENTO);
        int umbralNuevo = (kmNuevo / KM_INTERVALO_MANTENIMIENTO);

        if (umbralNuevo <= umbralAnterior) {
            return;
        }

        JOptionPane.showMessageDialog(this,
                "⚠ El camión " + patente + " ha superado " + (umbralNuevo * KM_INTERVALO_MANTENIMIENTO) + " km.\n"
                + "Se recomienda una revisión general.",
                "Alerta de Mantenimiento",
                JOptionPane.WARNING_MESSAGE);

        MantenimientoDAO dataM = new MantenimientoDAO();
        if (dataM.tieneCitaActiva(patente)) {
            JOptionPane.showMessageDialog(this,
                    "El camión ya tiene una cita pendiente o en proceso.\nNo se agendará una nueva.",
                    "Cita ya existente",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Mantenimiento cita = new Mantenimiento();
        cita.setCamion(patente);
        cita.setTipoMantenimiento("Revisión General");
        cita.setFecha(new java.sql.Date(System.currentTimeMillis()));
        cita.setKmActual(kmNuevo);
        cita.setCosto(30000);
        cita.setEstado(0);
        cita.setObservacion("Cita generada automáticamente por alcance de " + kmNuevo + " km.");
        cita.setTaller("Taller Central");

        if (dataM.agregar(cita)) {
            JOptionPane.showMessageDialog(this,
                    "Se agendó automáticamente una cita de revisión general.",
                    "Cita agendada",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo agendar la cita automáticamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga en el combo solo los camiones disponibles para la sesión actual.
     */
    private void cargarCamionesEnCombo() {
        String patenteSeleccionada = camionSeleccionado != null
                ? camionSeleccionado.getPatente()
                : (String) camion_cb.getSelectedItem();

        camion_cb.removeAllItems();
        listaCamiones = obtenerListaCamiones();
        camionSeleccionado = null;
        int indiceSeleccionado = -1;

        for (int i = 0; i < listaCamiones.size(); i++) {
            Camion c = listaCamiones.get(i);
            camion_cb.addItem(c.getPatente());
            if (patenteSeleccionada != null && patenteSeleccionada.equals(c.getPatente())) {
                indiceSeleccionado = i;
            }
        }

        if (!listaCamiones.isEmpty()) {
            int indiceFinal = indiceSeleccionado >= 0 ? indiceSeleccionado : 0;
            camion_cb.setSelectedIndex(indiceFinal);
            camionSeleccionado = listaCamiones.get(indiceFinal);
        }
    }

    /**
     * Refresca la tabla con el último registro de kilometraje por camión.
     */
    private void cargarTabla() {
        Object[] nombresColumnas = {"Camión", "Conductor", "Kilometraje", "Observaciones", "Fecha registro"};
        DefaultTableModel modelo = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        RegKmCamionesDAO registroDAO = new RegKmCamionesDAO();
        for (regKmCamiones registro : registroDAO.listarUltimosRegistrosPorRutConductor(rutConductorSesion)) {
            Camion c = registro.getCamion();
            Conductor conductor = registro.getCamionero();
            Object[] fila = {
                c.getPatente(),
                conductor != null ? conductor.getNombre() + " " + conductor.getApellido() : "Sin conductor",
                registro.getKilometraje(),
                registro.getObservacion() != null ? registro.getObservacion() : "",
                formatearFechaRegistro(registro)
            };
            modelo.addRow(fila);
        }
        jTable1.setModel(modelo);
    }

    /**
     * Formatea la fecha del registro para su visualización en la tabla.
     *
     * @param registro registro a formatear
     * @return fecha formateada o cadena vacía si no existe fecha
     */
    private String formatearFechaRegistro(regKmCamiones registro) {
        if (registro.getFecha() == null) {
            return "";
        }
        return FORMATO_FECHA_REGISTRO.format(registro.getFecha().toLocalDateTime());
    }

    private static final int KM_INTERVALO_MANTENIMIENTO = 5000;
    private Camion camionSeleccionado;
    private List<Camion> listaCamiones;
    private Conductor conductorSesion;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> camion_cb;
    private javax.swing.JButton cancelar_bt;
    private javax.swing.JLabel conductor_lb;
    private javax.swing.JButton guardar_bt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner kmagregado_spinner;
    private javax.swing.JTextField observacion_txt;
    // End of variables declaration//GEN-END:variables
}
