package pl.netplus.appbase.repositories;

import java.util.ArrayList;

import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.interfaces.IBaseRepository;

public class CategoriesRepository implements IBaseRepository<Category> {

	@Override
	public Category read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Category> readAll() {
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Bo¿e narodzenie", 12));
		categories.add(new Category("Walentynki", 43));
		categories.add(new Category("Dzieñ babci", 22));
		return categories;
	}

	@Override
	public boolean insertOrUpdate(Category item) {
		// TODO Auto-generated method stub
		return false;
	}

}
