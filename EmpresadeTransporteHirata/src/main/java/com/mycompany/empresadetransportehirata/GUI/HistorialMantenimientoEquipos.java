package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.MantenimientoEquipoDAO;
import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.MantenimientoEquipo;
import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Ventana de historial de mantenimientos de equipos de oficina.
 * Permite consultar, buscar, registrar, actualizar y eliminar mantenimientos
 * desde una interfaz con filtros de búsqueda en tiempo real.
 */
public class HistorialMantenimientoEquipos extends javax.swing.JInternalFrame {

    private static final String ROL_TECNICO_IT = "tecnico_it";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final MantenimientoEquipoDAO dao = new MantenimientoEquipoDAO();
    private final String rolUsuarioSesion;

    private final DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private TableRowSorter<DefaultTableModel> sorterTabla;

    /** Crea la ventana sin validación de rol (usa rol null). */
    public HistorialMantenimientoEquipos() {
        this(null);
    }

    /**
     * Crea la ventana y valida que solo el rol indicado pueda operarla.
     * @param rolUsuarioSesion rol del usuario autenticado
     */
    public HistorialMantenimientoEquipos(String rolUsuarioSesion) {
        this.rolUsuarioSesion = rolUsuarioSesion;
        initComponents();
        setTitle("Registro de Mantenimiento de Equipos de Oficina");
        configurarCierreVentana();
        aplicarEstiloVisual();

        if (!validarAccesoPorRol()) return;

        configurarTabla();
        configurarFiltros();
        cargarEquiposEnCombo();
        cargarTabla();
    }

