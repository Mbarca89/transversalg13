package persistencia;

import modelo.Inscripcion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Alumno;
import modelo.Materia;

/**
 *
 * @author ernesto
 */
public class InscripcionData {

    private Connection conectado = null;
    private MateriaData md;
    private AlumnoData ad;

    public InscripcionData(DbConnection conexion) {
        this.conectado = (Connection) conexion.establishConnection();
        md = new MateriaData(conexion);
        ad = new AlumnoData(conexion);
    }

    public boolean guardarInscripcion(Inscripcion insc) {
        String sql = "INSERT INTO inscripcion(idAlumno, idMateria, nota) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conectado.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, insc.getAlumno().getIdAlumno());
            ps.setInt(2, insc.getMateria().getIdMateria());
            ps.setInt(3, insc.getNota());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
            return false;
        }
    }

    public boolean actualizarNota(int idAlumno, int idMateria, int nota) {
        String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? and idMateria = ?";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Nota actualizada para Alumno " + idAlumno + " en Materia " + idMateria);
                return true;
            } else {
                System.out.println("️No se encontró inscripción para ese alumno y materia.");
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
            return false;
        }
    }

    public List<Inscripcion> obtenerInscripciones() {

        ArrayList<Inscripcion> cursada = new ArrayList<>();

        String sql = "SELECT * FROM inscripcion";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Inscripcion Insc = new Inscripcion();
                Insc.setIdInscripcion(rs.getInt("idIncripto"));
                Alumno alum = ad.obtenerAlumnoPorId(rs.getInt("idAlumno"));
                Materia mate = md.obtenerMateriaPorId(rs.getInt("idMateria"));
                Insc.setAlumno(alum);
                Insc.setMateria(mate);
                Insc.setNota(rs.getInt("nota"));
                cursada.add(Insc);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
        }
        return cursada;
    }

    public List<Inscripcion> obtenerInscripcionAlumno(int idAlumno) {
        ArrayList<Inscripcion> cursada = new ArrayList<>();

        String sql = "SELECT * FROM inscripcion WHERE idAlumno= ?";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Inscripcion Insc = new Inscripcion();
                Insc.setIdInscripcion(rs.getInt("idIncripto"));
                Alumno alum = ad.obtenerAlumnoPorId(rs.getInt("idAlumno"));
                Materia mate = md.obtenerMateriaPorId(rs.getInt("idMateria"));
                Insc.setAlumno(alum);
                Insc.setMateria(mate);
                Insc.setNota(rs.getInt("nota"));
                cursada.add(Insc);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
        }
        return cursada;
    }

    public List<Materia> obtenerMateriaCursada(int idAlumno) {
        ArrayList<Materia> materias = new ArrayList<>();
        String sql = "SELECT inscripcion.idMateria, nombre, anio From inscripcion, " + " materia Where inscripcion.idMateria = materia.idMateria " + " And inscripcion.idAlumno = ?";
        try {

            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materias.add(materia);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
        }
        return materias;
    }

    public List<Materia> obtenerMateriaSinCursar(int idAlumno) {
        ArrayList<Materia> materias = new ArrayList<>();

        String sql = "Select * From materia Where estado=1 AND idMateria Not In " + "(Select idMateria From inscripcion Where idAlumno=?)";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materias.add(materia);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la tabla");
        }
        return materias;
    }

    public boolean borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?";
        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la inscripción: " + ex.getMessage());
            return false;
        }
    }
}
