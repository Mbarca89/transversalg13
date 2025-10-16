/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author Manuel Zuñiga
 */
import modelo.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MateriaData {

    private final Connection conectado;

    public MateriaData(DbConnection conexion) {
        this.conectado = (Connection) conexion.establishConnection();
    }

    public boolean guardarMateria(Materia m) {
        String sql = "INSERT INTO materia (nombre, anio, estado) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conectado.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setIdMateria(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizarMateria(Materia m) {
        String sql = "UPDATE materia SET nombre = ?, anio = ?, estado = ? WHERE idMateria = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());
            ps.setInt(4, m.getIdMateria());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean bajaLogicaMateria(int idMateria) {
        String sql = "UPDATE materia SET estado = ? WHERE idMateria = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, idMateria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean altaLogicaMateria(int idMateria) {
        String sql = "UPDATE materia SET estado = ? WHERE idMateria = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setInt(2, idMateria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarMateriaFisico(int idMateria) {
        String sql = "DELETE FROM materia WHERE idMateria = ?";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Materia obtenerMateriaPorId(int idMateria) {
        String sql = "SELECT idMateria, nombre, anio, estado FROM materia WHERE idMateria = ? LIMIT 1";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Materia> obtenerMateriasActivas() {
        String sql = "SELECT idMateria, nombre, anio, estado FROM materia WHERE estado = 1 ORDER BY anio, nombre";
        List<Materia> out = new ArrayList<>();
        try (PreparedStatement ps = conectado.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public List<Materia> buscarMaterias(String nombre, Integer anio, Integer estado) {
        StringBuilder sql = new StringBuilder(
                "SELECT idMateria, nombre, anio, estado FROM materia WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND LOWER(nombre) LIKE ?");
            params.add("%" + nombre.trim().toLowerCase() + "%");
        }
        if (anio != null) {
            sql.append(" AND anio = ?");
            params.add(anio);
        }
        if (estado != null) {
            sql.append(" AND estado = ?");
            params.add(estado == 1);
        }
        sql.append(" ORDER BY anio, nombre");

        List<Materia> out = new ArrayList<>();
        try (PreparedStatement ps = conectado.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) {
                    ps.setString(i + 1, (String) p);
                } else if (p instanceof Integer) {
                    ps.setInt(i + 1, (Integer) p);
                } else if (p instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) p);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    // seria donde el caso donde el profesor quiera mostrar todas las materias (activas e inactivas)//
    public List<Materia> listarTodasMaterias() {
        String sql = "SELECT idMateria, nombre, anio, estado FROM materia ORDER BY anio, nombre";
        List<Materia> lista = new ArrayList<>();
        try (PreparedStatement ps = conectado.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    private Materia mapRow(ResultSet rs) throws SQLException {
        Materia m = new Materia();
        m.setIdMateria(rs.getInt("idMateria"));
        m.setNombre(rs.getString("nombre"));
        m.setAnio(rs.getInt("anio"));
        m.setEstado(rs.getBoolean("estado"));
        return m;
    }

    public boolean existeNombreAnio(String nombre, int anio) {
        String sql = "SELECT 1 FROM materia WHERE LOWER(nombre)=LOWER(?) AND anio=? LIMIT 1";
        try (PreparedStatement ps = conectado.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, anio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Materia buscarMateria(int idMateria) {
        Materia materia = null;
        String sql = "SELECT * FROM materia WHERE idMateria = ?";

        try {
            PreparedStatement ps = conectado.prepareStatement(sql);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(rs.getBoolean("estado"));
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la materia: " + ex.getMessage());
        }

        return materia;
    }
}
