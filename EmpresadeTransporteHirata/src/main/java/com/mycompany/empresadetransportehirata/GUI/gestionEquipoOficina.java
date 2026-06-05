/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.EquipoOficinaDAO;
import com.mycompany.empresadetransportehirata.Data.SoftwareDAO;
import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.Software;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 * Ventana de gestión de equipos de oficina.
 * Permite registrar, actualizar, eliminar y consultar equipos,
 * así como asignar/desasignar software a cada equipo.
 */
public class gestionEquipoOficina extends javax.swing.JInternalFrame {
    // ── ATRIBUTOS DE LÓGICA Y DATOS (Declarados correctamente a nivel de clase) ──
    private final EquipoOficinaDAO daoEquipo = new EquipoOficinaDAO();
    private final SoftwareDAO daoSoftware = new SoftwareDAO();
    private DefaultTableModel modeloEquipos;
    private List<Software> listaSoftware;

    // ── COMPONENTES GRÁFICOS (Declarados a nivel de clase) ──
    private javax.swing.JComboBox<String> cb_tipo, cb_estado;
    private javax.swing.JComboBox<Software> cb_software;
    private javax.swing.JTextField txt_id, txt_nombre, txt_marca, txt_modelo, txt_serie;
    private javax.swing.JButton bt_guardar, bt_actualizar, bt_eliminar, bt_limpiar;
    private javax.swing.JTable tabla_equipos;
    /** Crea la ventana y prepara los componentes visuales y datos iniciales. */
    public gestionEquipoOficina() {
        super("Gestión de Equipos de Oficina", true, true, true, true);
        
        // Inicializador nativo de NetBeans (por si usas el diseñador visual)
        initComponents(); 
        
        // Configuración de tamaño y construcción visual personalizada
        setSize(1000, 600);
        setMinimumSize(new java.awt.Dimension(1000, 600));
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
        txt_id      = new javax.swing.JTextField();
        txt_nombre  = new javax.swing.JTextField();
        txt_marca   = new javax.swing.JTextField();
        txt_modelo  = new javax.swing.JTextField();
        txt_serie   = new javax.swing.JTextField();
        cb_tipo     = new javax.swing.JComboBox<>(new String[]{"PC", "Impresora", "Monitor", "Router", "Mouse", "Teclado"});
        cb_estado   = new javax.swing.JComboBox<>(new String[]{"Activo", "En reparación", "Dado de baja"});
        cb_software = new javax.swing.JComboBox<>();

        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(220, 230, 240));

