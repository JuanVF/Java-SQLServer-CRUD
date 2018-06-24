package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class CRUD {
    private String driver   = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String database = "jdbc:sqlserver://localhost:1433;databasename=dbConnExample";
    private String user     = "sa";
    private String password = "1234";
    private Connection cn;
    
    public CRUD(){
        try{
            Class.forName(driver);
            cn = DriverManager.getConnection(database,user,password);
            
            System.out.println("Conexion exitosa");
        }catch(Exception e){
            System.out.println("Error CRUD, Conn: " + e.getMessage());
        }
    }
    
    public DefaultTableModel show(){
        try{
            
            String [] titles        = {"Cedula","Nombre","Apellidos","Provincia"};
            String [] registers     = new String [4];
            DefaultTableModel model = new DefaultTableModel(null,titles);
            CallableStatement cs    = cn.prepareCall("{call paSelectFromUsers}");
            ResultSet rs            = cs.executeQuery();
            
            while(rs.next()){
                registers[0] = rs.getString("Cedula");
                registers[1] = rs.getString("Nombre");
                registers[2] = rs.getString("Apellidos");
                registers[3] = rs.getString("Provincia");
                
                model.addRow(registers);
            }
            return model;
        }catch(Exception e){
            System.out.println("Error CRUD, show: " + e.getMessage());
        }
        return null;
    }
    
    public void insert(String [] data){
        try{
            CallableStatement cs    = cn.prepareCall(("{call paInsertIntoUsers(?,?,?,?)}"));
            cs.setString(1,data[0]);
            cs.setString(2,data[1]);
            cs.setString(3,data[2]);
            cs.setString(4,data[3]);
            
            cs.executeQuery();
        }catch(Exception e){
            System.out.println("Error CRUD, insert: " + e.getMessage());
        }
    }
    
    public void delete(String ID){
        try{
            CallableStatement cs    = cn.prepareCall(("{call paDeleteFromUsers(?)}"));
            cs.setString(1,ID);
            
            cs.executeQuery();
        }catch(Exception e){
            System.out.println("Error CRUD, delete: " + e.getMessage());
        }
    }
    
    public void update(String [] data){
        try{
            CallableStatement cs    = cn.prepareCall(("{call paUpdateUsers(?,?,?,?)}"));
            cs.setString(1,data[0]);
            cs.setString(2,data[1]);
            cs.setString(3,data[2]);
            cs.setString(4,data[3]);
            
            cs.executeQuery();
        }catch(Exception e){
            System.out.println("Error CRUD, insert: " + e.getMessage());
        }
    }
}
