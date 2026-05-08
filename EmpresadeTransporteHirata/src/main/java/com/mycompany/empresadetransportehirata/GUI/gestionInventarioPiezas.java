/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.empresadetransportehirata.GUI;

import com.mycompany.empresadetransportehirata.Data.InventarioPiezasDAO;
import com.mycompany.empresadetransportehirata.Logica.PiezaInventario;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

/**
 * Pantalla de control de inventario de piezas.
 */
public class gestionInventarioPiezas extends javax.swing.JInternalFrame {

    private static final String ROL_ADMIN_INVENTARIO = "admin_inventario";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String UNIDAD_POR_DEFECTO = "Seleccione una unidad";
    private static final int MAX_NOMBRE = 100;
    private static final int MAX_DESCRIPCION = 255;
    private static final int MAX_UBICACION = 100;

    private final InventarioPiezasDAO inventarioDAO = new InventarioPiezasDAO();
    private final String rolUsuarioSesion;
    private final DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JMenuItem opcionVerTabla;
    private JMenuItem opcionEditarTabla;
    private JMenuItem opcionEliminarTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;
    private int idEditando = -1;

    /**
     * Inicializa la ventana y configura su estilo, listeners y datos.
     */
    public gestionInventarioPiezas() {
        this(null);
    }

    /**
     * Inicializa la ventana y valida que solo el administrador de inventario
     * pueda utilizarla.
     *
     * @param rolUsuarioSesion rol activo de la sesión que intenta abrir la
     * funcionalidad
     */
    public gestionInventarioPiezas(String rolUsuarioSesion) {
        this.rolUsuarioSesion = rolUsuarioSesion;
        initComponents();
        setLocation(380, 140);
        setTitle("Control de inventario de piezas");

        if (!validarAccesoPorRol()) {
            return;
        }

        configurarCierreVentana();
        configurarCombos();
        configurarControlesFormulario();
        aplicarEstiloVisual();
        configurarTabla();
        configurarFiltrosTabla();
        configurarInteraccionesTabla();
        limpiarFormulario();
        cargarTabla();
    }

    /**
     * Restringe el acceso de la pantalla al rol definido para inventario.
     *
     * @return true si la sesión puede continuar usando la pantalla
     */
    private boolean validarAccesoPorRol() {
        if (ROL_ADMIN_INVENTARIO.equals(rolUsuarioSesion)) {
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "Solo el administrador de inventario puede acceder a esta funcionalidad.",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE);
        java.awt.EventQueue.invokeLater(this::dispose);
        return false;
    }