        for (javax.swing.JTextField tf : new javax.swing.JTextField[]{txt_nombre, txt_marca, txt_modelo, txt_serie}) {
            tf.setBackground(colorCampo);
            tf.setBorder(javax.swing.BorderFactory.createLineBorder(colorBorde, 2, true));
            tf.setPreferredSize(new java.awt.Dimension(160, 28));
        }
        cb_tipo.setBackground(colorCampo);
        cb_estado.setBackground(colorCampo);
        cb_software.setBackground(colorCampo);

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
            "Registro de Equipo",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13), colorTitulo));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(4, 8, 4, 8);
        gbc.fill   = java.awt.GridBagConstraints.HORIZONTAL;

        Object[][] campos = {
            {"ID",       txt_id},
            {"Nombre",   txt_nombre},
            {"Tipo",     cb_tipo},
            {"Marca",    txt_marca},
            {"Modelo",   txt_modelo},
            {"Serie",    txt_serie},
            {"Estado",   cb_estado},
            {"Software", cb_software}
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
        
        // ── Tabla equipos (derecha) ──
        tabla_equipos = new javax.swing.JTable();
        modeloEquipos = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Tipo", "Marca", "Modelo", "Serie", "Estado", "Software"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla_equipos.setModel(modeloEquipos);
        aplicarEstiloTabla(tabla_equipos);
        tabla_equipos.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla_equipos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tabla_equipos.getSelectedRow();
                if (fila >= 0) cargarFormularioDesdeTabla(fila);
            }
        });

        javax.swing.JScrollPane scrollEq = new javax.swing.JScrollPane(tabla_equipos);
        scrollEq.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120,170,220), 2),
            "Equipos registrados",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12), colorTitulo));

        // ── Layout principal ──
        getContentPane().setBackground(fondoPrincipal);
        getContentPane().setLayout(new java.awt.BorderLayout(12, 0));
        getContentPane().add(jPanel1,   java.awt.BorderLayout.WEST);
        getContentPane().add(scrollEq, java.awt.BorderLayout.CENTER);

        cargarSoftwareEnCombo();
        cargarTabla();
    }

    // ── Métodos de lógica ────────────────────────────────────────────────────

    /** Valida los datos y persiste un nuevo equipo de oficina. */
    private void guardar() {
        if (!validar()) return;

        EquipoOficina e = construir();
        if (daoEquipo.guardar(e)) {
            Software sw = (Software) cb_software.getSelectedItem();
            if (sw != null && sw.getId() > 0) {
                int nuevoId = obtenerUltimoIdEquipo();
                daoSoftware.asignarAEquipo(sw.getId(), nuevoId);
            }
            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Actualiza los datos del equipo seleccionado en la tabla. */
    private void actualizar() {
        if (txt_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla para actualizar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validar()) return;

        EquipoOficina e = construir();
        e.setId(Integer.parseInt(txt_id.getText().trim()));

        if (daoEquipo.actualizar(e)) {
            Software sw = (Software) cb_software.getSelectedItem();
            if (sw != null && sw.getId() > 0) {
                daoSoftware.asignarAEquipo(sw.getId(), e.getId());
            } else {
                daoSoftware.quitarAsignacion(e.getId());
            }
            JOptionPane.showMessageDialog(this, "Equipo actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Elimina el equipo seleccionado previa confirmación. */
    private void eliminar() {
        if (txt_id.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(txt_id.getText().trim());
        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar este equipo? Los mantenimientos asociados también se eliminarán.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (daoEquipo.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Equipo eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Carga los datos de la fila seleccionada en el formulario de edición. */
    private void cargarFormularioDesdeTabla(int fila) {
        txt_id.setText(modeloEquipos.getValueAt(fila, 0).toString());
        txt_nombre.setText(modeloEquipos.getValueAt(fila, 1).toString());
        cb_tipo.setSelectedItem(modeloEquipos.getValueAt(fila, 2).toString());
        txt_marca.setText(modeloEquipos.getValueAt(fila, 3).toString());
        txt_modelo.setText(modeloEquipos.getValueAt(fila, 4).toString());
        txt_serie.setText(modeloEquipos.getValueAt(fila, 5) != null
            ? modeloEquipos.getValueAt(fila, 5).toString() : "");
        cb_estado.setSelectedItem(modeloEquipos.getValueAt(fila, 6).toString());
        String swNombre = modeloEquipos.getValueAt(fila, 7) != null
            ? modeloEquipos.getValueAt(fila, 7).toString() : "Sin asignar";
        for (int i = 0; i < cb_software.getItemCount(); i++) {
            Software s = cb_software.getItemAt(i);
            if ((s == null || s.getId() == 0) && swNombre.equals("Sin asignar")) {
                cb_software.setSelectedIndex(i);
                break;
            }
            if (s != null && s.getNombre().equals(swNombre)) {
                cb_software.setSelectedIndex(i);
                break;
            }
        }
        aplicarEstadoBotones(true);
    }

    /** Valida que los campos obligatorios del formulario estén completos. */
    private boolean validar() {
        if (txt_nombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del equipo.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txt_nombre.requestFocus();
            return false;
        }
        if (txt_marca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la marca del equipo.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txt_marca.requestFocus();
            return false;
        }
        if (txt_modelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el modelo del equipo.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txt_modelo.requestFocus();
            return false;
        }
        return true;
    }

    /** Construye una entidad {@link EquipoOficina} con los datos del formulario. */
    private EquipoOficina construir() {
        EquipoOficina e = new EquipoOficina();
        e.setNombre(txt_nombre.getText().trim());
        e.setTipo(cb_tipo.getSelectedItem().toString());
        e.setMarca(txt_marca.getText().trim());
        e.setModelo(txt_modelo.getText().trim());
        e.setSerie(txt_serie.getText().trim());
        e.setEstado(cb_estado.getSelectedItem().toString());
        return e;
    }

    /** Obtiene el ID más alto entre los equipos registrados. */
    private int obtenerUltimoIdEquipo() {
        List<EquipoOficina> lista = daoEquipo.listar();
        if (lista.isEmpty()) return 1;
        return lista.get(lista.size() - 1).getId();
    }

    /** Carga las opciones de software disponibles en el combo de selección. */
    private void cargarSoftwareEnCombo() {
        cb_software.removeAllItems();
        cb_software.addItem(null);
        listaSoftware = daoSoftware.listar();
        for (Software s : listaSoftware) {
            cb_software.addItem(s);
        }
        cb_software.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Sin asignar");
                } else {
                    setText(((Software) value).getNombre());
                }
                return this;
            }
        });
    }

    /** Refresca la tabla de equipos con los datos actuales de la BD. */
    private void cargarTabla() {
        modeloEquipos.setRowCount(0);
        for (EquipoOficina eq : daoEquipo.listar()) {
            String swNombre = eq.getSoftware() != null ? eq.getSoftware().getNombre() : "Sin asignar";
            modeloEquipos.addRow(new Object[]{
                eq.getId(), eq.getNombre(), eq.getTipo(), eq.getMarca(),
                eq.getModelo(), eq.getSerie(), eq.getEstado(), swNombre
            });
        }
    }

    /** Restablece el formulario y las selecciones a su estado inicial. */
    private void limpiar() {
        txt_id.setText("");
        txt_nombre.setText("");
        txt_marca.setText("");
        txt_modelo.setText("");
        txt_serie.setText("");
        cb_tipo.setSelectedIndex(0);
        cb_estado.setSelectedIndex(0);
        cb_software.setSelectedIndex(0);
        tabla_equipos.clearSelection();
        aplicarEstadoBotones(false);
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

    /** Método puente que delega en {@link #aplicarEstiloTabla}. */
    private void aplicarStyleTabla(javax.swing.JTable t) {
        // Método puente para no romper tu llamada original a aplicarEstiloTabla
        aplicarEstiloTabla(t);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
