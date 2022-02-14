package modelPackage;

public abstract class Person {
	private String name;
	private int id;
	
	public Person(String name,int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nId: " + id +"\n";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
