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
/**
 *
 * @author tomas
 */
public class AlumnoData {

  private Connection conectado=null;

  
    public AlumnoData(DbConnection conexion) {
        this.conectado= (Connection) conexion.establishConnection();
    }
    
    public void guardarAlumno(Alumno a){
        String sql= "INSERT INTO alumno (dni,nombre,apellido,fechaNacimiento,estado) VALUES(?,?,?,?,?)";
        
        try{
            PreparedStatement ps= conectado.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,a.getDni());
            ps.setString(2,a.getNombre());
            ps.setString(3,a.getApellido());
            ps.setDate(4, Date.valueOf(a.getFechaNacimiento()));
            ps.setBoolean(5,a.getEstado());
            ps.executeUpdate();
            
            ResultSet rs= ps.getGeneratedKeys();
            if(rs.next()){
                a.setIdAlumno(rs.getInt(1));
            }else{
                System.out.println("no se pudo tener el id");  
            }
            ps.close();
                System.out.println("guardado");
            
        }catch (SQLException ex){
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarAlumno(Alumno a){
        String sql= "UPDATE alumno SET dni = ?, nombre = ?, apellido = ?, fechaNacimiento = ?, estado = ? WHERE idAlumno = ?";
        
        try {
        PreparedStatement ps = conectado.prepareStatement(sql);
        ps.setString(1, a.getDni());
        ps.setString(2, a.getNombre());
        ps.setString(3, a.getApellido());
        ps.setDate(4, Date.valueOf(a.getFechaNacimiento()));
        ps.setBoolean(5, a.getEstado());
        ps.setInt(6, a.getIdAlumno());

        int filasActualizadas=ps.executeUpdate();

        if (filasActualizadas > 0) {
            System.out.println("Alumno actualizado con éxito.");
        } else {
            System.out.println("No se encontró el alumno con ID: " + a.getIdAlumno());
        }
            
        }catch (SQLException ex){
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