    /** Verifica que el usuario tenga el rol adecuado para usar esta ventana. */
    private boolean validarAccesoPorRol() {
        if (ROL_TECNICO_IT.equals(rolUsuarioSesion)) return true;
        JOptionPane.showMessageDialog(this,
                "Solo el Técnico IT puede acceder al mantenimiento de equipos.",
                "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        java.awt.EventQueue.invokeLater(this::dispose);
        return false;
    }

    /** Configura el modelo de columnas y el ordenamiento de la tabla principal. */
    private void configurarTabla() {
        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Equipo", "Tipo Mant.", "Fecha", "Técnico", "Descripción", "Estado"});
        tablaHistorial.setModel(modeloTabla);
        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaHistorial.setRowSorter(sorterTabla);
        tablaHistorial.setRowHeight(22);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
    }

    /** Vincula el campo de búsqueda con el filtrado en tiempo real de la tabla. */
    private void configurarFiltros() {
        buscar_txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
            @Override public void removeUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
            @Override public void changedUpdate(DocumentEvent e) { aplicarFiltrosTabla(); }
        });
    }

    /** Aplica el filtro de texto sobre la tabla y actualiza el contador de registros visibles. */
    private void aplicarFiltrosTabla() {
        if (sorterTabla == null) return;
        List<RowFilter<Object, Object>> filtros = new ArrayList<>();
        String texto = buscar_txt.getText().trim();
        if (!texto.isEmpty()) filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1, 2, 4, 5));
        sorterTabla.setRowFilter(filtros.isEmpty() ? null : RowFilter.andFilter(filtros));
        registrosVisibles_lb.setText("Registros visibles: " + tablaHistorial.getRowCount());
    }

    /** Carga los equipos registrados en el combo de selección del formulario. */
    private void cargarEquiposEnCombo() {
        equipo_cb.removeAllItems();
        List<EquipoOficina> equipos = dao.listarEquipos();
        if (equipos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay equipos de oficina registrados.\nDebe registrar un equipo primero.",
                    "Sin equipos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (EquipoOficina e : equipos) {
            equipo_cb.addItem(new EquipoComboItem(e.getId(), e.getNombre(), e.getTipo()));
        }
    }

    /** Refresca la tabla de historial con los datos actuales de la BD. */
    private void cargarTabla() {
        try {
            modeloTabla.setRowCount(0);
            for (MantenimientoEquipo m : dao.listarHistorial()) {
                modeloTabla.addRow(new Object[]{
                    m.getId(), m.getNombreEquipo(), m.getTipoMantenimiento(),
                    m.getFecha() != null ? m.getFecha().toLocalDate().format(FORMATO_FECHA) : "Sin fecha",
                    m.getTecnico(), m.getDescripcion(), m.getEstado()
                });
            }
            aplicarFiltrosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar el historial de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Restablece el formulario al modo de registro. */
    private void limpiarCampos() {
        id_txt.setText("");
        if (equipo_cb.getItemCount() > 0) equipo_cb.setSelectedIndex(0);
        tipo_cb.setSelectedIndex(0);
        fecha_dc.setDate(new Date());
        tecnico_txt.setText("");
        descripcion_txt.setText("");
        estado_cb.setSelectedIndex(0);
        tablaHistorial.clearSelection();
        modoEdicion = false;
        guardar_bt.setText("Guardar");
        guardar_bt.setEnabled(true);
        actualizar_bt.setEnabled(false);
        eliminar_bt.setEnabled(false);
    }

    /** Carga los datos de la fila seleccionada en el formulario de edición. */
    private void cargarFormularioDesdeTabla(int fila) {
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        MantenimientoEquipo m = dao.buscarPorId(id);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el registro seleccionado.", "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        id_txt.setText(String.valueOf(m.getId()));
        for (int i = 0; i < equipo_cb.getItemCount(); i++) {
            EquipoComboItem item = equipo_cb.getItemAt(i);
            if (item.getId() == m.getIdEquipo()) {
                equipo_cb.setSelectedIndex(i);
                break;
            }
        }
        tipo_cb.setSelectedItem(m.getTipoMantenimiento());
        fecha_dc.setDate(m.getFecha());
        tecnico_txt.setText(m.getTecnico());
        descripcion_txt.setText(m.getDescripcion());
        estado_cb.setSelectedItem(m.getEstado());
        modoEdicion = true;
        guardar_bt.setText("Guardar");
        guardar_bt.setEnabled(true);
        actualizar_bt.setEnabled(true);
        eliminar_bt.setEnabled(true);
    }

    /** Valida que los campos obligatorios del formulario estén completos. */
    private boolean validarFormulario() {
        if (equipo_cb.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay equipos registrados. Registre un equipo primero.",
                    "Sin equipos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (fecha_dc.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha.", "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            fecha_dc.requestFocus();
            return false;
        }
        if (tecnico_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del técnico.", "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            tecnico_txt.requestFocus();
            return false;
        }
        if (descripcion_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una descripción del mantenimiento.", "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            descripcion_txt.requestFocus();
            return false;
        }
        return true;
    }

    /** Construye una entidad {@link MantenimientoEquipo} con los datos del formulario. */
    private MantenimientoEquipo construirDesdeFormulario() {
        EquipoComboItem eq = (EquipoComboItem) equipo_cb.getSelectedItem();
        MantenimientoEquipo m = new MantenimientoEquipo();
        m.setIdEquipo(eq.getId());
        m.setTipoMantenimiento(tipo_cb.getSelectedItem().toString());
        m.setFecha(new java.sql.Date(fecha_dc.getDate().getTime()));
        m.setTecnico(tecnico_txt.getText().trim());
        m.setDescripcion(descripcion_txt.getText().trim());
        m.setEstado(estado_cb.getSelectedItem().toString());
        if (!id_txt.getText().trim().isEmpty()) {
            m.setId(Integer.parseInt(id_txt.getText().trim()));
        }
        return m;
    }

    private boolean modoEdicion = false;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        id_txt = new javax.swing.JTextField();
        equipo_cb = new javax.swing.JComboBox<>();
        tipo_cb = new javax.swing.JComboBox<>();
        estado_cb = new javax.swing.JComboBox<>();
        fecha_dc = new com.toedter.calendar.JDateChooser();
        tecnico_txt = new javax.swing.JTextField();
        descripcion_txt = new javax.swing.JTextField();
        guardar_bt = new javax.swing.JButton();
        actualizar_bt = new javax.swing.JButton();
        eliminar_bt = new javax.swing.JButton();
        limpiar_bt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        buscar_txt = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        registrosVisibles_lb = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaHistorial = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro de Mantenimiento"));

        jLabel1.setText("Equipo");

        jLabel2.setText("Tipo Mantenimiento");

        jLabel3.setText("Fecha");

        jLabel4.setText("Técnico");

        jLabel5.setText("Descripción");

        jLabel6.setText("Estado");

        jLabel7.setText("ID");

        id_txt.setEditable(false);

        tipo_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Preventivo", "Correctivo"}));

        estado_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pendiente", "En Proceso", "Completado"}));

        fecha_dc.setDateFormatString("dd-MM-yyyy");

        guardar_bt.setText("Guardar");
        guardar_bt.addActionListener(this::guardar_btActionPerformed);

        actualizar_bt.setText("Actualizar");
        actualizar_bt.setEnabled(false);
        actualizar_bt.addActionListener(this::actualizar_btActionPerformed);

        eliminar_bt.setText("Eliminar");
        eliminar_bt.setEnabled(false);
        eliminar_bt.addActionListener(this::eliminar_btActionPerformed);

        limpiar_bt.setText("Limpiar");
        limpiar_bt.addActionListener(this::limpiar_btActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(equipo_cb, 0, 200, Short.MAX_VALUE)
                            .addComponent(tipo_cb, 0, 200, Short.MAX_VALUE)
                            .addComponent(fecha_dc, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(tecnico_txt)
                            .addComponent(descripcion_txt)
                            .addComponent(estado_cb, 0, 200, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(guardar_bt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualizar_bt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminar_bt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(limpiar_bt)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(equipo_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tipo_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(fecha_dc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tecnico_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(descripcion_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(estado_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar_bt)
                    .addComponent(actualizar_bt)
                    .addComponent(eliminar_bt)
                    .addComponent(limpiar_bt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Buscar");

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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar))
                    .addComponent(registrosVisibles_lb))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registrosVisibles_lb)
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
        tablaHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaHistorial.rowAtPoint(evt.getPoint());
                cargarFormularioDesdeTabla(fila);
            }
        });
        jScrollPane1.setViewportView(tablaHistorial);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    /** Guarda un nuevo mantenimiento en la base de datos. */
    private void guardar_btActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarFormulario()) return;

        EquipoComboItem eq = (EquipoComboItem) equipo_cb.getSelectedItem();
        if (!dao.existeEquipo(eq.getId())) {
            JOptionPane.showMessageDialog(this,
                    "El equipo seleccionado no está registrado en el sistema.\nDebe registrarlo primero.",
                    "Equipo no registrado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MantenimientoEquipo m = construirDesdeFormulario();
        if (dao.guardar(m)) {
            JOptionPane.showMessageDialog(this, "Mantenimiento registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Actualiza los datos del mantenimiento seleccionado. */
    private void actualizar_btActionPerformed(java.awt.event.ActionEvent evt) {
        if (id_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para actualizar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarFormulario()) return;

        MantenimientoEquipo m = construirDesdeFormulario();
        if (dao.actualizar(m)) {
            JOptionPane.showMessageDialog(this, "Mantenimiento actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Elimina el mantenimiento seleccionado previa confirmación. */
    private void eliminar_btActionPerformed(java.awt.event.ActionEvent evt) {
        if (id_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(id_txt.getText().trim());
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Mantenimiento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
            }
        }
    }

    /** Limpia el formulario y restablece el modo de registro. */
    private void limpiar_btActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

    /** Refresca la tabla de historial desde la base de datos. */
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarTabla();
    }

    /** Limpia el campo de búsqueda y restablece el foco. */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        buscar_txt.setText("");
        buscar_txt.requestFocusInWindow();
    }

    /** Aplica la paleta de colores, fuentes y bordes consistente del sistema. */
    private void aplicarEstiloVisual() {
        Color fondoCeleste = new Color(218, 238, 255);
        Color azulTexto = new Color(22, 90, 148);
        Color azulBoton = new Color(80, 140, 200);
        Color verdeBoton = new Color(34, 139, 34);
        Color naranjaBoton = new Color(255, 140, 0);
        Color rojoBoton = new Color(178, 34, 34);

        this.getContentPane().setBackground(fondoCeleste);
        jPanel1.setBackground(new Color(230, 241, 252));
        jPanel2.setBackground(fondoCeleste);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(azulTexto, 1),
                "Registro de Mantenimiento",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
                azulTexto));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(azulTexto, 1),
                "Búsqueda en Historial",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
                azulTexto));

        javax.swing.JLabel[] labels = {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8};
        for (javax.swing.JLabel l : labels) {
            l.setForeground(azulTexto);
            l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        }
        registrosVisibles_lb.setForeground(azulTexto);

        guardar_bt.setBackground(verdeBoton);
        guardar_bt.setForeground(Color.WHITE);
        guardar_bt.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        guardar_bt.setFocusPainted(false);

        actualizar_bt.setBackground(naranjaBoton);
        actualizar_bt.setForeground(Color.WHITE);
        actualizar_bt.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        actualizar_bt.setFocusPainted(false);

        eliminar_bt.setBackground(rojoBoton);
        eliminar_bt.setForeground(Color.WHITE);
        eliminar_bt.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        eliminar_bt.setFocusPainted(false);

        limpiar_bt.setBackground(azulBoton);
        limpiar_bt.setForeground(Color.WHITE);
        limpiar_bt.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        limpiar_bt.setFocusPainted(false);

        btnLimpiar.setBackground(azulBoton);
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnLimpiar.setFocusPainted(false);

        btnActualizar.setBackground(verdeBoton);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnActualizar.setFocusPainted(false);

        tablaHistorial.setRowHeight(22);
        tablaHistorial.setGridColor(new Color(210, 210, 210));
        tablaHistorial.setSelectionBackground(new Color(100, 149, 237));
        tablaHistorial.setSelectionForeground(Color.WHITE);
        tablaHistorial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tablaHistorial.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tablaHistorial.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(120, 170, 220), 2));
    }

    /** Configura el listener para mostrar opciones al cerrar la ventana. */
    private void configurarCierreVentana() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent evt) {
                manejarCierreVentana();
            }
        });
    }

    /** Muestra opciones de cerrar sesión, cerrar ventana o cancelar. */
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

        if (opcion == 0) {
            java.awt.Window ventanaPadre = javax.swing.SwingUtilities.getWindowAncestor(this);
            java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            } else {
                dispose();
            }
        } else if (opcion == 1) {
            dispose();
        }
    }

    /** Ítem interno para mostrar equipos en el combo con formato "Nombre (Tipo)". */
    private static class EquipoComboItem {
        private final int id;
        private final String nombre;
        private final String tipo;

        /**
         * Crea un ítem de equipo para el combo.
         * @param id     identificador del equipo
         * @param nombre nombre del equipo
         * @param tipo   tipo de equipo (PC, Impresora, etc.)
         */
        EquipoComboItem(int id, String nombre, String tipo) {
            this.id = id;
            this.nombre = nombre;
            this.tipo = tipo;
        }

        /** @return identificador del equipo */
        int getId() { return id; }

        @Override
        public String toString() {
            return nombre + " (" + tipo + ")";
        }
    }

    // Variables declaration
    private javax.swing.JButton guardar_bt;
    private javax.swing.JButton actualizar_bt;
    private javax.swing.JButton eliminar_bt;
    private javax.swing.JButton limpiar_bt;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JComboBox<EquipoComboItem> equipo_cb;
    private javax.swing.JComboBox<String> tipo_cb;
    private javax.swing.JComboBox<String> estado_cb;
    private com.toedter.calendar.JDateChooser fecha_dc;
    private javax.swing.JTextField id_txt;
    private javax.swing.JTextField tecnico_txt;
    private javax.swing.JTextField descripcion_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel registrosVisibles_lb;
    private javax.swing.JTable tablaHistorial;
    // End of variables declaration
}
