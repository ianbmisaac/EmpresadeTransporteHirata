/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import javax.swing.JOptionPane;

import com.mycompany.empresadetransportehirata.Data.consultas;
import com.mycompany.empresadetransportehirata.Logica.ValidarRut;

/**
 * Ventana de autenticación del sistema.
 *
 * <p>Solicita rut y contraseña, valida el formato del rut y delega la
 * autenticación a la capa de datos.</p>
 */
public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    /**
     * Crea la ventana de login y aplica su estilo visual inicial.
     */
    public Login() {
        initComponents();
        aplicarEstiloYLayout();
        getRootPane().setDefaultButton(jButton1);
    }

    /**
     * Reorganiza visualmente la ventana para presentar un formulario de acceso
     * con estilo personalizado.
     */
    private void aplicarEstiloYLayout() {
        java.awt.Color fondoVentana = new java.awt.Color(236, 244, 252);
        java.awt.Color fondoTarjeta = new java.awt.Color(248, 252, 255);
        java.awt.Color bordeTarjeta = new java.awt.Color(120, 170, 210);
        java.awt.Color textoPrincipal = new java.awt.Color(17, 79, 128);
        java.awt.Color textoSecundario = new java.awt.Color(29, 108, 154);
        java.awt.Color colorCampo = new java.awt.Color(255, 255, 255);
        java.awt.Color bordeCampo = new java.awt.Color(128, 182, 218);
        java.awt.Color colorBoton = new java.awt.Color(0, 148, 196);

        setTitle("Ingreso al Sistema - Empresa de Transporte Hirata");

        jLabel3.setText("Empresa de Transporte Hirata");
        jLabel4.setText("Ingresa tus credenciales para continuar");

        jLabel3.setForeground(textoPrincipal);
        jLabel4.setForeground(textoSecundario);
        jLabel1.setForeground(textoPrincipal);
        jLabel2.setForeground(textoPrincipal);

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", java.awt.Font.BOLD, 30));
        jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        user.setBackground(colorCampo);
        pass.setBackground(colorCampo);
        user.setForeground(new java.awt.Color(33, 61, 85));
        pass.setForeground(new java.awt.Color(33, 61, 85));
        user.setBorder(javax.swing.BorderFactory.createLineBorder(bordeCampo, 2, true));
        pass.setBorder(javax.swing.BorderFactory.createLineBorder(bordeCampo, 2, true));
        user.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        pass.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        jButton1.setText("Ingresar");
        jButton1.setBackground(colorBoton);
        jButton1.setForeground(java.awt.Color.WHITE);
        jButton1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2, true));
        jButton1.setFocusPainted(false);

        java.awt.Container contenedor = getContentPane();
        contenedor.removeAll();
        contenedor.setBackground(fondoVentana);
        contenedor.setLayout(new java.awt.GridBagLayout());

        javax.swing.JPanel tarjeta = new javax.swing.JPanel(new java.awt.GridBagLayout());
        tarjeta.setBackground(fondoTarjeta);
        tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(bordeTarjeta, 3, true),
            javax.swing.BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 6, 5, 6);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tarjeta.add(jLabel3, gbc);

        gbc.gridy = 1;
        tarjeta.add(jLabel4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tarjeta.add(jLabel1, gbc);

        gbc.gridy = 3;
        user.setPreferredSize(new java.awt.Dimension(320, 34));
        tarjeta.add(user, gbc);

        gbc.gridy = 4;
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tarjeta.add(jLabel2, gbc);

        gbc.gridy = 5;
        pass.setPreferredSize(new java.awt.Dimension(320, 34));
        tarjeta.add(pass, gbc);

        gbc.gridy = 6;
        jButton1.setPreferredSize(new java.awt.Dimension(320, 40));
        tarjeta.add(jButton1, gbc);

        java.awt.GridBagConstraints root = new java.awt.GridBagConstraints();
        root.gridx = 0;
        root.gridy = 0;
        root.insets = new java.awt.Insets(12, 12, 12, 12);
        root.fill = java.awt.GridBagConstraints.NONE;
        contenedor.add(tarjeta, root);

        java.awt.Dimension tamanoFijo = new java.awt.Dimension(760, 560);
        setSize(tamanoFijo);
        setMinimumSize(tamanoFijo);
        setMaximumSize(tamanoFijo);
        setResizable(false);
        setLocationRelativeTo(null);
        contenedor.revalidate();
        contenedor.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        user = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Rut");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Ingresar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        user.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        user.addActionListener(this::userActionPerformed);

        pass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pass.addActionListener(this::passActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Empresa de Transporte Hirata ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Ingresa tus credenciales para continuar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(user)
                        .addComponent(pass)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Ejecuta el inicio de sesión al presionar Enter en el campo contraseña.
     *
     * @param evt evento de acción del campo
     */
    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
        jButton1.doClick();
    }//GEN-LAST:event_passActionPerformed

    /**
     * Traslada el foco al campo de contraseña al confirmar el rut.
     *
     * @param evt evento de acción del campo rut
     */
    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        // TODO add your handling code here:
        pass.requestFocus();
    }//GEN-LAST:event_userActionPerformed

    /**
     * Valida las credenciales ingresadas y, si son correctas, abre el menú
     * principal con el rol autenticado.
     *
     * @param evt evento del botón ingresar
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String rut = normalizarRut(user.getText());
        String contrasena = new String(pass.getPassword()).trim();
        
        
        if (rut.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un RUT.");
            user.requestFocusInWindow();
            return;
        }
        
        if (contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una contraseña.");
            pass.requestFocusInWindow();
            return;
        }

        if (!ValidarRut.validar(rut)) {
            JOptionPane.showMessageDialog(null, "El RUT debe tener formato válido, por ejemplo: 12345678-9");
            user.requestFocusInWindow();
            user.selectAll();
            return;
        }

        user.setText(rut);
        
        consultas q = new consultas();
        String rolObtienido = q.obtenerRolUsuario(rut, contrasena);

        if (!rolObtienido.isEmpty()) {
            new menu(rolObtienido, rut).setVisible(true); 
            this.dispose(); 
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Normaliza el rut eliminando separadores extra y reinsertando el guion
     * antes del dígito verificador.
     *
     * @param rutIngresado rut ingresado por el usuario
     * @return rut normalizado listo para validación y autenticación
     */
    private String normalizarRut(String rutIngresado) {
        if (rutIngresado == null) {
            return "";
        }

        String rutLimpio = rutIngresado
                .trim()
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();

        if (rutLimpio.length() <= 1) {
            return rutLimpio;
        }

        return rutLimpio.substring(0, rutLimpio.length() - 1) + "-" + rutLimpio.charAt(rutLimpio.length() - 1);
    }

    /**
     * Punto de entrada para ejecutar la pantalla de login en modo standalone.
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
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables
}
