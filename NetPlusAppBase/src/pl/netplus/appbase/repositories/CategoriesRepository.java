package pl.netplus.appbase.repositories;

import java.util.ArrayList;
import java.util.Arrays;

import pl.netplus.appbase.database.DataBaseHelper;
import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.entities.Category;
import pl.netplus.appbase.entities.WebCategoryContainer;
import pl.netplus.appbase.exception.CommunicationException;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.httpconnection.Provider;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

public class CategoriesRepository implements IBaseRepository<Category> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_CATEGORIES = "INSERT INTO "
			+ DataBaseHelper.TABLE_CATEGORIES
			+ "(ID,Name, ItemCount)  Values(?,?,?)";

	@Override
	public Category read(int id) {
		return null;
	}

	@Override
	public Category read(int id, DataBaseManager dbManager) {
		Category item = null;
		Cursor cursor = dbm.getDataBase().query(
				DataBaseHelper.TABLE_CATEGORIES,
				new String[] { "ID,Name, ItemCount" }, "ID = ? ",
				new String[] { String.valueOf(id) }, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				item = new Category();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setCount(cursor.getInt(2));
				break;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return item;
	}

	@Override
	public ArrayList<Category> readAll(Bundle bundle) {
		dbm.checkIsOpen();
		ArrayList<Category> list = new ArrayList<Category>();
		Cursor cursor = dbm.getDataBase().query(
				DataBaseHelper.TABLE_CATEGORIES,
				new String[] { "ID,Name, ItemCount" }, null, null, null, null,
				"ID");
		if (cursor.moveToFirst()) {
			do {
				Category item = new Category();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setCount(cursor.getInt(2));
				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();

		NetPlusAppGlobals.getInstance().setCategories(list);

		new FavoritesRepository().readAll(null);
		return list;
	}

	@Override
	public boolean insertOrUpdate(Category item, DataBaseManager dbManager) {

		Category oldItem = null;// read(item.getId(), dbManager);
		if (oldItem == null) {
			SQLiteStatement insertStmt = dbManager.getDataBase()
					.compileStatement(INSERT_TO_CATEGORIES);
			insertStmt.bindLong(1, item.getId());
			insertStmt.bindString(2, item.getName());
			insertStmt.bindLong(3, item.getCount());
			long result = insertStmt.executeInsert();

			return result > 0;
		} else {

		}
		return false;

	}

	@Override
	public long getFromServer(DataBaseManager dbManager, String urlAddress,
			IHttpRequestToAsyncTaskCommunication listener) {
		boolean result = false;
		Provider<WebCategoryContainer> provider = new Provider<WebCategoryContainer>(
				WebCategoryContainer.class);
		WebCategoryContainer content = new WebCategoryContainer();
		try {
			content = provider.getObjects(urlAddress, listener);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}

		ArrayList<Category> items = new ArrayList<Category>(
				Arrays.asList(content.items));

		boolean insertResult = true;
		for (Category category : items) {
			insertResult = insertOrUpdate(category, dbManager);
			if (!insertResult) {
				return -1;
			}
		}
		// NetPlusAppGlobals.getInstance().setCategories(items);
		result = true;
		return result ? 1 : -1;
	}

	@Override
	public boolean delete(Category item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Category> readById(int value, Bundle bundle) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteAll(DataBaseManager otherDbm) {
		if (otherDbm != null) {
			int result = otherDbm.getDataBase().delete(
					DataBaseHelper.TABLE_CATEGORIES, null, null);
			return result > 0;
		}
		return false;

	}
}
