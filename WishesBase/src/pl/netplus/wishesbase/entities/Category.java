package pl.netplus.wishesbase.entities;

public class Category extends ModelBase {

	private String name;
	
	public Category(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
