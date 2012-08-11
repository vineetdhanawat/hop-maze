package com.rausto.hopmaze;

import java.util.List;
import java.lang.Math;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
@SuppressWarnings("unused")

public class Physics extends Activity implements SensorEventListener {
	
	private static final String TAG = "HopMaze";
	public static final String KEY_DIFFICULTY = "com.rausto.hopmaze.difficulty";
	private static final int DIFFICULTY_EASY = 0;
	private static final int DIFFICULTY_MEDIUM = 1;
	private static final int DIFFICULTY_HARD = 2;
	
	// Index for current position of user in the maze
	int currentPos=0;	
	// String being passed to draw final view
	static String nearest [] = {"1111","1111","1111","1111","1111","1111","1111","1111","1111"};    
	// Set of nearest 9 (with ref to currentPos) strings in hex form
	private String nearest9;	
	// Records the initial direction in which the app was started
	static int directionPointer = 0;	
	// Total no of steps taken by user at any current instant
	static int stepsTaken=0;
	// String used to display the difficult level
	static String diffLevel;
	// <int> Square root for length of string e.g 8 for 8x8 maze 
	private int sqr;
	// String used to store the entire maze as a string
	private String maze;	
	
	private SensorManager mgr;
	private List<Sensor> sensorList;
	//private TextView output;
	private int ACC_MIN_THR = 666, ACC_MID_THR = 1200, ACC_MAX_THR = 2000;
	private int jumpSet = 1;
	private double lastAHighest=0,thisAHighest=0,trueAVal=0; 	//Acc - Highest values, true value 
	private int lastOX=0, lastOY=0, baseOX=500;		//Orient - last X&Y,
	boolean isJump=false;
	private long lastTime=0;									//Just for syncing time in seconds
	private String showmsg;

	private final String easyMaze = "FXFFFFFF"+"F3AC9ACF"+"F9E51E5F"+"F1A22A4F"+"F5B8AA4F"+"F7D1AE5F"+"FB22AA6F"+"FFFFFFXF";
	private final String mediumMaze = "FXFFFFFF"+"F3AC9ACF"+"F9E51E5F"+"F1A22A4F"+"F5B8AA4F"+"F7D1AE5F"+"FB22AA6F"+"FFFFFFXF";
	private final String hardMaze = "FXFFFFFF"+"F3AC9ACF"+"F9E51E5F"+"F1A22A4F"+"F5B8AA4F"+"F7D1AE5F"+"FB22AA6F"+"FFFFFFXF";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(TAG, "onCreate");
		
