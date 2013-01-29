package pl.netplus.appbase.repositories;

import java.util.ArrayList;

import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ItemContainer;

public class ReadAllDataRepository {

	public ItemContainer readAll() {

		ItemContainer container = new ItemContainer();

		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Bo¿e narodzenie", 12));
		categories.add(new Category("Walentynki", 43));
		categories.add(new Category("Dzieñ babci", 22));

		container.setCategories(categories);

		return container;
	}
}
