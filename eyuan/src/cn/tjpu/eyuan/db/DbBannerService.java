package cn.tjpu.eyuan.db;

import android.database.Cursor;
import android.database.SQLException;

public interface DbBannerService {

	//---opens the database---
	public abstract DbBannerService open() throws SQLException;

	//---closes the database---
	public abstract void close();

	//---insert a contact into the database---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice1#insertBanner(java.lang.String, java.lang.String)
	 */
	public abstract long insertBanner(String text);

	//---deletes a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice1#deleteBanner(long)
	 */
	public abstract boolean deleteBanner(long rowId);

	//---retrieves all the contacts---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice1#getAllBanner()
	 */
	public abstract Cursor getAllBanner();

	//---retrieves a particular contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice1#getABanner(long)
	 */
	public abstract Cursor getABanner(long rowId) throws SQLException;

	//---updates a contact---
	/* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.Dbservice1#updateBanner(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract boolean updateBanner(long rowId, String author,
			String image_url, String name, String remark, String reviewer,
			String section, String source, String text, String time);

	public abstract int countBanner();

}