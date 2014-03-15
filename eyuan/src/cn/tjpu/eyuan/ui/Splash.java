package cn.tjpu.eyuan.ui;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.tjpu.eyuan.DemoApplication;
import cn.tjpu.eyuan.R;
import cn.tjpu.eyuan.db.DBNEWSAdapter;
import cn.tjpu.eyuan.db.DBNOTICEAdapter;
import cn.tjpu.eyuan.db.DbNewsService;
import cn.tjpu.eyuan.db.DbNoticeService;
import cn.tjpu.eyuan.guideview.MainGuide;
import cn.tjpu.eyuan.internet.DownLoad;
import cn.tjpu.eyuan.internet.DownPic;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends Activity {
	 private final int SPLASH_DISPLAY_LENGHT = 1000; //延迟三秒 
	 private ImageView splash2;
	 public String strnews,strnotice,strbanner;
     //是否是第一次使用
     private boolean isFirstUse;
	 DemoApplication imageCache = DemoApplication.getInstance();
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	        ReadJSONFeedTask tasknews=new ReadJSONFeedTask();
	        ReadJSONFeedTask tasknotice=new ReadJSONFeedTask();
	        ReadJSONFeedTask taskbanner=new ReadJSONFeedTask();
	   
				try {
					strnews=tasknews.execute("http://172.31.137.16:8080/eyuannews/ListServlet?format=json").get();
					strnotice=tasknotice.execute("http://172.31.137.16:8080/eyuannews/NoticeServlet?format=json").get();
					strbanner=taskbanner.execute("http://172.31.137.16:8080/eyuannews/NoticeServlet?format=json").get();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				} catch (ExecutionException e1) {
					
					e1.printStackTrace();
				}  
				
	        DbNewsService dbnews = new DBNEWSAdapter(this);	        
	         dbnews.open();
	         try {
					JSONArray jsonArray = new JSONArray(strnews);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
							long id = dbnews.insertNews(jsonObject.getString("author"),
									jsonObject.getInt("banner_id") , jsonObject.getString("image_url"),
					        		jsonObject.getString("name"),jsonObject.getString("remark"),
					        		jsonObject.getString("reviewer"),jsonObject.getString("section"),
					        		jsonObject.getString("source"),jsonObject.getString("text") ,jsonObject.getString("timer"));
					}	
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
	         dbnews.close();
	         
	         
	         DbNoticeService dbnotice= new DBNOTICEAdapter(this); 
	         dbnotice.open();
	         try {
	 			JSONArray jsonArray = new JSONArray(strnotice);
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject jsonObject = jsonArray.getJSONObject(i);
			      
	 			        long id1 = dbnotice.insertNotice(jsonObject.getString("text"),jsonObject.getString("validity"));
	 			        
	 			}	
	 		} catch (JSONException e) {
	 			e.printStackTrace();
	 		}
	         dbnotice.close();

	   /*      
	         DbBannerService dbbanner= new DBBANNERAdapter(null);
		        dbbanner.open();
	         try {
	 			JSONArray jsonArray = new JSONArray(strbanner);
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject jsonObject = jsonArray.getJSONObject(i);
	 			        long id = dbbanner.insertBanner(jsonObject.getString("text"));
	 			       
	 			}	
	 		} catch (JSONException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	         dbbanner.close();
	        */
	        
            
	            new Handler().postDelayed(new Runnable(){ 
                       @Override 
	                 public void run() { 
	                	DownPic aa=new DownPic(imageCache);
	                	String ii="http://ygimg1.mnsfz.com/pic/yangguang/2014-2-24/1/1145040205359490716.jpg";
	     	            splash2 =(ImageView) findViewById(R.id.splash2);
	     	            splash2.setImageBitmap(aa.DownPic(ii));
	                 } 
	                    
	                }, SPLASH_DISPLAY_LENGHT);
	          
	            new Handler().postDelayed(new Runnable(){
	    		/*	@Override
	    			public void run(){
	    				Intent intent = new Intent (Splash.this,MainGuide.class);			
	    				startActivity(intent);			
	    				Splash.this.finish();
	    				*/
	    				 public void run() {
	                            try {
	                                    /**
	                                     * 延迟两秒时间
	                                     */
	                                    Thread.sleep(2000);
	                                    
	                                    //读取SharedPreferences中需要的数据
	                                    SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_WORLD_READABLE);

	                            isFirstUse = preferences.getBoolean("isFirstUse", true);

	                            /**
	                                     *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
	                                     */
	                                    if (isFirstUse) {
	                                            startActivity(new Intent(Splash.this, MainGuide.class));
	                                    } else {
	                                            startActivity(new Intent(Splash.this, MainActivity.class));
	                                    }
	                                    finish();
	                                    
	                                    //实例化Editor对象
	                                    Editor editor = preferences.edit();
	                            //存入数据
	                            editor.putBoolean("isFirstUse", false);
	                            //提交修改
	                            editor.commit();


	                            } catch (InterruptedException e) {

	                            }
	    			}
	    		}, 2300);
	            

	            }

	            
	            
	          	  
	      

	  private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
		  DownLoad bb=new DownLoad();
	        protected String doInBackground(String... urls) {
	            return bb.readJSON(urls[0]);
	        }
	    }
	 
	  
	  
	  
}
