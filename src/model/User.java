package model;

public class User implements Comparable<User>{
	private String name;
	private String CC;
	private Priority priority;
	
	public User(String name, String CC, Priority priority) {
		super();
		this.name = name;
		this.CC = CC;
		this.priority = priority;
	}
	
	public User(String name, String CC) {
		super();
		this.name = name;
		this.CC = CC;
	}

	public String getName() {
		return name;
	}

	public String getCC() {
		return CC;
	}

	public Priority getPriority() {
		return priority;
	}

	@Override
	public int compareTo(User otherUser) {
		return CC.compareTo(otherUser.CC);
	}
}
