package com.assignment2.game;

import java.io.FileWriter;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Player extends GameObject {
	public String name;
	public double xspeed, yspeed, currentFloor;
	private double initX, initY;
	public boolean jumpPressed, jumpWasPressed, grounded;
	public int jumpsLeft, points = 0, lives = 2;
	public CollisionObject currentContact = null;
	
	private int dh = Display.getHeight();
	private int dw = Display.getWidth();
	
	public boolean isAlive = true;
	
	public Player(double x, double y, double width, double height, double r, double g, double b) {
		super(x, y, width, height, r, g, b);
		xspeed = 1;
		// TODO Auto-generated constructor stub
	}
	
	public Player(double x, double y, double width, double height, double r, double g, double b, String texName) {
		super(x, y, width, height, r, g, b, texName);
		initX = x;
		initY = y;
	}

	public void logic(){
		// Movement
		x += xspeed;
		y += yspeed;
		
		// Apply gravity
		yspeed += 0.4;
		
		// Limit the y-speed
		if(yspeed >= 7.0f)
			yspeed = 7.0f;
		
		if(isAlive){
			// Limit the X axis
			x = Math.max(0, Math.min(dw, x));
			
			// Either collision or limitations
			if(y >= dh - 50){
				grounded = true;
				currentFloor = dh - 50;
			}
			
			// If grounded
			if(grounded){
				// Reset jumps left
				jumpsLeft = 2;
				yspeed = 0;
				// Avoiding loop-pressed
				if(jumpPressed && !jumpWasPressed){
					grounded = false;
				}else
					y = currentFloor;
				if(!isLeftDown())
					xspeed = xspeed*0.7;
				
				if(!isRightDown() && xspeed > 0)
					xspeed = xspeed*0.7;
			}
			
			if(jumpPressed && !jumpWasPressed && jumpsLeft-- > 0){
				y -= 4;
				yspeed = -7;
				grounded = false;
			}
			
			if(isLeftDown())
				xspeed = Math.max(-4, xspeed - 1);
			
			if(isRightDown())
				xspeed = Math.min(4, xspeed + 1);
				
			jumpWasPressed = jumpPressed;
			jumpPressed = isJumpDown();
		}
	}
	
	public void kill(){
		isAlive = false;
		yspeed = -8;
		
		if(lives > 0){
			lives --;
		}else{
			GameOver();
		}
	}
	
	public void draw(){
		logic();
		super.draw();
	}
	
	public void OnCollisionStart(CollisionObject col){
		currentContact = col;
		applyCollision(currentContact.getCollidedSide(this));
	}
	
	public void OnCollisionEnd(CollisionObject col){
		if(currentContact != null && y + 2 >= currentContact.y + currentContact.height && y + height <= currentContact.y && x + (width / 2) <= currentContact.x - (col.width / 2) && x - (width / 2) >= currentContact.x + (currentContact.width / 2)){
			y = currentContact.y - currentContact.height;
		}
		else{
			grounded = false;
			currentContact = null;
		}
	}
	
	public void applyCollision(int side){
		// If there's no contact or the player is dead, don't apply the function
		if(currentContact == null || !isAlive)
			return;
		
		// Distance between it's Y position and height
		double d = currentContact.y - currentContact.height;
		// Where the X starts
		double startingX = currentContact.x - (currentContact.width / 2);
		// Where the X ends
		double endingX = currentContact.x + (currentContact.width / 2);
		
		switch (side) {
		// UP
		case 1:
			grounded = true;
			currentFloor = d;
			break;
			
		// RIGHT	
		case 2:
			grounded = false;
			x = endingX + (width / 2);
			break;
			
		// LEFT	
		case -2:
			grounded = false;
			x = startingX;
			break;
			
		// DOWN	
		case -1:
			grounded = false;
			if(yspeed < 0.0f && y >= currentContact.y)
				yspeed *= -1;
			break;

		default:
			if(y < dh - 50 || side == 0){
				grounded = false;
			}
			break;
		}
	}
	
	public boolean isRightDown(){
		return (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) | Keyboard.isKeyDown(Keyboard.KEY_D));
	}
	
	public boolean isLeftDown(){
		return (Keyboard.isKeyDown(Keyboard.KEY_LEFT) | Keyboard.isKeyDown(Keyboard.KEY_A));
	}
	
	public boolean isJumpDown(){
		return (Keyboard.isKeyDown(Keyboard.KEY_UP) | Keyboard.isKeyDown(Keyboard.KEY_W) | Keyboard.isKeyDown(Keyboard.KEY_SPACE));
	}

	public void resetPosition() {
		// TODO Auto-generated method stub
		x = initX;
		y = initY;
		xspeed = 0;
		yspeed = 0;
		isAlive = true;
	}
	
	public void GameOver(){
		// Save player's score and name to a file
		try(FileWriter fw = new FileWriter("leaderboard.txt", true)){
			fw.write(name + ";" + points + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
			Leaderboard.main(new String[]{"ALIVE"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
