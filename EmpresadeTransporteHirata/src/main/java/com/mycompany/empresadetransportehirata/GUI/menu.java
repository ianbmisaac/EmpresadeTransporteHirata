/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Ventana principal del sistema.
 *
 * Habilita opciones del menú según el rol autenticado y abre las pantallas
 * internas correspondientes.
 */
public class menu extends javax.swing.JFrame {

    private static final String ROL_CONDUCTOR = "conductor";
    private static final String ROL_ADMIN_FLOTA = "admin_flota";
    private static final String ROL_ADMIN_MANTENIMIENTO = "admin_mantenimiento";
    private static final String ROL_ADMIN_INVENTARIO = "admin_inventario";
    private static final String ROL_TECNICO_IT = "tecnico_it";

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(menu.class.getName());
    private final String rolUsuarioSesion;
    private final String rutUsuarioSesion;

    /**
     * Crea el menú principal sin rut de sesión asociado.
     *
     * @param rol rol autenticado del usuario
     */
    public menu(String rol) {
        this(rol, null);
    }

    /**
     * Crea el menú principal y configura permisos según el rol.
     *
     * @param rol rol autenticado del usuario
     * @param rutUsuarioSesion rut del usuario en sesión, si aplica
     */
    public menu(String rol, String rutUsuarioSesion) {
        initComponents();
        this.rolUsuarioSesion = rol;
        this.rutUsuarioSesion = rutUsuarioSesion;
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setTitle("Empresa de Transporte Hirata");

        
        java.awt.Color fondoPrincipal = new java.awt.Color(242, 248, 255);
        java.awt.Color colorBarra = new java.awt.Color(22, 90, 148);
        java.awt.Color colorTexto = java.awt.Color.WHITE;
        java.awt.Font fuenteMenu = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13);

       
        panel.setBackground(fondoPrincipal);

        agregarPanelBienvenida();

