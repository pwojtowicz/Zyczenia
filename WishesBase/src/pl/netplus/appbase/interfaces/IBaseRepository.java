package pl.netplus.appbase.interfaces;

import java.util.ArrayList;

public interface IBaseRepository<T> {
		
	public abstract T read(int id);
	
	public abstract ArrayList<T> readAll();
	

}