    /**
     * Ajusta el comportamiento base de los controles del formulario y activa
     * el filtrado en tiempo real de la tabla.
     */
    private void configurarControlesFormulario() {
        descripcion_ta.setLineWrap(true);
        descripcion_ta.setWrapStyleWord(true);
        descripcion_ta.setRows(4);

        configurarSpinner(cantidad_spn);
        configurarSpinner(stockMinimo_spn);

        id_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fecha_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        unidad_cb.setMaximumRowCount(5);
        registrosVisibles_lb.setText("Registros visibles: 0");

        buscar_txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltrosTabla();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltrosTabla();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltrosTabla();
            }
        });
    }

    /**
     * Aplica un modelo numérico uniforme a los spinners de cantidad y stock.
     */
    private void configurarSpinner(javax.swing.JSpinner spinner) {
        spinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(9999999), Integer.valueOf(1)));
        spinner.setEditor(new javax.swing.JSpinner.NumberEditor(spinner, "#"));
    }

    /**
     * Define ajustes visuales que no dependen del contenido cargado.
     */
    private void aplicarEstiloVisual() {
        id_txt.setEditable(false);
        fecha_txt.setEditable(false);
        tablaInventario.setRowHeight(22);
        tablaInventario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaInventario.getTableHeader().setReorderingAllowed(false);
        tablaInventario.setFillsViewportHeight(true);
        descripcion_ta.setFont(nombre_txt.getFont());
        estadoFormulario_lb.setFont(estadoFormulario_lb.getFont().deriveFont(java.awt.Font.BOLD));
        registrosVisibles_lb.setFont(registrosVisibles_lb.getFont().deriveFont(java.awt.Font.BOLD));
    }

    /**
     * Inicializa el combo de unidades con las opciones válidas para stock.
     */
    private void configurarCombos() {
        unidad_cb.removeAllItems();
        unidad_cb.addItem(UNIDAD_POR_DEFECTO);
        unidad_cb.addItem("Unidad");
        unidad_cb.addItem("Caja");
        unidad_cb.addItem("Kit");
        unidad_cb.addItem("Pack");
    }

    /**
     * Intercepta el cierre del internal frame para mostrar la confirmación de
     * sesión en lugar de cerrar de forma inmediata.
     */
    private void configurarCierreVentana() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent evt) {
                manejarCierreVentana();
            }
        });
    }

    /**
     * Configura el modelo de columnas y el ordenamiento principal de la tabla.
     */
    private void configurarTabla() {
        modeloTabla.setColumnIdentifiers(new Object[]{
            "ID", "Pieza", "Descripción", "Cantidad", "Unidad", "Stock mínimo", "Ubicación", "Fecha registro", "Estado"
        });
        tablaInventario.setModel(modeloTabla);
        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaInventario.setRowSorter(sorterTabla);
        if (tablaInventario.getColumnModel().getColumnCount() == 9) {
            tablaInventario.getColumnModel().getColumn(0).setPreferredWidth(45);
            tablaInventario.getColumnModel().getColumn(1).setPreferredWidth(140);
            tablaInventario.getColumnModel().getColumn(2).setPreferredWidth(220);
            tablaInventario.getColumnModel().getColumn(3).setPreferredWidth(65);
            tablaInventario.getColumnModel().getColumn(4).setPreferredWidth(65);
            tablaInventario.getColumnModel().getColumn(5).setPreferredWidth(85);
            tablaInventario.getColumnModel().getColumn(6).setPreferredWidth(120);
            tablaInventario.getColumnModel().getColumn(7).setPreferredWidth(95);
            tablaInventario.getColumnModel().getColumn(8).setPreferredWidth(90);
        }
    }

    /**
     * Carga las opciones del filtro por estado y aplica el estado inicial.
     */
    private void configurarFiltrosTabla() {
        filtroEstado_cb.removeAllItems();
        filtroEstado_cb.addItem("Todos");
        filtroEstado_cb.addItem("Stock bajo");
        filtroEstado_cb.addItem("Disponible");
        aplicarFiltrosTabla();
    }

    /**
     * Combina el filtro por texto con el filtro por estado y actualiza el
     * contador de filas visibles.
     */
    private void aplicarFiltrosTabla() {
        if (sorterTabla == null) {
            return;
        }

        List<RowFilter<Object, Object>> filtros = new ArrayList<>();
        String textoBusqueda = buscar_txt.getText().trim();
        if (!textoBusqueda.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda), 1, 2, 4, 6, 8));
        }

        Object estadoSeleccionado = filtroEstado_cb.getSelectedItem();
        String estado = estadoSeleccionado != null ? estadoSeleccionado.toString() : "Todos";
        if ("Stock bajo".equals(estado)) {
            filtros.add(RowFilter.regexFilter("^Stock bajo$", 8));
        } else if ("Disponible".equals(estado)) {
            filtros.add(RowFilter.regexFilter("^Disponible$", 8));
        }

        if (filtros.isEmpty()) {
            sorterTabla.setRowFilter(null);
        } else {
            sorterTabla.setRowFilter(RowFilter.andFilter(filtros));
        }

        actualizarRegistrosVisibles();
    }

    /**
     * Refleja en pantalla cuántos registros siguen visibles tras el filtrado.
     */
    private void actualizarRegistrosVisibles() {
        registrosVisibles_lb.setText("Registros visibles: " + tablaInventario.getRowCount());
        actualizarAccionesTablaSegunDisponibilidad();
    }

    /**
     * Habilita o bloquea las acciones contextuales de la tabla según si hay
     * filas visibles disponibles para operar.
     */
    private void actualizarAccionesTablaSegunDisponibilidad() {
        boolean hayRegistrosVisibles = tablaInventario.getRowCount() > 0;

        if (opcionVerTabla != null) {
            opcionVerTabla.setEnabled(hayRegistrosVisibles);
        }
        if (opcionEditarTabla != null) {
            opcionEditarTabla.setEnabled(hayRegistrosVisibles);
        }
        if (opcionEliminarTabla != null) {
            opcionEliminarTabla.setEnabled(hayRegistrosVisibles);
        }
    }

    /**
     * Agrega menú contextual y doble clic sobre la tabla para reutilizar las
     * acciones principales del formulario.
     */
    private void configurarInteraccionesTabla() {
        JPopupMenu menuTabla = new JPopupMenu();

        opcionVerTabla = new JMenuItem("Ver detalle");
        opcionVerTabla.addActionListener(e -> mostrarDetallePieza());
        menuTabla.add(opcionVerTabla);

        opcionEditarTabla = new JMenuItem("Editar");
        opcionEditarTabla.addActionListener(e -> cargarSeleccionEnFormulario());
        menuTabla.add(opcionEditarTabla);

        opcionEliminarTabla = new JMenuItem("Eliminar");
        opcionEliminarTabla.addActionListener(e -> eliminarPiezaSeleccionada());
        menuTabla.add(opcionEliminarTabla);

        tablaInventario.setComponentPopupMenu(menuTabla);
        actualizarAccionesTablaSegunDisponibilidad();
        tablaInventario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                seleccionarFilaDesdeEvento(evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                seleccionarFilaDesdeEvento(evt);
            }
        });
    }

    /**
     * Selecciona la fila asociada al evento del mouse para que popup y doble
     * clic operen siempre sobre la misma pieza.
     */
    private void seleccionarFilaDesdeEvento(MouseEvent evt) {
        int fila = tablaInventario.rowAtPoint(evt.getPoint());
        if (fila >= 0) {
            tablaInventario.setRowSelectionInterval(fila, fila);
        }
        if (evt.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evt) && fila >= 0) {
            cargarSeleccionEnFormulario();
        }
    }

    /**
     * Recupera el inventario persistido, refresca la tabla y actualiza el
     * resumen general de stock.
     */
    private void cargarTabla() {
        try {
            List<PiezaInventario> piezas = inventarioDAO.listarPiezas();
            mostrarPiezasEnTabla(piezas);
            aplicarFiltrosTabla();
            actualizarResumen();
        } catch (RuntimeException ex) {
            modeloTabla.setRowCount(0);
            resumenTotal_lb.setText("0");
            resumenStockBajo_lb.setText("0");
            resumenUltimaActualizacion_lb.setText("Sin movimientos");
            alertaStock_lb.setText("No fue posible cargar el inventario.");
            actualizarRegistrosVisibles();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al cargar inventario", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Traduce la lista de entidades a filas visibles de la tabla principal.
     */
    private void mostrarPiezasEnTabla(List<PiezaInventario> piezas) {
        modeloTabla.setRowCount(0);
        for (PiezaInventario pieza : piezas) {
            modeloTabla.addRow(new Object[]{
                pieza.getId(),
                pieza.getNombre(),
                descripcionVisible(pieza.getDescripcion()),
                pieza.getCantidad(),
                pieza.getUnidad(),
                pieza.getStockMinimo(),
                pieza.getUbicacion(),
                formatearFecha(pieza.getFechaRegistro()),
                pieza.isStockBajo() ? "Stock bajo" : "Disponible"
            });
        }
    }

    /**
     * Calcula los indicadores rápidos mostrados sobre la tabla.
     */
    private void actualizarResumen() {
        int totalPiezas = inventarioDAO.contarPiezasRegistradas();
        int stockBajo = inventarioDAO.contarPiezasConStockBajo();

        resumenTotal_lb.setText(String.valueOf(totalPiezas));
        resumenStockBajo_lb.setText(String.valueOf(stockBajo));
        resumenUltimaActualizacion_lb.setText(inventarioDAO.obtenerUltimaActualizacionVisible());

        if (totalPiezas == 0) {
            alertaStock_lb.setText("No hay piezas registradas en el inventario.");
        } else if (stockBajo > 0) {
            alertaStock_lb.setText("Atención: hay piezas bajo el stock mínimo.");
        } else {
            alertaStock_lb.setText("Inventario dentro de los rangos esperados.");
        }
    }

    /**
     * Restablece el formulario al modo alta y prepara el próximo ID sugerido.
     */
    private void limpiarFormulario() {
        idEditando = -1;
        id_txt.setText(String.valueOf(inventarioDAO.obtenerProximoIdDisponible()));
        nombre_txt.setText("");
        descripcion_ta.setText("");
        cantidad_spn.setValue(Integer.valueOf(0));
        stockMinimo_spn.setValue(Integer.valueOf(0));
        ubicacion_txt.setText("");
        fecha_txt.setText(FORMATO_FECHA.format(LocalDate.now()));
        if (unidad_cb.getItemCount() > 0) {
            unidad_cb.setSelectedIndex(0);
        }
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tablaInventario.clearSelection();
        actualizarEstadoFormularioRegistro();
        nombre_txt.requestFocusInWindow();
    }

    /**
     * Muestra el estado visual correspondiente al modo de registro.
     */
    private void actualizarEstadoFormularioRegistro() {
        estadoFormulario_lb.setText("Modo actual: Registro de nueva pieza");
    }

    /**
     * Muestra el estado visual correspondiente al modo de edición.
     *
     * @param pieza pieza cargada actualmente en el formulario
     */
    private void actualizarEstadoFormularioEdicion(PiezaInventario pieza) {
        estadoFormulario_lb.setText("Modo actual: Edición de pieza #" + pieza.getId() + " - " + pieza.getNombre());
    }

    /**
     * Elimina la pieza seleccionada, ya sea desde el formulario o desde la
     * fila activa de la tabla.
     */
    private void eliminarPiezaSeleccionada() {
        int idAEliminar = idEditando;
        if (idAEliminar == -1) {
            int fila = tablaInventario.getSelectedRow();
            if (fila >= 0) {
                idAEliminar = Integer.parseInt(String.valueOf(tablaInventario.getValueAt(fila, 0)));
            }
        }

        if (idAEliminar == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una pieza para eliminarla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar la pieza seleccionada del inventario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                if (inventarioDAO.eliminarPieza(idAEliminar)) {
                    JOptionPane.showMessageDialog(this,
                            "La pieza fue eliminada correctamente.",
                            "Inventario actualizado",
                            JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                    limpiarFormulario();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lleva al formulario la pieza seleccionada para permitir su edición.
     */
    private void cargarSeleccionEnFormulario() {
        int fila = tablaInventario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una pieza en la tabla para cargar sus datos.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(tablaInventario.getValueAt(fila, 0)));
        PiezaInventario pieza = inventarioDAO.buscarPorId(id);
        if (pieza == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo recuperar la pieza seleccionada.",
                    "Registro no disponible",
                    JOptionPane.ERROR_MESSAGE);
            cargarTabla();
            return;
        }

        idEditando = pieza.getId();
        id_txt.setText(String.valueOf(pieza.getId()));
        nombre_txt.setText(valorSeguro(pieza.getNombre()));
        descripcion_ta.setText(valorSeguro(pieza.getDescripcion()));
        cantidad_spn.setValue(Integer.valueOf(pieza.getCantidad()));
        unidad_cb.setSelectedItem(valorSeguro(pieza.getUnidad()));
        stockMinimo_spn.setValue(Integer.valueOf(pieza.getStockMinimo()));
        ubicacion_txt.setText(valorSeguro(pieza.getUbicacion()));
        fecha_txt.setText(formatearFecha(pieza.getFechaRegistro()));
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        actualizarEstadoFormularioEdicion(pieza);
        nombre_txt.requestFocusInWindow();
    }

    /**
     * Muestra un resumen legible de la pieza seleccionada sin cambiar el modo
     * actual del formulario.
     */
    private void mostrarDetallePieza() {
        int fila = tablaInventario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una pieza para ver el detalle.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(tablaInventario.getValueAt(fila, 0)));
        PiezaInventario pieza = inventarioDAO.buscarPorId(id);
        if (pieza == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo recuperar la pieza seleccionada.",
                    "Registro no disponible",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder detalle = new StringBuilder();
        detalle.append("ID: ").append(tablaInventario.getValueAt(fila, 0)).append("\n");
        detalle.append("Nombre: ").append(tablaInventario.getValueAt(fila, 1)).append("\n");
        detalle.append("Descripción: ").append(descripcionVisible(pieza.getDescripcion())).append("\n");
        detalle.append("Cantidad: ").append(tablaInventario.getValueAt(fila, 3)).append("\n");
        detalle.append("Unidad: ").append(tablaInventario.getValueAt(fila, 4)).append("\n");
        detalle.append("Stock mínimo: ").append(tablaInventario.getValueAt(fila, 5)).append("\n");
        detalle.append("Ubicación: ").append(tablaInventario.getValueAt(fila, 6)).append("\n");
        detalle.append("Fecha registro: ").append(tablaInventario.getValueAt(fila, 7)).append("\n");
        detalle.append("Estado: ").append(tablaInventario.getValueAt(fila, 8));

        JOptionPane.showMessageDialog(this,
                detalle.toString(),
                "Detalle de pieza",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Construye una entidad a partir del formulario después de validar campos
     * obligatorios, largos máximos y consistencia de stock.
     *
     * @param preservarFechaActual indica si se debe respetar la fecha mostrada
     * en pantalla, útil al editar un registro existente
     * @return pieza lista para persistir o null si alguna validación falla
     */
    private PiezaInventario construirPiezaDesdeFormulario(boolean preservarFechaActual) {
        String nombre = nombre_txt.getText().trim();
        String descripcion = descripcion_ta.getText().trim();
        String ubicacion = ubicacion_txt.getText().trim();
        Object unidadSeleccionada = unidad_cb.getSelectedItem();
        String unidad = unidadSeleccionada != null ? unidadSeleccionada.toString().trim() : "";

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el nombre de la pieza.",
                    "Dato obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            nombre_txt.requestFocusInWindow();
            return null;
        }

        if (!validarLargoCampo(nombre, MAX_NOMBRE, "nombre de la pieza", nombre_txt)) {
            return null;
        }

        if (!descripcion.isEmpty() && !validarLargoCampo(descripcion, MAX_DESCRIPCION, "descripción", descripcion_ta)) {
            return null;
        }

        if (unidad.isEmpty() || UNIDAD_POR_DEFECTO.equals(unidad)) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar la unidad de control del stock.",
                    "Dato obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            unidad_cb.requestFocusInWindow();
            return null;
        }

        if (ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe indicar la ubicación física de la pieza.",
                    "Dato obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            ubicacion_txt.requestFocusInWindow();
            return null;
        }

        if (!validarLargoCampo(ubicacion, MAX_UBICACION, "ubicación", ubicacion_txt)) {
            return null;
        }

        int cantidad = ((Number) cantidad_spn.getValue()).intValue();
        int stockMinimo = ((Number) stockMinimo_spn.getValue()).intValue();

        if (cantidad < 0 || stockMinimo < 0) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad y el stock mínimo no pueden ser negativos.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (!confirmarStockBajo(cantidad, stockMinimo)) {
            return null;
        }

        PiezaInventario pieza = new PiezaInventario();
        pieza.setNombre(nombre);
        pieza.setDescripcion(descripcion);
        pieza.setCantidad(cantidad);
        pieza.setUnidad(unidad);
        pieza.setStockMinimo(stockMinimo);
        pieza.setUbicacion(ubicacion);
        pieza.setFechaRegistro(preservarFechaActual ? convertirFecha(fecha_txt.getText().trim()) : Date.valueOf(LocalDate.now()));
        return pieza;
    }

    /**
     * Valida que un campo de texto no exceda el máximo permitido y resalta el
     * control cuando la validación falla.
     */
    private boolean validarLargoCampo(String valor, int maximo, String nombreCampo, java.awt.Component componente) {
        if (valor.length() <= maximo) {
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "El campo " + nombreCampo + " permite un máximo de " + maximo + " caracteres.",
                "Dato demasiado largo",
                JOptionPane.WARNING_MESSAGE);

        if (componente instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) componente;
            textComponent.requestFocusInWindow();
            textComponent.selectAll();
        }
        return false;
    }

    /**
     * Solicita confirmación si la pieza quedará inmediatamente bajo el stock
     * mínimo definido.
     */
    private boolean confirmarStockBajo(int cantidad, int stockMinimo) {
        if (stockMinimo > 0 && cantidad <= stockMinimo) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "La pieza quedará registrada con stock bajo. ¿Desea continuar?",
                    "Confirmar stock bajo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            return opcion == JOptionPane.YES_OPTION;
        }
        return true;
    }

    /**
     * Centraliza la decisión al cerrar la ventana para distinguir entre cerrar
     * solo la vista y cerrar la sesión completa.
     */
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
            cerrarSesion();
        } else if (opcion == 1) {
            dispose();
        }
    }

    /**
     * Regresa al login reutilizando la ventana principal si existe una abierta.
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

    /**
     * Convierte el texto mostrado en el campo fecha al tipo SQL Date y usa la
     * fecha actual si el valor no puede parsearse.
     */
    private Date convertirFecha(String fechaTexto) {
        try {
            return Date.valueOf(LocalDate.parse(fechaTexto, FORMATO_FECHA));
        } catch (DateTimeParseException ex) {
            return Date.valueOf(LocalDate.now());
        }
    }

    /**
     * Formatea la fecha para la presentación uniforme en tabla y formulario.
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "Sin fecha";
        }
        return fecha.toLocalDate().format(FORMATO_FECHA);
    }

    /**
     * Evita nulls visibles cuando se cargan datos de una pieza existente.
     */
    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }

    /**
     * Reemplaza descripciones vacías por un texto amigable para la interfaz.
     */
    private String descripcionVisible(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            return "Sin descripción registrada";
        }
        return descripcion;
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
        estadoFormulario_lb = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        id_txt = new javax.swing.JTextField();
        nombre_txt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcion_ta = new javax.swing.JTextArea();
        cantidad_spn = new javax.swing.JSpinner();
        unidad_cb = new javax.swing.JComboBox<>();
        stockMinimo_spn = new javax.swing.JSpinner();
        ubicacion_txt = new javax.swing.JTextField();
        fecha_txt = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        resumenTotal_lb = new javax.swing.JLabel();
        resumenStockBajo_lb = new javax.swing.JLabel();
        resumenUltimaActualizacion_lb = new javax.swing.JLabel();
        alertaStock_lb = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        buscar_txt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        filtroEstado_cb = new javax.swing.JComboBox<>();
        btnLimpiarFiltros = new javax.swing.JButton();
        registrosVisibles_lb = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la pieza"));

    estadoFormulario_lb.setText("Modo actual: Registro de nueva pieza");

        jLabel1.setText("ID");

        jLabel2.setText("Nombre");

        jLabel3.setText("Descripción");

        jLabel4.setText("Cantidad");

        jLabel5.setText("Unidad");

        jLabel6.setText("Stock mínimo");

        jLabel7.setText("Ubicación / bodega");

        jLabel12.setText("Fecha registro");

    descripcion_ta.setColumns(20);
    descripcion_ta.setRows(5);
    jScrollPane2.setViewportView(descripcion_ta);

    cantidad_spn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        unidad_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad", "Caja", "Kit", "Pack" }));

    stockMinimo_spn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(estadoFormulario_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_txt)
                            .addComponent(nombre_txt)
                            .addComponent(jScrollPane2)
                            .addComponent(cantidad_spn)
                            .addComponent(unidad_cb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stockMinimo_spn)
                            .addComponent(ubicacion_txt)
                            .addComponent(fecha_txt)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estadoFormulario_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombre_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cantidad_spn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(unidad_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(stockMinimo_spn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ubicacion_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(fecha_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen de stock"));

        jLabel8.setText("Piezas registradas");

        jLabel9.setText("Con stock bajo");

        jLabel10.setText("Última actualización");

        jLabel11.setText("Estado general");

        resumenTotal_lb.setText("0");

        resumenStockBajo_lb.setText("0");

        resumenUltimaActualizacion_lb.setText("Sin movimientos");

        alertaStock_lb.setText("Inventario dentro de los rangos esperados.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resumenTotal_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resumenStockBajo_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resumenUltimaActualizacion_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                            .addComponent(alertaStock_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(resumenTotal_lb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(resumenStockBajo_lb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(resumenUltimaActualizacion_lb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(alertaStock_lb))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda y filtros"));

        jLabel13.setText("Buscar");

        jLabel14.setText("Estado");

        filtroEstado_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Stock bajo", "Disponible" }));
        filtroEstado_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroEstado_cbActionPerformed(evt);
            }
        });

        btnLimpiarFiltros.setText("Limpiar filtros");
        btnLimpiarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFiltrosActionPerformed(evt);
            }
        });

        registrosVisibles_lb.setText("Registros visibles: 0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filtroEstado_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimpiarFiltros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registrosVisibles_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(filtroEstado_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarFiltros)
                    .addComponent(registrosVisibles_lb))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaInventario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unused")
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            PiezaInventario pieza = construirPiezaDesdeFormulario(false);
            if (pieza == null) {
                return;
            }

            if (inventarioDAO.existePiezaPorNombre(pieza.getNombre())) {
                JOptionPane.showMessageDialog(this,
                        "La pieza ya existe en el inventario. Verifique el nombre antes de guardar.",
                        "Pieza duplicada",
                        JOptionPane.WARNING_MESSAGE);
                nombre_txt.requestFocusInWindow();
                nombre_txt.selectAll();
                return;
            }

            int idAsignado = inventarioDAO.obtenerProximoIdDisponible();
            int guardado = inventarioDAO.guardarPieza(pieza, idAsignado);
            if (guardado > 0) {
                JOptionPane.showMessageDialog(this,
                        "La pieza fue registrada correctamente.",
                        "Inventario actualizado",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
                limpiarFormulario();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    @SuppressWarnings("unused")
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (idEditando == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una pieza de la tabla para actualizarla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PiezaInventario pieza = construirPiezaDesdeFormulario(true);
            if (pieza == null) {
                return;
            }

            if (inventarioDAO.existePiezaPorNombreExcluyendoId(pieza.getNombre(), idEditando)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe otra pieza registrada con ese nombre.",
                        "Pieza duplicada",
                        JOptionPane.WARNING_MESSAGE);
                nombre_txt.requestFocusInWindow();
                nombre_txt.selectAll();
                return;
            }

            pieza.setId(idEditando);
            if (inventarioDAO.actualizarPieza(pieza)) {
                JOptionPane.showMessageDialog(this,
                        "La pieza fue actualizada correctamente.",
                        "Inventario actualizado",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
                limpiarFormulario();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al actualizar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    @SuppressWarnings("unused")
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarPiezaSeleccionada();
    }//GEN-LAST:event_btnEliminarActionPerformed

    @SuppressWarnings("unused")
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    @SuppressWarnings("unused")
    private void filtroEstado_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroEstado_cbActionPerformed
        aplicarFiltrosTabla();
    }//GEN-LAST:event_filtroEstado_cbActionPerformed

    @SuppressWarnings("unused")
    private void btnLimpiarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFiltrosActionPerformed
        buscar_txt.setText("");
        filtroEstado_cb.setSelectedIndex(0);
        aplicarFiltrosTabla();
        buscar_txt.requestFocusInWindow();
    }//GEN-LAST:event_btnLimpiarFiltrosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alertaStock_lb;
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiarFiltros;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JSpinner cantidad_spn;
    private javax.swing.JTextArea descripcion_ta;
    private javax.swing.JLabel estadoFormulario_lb;
    private javax.swing.JTextField fecha_txt;
    private javax.swing.JComboBox<String> filtroEstado_cb;
    private javax.swing.JTextField id_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nombre_txt;
    private javax.swing.JLabel registrosVisibles_lb;
    private javax.swing.JLabel resumenStockBajo_lb;
    private javax.swing.JLabel resumenTotal_lb;
    private javax.swing.JLabel resumenUltimaActualizacion_lb;
    private javax.swing.JSpinner stockMinimo_spn;
    private javax.swing.JTable tablaInventario;
    private javax.swing.JComboBox<String> unidad_cb;
    private javax.swing.JTextField ubicacion_txt;
    // End of variables declaration//GEN-END:variables
}