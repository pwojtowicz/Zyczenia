package pl.netplus.appbase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "netpluswishes.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_CATEGORIES = "Category";
	public static final String TABLE_OBJECTS = "Objects";
	public static final String TABLE_FAVORITES = "Favorite";

	private static final String CREATE_CATEGORIES = "create table "
			+ TABLE_CATEGORIES
			+ "(ID integer primary key, Name text not null, ItemCount integer not null);";

	private static final String CREATE_OBJECTS = "create table "
			+ TABLE_OBJECTS
			+ "(ID integer primary key, Content text not null, Categories text, Rating REAL, udate INTEGER);";

	private static final String CREATE_FAVORITES = "create table "
			+ TABLE_FAVORITES
			+ "(ID integer primary key autoincrement, ObjectId integer);";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQLiteDatabase db = getWritableDatabase();
		db.needUpgrade(DATABASE_VERSION);
		db.close();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_CATEGORIES);
		database.execSQL(CREATE_OBJECTS);
		database.execSQL(CREATE_FAVORITES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
	}

}
