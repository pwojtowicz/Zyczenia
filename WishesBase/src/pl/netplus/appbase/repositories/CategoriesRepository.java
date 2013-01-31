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
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

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
	public ArrayList<Category> readAll() {
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
		return list;
	}

	@Override
	public boolean insertOrUpdate(Category item) {
		Category oldItem = read(item.getId());
		if (oldItem == null) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_CATEGORIES);
			insertStmt.bindLong(1, item.getId());
			insertStmt.bindString(2, item.getName());
			insertStmt.bindLong(3, item.getCount());
			long result = insertStmt.executeInsert();
			dbm.close();
			return result > 0 ? true : false;
		}

		return false;
	}

	@Override
	public ArrayList<Category> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener) {
		Provider<WebCategoryContainer> provider = new Provider<WebCategoryContainer>(
				WebCategoryContainer.class);
		WebCategoryContainer content = new WebCategoryContainer();
		try {
			content = provider
					.getObjects(
							"http://zyczenia.tja.pl/api/android_bramka.php?co=lista_kategorii",
							listener);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}

		ArrayList<Category> items = new ArrayList<Category>(
				Arrays.asList(content.items));

		for (Category category : items) {
			insertOrUpdate(category);
		}
		return items;
	}

	@Override
	public int readTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Category item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Category> readById(int value) {
		// TODO Auto-generated method stub
		return null;
	}
}
