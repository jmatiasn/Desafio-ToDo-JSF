package mbean;
 
import java.util.ArrayList;
 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dao.TodoDAO;
import model.Todo;
 
@ManagedBean @RequestScoped
public class TodoBean {

    private Todo todo = new Todo();
	public ArrayList todoListFromDB;
	
    public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}
    
    @PostConstruct
    public void init() {
        todoListFromDB = TodoDAO.list();
    }
 
    public ArrayList todoList() {
        return todoListFromDB;
    }
     
    public String saveTodo(Todo newTodoObj) {
        return TodoDAO.createTodo(newTodoObj);
    }
     
    public String editTodoRecord(int todoId) {
        return TodoDAO.editTodo(todoId);
    }
     
    public String updateTodoRecord(Todo updateTodoObj) {
        return TodoDAO.updateTodo(updateTodoObj);
    }
     
    public String deleteTodoRecord(int todoId) {
        return TodoDAO.deleteTodo(todoId);
    }
    
    public String redirectToCreateTodoPage() {
    	todo = new Todo();
    	return "createTodo.xhtml?faces-redirect=true";
    }
}