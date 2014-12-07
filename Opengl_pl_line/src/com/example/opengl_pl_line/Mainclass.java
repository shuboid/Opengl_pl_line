package com.example.opengl_pl_line;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class Mainclass extends Activity {
	GLSurfaceView mview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mview = new GLSurfaceView(this);
        mview.setEGLContextClientVersion(2);
        mview.setRenderer(new Myrenderer());
       
        
    /*    
        Toast t=new Toast(getApplicationContext());
        t.setText("hi this activity is started");
        t.show();*/
        
        
     setContentView(mview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      // getMenuInflater().inflate(R.menu.activity_mainclass, menu);
        return true;
    }
    
}
