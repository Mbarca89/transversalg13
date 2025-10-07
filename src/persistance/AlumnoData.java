package persistance;

import Alumno.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class AlumnoData {

    private Connection conectado = null;

    public AlumnoData(DbConnection conexion) {
        this.conectado = (Connection) conexion.establishConnection();
    }

    public boolean guardarAlumno(Alumno a) {
        String sql = "INSERT INTO alumno (dni,nombre,apellido,fechaNacimiento,estado) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = conectado.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setDate(4, java.sql.Date.valueOf(a.getFechaNacimiento()));
            ps.setBoolean(5, a.getEstado());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setIdAlumno(rs.getInt(1));
                }
            }
            return true;

        } catch (SQLException ex) {
            // Opcional: detectar DNI duplicado (clave única)
            if ("23000".equals(ex.getSQLState()) && ex.getErrorCode() == 1062) {
                System.out.println("DNI duplicado.");
            }
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void actualizarAlumno(Alumno a) {
        String sql = "UPDATE alumno SET dni = ?, nombre = ?, apellido = ?, fechaNacimiento = ?, estado = ? WHERE idAlumno = ?";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setDate(4, Date.valueOf(a.getFechaNacimiento()));
            ps.setBoolean(5, a.getEstado());
            ps.setInt(6, a.getIdAlumno());

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Alumno actualizado con éxito.");
            } else {
                System.out.println("No se encontró el alumno con ID: " + a.getIdAlumno());
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean bajaLogicaAlumno(int idAlumno) {
        String sql = "UPDATE alumno SET estado = ? WHERE idAlumno = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setBoolean(1, false); // inactivo
            ps.setInt(2, idAlumno);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Baja lógica realizada para idAlumno=" + idAlumno);
                return true;
            } else {
                System.out.println("No se encontró alumno con idAlumno=" + idAlumno);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean altaLogicaAlumno(int idAlumno) {
        String sql = "UPDATE alumno SET estado = ? WHERE idAlumno = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setBoolean(1, true); // activo
            ps.setInt(2, idAlumno);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alta lógica realizada para idAlumno=" + idAlumno);
                return true;
            } else {
                System.out.println("No se encontró alumno con idAlumno=" + idAlumno);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarAlumnoFisico(int idAlumno) {
        String sql = "DELETE FROM alumno WHERE idAlumno = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno eliminado físicamente: idAlumno=" + idAlumno);
                return true;
            } else {
                System.out.println("No se encontró alumno con idAlumno=" + idAlumno);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Alumno> buscarAlumnos(String dni, String nombre, Integer estado) {
        StringBuilder sql = new StringBuilder(
                "SELECT idAlumno, dni, nombre, apellido, fechaNacimiento, estado FROM alumno WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (dni != null && !dni.isEmpty()) {
            sql.append(" AND dni LIKE ?");
            params.add("%" + dni.trim() + "%");
        }

        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND (LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ?)");
            String like = "%" + nombre.trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
        }

        if (estado != null) {
            sql.append(" AND estado = ?");
            params.add(estado == 1);
        }

        sql.append(" ORDER BY apellido, nombre");

        List<Alumno> lista = new ArrayList<>();
        try (PreparedStatement ps = conectado.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) {
                    ps.setString(i + 1, (String) p);
                } else if (p instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) p);
                } else if (p instanceof Integer) {
                    ps.setInt(i + 1, (Integer) p);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alumno a = new Alumno();
                    a.setIdAlumno(rs.getInt("idAlumno"));
                    a.setDni(rs.getString("dni"));
                    a.setNombre(rs.getString("nombre"));
                    a.setApellido(rs.getString("apellido"));
                    a.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                    a.setEstado(rs.getBoolean("estado"));
                    lista.add(a);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public Alumno obtenerAlumnoPorDni(String dni) {
        String sql = "SELECT idAlumno, dni, nombre, apellido, fechaNacimiento, estado "
                + "FROM alumno WHERE dni = ? LIMIT 1";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Alumno a = new Alumno();
                    a.setIdAlumno(rs.getInt("idAlumno"));
                    a.setDni(rs.getString("dni"));
                    a.setNombre(rs.getString("nombre"));
                    a.setApellido(rs.getString("apellido"));
                    a.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                    a.setEstado(rs.getBoolean("estado"));
                    return a;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
