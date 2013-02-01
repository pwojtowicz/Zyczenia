package pl.netplus.appbase.repositories;

import java.util.ArrayList;

import pl.netplus.appbase.database.DataBaseHelper;
import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.entities.Favorite;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.wishesbase.support.NetPlusAppGlobals;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class FavoritesRepository implements IBaseRepository<Favorite> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_FAVORITES = "INSERT INTO "
			+ DataBaseHelper.TABLE_FAVORITES + "(ObjectId)  Values(?)";

	@Override
	public Favorite read(int id) {
		return null;
	}

	@Override
	public ArrayList<Favorite> readAll() {
		dbm.checkIsOpen();
		ArrayList<Favorite> list = new ArrayList<Favorite>();
		Cursor cursor = dbm.getDataBase().query(DataBaseHelper.TABLE_FAVORITES,
				new String[] { "ID,ObjectId" }, null, null, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				Favorite item = new Favorite();
				item.setId(cursor.getInt(0));
				item.setObjectId(cursor.getInt(1));

				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();

		NetPlusAppGlobals.getInstance().setFavoritesId(list);
		return list;
	}

	@Override
	public boolean insertOrUpdate(Favorite item) {
		if (item.isFavorite()) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_FAVORITES);
			insertStmt.bindLong(1, item.getObjectId());
			long result = insertStmt.executeInsert();
			dbm.close();

			if (result > 0) {
				NetPlusAppGlobals.getInstance().addToFavorites(
						item.getObjectId());
				return true;
			}
			return false;
		} else {
			return delete(item);
		}

	}

	@Override
	public ArrayList<Favorite> getFromServer(
			IHttpRequestToAsyncTaskCommunication listener) {
		return null;
	}

	@Override
	public boolean delete(Favorite item) {
		dbm.checkIsOpen();

		int result = dbm.getDataBase().delete(DataBaseHelper.TABLE_FAVORITES,
				"ObjectId = ?",
				new String[] { String.valueOf(item.getObjectId()) });
		dbm.close();

		if (result > 0) {
			NetPlusAppGlobals.getInstance().removedFromFavorites(
					item.getObjectId());
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Favorite> readById(int value) {
		// TODO Auto-generated method stub
		return null;
	}

}
