
package Alumno;

public class Alumno{
    private int idAlumno;
    private String dni;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private boolean activo;

    public Alumno(int idAlumno, String dni, String nombre, String apellido, String fechaNacimiento, boolean activo) {
        this.idAlumno = idAlumno;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
    }

    // Getters y Setters //

    @Override
    public String toString() {
        return idAlumno + " - " + nombre + " " + apellido;
    }
}