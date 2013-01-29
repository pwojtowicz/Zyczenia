package pl.netplus.wishesphone.support;

import java.util.ArrayList;

import pl.netplus.appbase.entities.Category;

public class WishesGlobals {

	private static volatile WishesGlobals instance = null;

	private ArrayList<Category> categories;

	public static WishesGlobals getInstance() {
		if (instance == null) {
			synchronized (WishesGlobals.class) {
				if (instance == null) {
					instance = new WishesGlobals();
				}
			}
		}
		return instance;
	}

	private WishesGlobals() {
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

}
