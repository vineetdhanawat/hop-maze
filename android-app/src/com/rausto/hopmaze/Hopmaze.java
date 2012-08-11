package com.rausto.hopmaze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressWarnings("unused")

public class Hopmaze extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private static final String TAG = "ARMaze";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Set up click listeners for all the buttons
    View startButton = findViewById(R.id.start_button);
    startButton.setOnClickListener(this);
    View aboutButton = findViewById(R.id.about_button);
    aboutButton.setOnClickListener(this);
    View exitButton = findViewById(R.id.exit_button);
    exitButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
	      switch (v.getId()) {
	      
	      case R.id.start_button:
	    	  openNewGameDialog();
		      break;

	      case R.id.about_button:
	          Intent i = new Intent(this, About.class);
	          startActivity(i);
	          break;

	      // More buttons go here (if any) ...

	      case R.id.exit_button:
	         finish();
	         break;
	      }
	   }
	
	private void openNewGameDialog() {
	      new AlertDialog.Builder(this)
	           .setTitle(R.string.new_game_title)
	           .setItems(R.array.difficulty,
	            new DialogInterface.OnClickListener() {
	               @Override
				public void onClick(DialogInterface dialoginterface, int i) {
	                  startGame(i);
	               }
	            })
	           .show();
	   }
	
	private void startGame(int i) {
	      Log.d(TAG, "clicked on " + i);
	      Intent intent = new Intent(Hopmaze.this, Physics.class);
	      intent.putExtra(Physics.KEY_DIFFICULTY, i);
	      startActivity(intent);
	      //finish();
	   }
	
	@Override
	public void onBackPressed() {	 
		finish();
	}

}