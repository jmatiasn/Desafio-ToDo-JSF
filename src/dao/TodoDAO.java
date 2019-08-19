package dao;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;

import mbean.TodoBean;
import model.Todo;
 
public class TodoDAO {
 
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
 
    public static Connection getConnection(){  
        try{  
            Class.forName("com.mysql.jdbc.Driver");     
            String db_url ="jdbc:mysql://localhost:3306/sys",
                    db_userName = "root",
                    db_password = "root";
            connObj = DriverManager.getConnection(db_url,db_userName,db_password);  
        } catch(Exception sqlException) {  
            sqlException.printStackTrace();
        }  
        return connObj;
    }
 
    public static ArrayList list() {
        ArrayList todoList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from todo_record");    
            while(resultSetObj.next()) {  
                Todo tObj = new Todo(); 
                tObj.setId(resultSetObj.getInt("id"));  
                tObj.setTodo(resultSetObj.getString("todo"));   
                todoList.add(tObj);  
            }   
            System.out.println("Total Records Fetched: " + todoList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return todoList;
    }
 
    public static String createTodo(Todo newTodoObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into todo_record (todo) values (?)");         
            pstmt.setString(1, newTodoObj.getTodo());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "todoList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createTodo.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editTodo(int todoId) {
        Todo editRecord = null;
        System.out.println("editTodoRecordInDB() : Todo Id: " + todoId);
 
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from todo_record where id = "+todoId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new Todo(); 
                editRecord.setId(resultSetObj.getInt("id"));
                editRecord.setTodo(resultSetObj.getString("todo"));
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editTodo.xhtml?faces-redirect=true";
    }
 
    public static String updateTodo(Todo updateTodoObj) {
        try {
            pstmt = getConnection().prepareStatement("update todo_record set todo=? where id = ?");
            pstmt.setString(1,updateTodoObj.getTodo());  
            pstmt.setInt(2,updateTodoObj.getId());  
            pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/todoList.xhtml?faces-redirect=true";
    }
 
    public static String deleteTodo(int todoId){
        System.out.println("deleteTodoRecordInDB() : Todo Id: " + todoId);
        try {
            pstmt = getConnection().prepareStatement("delete from todo_record where id = "+todoId);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/todoList.xhtml?faces-redirect=true";
    }
}