        //output = (TextView) findViewById(R.id.output);
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY,0);
		maze = getMaze(diff);
		processView();
		
		Toast.makeText(getBaseContext(), "Maze Loaded", Toast.LENGTH_SHORT).show();		
		stepsTaken=0; directionPointer=0;
		
	}
	
	private String getMaze(int diff) {
		String maz;
		// TODO: Continue last game
		switch (diff) {
		case DIFFICULTY_HARD:
			maz = hardMaze;
			diffLevel="Hard";
			break;
		case DIFFICULTY_MEDIUM:
			maz = mediumMaze;
			diffLevel="Medium";
			break;
		case DIFFICULTY_EASY:
		default:
			maz = easyMaze;
			diffLevel="Easy";
			break;
		}
		sqr = (int) Math.sqrt(maz.length());
		currentPos =(maz.length()-sqr)-2;
		
		return maz;
	}
	
	private void processView(){
		nearest9=getNearest9(maze);
		
	       String line;
	       if(directionPointer==1)
	    	   line=Character.toString(nearest9.charAt(2))+Character.toString(nearest9.charAt(5))+Character.toString(nearest9.charAt(8))+Character.toString(nearest9.charAt(1))+Character.toString(nearest9.charAt(4))+Character.toString(nearest9.charAt(7))+Character.toString(nearest9.charAt(0))+Character.toString(nearest9.charAt(3))+Character.toString(nearest9.charAt(6));
	       else if(directionPointer==2)
	    	   line=Character.toString(nearest9.charAt(8))+Character.toString(nearest9.charAt(7))+Character.toString(nearest9.charAt(6))+Character.toString(nearest9.charAt(5))+Character.toString(nearest9.charAt(4))+Character.toString(nearest9.charAt(3))+Character.toString(nearest9.charAt(2))+Character.toString(nearest9.charAt(1))+Character.toString(nearest9.charAt(0));
	       else if(directionPointer==3)
	    	   line=Character.toString(nearest9.charAt(6))+Character.toString(nearest9.charAt(3))+Character.toString(nearest9.charAt(0))+Character.toString(nearest9.charAt(7))+Character.toString(nearest9.charAt(4))+Character.toString(nearest9.charAt(1))+Character.toString(nearest9.charAt(8))+Character.toString(nearest9.charAt(5))+Character.toString(nearest9.charAt(2));
	       else line=nearest9;

	       for (int i = 0,j = line.length(); i < j; i++){
	     	//if(Character.toString(line.charAt(i)).equals("1"))
	    	switch( line.charAt(i) ) {
	    	case '0': nearest[i]="0000"; break;
	        case '1': nearest[i]="0001"; break;
	        case '2': nearest[i]="0010"; break;
	        case '3': nearest[i]="0011"; break;
	        case '4': nearest[i]="0100"; break;
	        case '5': nearest[i]="0101"; break;
	        case '6': nearest[i]="0110"; break;
	        case '7': nearest[i]="0111"; break;
	        case '8': nearest[i]="1000"; break;
	        case '9': nearest[i]="1001"; break;
	        case 'A': nearest[i]="1010"; break;
	        case 'B': nearest[i]="1011"; break;
	        case 'C': nearest[i]="1100"; break;
	        case 'D': nearest[i]="1101"; break;
	        case 'E': nearest[i]="1110"; break;
	        case 'F': nearest[i]="1111"; break;
	        case 'X': nearest[i]="xxxx"; break;
	        default : break;
	    	   }
	     	//System.out.print(Integer.toBinaryString(Character.digit(line.charAt(i),16)));
	        }

			RenderMap renderMap = new RenderMap(this);
			setContentView(renderMap);
			renderMap.requestFocus();
			
	}
	
	private String getNearest9(String maze) {
		int i = currentPos;
		String nearest9 = Character.toString(maze.charAt(i-sqr-1))+Character.toString(maze.charAt(i-sqr))+Character.toString(maze.charAt(i-sqr+1))+Character.toString(maze.charAt(i-1))+Character.toString(maze.charAt(i))+Character.toString(maze.charAt(i+1))+Character.toString(maze.charAt(i+sqr-1))+Character.toString(maze.charAt(i+sqr))+Character.toString(maze.charAt(i+sqr+1));
		return nearest9;
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
    protected void onResume() {
       super.onResume();
       // Start updates for one or more sensors
       sensorList = mgr.getSensorList(Sensor.TYPE_ALL);
       for (Sensor sensor : sensorList) {
          mgr.registerListener(this, sensor,
                  SensorManager.SENSOR_DELAY_GAME);
       }
    }

    @Override
    protected void onPause() {
       super.onPause();
       // Stop updates to save power while app paused
       mgr.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		StringBuilder builder = new StringBuilder();
	      Sensor sensor = event.sensor;
	      if(sensor.getType()== Sensor.TYPE_ACCELEROMETER)
	      {
	    	  //Toast.makeText(getBaseContext(), "Entered Accelerometer", Toast.LENGTH_SHORT).show();
	    	  
	    	  /*
	    	  /////////////////////////////////////////////////////////////////////////////
	    	  ///							For testing Acc								///					
	    	  /////////////////////////////////////////////////////////////////////////////	    	  
	    	  builder.append("O: "+lastOX+" | "+lastOY+" | Base"+baseOX);
	    	  builder.append("\nCurrently pointing to: "+lastDir);	//This should be your directionPointer
	    	  builder.append(" \n ");
		      builder.append("Time: ");
		      builder.append((event.timestamp)/1000000000);			//Now in seconds
		      builder.append(",");
		      builder.append(lastTime);
		      builder.append("\nCurrent Acc: ");
		      builder.append(event.values[1]);
		      //*/
	    	  
	    	  //Is the Phone tilted? Should I remove the G effect?
		      if(lastOY<0 && lastOY>-90)
		      {
		    	  trueAVal=event.values[1]+Math.sin(lastOY*Math.PI/180)*10;	//Removing G from Acc
		       	  builder.append("\nTrue Acc: "+trueAVal);
		      }
		      
		      //For this second, what's the Highest Acc value? Check & Update
		      if((int)(trueAVal*1000)>thisAHighest)
		      {
		    	  thisAHighest = (int)(trueAVal*1000);						//Update highest value in some interval
		      }
		      
		      //Runs every second. Why this silly way to do this? Because I CAN. \m/
		      if(event.timestamp/1000000000 > lastTime)						//After every second
		      {
		    	  lastTime = event.timestamp/1000000000;
		    	  
		    	  /////////////////////////////////////////////////////////////////////////////////////////////////
		    	  // MY SUPER COOL ALGO HERE //////////////////////////////////////////////////////////////////////
		    	  		checkJump();	///////////////////////////////////////////////////////////////////////////
		    	  //        ^		///////////////////////////////////////////////////////////////////////////////
		    	  //   THAT IS IT!	///////////////////////////////////////////////////////////////////////////////
		    	  /////////////////////////////////////////////////////////////////////////////////////////////////		    	  
		    	  
		    	  lastAHighest = thisAHighest;		    	  
		    	  thisAHighest = 0;
		    	  
		      }
		      
		      /*builder.append("\nHighest Value: \n");
		      builder.append(lastAHighest);
		      builder.append(" | ");
		      builder.append(thisAHighest);*/
		     /* 
		      if(isJump)
		      {
		    	  builder.append("\nJumped!");
		    	  //Check if the Jump is valid/hit the walls
		    	  //If valid, update pos & redraw
		    	  //Else if hit, show colored wall
		      }
		      else
		    	  builder.append("\nDidn't Jump!");
		    	  */
		      /*builder.append("\njS:"+jumpSet+" \n");
		      builder.append(showmsg);
		      output.setText(builder);*/
	      } else if(sensor.getType()== Sensor.TYPE_ORIENTATION)	//Checking Angles etc here
	      {
	    	  if(event.values[0]<360)
	    		  lastOX=(int)event.values[0];
	    	  if(event.values[1]<0 && event.values[1]>-90)
	    		  lastOY=(int)event.values[1];
	    	  
	    	  if(baseOX>400)									//Base value not set
				{
				  if(lastOX<45)
					  baseOX=lastOX+315;
				  else
					  baseOX=lastOX-45;
				  directionPointer=0;
				  //Toast.makeText(getBaseContext(), "Base: "+directionPointer+" "+baseOX, Toast.LENGTH_SHORT).show();
				}
	    	  
	    	  int newDir;
	    	  if(lastOX<baseOX)
	    	  {
	    		  newDir = (lastOX+360-baseOX)/90;
	    	  } else
	    	  {
	    		  newDir= (lastOX - baseOX)/90;
	    	  }
	    	  if(newDir!=directionPointer)
	    	  {
	    		  directionPointer=newDir;
	    		  processView();
	    		  //Trigger event for 90 degree direction change
	    	  }
	      }/* else if(sensor.getType()== Sensor.TYPE_PROXIMITY)	//If you want to do fancy stuff with proximity
	      {
	    	  if(lastOX<45)
	    		  baseOX=lastOX+315;
	    	  else
	    		  baseOX=lastOX-45;
	    	  lastDir=0;
	      }*/
		
	}
	
	private void checkJump() {
		isJump = false;
		if(jumpSet == 1)						/////// Restarted
		{
			if(thisAHighest < ACC_MIN_THR)
			{
				jumpSet = 0;
				showmsg = "Stay Still";
			} else {
				showmsg = "HOLD STILL";
			}
		} else if(jumpSet == 0)					/////// Ask to wait
		{
			if(thisAHighest < ACC_MIN_THR)
			{
				jumpSet = -1;
				showmsg = "JUMP NOW";
			} else {
				jumpSet = 1;
				showmsg = "Hold Still Ra!";
			}
		} else if(jumpSet == -1)				/////// Allow jumping
		{
			if(thisAHighest > ACC_MAX_THR)
			{
				jumpSet = 5;
				showmsg = "In Air";
			} else if(thisAHighest > ACC_MID_THR){
				//jumpSet = 1;
				showmsg="Jump Harder again";				
			} else if(thisAHighest < ACC_MIN_THR)
			{
				showmsg = "Jump jump!";
			} 
		} else if(jumpSet == 5)  				/////// In Air. 
		{
			
			if(thisAHighest<ACC_MIN_THR)
			{
				jumpNow();
				//jump
			} else if(thisAHighest>ACC_MAX_THR)
			{
				jumpSet++;
				showmsg = "Flying?";				
			} else if(thisAHighest<ACC_MID_THR)
			{
				jumpSet = 4;
				showmsg = "STOOOP!";
			}
		} else if(jumpSet == 4)  				/////// Slow motion falling on the ground case
		{
			if(thisAHighest > ACC_MID_THR)
			{
				jumpSet = 1;
				showmsg = "Try again";
			} else if(thisAHighest < ACC_MIN_THR)
			{
				jumpNow();
				//jump
			}
		} else if(jumpSet == 6)   				/////// Slow motion flying in air case
		{
			if(thisAHighest < ACC_MIN_THR)
			{
				jumpNow();
				//jump
			}
			else {
				jumpSet = 1;
				showmsg = "STOP FLYIIING!";
			}
		}
	}
	
	private void jumpNow() {
		jumpSet = 1;
		showmsg = "YAY!!!!";
		isJump = true;
		
		checkWall();
	}
	
	private void checkWall() {
		//if(directionPointer
		//processView();
		Toast.makeText(getBaseContext(), "Wall check", Toast.LENGTH_SHORT).show();
			if(nearest[4].charAt(directionPointer)=='0')
			//if(nearest[4].charAt(0)=='0')
			{	
				stepsTaken++;
				if(directionPointer==0)
				{
					if(currentPos==sqr+1)
					{//Toast.makeText(getBaseContext(), "Yoyo Game Over!!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(this, GameOver.class);
						
						Bundle myBundle = new Bundle();
						myBundle.putInt ("stepsTaken", stepsTaken);
						i.putExtras(myBundle);

			          	startActivity(i);
					}
					else
						currentPos-=sqr;
				}					
				else if(directionPointer==1)
					currentPos+=1;
				else if(directionPointer==2)
					currentPos+=sqr;
				else if(directionPointer==3)
					currentPos-=1;
				
				if(currentPos!=sqr+1)
				Toast.makeText(getBaseContext(), "Jumped", Toast.LENGTH_SHORT).show();
				processView();				
			}
			else
			{
				Toast.makeText(getBaseContext(), "Hit wall", Toast.LENGTH_SHORT).show();				
			}
			
			
			//	nearest9
		
	}
}
