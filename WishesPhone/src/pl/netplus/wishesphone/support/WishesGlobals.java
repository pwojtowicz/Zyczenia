package pl.netplus.wishesphone.support;

import java.util.ArrayList;
import java.util.HashMap;

import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.Favorite;

public class WishesGlobals {

	private static volatile WishesGlobals instance = null;

	private ArrayList<Category> categories;
	private ArrayList<Integer> favorites;
	private HashMap<Integer, ArrayList<ContentObject>> objectsDictionary;

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

		this.categories = new ArrayList<Category>();
		for (Category category : categories) {
			if (category.getCount() > 0)
				this.categories.add(category);
		}
	}

	public void setObjectsDictionary(int categoryId,
			ArrayList<ContentObject> contentObjects) {
		if (this.objectsDictionary == null)
			this.objectsDictionary = new HashMap<Integer, ArrayList<ContentObject>>();

		if (this.objectsDictionary.containsKey(categoryId)) {
			this.objectsDictionary.remove(categoryId);
		}
		this.objectsDictionary.put(categoryId, contentObjects);
	}

	public ArrayList<ContentObject> getCategoriesContentObjects(int categoryId) {
		if (this.objectsDictionary != null) {
			if (this.objectsDictionary.containsKey(categoryId)) {
				ArrayList<ContentObject> tmp = this.objectsDictionary
						.get(categoryId);

				for (ContentObject contentObject : tmp) {
					if (favorites.contains(contentObject.getId()))
						contentObject.setFavorites(true);
				}

				return tmp;
			}

		}
		return null;

	}

	public void setFavorites(ArrayList<Favorite> favorites) {
		this.favorites = new ArrayList<Integer>();
		for (Favorite favorite : favorites) {
			this.favorites.add(favorite.getObjectId());
		}
	}

}
