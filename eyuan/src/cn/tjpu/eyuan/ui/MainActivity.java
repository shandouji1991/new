package cn.tjpu.eyuan.ui;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cn.tjpu.eyuan.DemoApplication;
import cn.tjpu.eyuan.R;
import cn.tjpu.eyuan.internet.DownLoad;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	// 内存缓存,SoftReference实现自动回收  
 //   public static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();  
    DemoApplication imageCache = DemoApplication.getInstance();
    /** 
     * 自动判断从内存还是从网络获取图片 
     *  
     * @param imageURL 
     * @return 
     */  
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        String ii="http://www.eyw.edu.cn/www/uploads/allimg/131231/30406-131231163021.jpg";
        String aa="http://www.eyw.edu.cn/www/uploads/allimg/131231/30406-131231151224.jpg";
        
        ImageView i=(ImageView)findViewById(R.id.imageView1);
        i.setImageBitmap(loadBitmap(ii));
        ImageView a=(ImageView)findViewById(R.id.imageView2);
        a.setImageBitmap(loadBitmap(aa));
        ImageView o=(ImageView)findViewById(R.id.imageView3);
        o.setImageBitmap(loadBitmap(aa));
        
        
          }


  


}
