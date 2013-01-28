package pl.netplus.wishesbase.entities;

public class ModelBase {
	
	public ModelBase(){
		
	}
	
	public ModelBase(int id){
		this.id=id;
	}
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
