package com.sothree.slidinguppanel.demo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class DemoActivity extends Activity {
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FragmentManager fragmentManager = getFragmentManager();  
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
        
        Fragment_Demo fragment = new Fragment_Demo();  
        fragmentTransaction.replace(android.R.id.content, fragment);          
        fragmentTransaction.commit();  
	}
   
}
