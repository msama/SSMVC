package com.firstapp;


import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

@TargetApi(11)
public class DisplayMessageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get Intent
        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(text);
        
        //textView.append(FileAccessLib.setFileName("Prova",this)?" True":" False");
        
        

        // Set the text view as the activity layout
        setContentView(textView);
        
    }

  

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
