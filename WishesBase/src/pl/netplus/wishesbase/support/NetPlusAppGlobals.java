package pl.netplus.wishesbase.support;

import java.util.ArrayList;
import java.util.HashMap;

import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.Favorite;

public class NetPlusAppGlobals {

	public static final int ITEMS_ALL = -2;
	public static final int ITEMS_FAVORITE = -1;
	public static final int ITEMS_SEARCH = -3;

	private static volatile NetPlusAppGlobals instance = null;

	private ArrayList<Category> categories;
	private ArrayList<Integer> favorites;
	private HashMap<Integer, ArrayList<ContentObject>> objectsDictionary;

	public static NetPlusAppGlobals getInstance() {
		if (instance == null) {
			synchronized (NetPlusAppGlobals.class) {
				if (instance == null) {
					instance = new NetPlusAppGlobals();
				}
			}
		}
		return instance;
	}

	private NetPlusAppGlobals() {
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = new ArrayList<Category>();
		for (Category category : categories) {
			if (category.getCount() > 0)
				this.categories.add(category);
		}
	}

	public ArrayList<Category> getCategories() {
		return categories;
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
				ArrayList<ContentObject> items = this.objectsDictionary
						.get(categoryId);

				// Jeœli znajdujê siê na liœcie ulubionych, to go oznacz
				if (favorites != null) {
					for (ContentObject contentObject : items) {
						if (favorites.contains(contentObject.getId()))
							contentObject.setFavorites(true);
					}
				}

				return items;

			}

		}
		return null;

	}

	public void setFavoritesId(ArrayList<Favorite> favorites) {
		this.favorites = new ArrayList<Integer>();
		for (Favorite favorite : favorites) {
			this.favorites.add(favorite.getObjectId());
		}
	}

	public ArrayList<Integer> getFavoritesId() {
		return this.favorites;
	}

	public int getFavoritesCount() {
		if (this.favorites != null)
			return this.favorites.size();
		return 0;
	}

	public void removedFromFavorites(int objectId) {
		if (this.favorites != null)
			this.favorites.remove(new Integer(objectId));

	}

	public void addToFavorites(int objectId) {
		if (this.favorites != null)
			this.favorites.add(objectId);

	}
}
