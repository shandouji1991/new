package cn.tjpu.eyuan.internet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import cn.tjpu.eyuan.DemoApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownPic {
	  DemoApplication imageCache = DemoApplication.getInstance();
	  
	 public Bitmap  DownPic(String str){
		 return loadBitmap(str);
	 }
	
    public DownPic(DemoApplication imageCache) {
		super();
		this.imageCache = imageCache;
	}

	public  Bitmap loadBitmap(String imageURL) {  
        long time = new Date().getTime();  
        System.out.println(time + "--loadBitmap--" + imageURL);  
  
        Bitmap bm = null;  
        if (imageCache.containsKey(imageURL)) {// 从内存中获取  
        	System.out.println(time + "--loadBitmap--from Cache");  
            SoftReference<Bitmap> reference = imageCache.getImageCache(imageURL);  
            bm = reference.get();  
        }  
        if (null == bm) {// 到网络下载  
        	System.out.println(time + "--loadBitmap--from Net");  
            try {
				bm = new DownloadImageTask().execute(imageURL).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            if (null != bm) {  
                imageCache.putImageCache(imageURL, new SoftReference<Bitmap>(bm)); // 保存到内存  
            }  
        }  
        if (null == bm) {  
        	System.out.println(time + "--loadBitmap--false");  
        } else {  
        	System.out.println(time + "--loadBitmap--true");  
        }  
        return bm;  
    }  
    
    private InputStream OpenHttpConnection(String urlString) 
    	    throws IOException
    	    {
    	        InputStream in = null;
    	        int response = -1;
    	               
    	        URL url = new URL(urlString); 
    	        URLConnection conn = url.openConnection();
    	                 
    	        if (!(conn instanceof HttpURLConnection))                     
    	            throw new IOException("Not an HTTP connection");        
    	        try{
    	            HttpURLConnection httpConn = (HttpURLConnection) conn;
    	            httpConn.setAllowUserInteraction(false);
    	            httpConn.setInstanceFollowRedirects(true);
    	            httpConn.setRequestMethod("GET");
    	            httpConn.connect();
    	            response = httpConn.getResponseCode();                 
    	            if (response == HttpURLConnection.HTTP_OK) {
    	                in = httpConn.getInputStream();                                 
    	            }                     
    	        }
    	        catch (Exception ex)
    	        {
    	        	Log.d("Networking", ex.getLocalizedMessage());
    	            throw new IOException("Error connecting");
    	        }
    	        return in;     
    	    }
    	    
    	    private Bitmap DownloadImage(String URL)
    	    {        
    	        Bitmap bitmap = null;
    	        InputStream in = null;        
    	        try {
    	            in = OpenHttpConnection(URL);
    	            bitmap = BitmapFactory.decodeStream(in);
    	            in.close();
    	        } catch (IOException e1) {
    	            Log.d("NetworkingActivity", e1.getLocalizedMessage());            
    	        }
    	        return bitmap;                
    	    }
    	    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	    	protected Bitmap doInBackground(String... urls) {
    	    		return DownloadImage(urls[0]);
    	    	}
    	    	
    	    	protected void onPostExecute(Bitmap result) {
    	    		
    	    	}
    	    }

}
