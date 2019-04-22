package com.assignment2.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.newdawn.slick.opengl.Texture;

public class GameObject {
	public double x, y, width, height, r, g, b;
	private double initialX, initialY;
	public int xDirection = 1, yDirection = 1;
	public Texture texture;
	private GameFunctions gFunc = new GameFunctions();
	
	public GameObject(double x, double y, double width, double height, double r, double g, double b) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.r = r;
		this.g = g;
		this.b = b;
		this.initialX = x;
		this.initialY = y;
	}
	
	public GameObject(double x, double y, double width, double height, double r, double g, double b, String texName) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.r = r;
		this.g = g;
		this.b = b;
		this.initialX = x;
		this.initialY = y;
		texture = gFunc.loadTexture(texName); 
	}
	
	public void draw(){
		glPushMatrix();
		
		glTranslated(x, y, 0);
		
		if(texture != null){
			texture.bind();
			glBegin(GL_QUADS);
			glColor3d(r, g, b);
			glTexCoord2f(0, 1);
			glVertex2d(-(width / 2), 0); //Bottom-Left
			glTexCoord2f(1, 1);
			glVertex2d((width / 2), 0);// Bottom-Right
			glTexCoord2f(1, 0);
			glVertex2d((width / 2), -height);// Upper-Right
			glTexCoord2f(0, 0);
			glVertex2d(-(width / 2), -height);// Upper-Left
			glEnd();
		}else{
			glBindTexture(GL_TEXTURE_2D, 0);
			glBegin(GL_QUADS);
			glColor3d(r, g, b);
			glVertex2d(-(width / 2), 0);
			glVertex2d((width / 2), 0);
			glVertex2d((width / 2), -height);
			glVertex2d(-(width / 2), -height);
			glEnd();
		}
		
		glPopMatrix();
	}
	
	public void animate(int startX, int endX, double speed, boolean local){
		if(local){
			startX += (int)initialX;
			endX += (int)initialX;
		}
		if(xDirection == 1){
			if(x < endX)
				x += speed;
			else
				xDirection = -1;
		}else{
			if(x > startX)
				x -= speed;
			else
				xDirection = 1;
		}		
	}
	
	public void animate(int startX, int endX, int startY, int endY, double speed, boolean local){
		if(local){
			startX += (int)initialX;
			endX += (int)initialX;
			
			startY = (int)initialY - startY;
			endY = (int)initialY - endY;
		}
		
		//Handle the X-Axis animation
		if(xDirection == 1){
			if(x < endX)
				x += speed;
			else
				xDirection = -1;
		}else{
			if(x > startX)
				x -= speed;
			else
				xDirection = 1;
		}
		
		//Handle the Y-Axis animation
		if(yDirection == 1){
			if(y > endY)
				y -= speed;
			else
				yDirection = -1;
		}else{
			if(y < startY)
				y += speed;
			else
				yDirection = 1;
		}	
	}
	
}
