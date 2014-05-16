/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aviva.utilesweb.comun;

import com.mysql.jdbc.Connection;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author Santiago
 */
public class Conexion
{

    private final String FICHERO_PROPERTIES="conexion.properties";   
    
    private String url= "";
    private String database = "";
    private String driver = "";
    private String userName = "";
    private String password = "";    
    
    private static Conexion instance = null;
    private java.sql.Connection conexion = null;
    
    private Conexion()
    {
    }

    public boolean crearConexion(){
        
        if(!leerProperties()){
            return false;
        }
        
        try {
            Class.forName(driver).newInstance();
            conexion = (Connection)DriverManager.getConnection(url+database,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
            conexion=null;
            return false;
        }
        return true;
    }
    
    public static Conexion getInstance()
    {
        if (instance == null){
            instance = new Conexion();
        }
        return instance;
    }
    
    private boolean leerProperties(){
        
        Properties propiedades = new Properties();
        
        try{
        
            propiedades.load(new FileInputStream(FICHERO_PROPERTIES));

            url = propiedades.getProperty("url");
            database = propiedades.getProperty("database");
            driver = propiedades.getProperty("driver");
            userName = propiedades.getProperty("username");
            password = propiedades.getProperty("password"); 
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
}