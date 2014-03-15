package cn.tjpu.eyuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBNEWSAdapter implements DbNewsService {
    static final String KEY_ROWID = "_id";
    static final String KEY_AUTHOR = "author";
    static final String KEY_BANNER_ID = "banner_id";
    static final String KEY_IMAGEURL = "image_url";
    static final String KEY_NAME = "name";
    static final String KEY_REMARK = "remark";
    static final String KEY_REVIEWER = "reviewer";
    static final String KEY_SECTION = "section";
    static final String KEY_SOURCE = "source";
    static final String KEY_TEXT = "text";
    static final String KEY_TIME = "time";
    
    
    static final String TAG = "DBNEWSAdapter";

    static final String DATABASE_NAME = "eyw_db";
    static final String DATABASE_TABLE = "news";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table news (_id integer primary key autoincrement," +
        "author text not null, " +
        "banner_id integer not null,"+
        "image_url text not null, " +	
        "remark text not null, " +
        "name text not null,"+
        "reviewer text not null,"+
        "section text not null,"+
        "source text not null,"+
        "text text not null, time text not null);";
    static final String DATABASE_CREATE1 =
            "create table notice (_id integer primary key autoincrement," + "text text not null, validity text not null);";
    static final String DATABASE_CREATE2 =
            "create table banner (_id integer primary key autoincrement," + "url text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBNEWSAdapter(Context ctx)
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
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
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
	public DbNewsService open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNewsService#close()
	 */
    @Override
	public void close() 
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#insertNews(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNewsService#insertNews(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	public long insertNews(String author,int banner_id, String image_url ,
    		String name,String remark,String  reviewer,String section,
    		String source,String text,String time) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AUTHOR,author );
        initialValues.put(KEY_BANNER_ID,banner_id);
        initialValues.put(KEY_IMAGEURL,image_url );
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_REMARK,remark);
        initialValues.put(KEY_REVIEWER,reviewer);
        initialValues.put(KEY_SECTION,section);
        initialValues.put(KEY_SOURCE,source);
        initialValues.put(KEY_TEXT,text);
        initialValues.put(KEY_TIME,time);
      
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#deleteNews(long)
	 */
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNewsService#deleteNews(long)
	 */

	public boolean deleteNews(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#getAllNews()
	 */
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNewsService#getAllNews()
	 */

	public Cursor getAllNews()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_AUTHOR,KEY_BANNER_ID,
        		KEY_IMAGEURL,KEY_NAME,KEY_REMARK,KEY_REVIEWER,KEY_SECTION,
        		KEY_SOURCE,KEY_TEXT,KEY_TIME
                }, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#getANews(long)
	 */

	public Cursor getANews(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_AUTHOR,KEY_BANNER_ID,
                		KEY_IMAGEURL,KEY_NAME,KEY_REMARK,KEY_REVIEWER,KEY_SECTION,
                		KEY_SOURCE,KEY_TEXT,KEY_TIME
                        }, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#updateNews(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	public boolean updateNews(long rowId,String author, int banner_id, String image_url ,
    		String name,String remark,String  reviewer,String section,
    		String source,String text,String time)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_AUTHOR,author );
        args.put(KEY_BANNER_ID,banner_id );
        args.put(KEY_IMAGEURL,image_url );
        args.put(KEY_NAME, name);
        args.put(KEY_REMARK,remark);
        args.put(KEY_REVIEWER,reviewer);
        args.put(KEY_SECTION,section);
        args.put(KEY_SOURCE,source);
        args.put(KEY_TEXT,text);
        args.put(KEY_TIME,time);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
	
	public int countNews()
    {
		String sql="select * from notice ";
		Cursor c= db.rawQuery(sql, null);
       c.moveToLast();
       return Integer.parseInt(c.getString(0));
              
    }

}
