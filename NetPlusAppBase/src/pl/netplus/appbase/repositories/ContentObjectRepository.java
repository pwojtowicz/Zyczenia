package pl.netplus.appbase.repositories;

import java.util.ArrayList;
import java.util.Arrays;

import pl.netplus.appbase.database.DataBaseHelper;
import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.entities.ContentObject;
import pl.netplus.appbase.entities.WebContentObjectContainer;
import pl.netplus.appbase.exception.CommunicationException;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.httpconnection.Provider;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

public class ContentObjectRepository implements IBaseRepository<ContentObject> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_OBJECTS = "INSERT INTO "
			+ DataBaseHelper.TABLE_OBJECTS
			+ "(ID, Content, Categories, Rating)  Values(?,?,?,?)";

	@Override
	public ContentObject read(int id) {
		ContentObject item = null;
		try {
			dbm.checkIsOpen();

			Cursor cursor = dbm.getDataBase().query(
					DataBaseHelper.TABLE_OBJECTS,
					new String[] { "ID,Content, Categories, Rating" },
					"ID = ? ", new String[] { String.valueOf(id) }, null, null,
					null);
			if (cursor.moveToFirst()) {
				do {
					item = new ContentObject();
					item.setId(cursor.getInt(0));
					item.setText(cursor.getString(1));
					item.setCategory(cursor.getString(2));
					item.setRating(cursor.getDouble(3));
					break;
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			dbm.close();
		} catch (SQLException e) {

		}
		return item;
	}

	@Override
	public ContentObject read(int id, DataBaseManager dbManager) {
		ContentObject item = null;
		Cursor cursor = dbManager.getDataBase().query(
				DataBaseHelper.TABLE_OBJECTS,
				new String[] { "ID,Content, Categories, Rating" }, "ID = ? ",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				item = new ContentObject();
				item.setId(cursor.getInt(0));
				item.setText(cursor.getString(1));
				item.setCategory(cursor.getString(2));
				item.setRating(cursor.getDouble(3));
				break;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return item;
	}

	public ArrayList<ContentObject> readFavorites() {
		ArrayList<ContentObject> list = new ArrayList<ContentObject>();

		ArrayList<Integer> favorites = NetPlusAppGlobals.getInstance()
				.getFavoritesId();

		for (Integer integer : favorites) {
			ContentObject co = read(integer);
			if (co != null)
				list.add(co);
		}
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_FAVORITE, list);
		return list;

	}

	@Override
	public ArrayList<ContentObject> readById(int value) {
		ArrayList<ContentObject> list = new ArrayList<ContentObject>();
		try {
			dbm.checkIsOpen();

			Cursor cursor = dbm.getDataBase().query(
					DataBaseHelper.TABLE_OBJECTS,
					new String[] { "ID,Content, Categories, Rating" },
					"Categories LIKE ? ",
					new String[] { "%(" + String.valueOf(value) + ")%" }, null,
					null, "ID");
			if (cursor.moveToFirst()) {
				do {
					ContentObject item = new ContentObject();
					item.setId(cursor.getInt(0));
					item.setText(cursor.getString(1));
					item.setCategory(cursor.getString(2));
					item.setRating(cursor.getDouble(3));
					list.add(item);
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			dbm.close();
		} catch (SQLException e) {
			return list;
		}
		NetPlusAppGlobals.getInstance().setObjectsDictionary(value, list);
		return list;
	}

	@Override
	public ArrayList<ContentObject> readAll() {
		dbm.checkIsOpen();
		ArrayList<ContentObject> list = new ArrayList<ContentObject>();
		Cursor cursor = dbm.getDataBase().query(DataBaseHelper.TABLE_OBJECTS,
				new String[] { "ID,Content, Categories, Rating" }, null, null,
				null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				ContentObject item = new ContentObject();
				item.setId(cursor.getInt(0));
				item.setText(cursor.getString(1));
				item.setCategory(cursor.getString(2));
				item.setRating(cursor.getDouble(3));
				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_ALL, list);
		return list;
	}

	@Override
	public boolean insertOrUpdate(ContentObject item, DataBaseManager dbManager) {
		return false;
	}

	@Override
	public long getFromServer(DataBaseManager dbManager, String urlAddress,
			IHttpRequestToAsyncTaskCommunication listener) {
		dbManager.getDataBase().setLockingEnabled(false);
		boolean result = false;
		Provider<WebContentObjectContainer> provider = new Provider<WebContentObjectContainer>(
				WebContentObjectContainer.class);
		WebContentObjectContainer content = new WebContentObjectContainer();
		try {
			content = provider.getObjects(urlAddress, null);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}

		ArrayList<ContentObject> items = new ArrayList<ContentObject>(
				Arrays.asList(content.items));

		ArrayList<Integer> itemsIds = readAllId(dbManager);

		boolean insertResult = true;
		for (ContentObject contentObject : items) {

			boolean shouldUpdate = itemsIds.contains(contentObject.getId());

			insertResult = insertOrUpdate(contentObject, shouldUpdate,
					dbManager);
			if (!insertResult) {
				return -1;
			}
		}
		result = true;
		return content != null ? content.serverTime : -1;
	}

	private boolean insertOrUpdate(ContentObject item, boolean shouldUpdate,
			DataBaseManager dbManager) {

		if (!shouldUpdate) {
			SQLiteStatement insertStmt = dbManager.getDataBase()
					.compileStatement(INSERT_TO_OBJECTS);
			insertStmt.bindLong(1, item.getId());
			insertStmt.bindString(2, item.getText());
			insertStmt.bindString(3, item.getCategory());
			insertStmt.bindDouble(4, item.getRating());
			return insertStmt.executeInsert() > 0;
		} else {
			ContentValues dataToInsert = new ContentValues();
			dataToInsert.put("Content", item.getText());
			dataToInsert.put("Categories", item.getCategory());
			dataToInsert.put("Rating", item.getRating());
			return dbManager.getDataBase().update(DataBaseHelper.TABLE_OBJECTS,
					dataToInsert, "ID = ?",
					new String[] { String.valueOf(item.getId()) }) > 0;

		}
	}

	private ArrayList<Integer> readAllId(DataBaseManager dbManager) {

		ArrayList<Integer> list = new ArrayList<Integer>();
		Cursor cursor = dbManager.getDataBase().query(
				DataBaseHelper.TABLE_OBJECTS, new String[] { "ID" }, null,
				null, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	@Override
	public boolean delete(ContentObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<ContentObject> searchObjects(int categoryId, String value) {
		dbm.checkIsOpen();
		ArrayList<ContentObject> list = new ArrayList<ContentObject>();

		String query = "Content LIKE ? ";
		String[] wheareClause = new String[] { "%" + value + "%" };

		if (categoryId > 0) {
			query += " AND Categories LIKE ? ";
			wheareClause = new String[] { "%" + value + "%",
					"%(" + String.valueOf(categoryId) + ")%" };
		}

		Cursor cursor = dbm.getDataBase().query(DataBaseHelper.TABLE_OBJECTS,
				new String[] { "ID,Content, Categories, Rating" }, query,
				wheareClause, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				ContentObject item = new ContentObject();
				item.setId(cursor.getInt(0));
				item.setText(cursor.getString(1));
				item.setCategory(cursor.getString(2));
				item.setRating(cursor.getDouble(3));
				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		NetPlusAppGlobals.getInstance().setObjectsDictionary(
				NetPlusAppGlobals.ITEMS_SEARCH, list);
		return list;
	}

	public long getObjectsToDeleteFromServer(DataBaseManager dbManager,
			String urlAddress, IHttpRequestToAsyncTaskCommunication listener) {

		Provider<WebContentObjectContainer> provider = new Provider<WebContentObjectContainer>(
				WebContentObjectContainer.class);
		WebContentObjectContainer content = new WebContentObjectContainer();
		try {
			content = provider.getObjects(urlAddress, null);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}
		ArrayList<ContentObject> items = new ArrayList<ContentObject>(
				Arrays.asList(content.items));

		ArrayList<Integer> itemsIds = readAllId(dbManager);
		for (ContentObject item : items) {
			itemsIds.remove((Integer) item.getId());
		}
		if (itemsIds.size() > 0) {
			for (Integer integer : itemsIds) {
				dbManager.getDataBase().delete(DataBaseHelper.TABLE_OBJECTS,
						"ID = " + String.valueOf(integer), null);
			}
		}

		return 0;
	}

}
