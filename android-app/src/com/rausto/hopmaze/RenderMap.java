package com.rausto.hopmaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

@SuppressWarnings("unused")

public class RenderMap extends View {
   private final Physics physics;
   private float width;    // width of one tile
   private float height;   // height of one tile
   private int selX; 
   private int selY;
   private int length,shift;
   private final Rect selRect = new Rect();
	public RenderMap(Context context) {

      super(context);
      this.physics = (Physics) context;
      setFocusable(true);
      setFocusableInTouchMode(true);
    }

	@Override
    protected void onDraw(Canvas canvas) {
       // Draw the background...
       Paint background = new Paint();
       background.setColor(getResources().getColor(R.color.black));
       length=getWidth()-10;
       //shift=(getHeight()-getWidth())/2+5;
       shift = 10;
       
       canvas.drawRect(0, 0, length, length, background);
    
       //Define colors for the grid lines
       Paint dark = new Paint();
       dark.setColor(getResources().getColor(R.color.white));
       dark.setStrokeWidth(7);
   		
       int pointer=Physics.directionPointer;
       
       for (int i=0,j=0,k=0;i<9;i++)
       {	
    	   j = (i%3); k = (i/3);
    	   if(Character.toString(Physics.nearest[i].charAt(0)).equals("x"))
    	   {
    		   canvas.drawLine(length/3*j+5, length/3*k+shift, length/3*(j+1)+5, length/3*(k+1)+shift,dark);
    		   canvas.drawLine(length/3*(j+1)+5, length/3*k+shift, length/3*j+5, length/3*(k+1)+shift,dark);
    	   }
    	   else
    	   {
	    	   if(Character.toString(Physics.nearest[i].charAt(pointer%4)).equals("1"))    		
	    	   canvas.drawLine(length/3*j+5, length/3*k+shift, length/3*(j+1)+5, length/3*k+shift, dark);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+1)%4)).equals("1"))
	    	   canvas.drawLine(length/3*(j+1)+5, length/3*k+shift, length/3*(j+1)+5, length/3*(k+1)+shift, dark);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+2)%4)).equals("1"))
	    	   canvas.drawLine(length/3*j+5, length/3*(k+1)+shift, length/3*(j+1)+5, length/3*(k+1)+shift, dark);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+3)%4)).equals("1"))
	    	   canvas.drawLine(length/3*j+5, length/3*k+shift, length/3*j+5, length/3*(k+1)+shift, dark);
    	   }
       }
       
     //Define colors for the grid lines
       Paint top = new Paint();
       top.setColor(getResources().getColor(R.color.black));
       top.setStrokeWidth(3);
       
       for (int i=0,j=0,k=0;i<9;i++)
       {	
    	   j = (i%3); k = (i/3);
	    	   if(Character.toString(Physics.nearest[i].charAt(pointer%4)).equals("1"))    		
	    	   canvas.drawLine(length/3*j+5, length/3*k+shift, length/3*(j+1)+5, length/3*k+shift, top);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+1)%4)).equals("1"))
	    	   canvas.drawLine(length/3*(j+1)+5, length/3*k+shift, length/3*(j+1)+5, length/3*(k+1)+shift, top);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+2)%4)).equals("1"))
	    	   canvas.drawLine(length/3*j+5, length/3*(k+1)+shift, length/3*(j+1)+5, length/3*(k+1)+shift, top);
	    	   if(Character.toString(Physics.nearest[i].charAt((pointer+3)%4)).equals("1"))
	    	   canvas.drawLine(length/3*j+5, length/3*k+shift, length/3*j+5, length/3*(k+1)+shift, top);
       }
       
       Paint arrow= new Paint();
       arrow.setColor(getResources().getColor(R.color.white));
       arrow.setStrokeWidth(3);
       canvas.drawLine(length/2+5, length/2-10+shift, length/2-10+5, length/2+shift+10, arrow);
       canvas.drawLine(length/2-10+5, length/2+shift+10, length/2+10+5, length/2+shift+10, arrow);
       canvas.drawLine(length/2+10+5, length/2+shift+10, length/2+5, length/2-10+shift, arrow);
       
       Paint text= new Paint();
       text.setColor(getResources().getColor(R.color.white));
       text.setTextSize(15);
       canvas.drawText("Steps : "+Physics.stepsTaken, 5, getHeight()/2+length/2+5, text);
       canvas.drawText("Level : "+Physics.diffLevel, getWidth()/2 - 15, getHeight()/2+length/2+5, text);
       
       
	}
}