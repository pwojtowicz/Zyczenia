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
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

public class ContentObjectRepository implements IBaseRepository<ContentObject> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_OBJECTS = "INSERT INTO "
			+ DataBaseHelper.TABLE_OBJECTS
			+ "(Content, Categories, Rating)  Values(?,?,?)";

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
	public boolean insertOrUpdate(ContentObject item) {
		ContentObject oldItem = read(item.getId());
		if (oldItem == null) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_OBJECTS);
			insertStmt.bindString(1, item.getText());
			insertStmt.bindString(2, item.getCategory());
			insertStmt.bindDouble(3, item.getRating());
			long result = insertStmt.executeInsert();
			dbm.close();
			return result > 0 ? true : false;
		}
		return false;
	}

	@Override
	public ArrayList<ContentObject> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener) {
		Provider<WebContentObjectContainer> provider = new Provider<WebContentObjectContainer>(
				WebContentObjectContainer.class);
		WebContentObjectContainer content = new WebContentObjectContainer();
		try {
			content = provider
					.getObjects(
							"http://zyczenia.tja.pl/api/android_bramka.php?co=lista_obekty&data=0",
							null);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}
		ArrayList<ContentObject> items = new ArrayList<ContentObject>(
				Arrays.asList(content.items));
		for (ContentObject contentObject : items) {
			insertOrUpdate(contentObject);
		}
		return items;
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
}
