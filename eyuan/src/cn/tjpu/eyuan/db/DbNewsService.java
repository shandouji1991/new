package cn.tjpu.eyuan.db;

import android.database.Cursor;
import android.database.SQLException;

public interface DbNewsService {

	//---opens the database---
	public abstract DbNewsService open() throws SQLException;

	//---closes the database---
	public abstract void close();

	//---insert a contact into the database---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#insertNews(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract long insertNews(String author, int banner_id,String image_url,
			String name, String remark, String reviewer, String section,
			String source, String text, String time);

	//---deletes a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#deleteNews(long)
	 */
	public abstract boolean deleteNews(long rowId);

	//---retrieves all the contacts---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#getAllNews()
	 */
	public abstract Cursor getAllNews();

	//---retrieves a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#getANews(long)
	 */
	public abstract Cursor getANews(long rowId) throws SQLException;

	//---updates a contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice#updateNews(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract boolean updateNews(long rowId, String author,int banner_id,
			String image_url, String name, String remark, String reviewer,
			String section, String source, String text, String time);

	public abstract int countNews();

}