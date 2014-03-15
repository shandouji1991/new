package cn.tjpu.eyuan.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import cn.tjpu.eyuan.R;

public class DatabasesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidemian);
        
        DbNewsService db = new DBNEWSAdapter(this);
       // DbNoticeService db1= new DBNOTICEAdapter(this);
        //DbBannerService db2= new DBBANNERAdapter(this);
        
        //--get all news---
        db.open();
        long id = db.insertNews("aaaaaaaaaaaaaa",0, "aaaaaaaaaaaaaa","aaaaaaaaaaaaaa",
        		"dsdfh414jkdh","dsdfh4141jkdh","dsd414fhjkdh",
        		"ds414dfhjkdh","dsdfhj414kdh","dsdfhjk4141dh");
        id = db.insertNews("Mary414 Jackson",1, "mary@jac414kson.com","411414","ds4141dfhjkdh","dsdf4141hjkdh",
        		"dsdfhj41kdh","414","dsdfhj1414kdh","414");
        Cursor c = db.getANews(1);
        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();    
    }

	private void DisplayContact(Cursor c) {
		// TODO Auto-generated method stub
		
		TextView tv = (TextView) findViewById(R.id.text1);
		 tv.setText("_id: " + c.getString(0) + "\n" +
	                "name: " + c.getString(1) + c.getString(2) + "\n" + c.getString(3) + "\n"
	                + c.getString(4) + "\n" + c.getString(5) + "\n" + c.getString(6) + "\n"
	                + c.getString(7) + "\n" + c.getString(8) + "\n" + c.getString(9));
		
	}
     
        
}   
        
/*
 *    
        DbNewsService db= new DBNEWSAdapter(this);
      //---add a contact---
        db.open();
        long id = db.insertNews("aaaaaaaaaaaaaa", "aaaaaaaaaaaaaa","aaaaaaaaaaaaaa",
        		"dsdfh414jkdh","dsdfh4141jkdh","dsd414fhjkdh",
        		"ds414dfhjkdh","dsdfhj414kdh","dsdfhjk4141dh");
        id = db.insertNews("Mary414 Jackson", "mary@jac414kson.com","411414","ds4141dfhjkdh","dsdf4141hjkdh",
        		"dsdfhj41kdh","414","dsdfhj1414kdh","414");
        db.close();
        
        
        DBNEWSAdapter db = new DBNEWSAdapter(this);
        DBNOTICEAdapter db1= new DBNOTICEAdapter(this);
        DBBANNERAdapter db2= new DBBANNERAdapter(this);

       
        //---add a contact---
        db.open();
        long id = db.insertNews("Wei414141-Meng Lee", "weimengle41414e@learn2develop.net","dsd41414fhjkdh",
        		"dsdfh414jkdh","dsdfh4141jkdh","dsd414fhjkdh",
        		"ds414dfhjkdh","dsdfhj414kdh","dsdfhjk4141dh");
        id = db.insertNews("Mary414 Jackson", "mary@jac414kson.com","411414","ds4141dfhjkdh","dsdf4141hjkdh",
        		"dsdfhj41kdh","414","dsdfhj1414kdh","414");
        db.close();
        
        //---add a contact---
        db1.open();
        long id2 = db1.insertNotice("Wei414-Meng Lee", "41441@4141.net");
        id2 = db1.insertNotice("Ma4141r414y 414", "414@414.com");
        db1.close();
        
        //---add a contact---
        db2.open();
        long id3 = db2.insertBanner("Wei414-Meng Lee", "41441@4141.net");
        id3 = db2.insertBanner("Ma4141r414y 414", "414@414.com");
        db2.close();
        
        
        */
        

     
        
        /*
        //--get all news---
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
        */
        
        /*
        //---get a contact---
        db.open();
        Cursor c = db.getContact(2);
        if (c.moveToFirst())        
            DisplayContact(c);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        db.close();     
        */
        
        /*
        //---update contact---
        db.open();
        if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();
        */
        
        /*
        //---delete a contact---
        db.open();
        if (db.deleteContact(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();
        */
     /*   
        try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("eyw_db"),
                    new FileOutputStream(destPath + "/eyw_db"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        //---get all contacts---
        db.open();
        Cursor c = db.getAll();
        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
    }
    
    public void CopyDB(InputStream inputStream, 
    OutputStream outputStream) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
    

    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "_id: " + c.getString(0) + "\n" +
                "name: " + c.getString(1) ,
                Toast.LENGTH_LONG).show();
    }
}
        */