package cn.tjpu.eyuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBBANNERAdapter implements DbBannerService  {
    static final String KEY_ROWID = "_id";
    static final String KEY_URL = "url";
    
    static final String TAG = "DBBANNERAdapter";

    static final String DATABASE_NAME = "eyw_db";
    static final String DATABASE_TABLE = "banner";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table banner (_id integer primary key autoincrement," + "url text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase dbbanner;
    
    public DBBANNERAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    @Override
	public DbBannerService open() throws SQLException 
    {
        dbbanner = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbBannerService#close()
	 */
    @Override
	public void close() 
    {
        DBHelper.close();
    }

    //---insert a contact into the database---

	public long insertBanner(String text) 
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_URL,text);
       
      
        return dbbanner.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
  
	public boolean deleteBanner(long rowId) 
    {
        return dbbanner.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---

	public Cursor getAllBanner()
    {
        return dbbanner.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_URL
                }, null, null, null, null, null);
    }

    //---retrieves a particular contact---

	public Cursor getABanner(long rowId) throws SQLException 
    {
        Cursor mCursor =
                dbbanner.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_URL
                        }, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---

	public boolean updateBanner(long rowId,String author, String image_url ,
    		String name,String remark,String  reviewer,String section,
    		String source,String text,String time)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_URL,text);
        return dbbanner.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

	
	public int countBanner()
    {
		String sql="select * from notice ";
		Cursor c= dbbanner.rawQuery(sql, null);
       c.moveToLast();
       return Integer.parseInt(c.getString(0));
              
    }
}

