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
    private DefaultTableModel modelo;

    public VistaInscripcion(AlumnoData alumnoData, MateriaData materiaData, InscripcionData inscripcionData) {
        this.alumnoData = alumnoData;
        this.materiaData = materiaData;
        this.inscripcionData = inscripcionData;
        initComponents();
        modelo = (DefaultTableModel) tablaMaterias.getModel();
        tablaMaterias.setModel(modelo);
        modelo.setRowCount(0);
        cargarComboAlumnos();

        rbInscriptas.setSelected(true);
        cargarMateriasInscriptas();

        cbAlumnos.addActionListener(e -> {
            limpiarTabla();
            rbInscriptas.setSelected(true);
            cargarMateriasInscriptas();
        });
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupMaterias = new javax.swing.ButtonGroup();
        lblTitulo = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        cbAlumnos = new javax.swing.JComboBox();
        rbInscriptas = new javax.swing.JRadioButton();
        rbNoInscriptas = new javax.swing.JRadioButton();
        btnInscribir = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        scrLista = new javax.swing.JScrollPane();
        tablaMaterias = new javax.swing.JTable();

        setTitle("Gestión de Inscripciones");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTitulo.setText("GESTIÓN DE INSCRIPCIONES");

        lblBuscar.setText("Seleccionar alumno:");

        buttonGroupMaterias.add(rbInscriptas);
        rbInscriptas.setText("Materias Inscriptas");
        rbInscriptas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInscriptasActionPerformed(evt);
            }
        });

        buttonGroupMaterias.add(rbNoInscriptas);
        rbNoInscriptas.setText("Materias No Inscriptas");
        rbNoInscriptas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoInscriptasActionPerformed(evt);
            }
        });

        btnInscribir.setText("Inscribir");
        btnInscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInscribirActionPerformed(evt);
            }
        });

        btnAnular.setText("Anular Inscripción");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        tablaMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Año"
            }
        ));
        scrLista.setViewportView(tablaMaterias);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrLista, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInscribir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbInscriptas)
                                .addGap(18, 18, 18)
                                .addComponent(rbNoInscriptas))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(cbAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBuscar)
                    .addComponent(cbAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbInscriptas)
                    .addComponent(rbNoInscriptas))
                .addGap(18, 18, 18)
                .addComponent(scrLista, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInscribir)
                    .addComponent(btnAnular)
                    .addComponent(btnSalir))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbInscriptasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInscriptasActionPerformed
        // TODO add your handling code here:
        cargarMateriasInscriptas();
    }//GEN-LAST:event_rbInscriptasActionPerformed

    private void rbNoInscriptasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoInscriptasActionPerformed
        // TODO add your handling code here:
        cargarMateriasNoInscriptas();
    }//GEN-LAST:event_rbNoInscriptasActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInscribirActionPerformed
       inscribirMateria();
    }//GEN-LAST:event_btnInscribirActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
       anularInscripcion();
    }//GEN-LAST:event_btnAnularActionPerformed

    private void limpiarTabla() {
        modelo.setRowCount(0);
    }

    private void cargarComboAlumnos() {
        cbAlumnos.removeAllItems();
        for (Alumno a : alumnoData.obtenerTodosLosAlumnos()) {
            cbAlumnos.addItem(a);
        }
    }

    private void cargarMateriasInscriptas() {
        limpiarTabla();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();
        if (alumno != null) {
            List<Materia> lista = inscripcionData.obtenerMateriaCursada(alumno.getIdAlumno());
            for (Materia m : lista) {
                modelo.addRow(new Object[]{m.getIdMateria(), m.getNombre(), m.getAnio()});
            }
        }
    }

    private void cargarMateriasNoInscriptas() {
        limpiarTabla();
        Alumno alumno = (Alumno) cbAlumnos.getSelectedItem();
        if (alumno != null) {
            List<Materia> lista = inscripcionData.obtenerMateriaSinCursar(alumno.getIdAlumno());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnInscribir;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroupMaterias;
    private javax.swing.JComboBox cbAlumnos;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton rbInscriptas;
    private javax.swing.JRadioButton rbNoInscriptas;
    private javax.swing.JScrollPane scrLista;
    private javax.swing.JTable tablaMaterias;
    // End of variables declaration//GEN-END:variables

}
