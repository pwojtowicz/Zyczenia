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
		return null;
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
	public int readTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(ContentObject item) {
		// TODO Auto-generated method stub
		return false;
	}

}
