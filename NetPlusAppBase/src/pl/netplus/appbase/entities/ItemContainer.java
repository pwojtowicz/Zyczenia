package pl.netplus.appbase.entities;

import java.util.ArrayList;

public class ItemContainer extends ModelBase {

	private ArrayList<Category> categories;
	private ArrayList<ContentObject> contents;
	private ArrayList<Favorite> favorites;

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public ArrayList<ContentObject> getContents() {
		return contents;
	}

	public void setContents(ArrayList<ContentObject> contents) {
		this.contents = contents;
	}

	public ArrayList<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(ArrayList<Favorite> favorites) {
		this.favorites = favorites;
	}

}
