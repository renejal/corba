
/**
 * Utiliza la API JDBC de java para conectarse a una base de datos MySQL
 * -----------------------------------------------------------------
 */
package Datos;
import java.sql.*;

/**
 * Esta clase permite que las Clases tipo Entidad tengan persistencia
 * por medio de una base de datos relacional
 */
public class ConexionBD {
   
   private Connection con;
    
   private String bd;
   private String login;
   private String password;
   private String url;
    
   
    public ConexionBD() {
        con=null;
        bd="bdAnteproyecto";
        login="root";
        password="";
        url = "jdbc:mysql://localhost/"+bd;
    }
    /**Permite hacer la conexion con la base de datos    
     */
    public int conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           //crea una instancia de la controlador de la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdanteproyecto",login,password);
            if(con != null){
                System.out.println("conexion establecida");
            }
            else
            {
                System.out.println("no se pudo realizar la conexion");
            }
            // gnera una conexión con la base de datos
             return 1;
        }
        catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }
    /**Cierra la conexion con la base de datos
     *
     */
   public void desconectar(){
       try{
            con.close();
        }

        catch(Exception e){
            System.out.println("Error " + e.getMessage());
        }
   }
     /**Retorna un objeto que almacena la referencia a la conexion con la base de datos
     *
     */
    public Connection getConnection(){
      return con;
   }
 
   
}
