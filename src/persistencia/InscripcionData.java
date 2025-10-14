package persistencia;

import modelo.Inscripcion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Alumno;
import modelo.Materia;



/**
 *
 * @author ernesto
 */
public class InscripcionData {
    
    private Connection conectado = null;
    private MateriaData md = new MateriaData();
    private AlumnoData ad = new AlumnoData();
    
    public InscripcionData(DbConnection conexion){
        this.conectado = (Connection) conexion.establishConnection();
    }
    
    public void guardarInscripcion(Inscripcion insc){
         String sql="INSERT INTO inscripcion(idAlumno, idMateria, nota) VALUES (?, ?, ?)";
        try { 
            PreparedStatement ps=conectado.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,insc.getAlumno().getIdAlumno());
            ps.setInt(2,insc.getMateria().getIdMateria());
            ps.setDouble(3,insc.getNota());
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
           if(rs.next()){
           insc.setIdInscripcion(rs.getInt(1));
           JOptionPane.showMessageDialog(null,"Inscripto correctamente");
           }
           
           ps.close();
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la tabla");

        }
    
    }
    
    public void actualizarNota(int idAlumno, int idMateria, double nota){
    String sql="UPDATE inscripcion SET nota = ? WHERE idAlumno = ? and idMateria = ?";
    
        try {
            PreparedStatement ps=conectado.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            int result = ps.executeUpdate();
            
            if (result>0){
            JOptionPane.showMessageDialog(null,"Nota actualizada");
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la tabla");
        }
    }
    
    public void borrarInscripcion (int idAlumno, int idMateria){
            
        String sql="DELETE FROM inscripcion WHERE idAlumno=? and idMateria=?";
        
        try {
            PreparedStatement ps=conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            int result=ps.executeUpdate();
            if (result>0){
            JOptionPane.showMessageDialog(null,"Inscripcion borrada");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la tabla");
        }
        
            }
    
    public List<Inscripcion> obtenerInscripcion(){
    
    ArrayList<Inscripcion> cursada= new ArrayList<>();
    
    String sql = "SELECT * FROM inscripcion";
    
        try {
            PreparedStatement ps=conectado.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
            Inscripcion Insc=Inscripcion();
            Insc.setIdInscripcion(rs.getInt("idIncripto"));
            Alumno alum=ad.buscarAlumnos(rs.getInt("idAlumno"));
            Materia mate=md.obtenerMateriaPorId(rs.getInt("idMateria"));
            Insc.SetAlumno (alum);
            Insc.setMateria(mate);
            Insc.setNota(rs.getDouble(nota));
            cursada.add(Insc);
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la taqbla");
        }
        return cursada;
    }
    
    public List<Inscripcion> obtenerInscripcionAlumno(int idAlumno){
    
    ArrayList<Inscripcion> cursada= new ArrayList<>();
    
    String sql = "SELECT * FROM inscripcion WHERE idAlumno= ?";
    
        try {
            PreparedStatement ps=conectado.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
            Inscripcion Insc=Inscripcion();
            Insc.setIdInscripcion(rs.getInt("idIncripto"));
            Alumno alum=ad.buscarAlumnos(rs.getInt("idAlumno"));
            Materia mate=md.obtenerMateriaPorId(rs.getInt("idMateria"));
            Insc.SetAlumno (alum);
            Insc.setMateria(mate);
            Insc.setNota(rs.getDouble(nota));
            cursada.add(Insc);
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la taqbla");
        }
        return cursada;
    }


    public List<Materia> obtenerMateriaCursada(int idAlumno){
    
    ArrayList<Materia> materias=new ArrayList<>();

    String sql = "SELECT inscripcion.idMateria, nombre, anio From inscripcion, "+" materia Where incripcion.idMateria = materia.idMateria "+" And inscripcion.idAlumno = ?";
    
        try {
            PreparedStatement ps=conectado.prepareStatement(sql);
                    ps.setInt(1, idAlumno);
                    ResultSet rs=ps.executeQuery();
                    while (rs.next()){
                    Materia materia=new Materia();
                    materia.setIdMateria(rs.getInt("idMateria"));
                    materia.setNombre(rs.getString("nombre"));
                    materia.setAnio(rs.getInt("anio"));
                    materias.add(materia);
                    }
                    ps.close();
                   
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error en la taqbla");            
        }
        return materias;
    }
}
 