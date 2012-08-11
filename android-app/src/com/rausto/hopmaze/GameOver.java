package com.rausto.hopmaze;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOver extends Activity {
	
	private int stepsTaken;
	private TextView gameOverText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Intent myLocalIntent = getIntent();
		Bundle myBundle = myLocalIntent.getExtras();
		int stepsTaken = myBundle.getInt("stepsTaken");

		//int diff = getIntent().getIntExtra(stepsTaken,-1);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		gameOverText = (TextView) findViewById(R.id.about_content);
	      // Set “about.xml” layout as a default view
		gameOverText.setText("Congratulations ! You Have Finished the Maze in "+ stepsTaken +" Steps");
	}
	
	@Override
	public void onBackPressed() {	 
		Intent i = new Intent(this, Hopmaze.class);
		startActivity(i);
	}

}
