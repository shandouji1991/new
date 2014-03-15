package cn.tjpu.eyuan;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.app.Application;
import android.graphics.Bitmap;

public class DemoApplication extends Application {
	public static  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
   
	 private static DemoApplication me;

	    @Override
	    public void onCreate() {        
	        super.onCreate();
	        me = this ;

	    }
	    public static DemoApplication getInstance() {
	         return me;
	    }
	
	public void  putImageCache(String key,SoftReference<Bitmap> object) {
	    imageCache.put(key, object);
	}

	public  SoftReference<Bitmap> getImageCache(String key) {
		return imageCache.get(key);
	}

	public boolean containsKey(String imageURL) {
		// TODO Auto-generated method stub
		
		return imageCache.containsKey(imageURL);
	}
    
}
