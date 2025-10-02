
package Alumno;

import java.time.LocalDate;

public class Alumno{
    private int idAlumno;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private boolean estado;

    public Alumno(int idAlumno, String dni, String nombre, String apellido, LocalDate fechaNacimiento, boolean estado) {
        this.idAlumno = idAlumno;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    // Getters //
    
    public int getIdAlumno() {
        return idAlumno;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean isestado() {
        return estado;
    }

    //Setters//
    
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
    
    
    @Override
    public String toString() {
        return idAlumno + " - " + nombre + " " + apellido;
    }
}