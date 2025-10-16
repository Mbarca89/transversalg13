package vistas;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Alumno;
import modelo.Inscripcion;
import modelo.Materia;
import persistencia.AlumnoData;
import persistencia.InscripcionData;
import persistencia.MateriaData;

public class VistaInscripcion extends JInternalFrame {

    private final AlumnoData alumnoData;
    private final MateriaData materiaData;
    private final InscripcionData inscripcionData;

    private JComboBox<Alumno> cbAlumnos;
    private JTextField txtBuscarAlumno;
    private JRadioButton rbInscriptas;
    private JRadioButton rbNoInscriptas;
    private JTable tablaMaterias;
    private JButton btnInscribir;
    private JButton btnAnular;
    private JButton btnSalir;
    private DefaultTableModel modelo;

    public VistaInscripcion(AlumnoData alumnoData, MateriaData materiaData, InscripcionData inscripcionData) {
        this.alumnoData = alumnoData;
        this.materiaData = materiaData;
        this.inscripcionData = inscripcionData;
        initComponents();
        cargarComboAlumnos();
        armarCabecera();
        setVisible(true);
    }

    private void initComponents() {
        setClosable(true);
        setIconifiable(true);
        setTitle("Gestión de Inscripciones");
        setSize(700, 450);
        setLayout(null);

        JLabel lblTitulo = new JLabel("GESTIÓN DE INSCRIPCIONES");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTitulo.setBounds(230, 10, 300, 25);
        add(lblTitulo);

        JLabel lblBuscar = new JLabel("Buscar alumno:");
        lblBuscar.setBounds(40, 50, 120, 25);
        add(lblBuscar);

        txtBuscarAlumno = new JTextField();
        txtBuscarAlumno.setBounds(140, 50, 200, 25);
        add(txtBuscarAlumno);

        cbAlumnos = new JComboBox<>();
        cbAlumnos.setBounds(360, 50, 280, 25);
        add(cbAlumnos);

        rbInscriptas = new JRadioButton("Materias Inscriptas");
        rbInscriptas.setBounds(150, 90, 160, 25);
        rbNoInscriptas = new JRadioButton("Materias No Inscriptas");
        rbNoInscriptas.setBounds(350, 90, 180, 25);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbInscriptas);
        grupo.add(rbNoInscriptas);

        add(rbInscriptas);
        add(rbNoInscriptas);

        modelo = new DefaultTableModel();
        tablaMaterias = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaMaterias);
        scroll.setBounds(50, 130, 600, 200);
        add(scroll);

        btnInscribir = new JButton("Inscribir");
        btnInscribir.setBounds(80, 360, 120, 30);
        add(btnInscribir);

        btnAnular = new JButton("Anular Inscripción");
        btnAnular.setBounds(270, 360, 160, 30);
        add(btnAnular);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(500, 360, 120, 30);
        add(btnSalir);

        
        txtBuscarAlumno.addActionListener(e -> buscarAlumno());
        rbInscriptas.addActionListener(e -> cargarMateriasInscriptas());
        rbNoInscriptas.addActionListener(e -> cargarMateriasNoInscriptas());
        btnInscribir.addActionListener(e -> inscribirMateria());
        btnAnular.addActionListener(e -> anularInscripcion());
        btnSalir.addActionListener(e -> dispose());
    }

    private void armarCabecera() {
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Año");
    }

    private void limpiarTabla() {
        modelo.setRowCount(0);
    }

    private void cargarComboAlumnos() {
        cbAlumnos.removeAllItems();
        for (Alumno a : alumnoData.listarAlumnos()) {
            cbAlumnos.addItem(a);
        }
    }

    private void buscarAlumno() {
        String texto = txtBuscarAlumno.getText().trim().toLowerCase();
        cbAlumnos.removeAllItems();
        for (Alumno a : alumnoData.listarAlumnos()) {
            if (a.getNombre().toLowerCase().contains(texto) || a.getApellido().toLowerCase().contains(texto)) {
                cbAlumnos.addItem(a);
            }
        }
    }

    private void cargarMateriasInscriptas() {
        limpiarTabla();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();
        if (alumno != null) {
            List<Materia> lista = inscripcionData.obtenerMateriasCursadas(alumno.getIdAlumno());
            for (Materia m : lista) {
                modelo.addRow(new Object[]{m.getIdMateria(), m.getNombre(), m.getAnio()});
            }
        }
    }

    private void cargarMateriasNoInscriptas() {
        limpiarTabla();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();
        if (alumno != null) {
            List<Materia> lista = inscripcionData.obtenerMateriasNoCursadas(alumno.getIdAlumno());
            for (Materia m : lista) {
                modelo.addRow(new Object[]{m.getIdMateria(), m.getNombre(), m.getAnio()});
            }
        }
    }

    private void inscribirMateria() {
        int filaSeleccionada = tablaMaterias.getSelectedRow();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();

        if (alumno == null || filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno y una materia para inscribir.");
            return;
        }

        int idMateria = (Integer) modelo.getValueAt(filaSeleccionada, 0);
        Materia materia = materiaData.buscarMateria(idMateria);

        Inscripcion insc = new Inscripcion(alumno, materia, 0);
        inscripcionData.guardarInscripcion(insc);

        JOptionPane.showMessageDialog(this, "Inscripción realizada correctamente.");
        if (rbNoInscriptas.isSelected()) {
            cargarMateriasNoInscriptas();
        }
    }

    private void anularInscripcion() {
        int filaSeleccionada = tablaMaterias.getSelectedRow();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();

        if (alumno == null || filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno y una materia para anular la inscripción.");
            return;
        }

        int idMateria = (Integer) modelo.getValueAt(filaSeleccionada, 0);
        inscripcionData.borrarInscripcionMateriaAlumno(alumno.getIdAlumno(), idMateria);

        JOptionPane.showMessageDialog(this, "Inscripción anulada correctamente.");
        if (rbInscriptas.isSelected()) {
            cargarMateriasInscriptas();
        }
    }
}




