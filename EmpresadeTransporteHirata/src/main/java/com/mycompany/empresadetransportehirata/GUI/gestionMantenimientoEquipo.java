/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.EquipoOficinaDAO;
import com.mycompany.empresadetransportehirata.Data.MantenimientoEquipoDAO;
import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.MantenimientoEquipo;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
/**
 * Ventana de gestión de mantenimiento de equipos de oficina.
 * Permite registrar, actualizar, eliminar y consultar mantenimientos
 * asociados a los equipos registrados en el sistema.
 */
public class gestionMantenimientoEquipo extends javax.swing.JInternalFrame {
    // ── Atributos de Lógica y Datos ──
    private final EquipoOficinaDAO daoEquipo = new EquipoOficinaDAO();
    private final MantenimientoEquipoDAO daoMant = new MantenimientoEquipoDAO();
    private DefaultTableModel modeloEquipos;
    private DefaultTableModel modeloMant;
    private List<EquipoOficina> listaEquipos;
    private List<MantenimientoEquipo> listaMantenimientos;
    private int equipoSeleccionadoId = -1;

    // ── Componentes Gráficos (Instanciados manualmente en inicializar()) ──
    // ── Propiedades para el control de Insumos ──
    private javax.swing.JComboBox<String> cb_piezasInventario;
    private javax.swing.JSpinner spn_cantidadPieza;
    private final com.mycompany.empresadetransportehirata.Data.InventarioPiezasDAO daoInventario = new com.mycompany.empresadetransportehirata.Data.InventarioPiezasDAO();
    private java.util.ArrayList<com.mycompany.empresadetransportehirata.Logica.PiezaInventario> listaPiezasDisponibles;
    private javax.swing.JComboBox<String> cb_tipo, cb_estado;
    private com.toedter.calendar.JDateChooser fecha_dt;
    private javax.swing.JTextField txt_id, txt_tecnico, txt_descripcion, txt_nombreEquipo;
    private javax.swing.JButton bt_guardar, bt_actualizar, bt_eliminar, bt_limpiar;
    private javax.swing.JTable tabla_equipos, tabla_mant;
    /**
     * Crea la ventana, configura los componentes visuales y carga los datos iniciales.
     */
    public gestionMantenimientoEquipo() {
        super("Gestión de Mantenimiento de Equipos", true, true, true, true);
        initComponents();
        setSize(1100, 650);
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setClosable(true);
        inicializar();
    }
    /** Construye y organiza todos los componentes visuales de la ventana. */
    private void inicializar() {
        java.awt.Color fondoPrincipal = new java.awt.Color(242, 248, 255);
        java.awt.Color fondoPanel    = new java.awt.Color(230, 241, 252);
        java.awt.Color colorTitulo   = new java.awt.Color(22, 90, 148);
        java.awt.Color colorCampo    = new java.awt.Color(250, 253, 255);
        java.awt.Color colorBorde    = new java.awt.Color(160, 192, 224);

        // ── Campos formulario ──
        txt_id           = new javax.swing.JTextField();
        txt_tecnico      = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        txt_nombreEquipo = new javax.swing.JTextField();

        cb_tipo          = new javax.swing.JComboBox<>(new String[]{"Preventivo", "Correctivo", "Instalación"});
        cb_estado        = new javax.swing.JComboBox<>(new String[]{"Pendiente", "En Proceso", "Completado"});
        fecha_dt         = new com.toedter.calendar.JDateChooser();
        // Inicialización de componentes de inventario
        cb_piezasInventario = new javax.swing.JComboBox<>();
        spn_cantidadPieza = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 9999, 1));
        
        spn_cantidadPieza.setPreferredSize(new java.awt.Dimension(160, 28));
        spn_cantidadPieza.setBorder(javax.swing.BorderFactory.createLineBorder(colorBorde, 2, true));
        cb_piezasInventario.setBackground(colorCampo);
        
        // Cargar datos iniciales
        cargarComboPiezas();

        
        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(220, 230, 240));
        txt_nombreEquipo.setEditable(false);
        txt_nombreEquipo.setBackground(new java.awt.Color(220, 230, 240));

        for (javax.swing.JTextField tf : new javax.swing.JTextField[]{txt_tecnico, txt_descripcion, txt_nombreEquipo}) {
            tf.setBackground(colorCampo);
            tf.setBorder(javax.swing.BorderFactory.createLineBorder(colorBorde, 2, true));
            tf.setPreferredSize(new java.awt.Dimension(160, 28));
        }

        cb_tipo.setBackground(colorCampo);
        cb_estado.setBackground(colorCampo);

        // ── Botones ──
        bt_guardar   = new javax.swing.JButton("Guardar");
        bt_actualizar = new javax.swing.JButton("Actualizar");
        bt_eliminar  = new javax.swing.JButton("Eliminar");
        bt_limpiar   = new javax.swing.JButton("Limpiar");

        estilizarBoton(bt_guardar,    new java.awt.Color(34,139,34),   new java.awt.Color(20,100,20));
        estilizarBoton(bt_actualizar, new java.awt.Color(255,140,0),   new java.awt.Color(200,95,0));
        estilizarBoton(bt_eliminar,   new java.awt.Color(178,34,34),   new java.awt.Color(128,20,20));
        estilizarBoton(bt_limpiar,    new java.awt.Color(70,130,180),  new java.awt.Color(40,90,140));

        bt_guardar.addActionListener(e -> guardar());
        bt_actualizar.addActionListener(e -> actualizar());
        bt_eliminar.addActionListener(e -> eliminar());
        bt_limpiar.addActionListener(e -> limpiar());

        aplicarEstadoBotones(false);

        // ── Panel formulario ──
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel(new java.awt.GridBagLayout());
        jPanel1.setBackground(fondoPanel);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120,170,220), 2),
            "Registro de Mantenimiento",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13), colorTitulo));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 8, 5, 8);
        gbc.fill   = java.awt.GridBagConstraints.HORIZONTAL;

        Object[][] campos = {
            {"ID",          txt_id},
            {"Equipo",      txt_nombreEquipo},
            {"Tipo",        cb_tipo},
            {"Fecha",       fecha_dt},
            {"Técnico",     txt_tecnico},
            {"Estado",      cb_estado},
            {"Insumo/Pieza", cb_piezasInventario}, 
            {"Cant. Usada", spn_cantidadPieza},    
            {"Descripción", txt_descripcion}
        };

        for (int i = 0; i < campos.length; i++) {
            javax.swing.JLabel lbl = new javax.swing.JLabel((String) campos[i][0]);
            lbl.setForeground(colorTitulo);
            lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            jPanel1.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            jPanel1.add((java.awt.Component) campos[i][1], gbc);
        }

        javax.swing.JPanel pnlBotones = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 6, 6));
        pnlBotones.setOpaque(false);
        pnlBotones.add(bt_guardar);
        pnlBotones.add(bt_actualizar);
        pnlBotones.add(bt_eliminar);
        pnlBotones.add(bt_limpiar);
        gbc.gridx = 0; gbc.gridy = campos.length; gbc.gridwidth = 2; gbc.insets = new java.awt.Insets(12,8,5,8);
        jPanel1.add(pnlBotones, gbc);

        // ── Tabla equipos (superior derecha) ──
        tabla_equipos = new javax.swing.JTable();
        modeloEquipos = new DefaultTableModel(
            new String[]{"ID","Nombre","Tipo","Marca","Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla_equipos.setModel(modeloEquipos);
        aplicarEstiloTabla(tabla_equipos);
        tabla_equipos.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla_equipos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tabla_equipos.getSelectedRow();
                if (fila >= 0) {
                    equipoSeleccionadoId = (int) modeloEquipos.getValueAt(fila, 0);
                    String nombreEq = modeloEquipos.getValueAt(fila, 1).toString();
                    txt_nombreEquipo.setText(nombreEq);
                    cargarMantenimientos(equipoSeleccionadoId);
                    limpiarFormularioSinEquipo();
                }
            }
        });

        // ── Tabla mantenimientos (inferior derecha) ──
        tabla_mant = new javax.swing.JTable();
        modeloMant = new DefaultTableModel(
            new String[]{"ID","Tipo","Fecha","Técnico","Estado","Descripción"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla_mant.setModel(modeloMant);
        aplicarEstiloTabla(tabla_mant);
        tabla_mant.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla_mant.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tabla_mant.getSelectedRow();
                if (fila >= 0) cargarFormularioDesdeTabla(fila);
            }
        });

        // ── Menú contextual tabla mantenimientos ──
        JPopupMenu menuMant = new JPopupMenu();
        JMenuItem ver    = new JMenuItem("Ver detalles");
        JMenuItem editar = new JMenuItem("Editar");
        JMenuItem elim   = new JMenuItem("Eliminar");
        ver.addActionListener(e -> verDetalles());
        editar.addActionListener(e -> {
            int f = tabla_mant.getSelectedRow();
            if (f >= 0) cargarFormularioDesdeTabla(f);
        });
        elim.addActionListener(e -> eliminar());
        menuMant.add(ver); menuMant.addSeparator();
        menuMant.add(editar); menuMant.add(elim);
        tabla_mant.setComponentPopupMenu(menuMant);
        tabla_mant.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                int f = tabla_mant.rowAtPoint(e.getPoint());
                if (f >= 0) tabla_mant.setRowSelectionInterval(f, f);
            }
        });

        javax.swing.JScrollPane scrollEq   = new javax.swing.JScrollPane(tabla_equipos);
        javax.swing.JScrollPane scrollMant = new javax.swing.JScrollPane(tabla_mant);
        scrollEq.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120,170,220), 2),
            "Equipos registrados",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), colorTitulo));
        scrollMant.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120,170,220), 2),
            "Historial de mantenimientos",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), colorTitulo));

        javax.swing.JSplitPane splitDerecho = new javax.swing.JSplitPane(
            javax.swing.JSplitPane.VERTICAL_SPLIT, scrollEq, scrollMant);
        splitDerecho.setResizeWeight(0.4);
        splitDerecho.setBorder(null);

        // ── Layout principal ──
        getContentPane().setBackground(fondoPrincipal);
        getContentPane().setLayout(new java.awt.BorderLayout(12, 0));
        getContentPane().add(jPanel1,      java.awt.BorderLayout.WEST);
        getContentPane().add(splitDerecho, java.awt.BorderLayout.CENTER);

        cargarEquipos();
        fecha_dt.setDate(new java.util.Date());

        java.awt.EventQueue.invokeLater(() -> {
            splitDerecho.setDividerLocation(240);
        });
    }

    // ── Métodos de lógica ────────────────────────────────────────────────────

    /** Valida los datos y persiste un nuevo mantenimiento. */
    private void guardar() {
        if (!validar()) return;

        if (equipoSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un equipo de la tabla superior antes de registrar.",
                "Equipo no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validaciones del stock en inventario
        int itemSeleccionado = cb_piezasInventario.getSelectedIndex();
        int cantidadPedida = (int) spn_cantidadPieza.getValue();

        if (itemSeleccionado > 0) { 
            com.mycompany.empresadetransportehirata.Logica.PiezaInventario piezaActual = listaPiezasDisponibles.get(itemSeleccionado - 1);
            
            if (cantidadPedida <= 0) {
                JOptionPane.showMessageDialog(this, "Si selecciona una pieza, la cantidad usada debe ser mayor a 0.", "Validación de Insumos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cantidadPedida > piezaActual.getCantidad()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay suficiente stock en inventario.\nStock disponible: " + piezaActual.getCantidad() + " " + piezaActual.getUnidad(), 
                    "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        MantenimientoEquipo m = construir();
        
        // Guardar mantenimiento en tabla real de base de datos
        int idGenerado = daoMant.guardarYObtenerId(m);
        
        if (idGenerado > 0) {
            actualizarEstadoHardwareFisico(equipoSeleccionadoId, m.getEstado());

            // Si se seleccionó pieza, vincular y restar stock
            if (itemSeleccionado > 0 && cantidadPedida > 0) {
                com.mycompany.empresadetransportehirata.Logica.PiezaInventario piezaActual = listaPiezasDisponibles.get(itemSeleccionado - 1);
                daoMant.registrarUsoPieza(idGenerado, piezaActual.getId(), cantidadPedida);
            }

            JOptionPane.showMessageDialog(this, "Mantenimiento registrado y stock actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarEquipos();
            cargarMantenimientos(equipoSeleccionadoId);
            cargarComboPiezas(); // Recarga el combo para mostrar el stock actualizado
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Actualiza los datos del mantenimiento seleccionado en la tabla. */
    private void actualizar() {
        if (txt_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un mantenimiento de la tabla para actualizar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validar()) return;

        MantenimientoEquipo m = construir();
        m.setId(Integer.parseInt(txt_id.getText().trim()));
        
        // CORRECCIÓN: Asegurar que mantenga el ID del equipo asociado al mantenimiento original
        int filaMant = tabla_mant.getSelectedRow();
        if (filaMant >= 0) {
             // Si por alguna razón cambió el equipoSeleccionadoId en la interfaz superior, respetamos el dueño original del mantenimiento
             m.setIdEquipo(equipoSeleccionadoId); 
        }

        if (daoMant.actualizar(m)) {
            actualizarEstadoHardwareFisico(m.getIdEquipo(), m.getEstado());

            JOptionPane.showMessageDialog(this, "Mantenimiento actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarEquipos();
            cargarMantenimientos(m.getIdEquipo());
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Elimina el mantenimiento seleccionado previa confirmación. */
    private void eliminar() {
        int fila = tabla_mant.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un mantenimiento de la tabla.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloMant.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar este registro de mantenimiento?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (daoMant.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Mantenimiento eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                cargarMantenimientos(equipoSeleccionadoId);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Actualiza el estado del equipo según el estado del mantenimiento.
     * @param idEquipo             identificador del equipo
     * @param estadoMantenimiento estado actual del mantenimiento
     */
    private void actualizarEstadoHardwareFisico(int idEquipo, String estadoMantenimiento) {
        EquipoOficina equipoActual = null;
        if (listaEquipos != null) {
            for (EquipoOficina eq : listaEquipos) {
                if (eq.getId() == idEquipo) {
                    equipoActual = eq;
                    break;
                }
            }
        }

        if (equipoActual != null) {
            if (estadoMantenimiento.equals("En Proceso")) {
                equipoActual.setEstado("En reparación");
            } else if (estadoMantenimiento.equals("Completado")) {
                equipoActual.setEstado("Activo");
            }
            daoEquipo.actualizar(equipoActual);
        }
    }

    /** Carga los datos de la fila seleccionada en el formulario de edición. */
    private void cargarFormularioDesdeTabla(int fila) {
    txt_id.setText(modeloMant.getValueAt(fila, 0).toString());
    cb_tipo.setSelectedItem(modeloMant.getValueAt(fila, 1).toString());
    if (modeloMant.getValueAt(fila, 2) != null)
        fecha_dt.setDate((java.util.Date) modeloMant.getValueAt(fila, 2));
    txt_tecnico.setText(modeloMant.getValueAt(fila, 3).toString());
    cb_estado.setSelectedItem(modeloMant.getValueAt(fila, 4).toString());
    txt_descripcion.setText(modeloMant.getValueAt(fila, 5) != null
        ? modeloMant.getValueAt(fila, 5).toString() : "");

    // ── Restaurar pieza/insumo y cantidad desde listaMantenimientos ──
    int idMantenimiento = Integer.parseInt(txt_id.getText().trim());
    MantenimientoEquipo mantEncontrado = null;
    if (listaMantenimientos != null) {
        for (MantenimientoEquipo m : listaMantenimientos) {
            if (m.getId() == idMantenimiento) {
                mantEncontrado = m;
                break;
            }
        }
    }
    if (mantEncontrado != null && mantEncontrado.getIdPieza() > 0) {
        int idxCombo = 0;
        if (listaPiezasDisponibles != null) {
            for (int i = 0; i < listaPiezasDisponibles.size(); i++) {
                if (listaPiezasDisponibles.get(i).getId() == mantEncontrado.getIdPieza()) {
                    idxCombo = i + 1; // +1 porque índice 0 es "Ninguno"
                    break;
                }
            }
        }
        cb_piezasInventario.setSelectedIndex(idxCombo);
        spn_cantidadPieza.setValue(mantEncontrado.getCantidadPieza());
    } else {
        cb_piezasInventario.setSelectedIndex(0);
        spn_cantidadPieza.setValue(0);
    }

    aplicarEstadoBotones(true);
    }

    /** Muestra un diálogo con 
     * 
     * 
     * 
     * 
     * los detalles del mantenimiento seleccionado. */
    private void verDetalles() {
        int fila = tabla_mant.getSelectedRow();
        if (fila < 0) return;
        JOptionPane.showMessageDialog(this,
            "ID:           " + modeloMant.getValueAt(fila, 0) + "\n" +
            "Tipo:         " + modeloMant.getValueAt(fila, 1) + "\n" +
            "Fecha:        " + modeloMant.getValueAt(fila, 2) + "\n" +
            "Técnico:      " + modeloMant.getValueAt(fila, 3) + "\n" +
            "Estado:       " + modeloMant.getValueAt(fila, 4) + "\n" +
            "Descripción:  " + modeloMant.getValueAt(fila, 5),
            "Detalles del Mantenimiento", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Valida que los campos obligatorios del formulario estén completos. */
    private boolean validar() {
    // ── 1. Validación del Técnico (No vacío y longitud máxima) ──
    String tecnico = txt_tecnico.getText().trim();
    if (tecnico.isEmpty()) {
        mostrarAlerta("Ingrese el nombre del técnico.", "Campo requerido");
        return false;
    }
    if (tecnico.length() > 100) { // Basado en tu tabla de asignación
        mostrarAlerta("El nombre del técnico no puede superar los 100 caracteres.", "Texto demasiado largo");
        return false;
    }

    // ── 2. Validación de la Descripción (No vacía y longitud máxima) ──
    String descripcion = txt_descripcion.getText().trim();
    if (descripcion.isEmpty()) {
        mostrarAlerta("La descripción del mantenimiento es obligatoria.", "Campo requerido");
        return false;
    }
    if (descripcion.length() > 500) { // Límite estricto asignado en tu tabla
        mostrarAlerta("La descripción no puede superar los 500 caracteres.", "Texto demasiado largo");
        return false;
    }

    // ── 3. Validación de Fecha ──
    if (fecha_dt.getDate() == null) {
        mostrarAlerta("Seleccione una fecha válida.", "Campo requerido");
        return false;
    }

    // ── 4. Lógica combinada: Fecha según el Estado ──
    String estadoActual = cb_estado.getSelectedItem().toString();
    java.util.Date fechaSeleccionada = fecha_dt.getDate();
    java.util.Date hoy = new java.util.Date();

    // Si NO está pendiente (está En Proceso o Completado), no puede ser una fecha futura
    if (!estadoActual.equals("Pendiente") && fechaSeleccionada.after(hoy)) {
        mostrarAlerta("Un mantenimiento en estado '" + estadoActual + "' no puede programarse para una fecha futura.", "Fecha Incoherente");
        return false;
    }

    return true; // Pasó todas las reglas lógicas con éxito
}

    /** Muestra un diálogo de advertencia con el mensaje y título indicados. */
    private void mostrarAlerta(String mensaje, String titulo) {
    JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
}

    /** Construye una entidad {@link MantenimientoEquipo} con los datos del formulario. */
    private MantenimientoEquipo construir() {
        MantenimientoEquipo m = new MantenimientoEquipo();
        m.setIdEquipo(equipoSeleccionadoId);
        m.setNombreEquipo(txt_nombreEquipo.getText().trim());
        m.setTipoMantenimiento(cb_tipo.getSelectedItem().toString());
        m.setFecha(new java.sql.Date(fecha_dt.getDate().getTime()));
        m.setTecnico(txt_tecnico.getText().trim());
        m.setDescripcion(txt_descripcion.getText().trim());
        m.setEstado(cb_estado.getSelectedItem().toString());
        return m;
    }

    /** Refresca la tabla de equipos con los datos actuales de la BD. */
    private void cargarEquipos() {
        modeloEquipos.setRowCount(0);
        listaEquipos = daoEquipo.listar();
        for (EquipoOficina eq : listaEquipos) {
            modeloEquipos.addRow(new Object[]{
                eq.getId(), eq.getNombre(), eq.getTipo(), eq.getMarca(), eq.getEstado()
            });
        }
    }

    /** Refresca la tabla de mantenimientos para el equipo indicado. */
    private void cargarMantenimientos(int idEquipo) {
       modeloMant.setRowCount(0);
    listaMantenimientos = new java.util.ArrayList<>();
    for (MantenimientoEquipo m : daoMant.listarHistorial()) {
        if (m.getIdEquipo() == idEquipo) {
            listaMantenimientos.add(m);
            modeloMant.addRow(new Object[]{
                m.getId(), m.getTipoMantenimiento(), m.getFecha(),
                m.getTecnico(), m.getEstado(), m.getDescripcion()
            });
        }
    }
    }

    /** Restablece el formulario y las selecciones a su estado inicial. */
    private void limpiar() {
        txt_id.setText("");
        txt_nombreEquipo.setText("");
        txt_tecnico.setText("");
        txt_descripcion.setText("");
        cb_tipo.setSelectedIndex(0);
        cb_estado.setSelectedIndex(0);
        fecha_dt.setDate(new java.util.Date());
        tabla_mant.clearSelection();
        equipoSeleccionadoId = -1;
        aplicarEstadoBotones(false);
        cb_piezasInventario.setSelectedIndex(0);
        spn_cantidadPieza.setValue(0);
    }

    /** Limpia solo el formulario de mantenimiento sin borrar la selección de equipo. */
    private void limpiarFormularioSinEquipo() {
        txt_id.setText("");
        txt_tecnico.setText("");
        txt_descripcion.setText("");
        cb_tipo.setSelectedIndex(0);
        cb_estado.setSelectedIndex(0);
        fecha_dt.setDate(new java.util.Date());
        tabla_mant.clearSelection();
        aplicarEstadoBotones(false);
        cb_piezasInventario.setSelectedIndex(0);
        spn_cantidadPieza.setValue(0);
    }

    /** Habilita/deshabilita botones según el modo (registro o edición). */
    private void aplicarEstadoBotones(boolean modoEdicion) {
        bt_guardar.setEnabled(!modoEdicion);
        bt_actualizar.setEnabled(modoEdicion);
        bt_eliminar.setEnabled(modoEdicion);
    }

    /** Aplica el estilo visual consistente a un botón. */
    private void estilizarBoton(javax.swing.JButton bt, java.awt.Color bg, java.awt.Color borde) {
        bt.setBackground(bg);
        bt.setForeground(java.awt.Color.WHITE);
        bt.setFont(bt.getFont().deriveFont(java.awt.Font.BOLD));
        bt.setFocusPainted(false);
        bt.setBorder(javax.swing.BorderFactory.createLineBorder(borde, 2, true));
    }

    /** Aplica el estilo visual consistente (colores, fuentes, zebra stripes) a una tabla. */
    private void aplicarEstiloTabla(javax.swing.JTable t) {
        t.setRowHeight(26);
        t.setSelectionBackground(new java.awt.Color(100,149,237));
        t.setSelectionForeground(java.awt.Color.WHITE);
        t.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        t.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        t.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        t.getTableHeader().setBackground(new java.awt.Color(70,130,180));
        t.getTableHeader().setForeground(java.awt.Color.WHITE);
        t.getTableHeader().setReorderingAllowed(false);
        t.setFillsViewportHeight(true);
        t.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable tbl, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(tbl, v, sel, foc, r, c);
                if (!sel) setBackground(r % 2 == 0 ? java.awt.Color.WHITE : new java.awt.Color(235,245,255));
                return this;
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Carga el combo de piezas del inventario con los datos disponibles. */
    private void cargarComboPiezas() {
        cb_piezasInventario.removeAllItems();
        cb_piezasInventario.addItem("Ninguno / Sin repuesto");
        
        try {
            listaPiezasDisponibles = daoInventario.listarPiezas();
            for (com.mycompany.empresadetransportehirata.Logica.PiezaInventario pieza : listaPiezasDisponibles) {
                cb_piezasInventario.addItem(pieza.getNombre() + " (Dispo: " + pieza.getCantidad() + " " + pieza.getUnidad() + ")");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar combo de piezas: " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
