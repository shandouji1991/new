package cn.tjpu.eyuan.db;

import android.database.Cursor;
import android.database.SQLException;

public interface DbNoticeService {

	//---opens the database---
	public abstract DbNoticeService open() throws SQLException;

	//---closes the database---
	public abstract void close();

	//---insert a contact into the database---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#insertNotice(java.lang.String, java.lang.String)
	 */
	public abstract long insertNotice(String text, String validity);

	//---deletes a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#deleteNotice(long)
	 */
	public abstract boolean deleteNotice(long rowId);

	//---retrieves all the contacts---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#getAllNotice()
	 */
	public abstract Cursor getAllNotice();

	//---retrieves a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#getANotice(long)
	 */
	public abstract Cursor getANotice(long rowId) throws SQLException;

	//---updates a contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#updateNotice(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract boolean updateNotice(long rowId, String author,
			String image_url, String name, String remark, String reviewer,
			String section, String source, String text, String time);

	public abstract int countNotice();

}