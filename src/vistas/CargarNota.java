package vistas;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Alumno;
import modelo.Materia;
import modelo.Inscripcion;
import persistencia.AlumnoData;
import persistencia.InscripcionData;

public class CargarNota extends javax.swing.JInternalFrame {

    private final AlumnoData alumnoData;
    private final InscripcionData inscripcionData;
    private DefaultTableModel modeloTabla;
    private DefaultListModel<Alumno> modeloLista;
    private Alumno alumnoActual = null;

    public CargarNota(AlumnoData alumnoData, InscripcionData inscripcionData) {
        this.alumnoData = alumnoData;
        this.inscripcionData = inscripcionData;
        initComponents();
        modeloLista = new DefaultListModel<>();
        lstLista.setModel(modeloLista);
        configurarTabla();

        lstLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                alumnoActual = lstLista.getSelectedValue();
                cargarMaterias();
            }
        });

    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID Materia", "Nombre", "Año", "Nota"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 3;
            }
        };

        tblMateria.setModel(modeloTabla);
        tblMateria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMateria.getTableHeader().setReorderingAllowed(false);
    }

    private void cargarMaterias() {
        if (alumnoActual == null) {
            return;
        }

        modeloTabla.setRowCount(0);
        List<Inscripcion> inscripciones = inscripcionData.obtenerInscripcionAlumno(alumnoActual.getIdAlumno());
        for (Inscripcion ins : inscripciones) {
            Materia m = ins.getMateria();
            modeloTabla.addRow(new Object[]{
                m.getIdMateria(),
                m.getNombre(),
                m.getAnio(),
                ins.getNota()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        lblDNI = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        scrLista = new javax.swing.JScrollPane();
        lstLista = new javax.swing.JList<>();
        scrTabla = new javax.swing.JScrollPane();
        tblMateria = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Cargar Nota");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        lblDNI.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDNI.setText("DNI:");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        scrLista.setMaximumSize(new java.awt.Dimension(259, 131));
        scrLista.setMinimumSize(new java.awt.Dimension(259, 131));

        lstLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrLista.setViewportView(lstLista);

        scrTabla.setMinimumSize(new java.awt.Dimension(340, 146));

        tblMateria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Materia", "Nota"
            }
        ));
        tblMateria.setMaximumSize(new java.awt.Dimension(150, 64));
        tblMateria.setMinimumSize(new java.awt.Dimension(150, 64));
        scrTabla.setViewportView(tblMateria);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDNI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDNI))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar)
                            .addComponent(scrTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(lblDNI)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(scrLista, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (tblMateria.isEditing()) {
            tblMateria.getCellEditor().stopCellEditing();
        }
        if (alumnoActual == null) {
            JOptionPane.showMessageDialog(this, "Debe buscar un alumno primero.");
            return;
        }
        boolean actualizado = false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int idMateria = (int) modeloTabla.getValueAt(i, 0);
            Object valorNota = modeloTabla.getValueAt(i, 3);

            int nota;
            try {
                nota = Integer.parseInt(valorNota.toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida en fila " + (i + 1));
                return;
            }

            if (nota < 0 || nota > 10) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 10 (fila " + (i + 1) + ")");
                return;
            }
            System.out.println(nota);
            actualizado = inscripcionData.actualizarNota(alumnoActual.getIdAlumno(), idMateria, nota);
        }

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Notas actualizadas correctamente.");
        }
        cargarMaterias();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
        modeloLista.clear();
        String dni = txtDNI.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI.");
            return;
        }

        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DNI debe contener solo números.");
            return;
        }

        List<Alumno> listaAlumnos = alumnoData.buscarAlumnos(dni, "", null);

        if (listaAlumnos.size() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontró ningun alumno");
            modeloTabla.setRowCount(0);
            lblDNI.setText("-");
        } else {
            llenarLista(listaAlumnos);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void llenarLista(List<Alumno> lista) {
        for (Alumno a : lista) {
            modeloLista.addElement(a);
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtDNI.setText("");
        modeloTabla.setRowCount(0);
        modeloLista.clear();
        alumnoActual = null;        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<Alumno> lstLista;
    private javax.swing.JScrollPane scrLista;
    private javax.swing.JScrollPane scrTabla;
    private javax.swing.JTable tblMateria;
    private javax.swing.JTextField txtDNI;
    // End of variables declaration//GEN-END:variables
}
