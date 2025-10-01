/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mauricio
 */
public class DbConnection {
    private String url;
    private String user;
    private String password;
    
    private Connection connection = null;

    public DbConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public Connection establishConnection () {
        if (connection == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(url,user,password);
                System.out.println("Conetado a la BD correctamente.");
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}
