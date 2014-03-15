package cn.tjpu.eyuan.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.tjpu.eyuan.db.DBBANNERAdapter;
import cn.tjpu.eyuan.db.DBNEWSAdapter;
import cn.tjpu.eyuan.db.DBNOTICEAdapter;
import cn.tjpu.eyuan.db.DbBannerService;
import cn.tjpu.eyuan.db.DbNewsService;
import cn.tjpu.eyuan.db.DbNoticeService;

import android.util.Log;

public class DownLoad {


	
    //、、下载关于news的数据并存入到数据库中，
	public String readJSON(String URL) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} else {
				Log.e("JSON", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return stringBuilder.toString();
	}

	
}