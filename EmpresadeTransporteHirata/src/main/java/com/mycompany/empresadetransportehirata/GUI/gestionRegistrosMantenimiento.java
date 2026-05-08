/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.CamionDAO;
import com.mycompany.empresadetransportehirata.Data.MantenimientoDAO;
import com.mycompany.empresadetransportehirata.Logica.Mantenimiento;
import com.mycompany.empresadetransportehirata.Logica.ValidadorDatos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de gestión de mantenimientos.
 *
 * Muestra camiones en la tabla superior y los mantenimientos del camión
 * seleccionado en la tabla inferior.
 */
public class gestionRegistrosMantenimiento extends javax.swing.JInternalFrame {

    private static final int ANIO_MINIMO_MANTENIMIENTO = 1980;

    private final MantenimientoDAO data = new MantenimientoDAO();
    private DefaultTableModel Mantenimiento = new DefaultTableModel();
    private DefaultTableModel MantenimientoPrevio = new DefaultTableModel();
    private String patenteCamionSeleccionado;

    /**
     * Inicializa la ventana y configura estilos, tablas, menús y listeners.
     */
    public gestionRegistrosMantenimiento() {
        initComponents();

        this.setClosable(true);
        this.setTitle("Gestión de Mantenimiento");

        java.awt.Color fondoPrincipal = new java.awt.Color(242, 248, 255);
        java.awt.Color fondoPanel = new java.awt.Color(230, 241, 252);
        java.awt.Color colorTitulo = new java.awt.Color(22, 90, 148);
        java.awt.Color colorCampo = new java.awt.Color(250, 253, 255);
        java.awt.Color colorBordeCampo = new java.awt.Color(160, 192, 224);

        getContentPane().setBackground(fondoPrincipal);
        jPanel1.setBackground(fondoPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2),
                "Registro de Mantenimiento",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13),
                colorTitulo));

        jLabel1.setForeground(colorTitulo);
        jLabel5.setForeground(colorTitulo);
        jLabel6.setForeground(colorTitulo);
        jLabel7.setForeground(colorTitulo);
        jLabel8.setForeground(colorTitulo);
        jLabel9.setForeground(colorTitulo);
        jLabel10.setForeground(colorTitulo);
        jLabel11.setForeground(colorTitulo);
        jLabel12.setForeground(colorTitulo);

        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel6.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel7.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel8.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel9.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel10.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel11.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel12.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        id_txt.setBackground(colorCampo);
        costo_txt.setBackground(colorCampo);
        observacion_txt.setBackground(colorCampo);
        taller_txt.setBackground(colorCampo);

        id_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        costo_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        observacion_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        taller_txt.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));

        camion_cb.setBackground(colorCampo);
        camion_cb.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        mantenimiento_cb.setBackground(colorCampo);
        mantenimiento_cb.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));
        estado_cb.setBackground(colorCampo);
        estado_cb.setBorder(javax.swing.BorderFactory.createLineBorder(colorBordeCampo, 2, true));

        agregar_butt.setBackground(new java.awt.Color(34, 139, 34));
        agregar_butt.setForeground(java.awt.Color.WHITE);
        agregar_butt.setFont(agregar_butt.getFont().deriveFont(java.awt.Font.BOLD));
        agregar_butt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 100, 20), 2, true));
        agregar_butt.setFocusPainted(false);

        actualizar_butt.setBackground(new java.awt.Color(255, 140, 0));
        actualizar_butt.setForeground(java.awt.Color.WHITE);
        actualizar_butt.setFont(actualizar_butt.getFont().deriveFont(java.awt.Font.BOLD));
        actualizar_butt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 95, 0), 2, true));
        actualizar_butt.setFocusPainted(false);

        eliminar_butt.setBackground(new java.awt.Color(178, 34, 34));
        eliminar_butt.setForeground(java.awt.Color.WHITE);
        eliminar_butt.setFont(eliminar_butt.getFont().deriveFont(java.awt.Font.BOLD));
        eliminar_butt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 20, 20), 2, true));
        eliminar_butt.setFocusPainted(false);

        limpiar_butt.setBackground(new java.awt.Color(70, 130, 180));
        limpiar_butt.setForeground(java.awt.Color.WHITE);
        limpiar_butt.setFont(limpiar_butt.getFont().deriveFont(java.awt.Font.BOLD));
        limpiar_butt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 90, 140), 2, true));
        limpiar_butt.setFocusPainted(false);

        agregar_butt.setToolTipText("Guardar un nuevo mantenimiento");
        actualizar_butt.setToolTipText("Actualizar el mantenimiento seleccionado");
        eliminar_butt.setToolTipText("Eliminar el mantenimiento seleccionado");
        limpiar_butt.setToolTipText("Limpiar campos y volver al modo registro");

        cargarCamionesEnCombo();

        mantenimiento_cb.removeAllItems();
        mantenimiento_cb.addItem("Cambio de Aceite");
        mantenimiento_cb.addItem("Neumáticos");
        mantenimiento_cb.addItem("Motor");
        mantenimiento_cb.addItem("Frenos");
        mantenimiento_cb.addItem("Suspensión");
        mantenimiento_cb.addItem("Revisión general");

        estado_cb.removeAllItems();
        estado_cb.addItem("Pendiente");
        estado_cb.addItem("En Proceso");
        estado_cb.addItem("Completado");

        Mantenimiento = new DefaultTableModel();
        Mantenimiento.addColumn("Patente");
        Mantenimiento.addColumn("Marca");
        Mantenimiento.addColumn("Modelo");
        Tb_Mant.setModel(Mantenimiento);

        Tb_Mant.setRowHeight(26);
        Tb_Mant.setGridColor(new java.awt.Color(210, 210, 210));
        Tb_Mant.setShowHorizontalLines(true);
        Tb_Mant.setShowVerticalLines(false);
        Tb_Mant.setSelectionBackground(new java.awt.Color(100, 149, 237));
        Tb_Mant.setSelectionForeground(java.awt.Color.WHITE);
        Tb_Mant.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        Tb_Mant.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Tb_Mant.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        Tb_Mant.getTableHeader().setBackground(new java.awt.Color(70, 130, 180));
        Tb_Mant.getTableHeader().setForeground(java.awt.Color.WHITE);
        Tb_Mant.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));
        Tb_Mant.getTableHeader().setReorderingAllowed(false);
        Tb_Mant.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2));
        Tb_Mant.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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

        MantenimientoPrevio = new DefaultTableModel();
        MantenimientoPrevio.addColumn("ID");
        MantenimientoPrevio.addColumn("Tipo Mantenimiento");
        MantenimientoPrevio.addColumn("Fecha");
        MantenimientoPrevio.addColumn("Costo");
        MantenimientoPrevio.addColumn("Estado");
        mant_previa.setModel(MantenimientoPrevio);

        mant_previa.setRowHeight(26);
        mant_previa.setGridColor(new java.awt.Color(210, 210, 210));
        mant_previa.setShowHorizontalLines(true);
        mant_previa.setShowVerticalLines(false);
        mant_previa.setSelectionBackground(new java.awt.Color(100, 149, 237));
        mant_previa.setSelectionForeground(java.awt.Color.WHITE);
        mant_previa.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        mant_previa.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        mant_previa.getTableHeader().setBackground(new java.awt.Color(70, 130, 180));
        mant_previa.getTableHeader().setForeground(java.awt.Color.WHITE);
        mant_previa.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 25));
        mant_previa.setFillsViewportHeight(true);
        jScrollPane3.getViewport().setBackground(java.awt.Color.WHITE);
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120, 170, 220), 2));
        mant_previa.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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

        cargarTabla();
        sincronizarKmTxt();

        JPopupMenu menuCamion = new JPopupMenu();
        JMenuItem opcionVerCamion = new JMenuItem("Ver datos del camión");
        opcionVerCamion.addActionListener(e -> verDetallesCamionSeleccionado());
        menuCamion.add(opcionVerCamion);
        Tb_Mant.setComponentPopupMenu(menuCamion);

        JPopupMenu menuMantenimiento = new JPopupMenu();
        JMenuItem opcionVerMantenimiento = new JMenuItem("Ver datos del mantenimiento");
        opcionVerMantenimiento.addActionListener(e -> verDetallesMantenimiento());
        menuMantenimiento.add(opcionVerMantenimiento);
        menuMantenimiento.addSeparator();

        JMenuItem opcionEditarMantenimiento = new JMenuItem("Editar mantenimiento");
        opcionEditarMantenimiento.addActionListener(e -> editarMantenimiento());
        menuMantenimiento.add(opcionEditarMantenimiento);

        JMenuItem opcionEliminarMantenimiento = new JMenuItem("Eliminar mantenimiento");
        opcionEliminarMantenimiento.addActionListener(e -> eliminarMantenimientoPopup());
        menuMantenimiento.add(opcionEliminarMantenimiento);
        mant_previa.setComponentPopupMenu(menuMantenimiento);

        Tb_Mant.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                seleccionarFilaDesdeEvento(evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                seleccionarFilaDesdeEvento(evt);
            }
        });

        mant_previa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                seleccionarFilaMantenimientoDesdeEvento(evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                seleccionarFilaMantenimientoDesdeEvento(evt);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int fila = mant_previa.rowAtPoint(evt.getPoint());
                cargarFormularioDesdeMantenimientoFila(fila);
            }
        });

        fecha_dt.setDate(new Date());
        aplicarEstadoBotones(false);
        if (camion_cb.getItemCount() > 0) {
            camion_cb.setSelectedIndex(0);
            patenteCamionSeleccionado = camion_cb.getItemAt(0);
            cargarMantenimientosPorCamion(patenteCamionSeleccionado);
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent evt) {
                manejarCierreSesion();
            }
        });
    }

    /**
     * Obtiene la lista de camiones desde la capa de datos.
     *
     * @return lista de camiones disponibles
     */
    private List<com.mycompany.empresadetransportehirata.Logica.Camion> obtenerListaCamiones() {
        CamionDAO camionDAO = new CamionDAO();
        return camionDAO.listarCamiones();
    }

    /**
     * Carga las patentes de camión en el combo de selección.
     */
    private void cargarCamionesEnCombo() {
        camion_cb.removeAllItems();
        for (com.mycompany.empresadetransportehirata.Logica.Camion c : obtenerListaCamiones()) {
            camion_cb.addItem(c.getPatente());
        }
    }

    /**
     * Refresca la tabla superior con el listado de camiones.
     */
    private void cargarTabla() {
        Mantenimiento.setRowCount(0);

        List<com.mycompany.empresadetransportehirata.Logica.Camion> listaCamiones = obtenerListaCamiones();

        for (com.mycompany.empresadetransportehirata.Logica.Camion c : listaCamiones) {
            Mantenimiento.addRow(new Object[]{
                c.getPatente(),
                c.getMarca(),
                c.getModelo()
            });
        }
        Tb_Mant.revalidate();
        Tb_Mant.repaint();
    }

    /**
     * Convierte el código de estado a texto legible.
     *
     * @param estado código de estado
     * @return etiqueta del estado
     */
    private String estadoTexto(int estado) {
        switch (estado) {
            case 0:
                return "Pendiente";
            case 1:
                return "En Proceso";
            case 2:
                return "Completado";
            default:
                return "Desconocido";
        }
    }

    /**
     * Habilita o deshabilita botones según modo edición.
     *
     * @param modoEdicion true para editar, false para registrar
     */
    private void aplicarEstadoBotones(boolean modoEdicion) {
        agregar_butt.setEnabled(!modoEdicion);
        actualizar_butt.setEnabled(modoEdicion);
        eliminar_butt.setEnabled(modoEdicion);
    }

    private void seleccionarFilaDesdeEvento(MouseEvent evt) {
        int fila = Tb_Mant.rowAtPoint(evt.getPoint());
        if (fila >= 0) {
            Tb_Mant.setRowSelectionInterval(fila, fila);
        }
    }

    /**
     * Selecciona en la tabla inferior la fila apuntada por el evento del mouse.
     *
     * @param evt evento del mouse sobre la tabla de mantenimientos
     */
    private void seleccionarFilaMantenimientoDesdeEvento(MouseEvent evt) {
        int fila = mant_previa.rowAtPoint(evt.getPoint());
        if (fila >= 0) {
            mant_previa.setRowSelectionInterval(fila, fila);
        }
    }

    /**
     * Valida los campos del formulario de mantenimiento.
     *
     * @return true si todos los datos son válidos
     */
    private boolean validarFormulario() {
        if (camion_cb.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un camión.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE);
            camion_cb.requestFocus();
            return false;
        }

        if (fecha_dt.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una fecha de mantenimiento.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE);
            fecha_dt.requestFocus();
            return false;
        }

        java.util.Date fechaSeleccionada = fecha_dt.getDate();

        if (!ValidadorDatos.esFechaRealista(fechaSeleccionada, ANIO_MINIMO_MANTENIMIENTO)) {
            JOptionPane.showMessageDialog(this,
                    "La fecha de mantenimiento es inválida. Debe estar entre " + ANIO_MINIMO_MANTENIMIENTO + " y la fecha actual.",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE);
            fecha_dt.requestFocus();
            return false;
        }

        if (costo_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el costo del mantenimiento.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE);
            costo_txt.requestFocus();
            return false;
        }

        if (obtenerCostoIngresado() == null) {
            return false;
        }

       return true;
    }

    /**
     * Convierte el costo ingresado a entero validando formato y rango positivo.
     *
     * @return costo válido o null si hay error
     */
    private Integer obtenerCostoIngresado() {
        try {
            int costo = Integer.parseInt(costo_txt.getText().trim());
            if (costo <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El costo debe ser un número mayor a 0.",
                        "Valor inválido",
                        JOptionPane.WARNING_MESSAGE);
                costo_txt.requestFocus();
                costo_txt.selectAll();
                return null;
            }
            return costo;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El costo debe contener solo números.",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE);
            costo_txt.requestFocus();
            costo_txt.selectAll();
            return null;
        }
    }

    /**
     * Carga en el formulario el mantenimiento seleccionado desde la tabla
     * inferior.
     *
     * @param fila índice de fila seleccionado
     */
    private void cargarFormularioDesdeMantenimientoFila(int fila) {
        if (fila < 0) {
            return;
        }

        int id = Integer.parseInt(MantenimientoPrevio.getValueAt(fila, 0).toString());
        Mantenimiento registro = data.buscarPorId(id);
        if (registro == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el mantenimiento seleccionado.",
                    "Sin datos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        id_txt.setText(String.valueOf(registro.getId()));
        camion_cb.setSelectedItem(registro.getCamion());
        mantenimiento_cb.setSelectedItem(registro.getTipoMantenimiento());
        fecha_dt.setDate(new Date(registro.getFecha().getTime()));
        km_txt.setText(String.valueOf(registro.getKmActual()));
        costo_txt.setText(String.valueOf(registro.getCosto()));
        estado_cb.setSelectedIndex(registro.getEstado());
        observacion_txt.setText(registro.getObservacion());
        taller_txt.setText(registro.getTaller());
        aplicarEstadoBotones(true);
    }

    /**
     * Construye una entidad Mantenimiento con los datos del formulario.
     *
     * @param costoIngresado costo ya validado
     * @return objeto de mantenimiento listo para persistir
     */
    private Mantenimiento construirMantenimientoDesdeFormulario(int costoIngresado) {
        Mantenimiento m = new Mantenimiento();
        m.setCamion(camion_cb.getSelectedItem().toString());
        m.setTipoMantenimiento(mantenimiento_cb.getSelectedItem().toString());
        m.setFecha(new java.sql.Date(fecha_dt.getDate().getTime()));
        m.setKmActual(Integer.parseInt(km_txt.getText().trim()));
        m.setCosto(costoIngresado);
        m.setEstado(estado_cb.getSelectedIndex());
        m.setObservacion(observacion_txt.getText().trim());
        m.setTaller(taller_txt.getText().trim());
        return m;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        id_txt = new javax.swing.JTextField();
        costo_txt = new javax.swing.JTextField();
        observacion_txt = new javax.swing.JTextField();
        taller_txt = new javax.swing.JTextField();
        camion_cb = new javax.swing.JComboBox<>();
        mantenimiento_cb = new javax.swing.JComboBox<>();
        estado_cb = new javax.swing.JComboBox<>();
        limpiar_butt = new javax.swing.JButton();
        eliminar_butt = new javax.swing.JButton();
        agregar_butt = new javax.swing.JButton();
        actualizar_butt = new javax.swing.JButton();
        fecha_dt = new com.toedter.calendar.JDateChooser();
        km_txt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tb_Mant = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        mant_previa = new javax.swing.JTable();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro de Mantenimiento"));

        jLabel1.setText("ID ");

        jLabel5.setText("Camion");

        jLabel6.setText("Tipo Mantenimiento");

        jLabel7.setText("Fecha");

        jLabel8.setText("Km actual");

        jLabel9.setText("Costo");

        jLabel10.setText("Estado");

        jLabel11.setText("Observacion");

        jLabel12.setText("Taller");

        id_txt.setEditable(false);
        id_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_txtActionPerformed(evt);
            }
        });

        camion_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        camion_cb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                camion_cbItemStateChanged(evt);
            }
        });
        camion_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camion_cbActionPerformed(evt);
            }
        });

        mantenimiento_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mantenimiento_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantenimiento_cbActionPerformed(evt);
            }
        });

        estado_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        estado_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_cbActionPerformed(evt);
            }
        });

        limpiar_butt.setText("Limpiar");
        limpiar_butt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiar_buttActionPerformed(evt);
            }
        });

        eliminar_butt.setText("Eliminar");
        eliminar_butt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_buttActionPerformed(evt);
            }
        });

        agregar_butt.setText("Agregar");
        agregar_butt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_buttActionPerformed(evt);
            }
        });

        actualizar_butt.setText("Actualizar");
        actualizar_butt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizar_buttActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel1)))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(camion_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(84, 84, 84)
                                    .addComponent(fecha_dt, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10))
                                    .addGap(61, 61, 61)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(estado_cb, 0, 158, Short.MAX_VALUE)
                                        .addComponent(costo_txt)
                                        .addComponent(observacion_txt)
                                        .addComponent(taller_txt)
                                        .addComponent(km_txt)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(actualizar_butt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(eliminar_butt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(agregar_butt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(limpiar_butt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mantenimiento_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(camion_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mantenimiento_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregar_butt)
                    .addComponent(jLabel7)
                    .addComponent(fecha_dt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualizar_butt)
                    .addComponent(jLabel8)
                    .addComponent(km_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(costo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(estado_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(observacion_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(taller_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(eliminar_butt)
                        .addGap(9, 9, 9)
                        .addComponent(limpiar_butt)))
                .addGap(70, 70, 70))
        );

        Tb_Mant.setModel(new javax.swing.table.DefaultTableModel(
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
        Tb_Mant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tb_MantMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tb_Mant);

        mant_previa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(mant_previa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Guarda un nuevo mantenimiento usando los datos validados del formulario.
     *
     * @param evt evento del botón agregar
     */
    private void agregar_buttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_buttActionPerformed
        if (!validarFormulario()) {
            return;
        }

        Integer costoIngresado = obtenerCostoIngresado();
        if (costoIngresado == null) {
            return;
        }

        Mantenimiento m = construirMantenimientoDesdeFormulario(costoIngresado);

        if (data.agregar(m)) {
            JOptionPane.showMessageDialog(this,
                    "Mantenimiento agregado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
            refrescarMantenimientosDelCamionSeleccionado();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar el mantenimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_agregar_buttActionPerformed

    /**
     * Limpia el formulario y restaura la selección principal cuando existe un
     * camión activo.
     */
    private void limpiarCampos() {
        String patenteActual = camion_cb.getSelectedItem() != null
                ? camion_cb.getSelectedItem().toString()
                : null;
        id_txt.setText("");
        fecha_dt.setDate(new Date());
        km_txt.setText("0");
        costo_txt.setText("");
        observacion_txt.setText("");
        taller_txt.setText("");
        if (patenteActual != null) {
            camion_cb.setSelectedItem(patenteActual);
        } else if (camion_cb.getItemCount() > 0) {
            camion_cb.setSelectedIndex(0);
        }
        if (mantenimiento_cb.getItemCount() > 0) {
            mantenimiento_cb.setSelectedIndex(0);
        }
        if (estado_cb.getItemCount() > 0) {
            estado_cb.setSelectedIndex(0);
        }
        Tb_Mant.clearSelection();
        mant_previa.clearSelection();
        aplicarEstadoBotones(false);
    }


    private void id_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_txtActionPerformed

    private void camion_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camion_cbActionPerformed

    }//GEN-LAST:event_camion_cbActionPerformed

    private void mantenimiento_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantenimiento_cbActionPerformed

    }//GEN-LAST:event_mantenimiento_cbActionPerformed

    private void estado_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_cbActionPerformed

    }//GEN-LAST:event_estado_cbActionPerformed

    private void limpiar_buttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiar_buttActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_limpiar_buttActionPerformed

    /**
     * Actualiza el mantenimiento seleccionado con los datos actuales del
     * formulario.
     *
     * @param evt evento del botón actualizar
     */
    private void actualizar_buttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizar_buttActionPerformed

        if (id_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un registro de la tabla para actualizar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarFormulario()) {
            return;
        }

        Integer costoIngresado = obtenerCostoIngresado();
        if (costoIngresado == null) {
            return;
        }
        Mantenimiento m = construirMantenimientoDesdeFormulario(costoIngresado);
        m.setId(Integer.parseInt(id_txt.getText().trim()));

        if (data.actualizar(m)) {
            JOptionPane.showMessageDialog(this,
                    "Mantenimiento actualizado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
            refrescarMantenimientosDelCamionSeleccionado();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el mantenimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_actualizar_buttActionPerformed

    /**
     * Reacciona a la selección de un camión en la tabla superior y carga sus
     * mantenimientos previos.
     *
     * @param evt evento de clic en la tabla superior
     */
    private void Tb_MantMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tb_MantMouseClicked
        int fila = Tb_Mant.getSelectedRow();
        if (fila >= 0) {
            String patenteCamion = Mantenimiento.getValueAt(fila, 0).toString();
            patenteCamionSeleccionado = patenteCamion;
            camion_cb.setSelectedItem(patenteCamion);
            cargarMantenimientosPorCamion(patenteCamion);
            limpiarCampos();
        }
    }//GEN-LAST:event_Tb_MantMouseClicked

    /**
     * Elimina el mantenimiento actualmente seleccionado desde el formulario.
     *
     * @param evt evento del botón eliminar
     */
    private void eliminar_buttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_buttActionPerformed
        if (id_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un registro de la tabla para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este registro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(id_txt.getText().trim());
            if (data.eliminar(id)) {
                JOptionPane.showMessageDialog(this,
                        "Mantenimiento eliminado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
                refrescarMantenimientosDelCamionSeleccionado();
            }
        }
    }//GEN-LAST:event_eliminar_buttActionPerformed

    /**
     * Carga la tabla inferior con los mantenimientos de una patente dada.
     *
     * @param patente patente del camión cuyos mantenimientos se mostrarán
     */
    private void cargarMantenimientosPorCamion(String patente) {
        MantenimientoPrevio.setRowCount(0);

        List<Mantenimiento> lista = data.listarPorCamion(patente);

        for (Mantenimiento m : lista) {
            MantenimientoPrevio.addRow(new Object[]{
                m.getId(),
                m.getTipoMantenimiento(),
                m.getFecha(),
                m.getCosto(),
                estadoTexto(m.getEstado())
            });
        }
    }

    /**
     * Reacciona al cambio de camión en el combo y refresca mantenimientos y
     * kilometraje asociado.
     *
     * @param evt evento de cambio de ítem del combo de camiones
     */
    private void camion_cbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_camion_cbItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            if (camion_cb.getSelectedItem() == null) {
                return;
            }
            String patente = camion_cb.getSelectedItem().toString();
            patenteCamionSeleccionado = patente;
            cargarMantenimientosPorCamion(patente);
            sincronizarKmTxt();
        }
    }//GEN-LAST:event_camion_cbItemStateChanged

    /**
     * Refresca la tabla inferior según el camión activo en el combo.
     */
    private void refrescarMantenimientosDelCamionSeleccionado() {
        if (camion_cb.getSelectedItem() == null) {
            return;
        }
        String patente = camion_cb.getSelectedItem().toString();
        patenteCamionSeleccionado = patente;
        cargarMantenimientosPorCamion(patente);
    }

    /**
     * Muestra en un diálogo los datos básicos del camión seleccionado en tabla
     * superior.
     */
    private void verDetallesCamionSeleccionado() {
        int fila = Tb_Mant.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un camión en la tabla superior.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder detalles = new StringBuilder();
        detalles.append("Patente: ").append(Mantenimiento.getValueAt(fila, 0)).append("\n");
        detalles.append("Marca: ").append(Mantenimiento.getValueAt(fila, 1)).append("\n");
        detalles.append("Modelo: ").append(Mantenimiento.getValueAt(fila, 2)).append("\n");

        JOptionPane.showMessageDialog(this,
                detalles.toString(),
                "Datos del camión",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra en un diálogo los datos completos del mantenimiento seleccionado.
     */
    private void verDetallesMantenimiento() {
        int fila = mant_previa.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un mantenimiento en la tabla inferior.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(MantenimientoPrevio.getValueAt(fila, 0).toString());
        Mantenimiento registro = data.buscarPorId(id);
        if (registro == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el mantenimiento seleccionado.",
                    "Sin datos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder detalles = new StringBuilder();
        detalles.append("ID: ").append(registro.getId()).append("\n");
        detalles.append("Camión: ").append(registro.getCamion()).append("\n");
        detalles.append("Tipo Mantenimiento: ").append(registro.getTipoMantenimiento()).append("\n");
        detalles.append("Fecha: ").append(registro.getFecha()).append("\n");
        detalles.append("Km Actual: ").append(registro.getKmActual()).append("\n");
        detalles.append("Costo: $").append(registro.getCosto()).append("\n");
        detalles.append("Estado: ").append(estadoTexto(registro.getEstado())).append("\n");
        detalles.append("Observación: ").append(registro.getObservacion()).append("\n");
        detalles.append("Taller: ").append(registro.getTaller()).append("\n");

        JOptionPane.showMessageDialog(this,
                detalles.toString(),
                "Detalles del Mantenimiento",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Lleva el mantenimiento seleccionado al formulario para su edición.
     */
    private void sincronizarKmTxt() {
        if (camion_cb.getSelectedItem() == null) {
            return;
        }
        String patente = camion_cb.getSelectedItem().toString();
        CamionDAO camionDAO = new CamionDAO();
        int kmActual = camionDAO.obtenerKmPorPatente(patente);
        km_txt.setText(String.valueOf(kmActual)); // Antes: km_jp.setValue(kmActual)
    }

    /**
     * Lleva el mantenimiento seleccionado al formulario para su edición.
     */
    private void editarMantenimiento() {
        int fila = mant_previa.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un mantenimiento en la tabla inferior.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        cargarFormularioDesdeMantenimientoFila(fila);
    }

    /**
     * Elimina el mantenimiento seleccionado desde el menú contextual de la
     * tabla inferior.
     */
    private void eliminarMantenimientoPopup() {
        int fila = mant_previa.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un mantenimiento en la tabla inferior.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este registro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(MantenimientoPrevio.getValueAt(fila, 0).toString());
            if (data.eliminar(id)) {
                JOptionPane.showMessageDialog(this,
                        "Mantenimiento eliminado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
                refrescarMantenimientosDelCamionSeleccionado();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar el mantenimiento.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Pregunta al usuario si desea cerrar sesión, cerrar solo la ventana o
     * cancelar el cierre.
     */
    private void manejarCierreSesion() {
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
            cerrarSesion();
        } else if (opcion == 1) {
            dispose();
        }
    }

    /**
     * Cierra la sesión actual y regresa a la pantalla de login.
     */
    private void cerrarSesion() {
        java.awt.Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
        if (ventanaPadre != null) {
            ventanaPadre.dispose();
        } else {
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tb_Mant;
    private javax.swing.JButton actualizar_butt;
    private javax.swing.JButton agregar_butt;
    private javax.swing.JComboBox<String> camion_cb;
    private javax.swing.JTextField costo_txt;
    private javax.swing.JButton eliminar_butt;
    private javax.swing.JComboBox<String> estado_cb;
    private com.toedter.calendar.JDateChooser fecha_dt;
    private javax.swing.JTextField id_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField km_txt;
    private javax.swing.JButton limpiar_butt;
    private javax.swing.JTable mant_previa;
    private javax.swing.JComboBox<String> mantenimiento_cb;
    private javax.swing.JTextField observacion_txt;
    private javax.swing.JTextField taller_txt;
    // End of variables declaration//GEN-END:variables
}
