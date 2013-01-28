package pl.netplus.wishesbase.interfaces;

import java.util.ArrayList;

public interface IBaseRepository<T> {
		
	public abstract T read(int id);
	
	public abstract ArrayList<T> readAll();
	

}
