/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.EquipoOficinaDAO;
import com.mycompany.empresadetransportehirata.Data.SoftwareDAO;
import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.Software;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author bruno
 */
public class gestionSoftware extends javax.swing.JInternalFrame {

    /**
     * Creates new form gestionSoftware
     */
    private static final String ROL_TECNICO_IT = "tecnico_it";
    private final String rolUsuarioSesion;

    private final SoftwareDAO softwareDAO = new SoftwareDAO();
    private final EquipoOficinaDAO equipoDAO = new EquipoOficinaDAO();

    private Software softwareSeleccionado = null;
    private EquipoOficina equipoSeleccionado = null;
    private boolean editandoSoftware = false;

    /**
     * Crea el formulario de gestión de software e inicializa las tablas.
     *
     * @param rolUsuarioSesion rol del usuario autenticado en la sesión actual
     */
    public gestionSoftware(String rolUsuarioSesion) {
        this.rolUsuarioSesion = rolUsuarioSesion;
        initComponents();
        aplicarEstiloVisual();
        cargarTablaSoftware();
        cargarTablaEquipos();
        configurarPopupMenus();

        this.setClosable(true);
        this.setTitle("Gestión de Software");
    }

    private void aplicarEstiloVisual() {
        Color fondoPrincipal = new Color(242, 248, 255);
        Color fondoPanel = new Color(230, 241, 252);
        Color colorTitulo = new Color(22, 90, 148);
        Color colorCampo = new Color(250, 253, 255);
        Color colorBordeCampo = new Color(160, 192, 224);
        Color headerBg = new Color(70, 130, 180);
        Color seleccion = new Color(100, 149, 237);

        getContentPane().setBackground(fondoPrincipal);
        jPanel1.setBackground(fondoPanel);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(120, 170, 220), 2),
                "Gestor de Software",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
                colorTitulo));

        javax.swing.JLabel[] labels = new javax.swing.JLabel[]{
            jLabel1, jLabel2, jLabel3, jLabel4
        };
        for (javax.swing.JLabel label : labels) {
            if (label != null) {
                label.setForeground(colorTitulo);
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            }
        }

        id_txt.setBackground(colorCampo);
        software_txt.setBackground(colorCampo);
        jTextField1.setBackground(colorCampo);
        cb_elegirtodoslosequipos.setBackground(fondoPanel);

        id_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        software_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        cb_elegirtodoslosequipos.setForeground(colorTitulo);

        bt_registrar.setBackground(new Color(34, 139, 34));
        bt_registrar.setForeground(Color.WHITE);
        bt_registrar.setFont(bt_registrar.getFont().deriveFont(java.awt.Font.BOLD));
        bt_registrar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(20, 100, 20), 2, true));
        bt_registrar.setFocusPainted(false);

        bt_eliminar.setBackground(new Color(178, 34, 34));
        bt_eliminar.setForeground(Color.WHITE);
        bt_eliminar.setFont(bt_eliminar.getFont().deriveFont(java.awt.Font.BOLD));
        bt_eliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(128, 20, 20), 2, true));
        bt_eliminar.setFocusPainted(false);

        bt_asignar.setBackground(new Color(255, 140, 0));
        bt_asignar.setForeground(Color.WHITE);
        bt_asignar.setFont(bt_asignar.getFont().deriveFont(java.awt.Font.BOLD));
        bt_asignar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 95, 0), 2, true));
        bt_asignar.setFocusPainted(false);

        bt_quitar.setBackground(new Color(70, 130, 180));
        bt_quitar.setForeground(Color.WHITE);
        bt_quitar.setFont(bt_quitar.getFont().deriveFont(java.awt.Font.BOLD));
        bt_quitar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(40, 90, 140), 2, true));
        bt_quitar.setFocusPainted(false);

        bt_cancelar.setBackground(new Color(70, 130, 180));
        bt_cancelar.setForeground(Color.WHITE);
        bt_cancelar.setFont(bt_cancelar.getFont().deriveFont(java.awt.Font.BOLD));
        bt_cancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(40, 90, 140), 2, true));
        bt_cancelar.setFocusPainted(false);

        tbl_software.setRowHeight(26);
        tbl_software.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tbl_software.setSelectionBackground(seleccion);
        tbl_software.setSelectionForeground(Color.WHITE);
        tbl_software.setGridColor(new Color(210, 210, 210));
        tbl_software.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tbl_software.getTableHeader().setBackground(headerBg);
        tbl_software.getTableHeader().setForeground(Color.WHITE);

        tbl_equipos.setRowHeight(26);
        tbl_equipos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tbl_equipos.setSelectionBackground(seleccion);
        tbl_equipos.setSelectionForeground(Color.WHITE);
        tbl_equipos.setGridColor(new Color(210, 210, 210));
        tbl_equipos.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tbl_equipos.getTableHeader().setBackground(headerBg);
        tbl_equipos.getTableHeader().setForeground(Color.WHITE);

        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(120, 170, 220), 2));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(120, 170, 220), 2));
    }

    private void configurarPopupMenus() {
        JPopupMenu menuSoftware = new JPopupMenu();

        JMenuItem opcionVerSoftware = new JMenuItem("Ver detalles");
        opcionVerSoftware.addActionListener(e -> mostrarDetallesSoftwareSeleccionado());
        menuSoftware.add(opcionVerSoftware);

        menuSoftware.addSeparator();

        JMenuItem opcionEditarSoftware = new JMenuItem("Editar");
        opcionEditarSoftware.addActionListener(e -> editarSoftwareSeleccionado());
        menuSoftware.add(opcionEditarSoftware);

        JMenuItem opcionEliminarSoftware = new JMenuItem("Eliminar");
        opcionEliminarSoftware.addActionListener(e -> bt_eliminarActionPerformed(null));
        menuSoftware.add(opcionEliminarSoftware);

        tbl_software.setComponentPopupMenu(menuSoftware);
        tbl_software.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                seleccionarFilaDesdeEvento(tbl_software, evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                seleccionarFilaDesdeEvento(tbl_software, evt);
            }
        });

        JPopupMenu menuEquipos = new JPopupMenu();

        JMenuItem opcionVerEquipo = new JMenuItem("Ver detalles");
        opcionVerEquipo.addActionListener(e -> mostrarDetallesEquipoSeleccionado());
        menuEquipos.add(opcionVerEquipo);

        menuEquipos.addSeparator();

        JMenuItem opcionSeleccionarEquipo = new JMenuItem("Seleccionar equipo");
        opcionSeleccionarEquipo.addActionListener(e -> seleccionarEquipoDesdeTabla());
        menuEquipos.add(opcionSeleccionarEquipo);

        JMenuItem opcionQuitarSoftware = new JMenuItem("Quitar software");
        opcionQuitarSoftware.addActionListener(e -> bt_quitarActionPerformed(null));
        menuEquipos.add(opcionQuitarSoftware);

        tbl_equipos.setComponentPopupMenu(menuEquipos);
        tbl_equipos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                seleccionarFilaDesdeEvento(tbl_equipos, evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                seleccionarFilaDesdeEvento(tbl_equipos, evt);
            }
        });
    }

    private void seleccionarFilaDesdeEvento(javax.swing.JTable tabla, MouseEvent evt) {
        int fila = tabla.rowAtPoint(evt.getPoint());
        if (fila >= 0) {
            tabla.setRowSelectionInterval(fila, fila);
        }
    }

    private void seleccionarSoftwareDesdeTabla() {
        int fila = tbl_software.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_software.getValueAt(fila, 0);
        String nombre = (String) tbl_software.getValueAt(fila, 1);

        softwareSeleccionado = new Software();
        softwareSeleccionado.setId(id);
        softwareSeleccionado.setNombre(nombre);
        software_txt.setText(nombre);
    }

    private void seleccionarEquipoDesdeTabla() {
        int fila = tbl_equipos.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_equipos.getValueAt(fila, 0);
        String nombre = (String) tbl_equipos.getValueAt(fila, 1);

        equipoSeleccionado = new EquipoOficina();
        equipoSeleccionado.setId(id);
        equipoSeleccionado.setNombre(nombre);
        id_txt.setText(String.valueOf(id));
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
        id_txt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        software_txt = new javax.swing.JTextField();
        cb_elegirtodoslosequipos = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        bt_eliminar = new javax.swing.JButton();
        bt_asignar = new javax.swing.JButton();
        bt_cancelar = new javax.swing.JButton();
        bt_quitar = new javax.swing.JButton();
        bt_registrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_software = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_equipos = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestor de Software"));

        jLabel1.setText("ID Equipo");

        id_txt.setEditable(false);
        id_txt.addActionListener(this::id_txtActionPerformed);

        jLabel2.setText("Software seleccionado");

        software_txt.setEditable(false);
        software_txt.addActionListener(this::software_txtActionPerformed);

        cb_elegirtodoslosequipos.setText("Elegir todos los equipos");
        cb_elegirtodoslosequipos.addActionListener(this::cb_elegirtodoslosequiposActionPerformed);

        jLabel4.setText("- - - - - - - - - - - -  Registro de software - - - - - - - - - - ");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jLabel3.setText("Nombre del software");

        bt_eliminar.setText("Eliminar");
        bt_eliminar.addActionListener(this::bt_eliminarActionPerformed);

        bt_asignar.setText("Asignar");
        bt_asignar.addActionListener(this::bt_asignarActionPerformed);

        bt_cancelar.setText("Cancelar");
        bt_cancelar.addActionListener(this::bt_cancelarActionPerformed);

        bt_quitar.setText("Quitar");
        bt_quitar.addActionListener(this::bt_quitarActionPerformed);

        bt_registrar.setText("Registrar");
        bt_registrar.addActionListener(this::bt_registrarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bt_registrar)
                        .addGap(36, 36, 36)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_eliminar)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb_elegirtodoslosequipos)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(software_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bt_asignar)
                        .addGap(23, 23, 23)
                        .addComponent(bt_quitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_cancelar)
                        .addGap(32, 32, 32))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(software_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cb_elegirtodoslosequipos)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_cancelar)
                    .addComponent(bt_asignar)
                    .addComponent(bt_quitar))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_registrar)
                    .addComponent(bt_eliminar)))
        );

        tbl_software.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_software.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_softwareMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_software);

        tbl_equipos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_equipos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_equiposMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_equipos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        /**
         * Carga todos los registros de software en {@code tbl_software}. Se
         * llama al iniciar y después de cada operación que modifique los datos.
         */
        /**
         * Carga todos los registros de software en {@code tbl_software}. Se
         * llama al iniciar y después de cada operación que modifique los datos.
         */
        private void cargarTablaSoftware() {
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID", "Nombre"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            List<Software> lista = softwareDAO.listar();
            for (Software s : lista) {
                modelo.addRow(new Object[]{s.getId(), s.getNombre()});
            }

            tbl_software.setModel(modelo);
        }

        /**
         * Carga todos los equipos de oficina en {@code tbl_equipos}. Se llama
         * al iniciar el formulario.
         */
        private void cargarTablaEquipos() {
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID", "Nombre", "Tipo", "Marca", "Serie", "Estado", "Software"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            List<EquipoOficina> lista = equipoDAO.listar();
            for (EquipoOficina e : lista) {
                String nombreSoftware = e.getSoftware() != null ? e.getSoftware().getNombre() : "Sin asignar";
                modelo.addRow(new Object[]{
                    e.getId(),
                    e.getNombre(),
                    e.getTipo(),
                    e.getMarca(),
                    e.getSerie(),
                    e.getEstado(),
                    nombreSoftware
                });
            }

            tbl_equipos.setModel(modelo);
        }

    private void cb_elegirtodoslosequiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_elegirtodoslosequiposActionPerformed
        if (cb_elegirtodoslosequipos.isSelected()) {
            // Deshabilita la selección de equipo individual
            tbl_equipos.setEnabled(false);
            id_txt.setText("Todos");
            equipoSeleccionado = null;
        } else {
            // Reactiva la selección individual
            tbl_equipos.setEnabled(true);
            id_txt.setText("");
            equipoSeleccionado = null;
        }
    }//GEN-LAST:event_cb_elegirtodoslosequiposActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
        /**
         * Captura el equipo seleccionado en {@code tbl_equipos} y muestra su id
         * en {@code id_txt}.
         *
         * @param evt evento de clic sobre la tabla de equipos
         */
    private void tbl_equiposMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_equiposMouseClicked
        int fila = tbl_equipos.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_equipos.getValueAt(fila, 0);
        String nombre = (String) tbl_equipos.getValueAt(fila, 1);

        equipoSeleccionado = new EquipoOficina();
        equipoSeleccionado.setId(id);
        equipoSeleccionado.setNombre(nombre);

        id_txt.setText(String.valueOf(id));
    }//GEN-LAST:event_tbl_equiposMouseClicked
        /**
         * Captura el software seleccionado en {@code tbl_software} y muestra su
         * nombre en {@code software_txt}.
         *
         * @param evt evento de clic sobre la tabla de software
         */
    private void tbl_softwareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_softwareMouseClicked
        int fila = tbl_software.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_software.getValueAt(fila, 0);
        String nombre = (String) tbl_software.getValueAt(fila, 1);

        softwareSeleccionado = new Software();
        softwareSeleccionado.setId(id);
        softwareSeleccionado.setNombre(nombre);

        software_txt.setText(nombre);

    }//GEN-LAST:event_tbl_softwareMouseClicked
        /**
         * Asigna el software seleccionado al equipo seleccionado. Valida que
         * ambos estén elegidos antes de intentar la operación.
         *
         * @param evt evento del botón Asignar
         */
    private void bt_asignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_asignarActionPerformed
        if (softwareSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un software de la tabla.");
            return;
        }

        if (cb_elegirtodoslosequipos.isSelected()) {
            List<EquipoOficina> todosLosEquipos = equipoDAO.listar();
            int exitosos = 0;

            for (EquipoOficina equipo : todosLosEquipos) {
                if (softwareDAO.asignarAEquipo(softwareSeleccionado.getId(), equipo.getId())) {
                    exitosos++;
                }
            }

            JOptionPane.showMessageDialog(this, "Software asignado a " + exitosos + " equipos.");
            cb_elegirtodoslosequipos.setSelected(false);
            tbl_equipos.setEnabled(true);

        } else {
            if (equipoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla.");
                return;
            }

            // idSoftware = software elegido, idEquipo = equipo elegido
            boolean ok = softwareDAO.asignarAEquipo(softwareSeleccionado.getId(), equipoSeleccionado.getId());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Software asignado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al asignar el software.");
            }
        }

        limpiarSeleccion();
        cargarTablaEquipos(); // recarga equipos para ver el cambio
        cargarTablaSoftware();
    }//GEN-LAST:event_bt_asignarActionPerformed
        /**
         * Quita la asignación de equipo del software seleccionado. No elimina
         * el software, solo lo deja sin equipo asociado.
         *
         * @param evt evento del botón Quitar
         */
    private void bt_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_quitarActionPerformed

        if (cb_elegirtodoslosequipos.isSelected()) {
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Desea quitar el software asignado a TODOS los equipos?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                List<EquipoOficina> todosLosEquipos = equipoDAO.listar();
                int exitosos = 0;

                for (EquipoOficina equipo : todosLosEquipos) {
                    if (equipo.getSoftware() != null) {
                        if (softwareDAO.quitarAsignacion(equipo.getId())) {
                            exitosos++;
                        }
                    }
                }

                JOptionPane.showMessageDialog(this, "Software quitado de " + exitosos + " equipos.");
                cb_elegirtodoslosequipos.setSelected(false);
                tbl_equipos.setEnabled(true);
                limpiarSeleccion();
                cargarTablaEquipos();
                cargarTablaSoftware();
            }

        } else {
            if (equipoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla.");
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Desea quitar el software asignado al equipo seleccionado?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                boolean ok = softwareDAO.quitarAsignacion(equipoSeleccionado.getId());
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Software quitado correctamente.");
                    limpiarSeleccion();
                    cargarTablaEquipos();
                    cargarTablaSoftware();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al quitar el software.");
                }
            }
        }
    }//GEN-LAST:event_bt_quitarActionPerformed
        /**
         * Limpia los campos y las selecciones actuales del formulario.
         *
         * @param evt evento del botón Cancelar
         */
    private void bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelarActionPerformed
        limpiarSeleccion();
    }//GEN-LAST:event_bt_cancelarActionPerformed
        /**
         * Registra un nuevo software con el nombre ingresado en
         * {@code jTextField1}. No lo asigna a ningún equipo; eso se hace con el
         * botón Asignar.
         *
         * @param evt evento del botón Registrar
         */
    private void bt_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_eliminarActionPerformed
        if (softwareSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un software de la tabla.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el software \"" + softwareSeleccionado.getNombre() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (softwareDAO.estaAsignado(softwareSeleccionado.getId())) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar, el software está asignado a un equipo.\nQuite la asignación primero.");
                return;
            }
            boolean ok = softwareDAO.eliminar(softwareSeleccionado.getId());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Software eliminado correctamente.");
                limpiarSeleccion();
                cargarTablaSoftware();
                cargarTablaEquipos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el software.");
            }
        }
    }//GEN-LAST:event_bt_eliminarActionPerformed
        /**
         * Resetea los campos de texto y las variables de selección.
         */
        private void limpiarSeleccion() {
            softwareSeleccionado = null;
            equipoSeleccionado = null;
            editandoSoftware = false;
            bt_registrar.setText("Registrar");
            id_txt.setText("");
            software_txt.setText("");
            jTextField1.setText("");
            tbl_software.clearSelection();
            tbl_equipos.clearSelection();
            cb_elegirtodoslosequipos.setSelected(false);
        }
    private void id_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_txtActionPerformed

    private void software_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_software_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_software_txtActionPerformed

    private void bt_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_registrarActionPerformed
        String nombre = jTextField1.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del software.");
            return;
        }

        if (editandoSoftware && softwareSeleccionado != null) {
            softwareSeleccionado.setNombre(nombre);
            boolean ok = softwareDAO.actualizar(softwareSeleccionado);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Software actualizado correctamente.");
                limpiarSeleccion();
                cargarTablaSoftware();
                editandoSoftware = false;
                bt_registrar.setText("Registrar");
                jTextField1.setText("");
                return;
            }
            JOptionPane.showMessageDialog(this, "Error al actualizar el software.");
            return;
        }

        Software nuevo = new Software();
        nuevo.setNombre(nombre);

        boolean ok = softwareDAO.guardar(nuevo);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Software registrado correctamente.");
            jTextField1.setText("");
            cargarTablaSoftware();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el software.");
        }
    }//GEN-LAST:event_bt_registrarActionPerformed

    private void mostrarDetallesSoftwareSeleccionado() {
        int fila = tbl_software.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_software.getValueAt(fila, 0);
        String nombre = (String) tbl_software.getValueAt(fila, 1);

        JOptionPane.showMessageDialog(this,
                "ID: " + id + "\nSoftware: " + nombre,
                "Detalle del software",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarDetallesEquipoSeleccionado() {
        int fila = tbl_equipos.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_equipos.getValueAt(fila, 0);
        String nombre = (String) tbl_equipos.getValueAt(fila, 1);
        String tipo = (String) tbl_equipos.getValueAt(fila, 2);
        String marca = (String) tbl_equipos.getValueAt(fila, 3);
        String serie = (String) tbl_equipos.getValueAt(fila, 4);
        String estado = (String) tbl_equipos.getValueAt(fila, 5);
        String software = (String) tbl_equipos.getValueAt(fila, 6);

        JOptionPane.showMessageDialog(this,
                "ID: " + id + "\nNombre: " + nombre + "\nTipo: " + tipo + "\nMarca: " + marca
                        + "\nSerie: " + serie + "\nEstado: " + estado + "\nSoftware: " + software,
                "Detalle del equipo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarSoftwareSeleccionado() {
        int fila = tbl_software.getSelectedRow();
        if (fila < 0) {
            return;
        }

        int id = (int) tbl_software.getValueAt(fila, 0);
        String nombre = (String) tbl_software.getValueAt(fila, 1);

        softwareSeleccionado = new Software();
        softwareSeleccionado.setId(id);
        softwareSeleccionado.setNombre(nombre);
        jTextField1.setText(nombre);
        editandoSoftware = true;
        bt_registrar.setText("Guardar");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_asignar;
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JButton bt_eliminar;
    private javax.swing.JButton bt_quitar;
    private javax.swing.JButton bt_registrar;
    private javax.swing.JCheckBox cb_elegirtodoslosequipos;
    private javax.swing.JTextField id_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField software_txt;
    private javax.swing.JTable tbl_equipos;
    private javax.swing.JTable tbl_software;
    // End of variables declaration//GEN-END:variables
}