        barra.setBackground(colorBarra);
        barra.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));

        estilizarMenu(jMenu1, colorBarra, colorTexto, fuenteMenu);
        estilizarMenu(jMenu2, colorBarra, colorTexto, fuenteMenu);
        estilizarMenu(jMenu3, colorBarra, colorTexto, fuenteMenu);

        estilizarMenuItem(jMenuItem1);
        estilizarMenuItem(jMenuItem2);
        estilizarMenuItem(jMenuItem3);
        estilizarMenuItem(jMenuItem4);
        estilizarMenuItem(jRadioButtonMenuItem1);

        jMenu1.setText("Sesión");
        jMenu2.setVisible(false);

        JMenuItem cerrarSesionItem = new JMenuItem("Cerrar sesión");
        estilizarMenuItem(cerrarSesionItem);
        cerrarSesionItem.addActionListener(e -> cerrarSesion());
        jMenu1.add(cerrarSesionItem);

        aplicarPermisosPorRol(rol);
    }

    /**
     * Construye el panel central de bienvenida con accesos rápidos según el
     * rol visible en la sesión actual.
     */
    private void agregarPanelBienvenida() {
        javax.swing.JPanel panelCentro = new javax.swing.JPanel();
        panelCentro.setLayout(new java.awt.GridBagLayout());
        panelCentro.setBackground(new java.awt.Color(242, 248, 255));
        panelCentro.setOpaque(true);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new java.awt.Insets(10, 0, 10, 0);

        // Título
        javax.swing.JLabel titulo = new javax.swing.JLabel("Empresa de Transporte Hirata");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        titulo.setForeground(new java.awt.Color(22, 90, 148));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.JLabel subtitulo = new javax.swing.JLabel("Sistema de Gestión");
        subtitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        subtitulo.setForeground(new java.awt.Color(80, 120, 160));

        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setForeground(new java.awt.Color(120, 170, 220));
        sep.setPreferredSize(new java.awt.Dimension(400, 2));

        javax.swing.JLabel instruccion = new javax.swing.JLabel("Seleccione una opción para comenzar");
        instruccion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 13));
        instruccion.setForeground(new java.awt.Color(120, 160, 200));

        gbc.gridy = 0;
        panelCentro.add(titulo, gbc);
        gbc.gridy = 1;
        panelCentro.add(sep, gbc);
        gbc.gridy = 2;
        panelCentro.add(subtitulo, gbc);
        gbc.gridy = 3;
        panelCentro.add(instruccion, gbc);

        // Panel de botones en fila
        javax.swing.JPanel panelBotones = new javax.swing.JPanel();
        panelBotones.setBackground(new java.awt.Color(242, 248, 255));
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));

        // Agrega solo los botones visibles según el rol 
        bienvenida_bt1 = crearBotonAcceso("📋 Registrar Kilometraje", e -> abrirVentanaInterna(new registroKilometrajeCamiones(rutUsuarioSesion)));
        bienvenida_bt2 = crearBotonAcceso("🚛 Camiones", e -> abrirVentanaInterna(new almacenInfoCamiones()));
        bienvenida_bt3 = crearBotonAcceso("👤 Conductores", e -> abrirVentanaInterna(new almacenarInfoConductor()));
        bienvenida_bt4 = crearBotonAcceso("🔧 Gestión Mantenimiento", e -> abrirVentanaInterna(new gestionRegistrosMantenimiento()));
        bienvenida_bt5 = crearBotonAcceso("📦 Inventario de Piezas", e -> abrirVentanaInterna(new gestionInventarioPiezas(rolUsuarioSesion)));

        panelBotones.add(bienvenida_bt1);
        panelBotones.add(bienvenida_bt2);
        panelBotones.add(bienvenida_bt3);
        panelBotones.add(bienvenida_bt4);
        panelBotones.add(bienvenida_bt5);

        gbc.gridy = 4;
        gbc.insets = new java.awt.Insets(30, 0, 0, 0);
        panelCentro.add(panelBotones, gbc);

        panelCentro.setBounds(0, 0, 1920, 1080);
        panel.add(panelCentro, javax.swing.JLayeredPane.DEFAULT_LAYER);

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                panelCentro.setBounds(0, 0, panel.getWidth(), panel.getHeight());
            }
        });
        javax.swing.JButton bt_cerrarSesion = new javax.swing.JButton("🚪 Cerrar Sesión");
        bt_cerrarSesion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        bt_cerrarSesion.setBackground(new java.awt.Color(178, 34, 34));
        bt_cerrarSesion.setForeground(java.awt.Color.WHITE);
        bt_cerrarSesion.setFocusPainted(false);
        bt_cerrarSesion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 20, 20), 2, true));
        bt_cerrarSesion.setPreferredSize(new java.awt.Dimension(200, 60));
        bt_cerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bt_cerrarSesion.addActionListener(e -> cerrarSesion());

        bt_cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bt_cerrarSesion.setBackground(new java.awt.Color(210, 50, 50));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bt_cerrarSesion.setBackground(new java.awt.Color(178, 34, 34));
            }
        });

        panelBotones.add(bt_cerrarSesion);
    }

    /**
     * Crea un botón de acceso rápido para abrir una funcionalidad del sistema.
     *
     * @param texto etiqueta del botón
     * @param accion acción a ejecutar al pulsarlo
     * @return botón configurado con estilo y listeners
     */
    private javax.swing.JButton crearBotonAcceso(String texto, java.awt.event.ActionListener accion) {
        javax.swing.JButton bt = new javax.swing.JButton(texto);
        bt.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        bt.setBackground(new java.awt.Color(22, 90, 148));
        bt.setForeground(java.awt.Color.WHITE);
        bt.setFocusPainted(false);
        bt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(15, 60, 110), 2, true));
        bt.setPreferredSize(new java.awt.Dimension(200, 60));
        bt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bt.addActionListener(accion);

        // Efecto hover
        bt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bt.setBackground(new java.awt.Color(34, 120, 190));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bt.setBackground(new java.awt.Color(22, 90, 148));
            }
        });

        return bt;
    }

    /**
     * Aplica estilo visual a un menú de la barra principal.
     *
     * @param menu menú a estilizar
     * @param fondo color de fondo
     * @param texto color del texto
     * @param fuente fuente a aplicar
     */
    private void estilizarMenu(javax.swing.JMenu menu,
            java.awt.Color fondo,
            java.awt.Color texto,
            java.awt.Font fuente) {
        menu.setBackground(fondo);
        menu.setForeground(texto);
        menu.setFont(fuente);
        menu.setOpaque(true);
        menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
    }

    /**
     * Aplica estilo visual homogéneo a un ítem de menú.
     *
     * @param item elemento de menú a estilizar
     */
    private void estilizarMenuItem(javax.swing.JMenuItem item) {
        item.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        item.setBackground(new java.awt.Color(230, 241, 252));
        item.setForeground(new java.awt.Color(22, 90, 148));
        item.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 14, 6, 14));
        item.setOpaque(true);
    }

    /**
     * Configura la visibilidad de opciones según el rol.
     *
     * @param rol rol autenticado
     */
    private void aplicarPermisosPorRol(String rol) {
        // Ocultar todo primero
        jMenuItem1.setVisible(false);
        jMenuItem2.setVisible(false);
        jRadioButtonMenuItem1.setVisible(false);
        jMenuItem3.setVisible(false);
        jMenuItem4.setVisible(false);
        jMenuItem5.setVisible(false);
        bienvenida_bt1.setVisible(false);
        bienvenida_bt2.setVisible(false);
        bienvenida_bt3.setVisible(false);
        bienvenida_bt4.setVisible(false);
        bienvenida_bt5.setVisible(false);

        if (ROL_CONDUCTOR.equals(rol)) {
            jMenuItem1.setVisible(true);
            bienvenida_bt1.setVisible(true);
            return;
        }

        if (ROL_ADMIN_FLOTA.equals(rol)) {
            jMenuItem2.setVisible(true);
            jRadioButtonMenuItem1.setVisible(true);
            bienvenida_bt2.setVisible(true);
            bienvenida_bt3.setVisible(true);
            return;
        }

        if (ROL_ADMIN_MANTENIMIENTO.equals(rol)) {
            jMenuItem3.setVisible(true);
            bienvenida_bt4.setVisible(true);
            return;
        }

        if (ROL_ADMIN_INVENTARIO.equals(rol)) {
            jMenuItem4.setVisible(true);
            bienvenida_bt5.setVisible(true);
        }
        if (ROL_TECNICO_IT.equals(rol)){
            jMenuItem5.setVisible(true);
        }
    }

    /**
     * Agrega y muestra una ventana interna en primer plano.
     *
     * @param frame ventana interna a mostrar
     */
    private void abrirVentanaInterna(javax.swing.JInternalFrame frame) {
        panel.add(frame);
        frame.toFront();
        frame.show();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JDesktopPane();
        barra = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");
        barra.add(jMenu1);

        jMenu2.setText("Edit");
        barra.add(jMenu2);

        jMenu3.setText("Opciones");

        jMenuItem1.setText("Registrar Kilometraje");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Camiones ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Conductores");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        jMenuItem3.setText("Gestión Mantenimiento");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Control Inventario");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Registro Software");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        barra.add(jMenu3);

        setJMenuBar(barra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Abre la pantalla de gestión de mantenimiento desde el menú.
     *
     * @param evt evento de selección del ítem
     */
    @SuppressWarnings("unused")
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        abrirVentanaInterna(new gestionRegistrosMantenimiento());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * Abre la pantalla de control de inventario desde el menú.
     *
     * @param evt evento de selección del ítem
     */
    @SuppressWarnings("unused")
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        abrirVentanaInterna(new gestionInventarioPiezas(rolUsuarioSesion));
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * Abre la pantalla de gestión de conductores desde el menú.
     *
     * @param evt evento de selección del ítem
     */
    @SuppressWarnings("unused")
    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        abrirVentanaInterna(new almacenarInfoConductor());
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    /**
     * Abre la pantalla de gestión de camiones desde el menú.
     *
     * @param evt evento de selección del ítem
     */
    @SuppressWarnings("unused")
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        abrirVentanaInterna(new almacenInfoCamiones());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * Abre la pantalla de registro de kilometraje usando el rut de la sesión
     * actual.
     *
     * @param evt evento de selección del ítem
     */
    @SuppressWarnings("unused")
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        abrirVentanaInterna(new registroKilometrajeCamiones(rutUsuarioSesion));
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
    @SuppressWarnings("unused")
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        abrirVentanaInterna(new gestionSoftware(rolUsuarioSesion));
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    /**
     * Cierra la sesión actual y vuelve a la pantalla de login.
     */
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar la sesión actual y volver al inicio?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
            dispose();
        }
    }

    /**
     * Punto de entrada para ejecutar el menú principal en modo standalone.
     *
     * @param args argumentos de línea de comandos no utilizados
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new menu("admin_flota").setVisible(true));
    }
    private javax.swing.JButton bienvenida_bt1;
    private javax.swing.JButton bienvenida_bt2;
    private javax.swing.JButton bienvenida_bt3;
    private javax.swing.JButton bienvenida_bt4;
    private javax.swing.JButton bienvenida_bt5;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barra;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JDesktopPane panel;
    // End of variables declaration//GEN-END:variables
}
