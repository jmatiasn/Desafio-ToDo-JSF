package model;

public class Todo {
	
	private int id;  
    private String todo;
    private Boolean done;

	public int getId() {
        return id;  
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getTodo() {
        return todo;
    }
 
    public void setTodo(String todo) {
        this.todo = todo;
    }
    
    public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
}
