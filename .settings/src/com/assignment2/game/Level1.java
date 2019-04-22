package com.assignment2.game;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

public class Level1 {
	public static Player player;
//	public static ArrayList<CollisionObject> obs = new ArrayList<>();
	
	// Set short variables to the Display / Screen
	private static int dw;
	private static int dh;
	
	private static GameFunctions gFunc = new GameFunctions();
	private static Texture groundTex;
	
	public static void main(String args[]) throws Exception {
		if(!Display.isCreated()){
			Display.setDisplayMode(new DisplayMode(800, 480));
			Display.create();
		}
		Display.setTitle("Level 1");
		dw = Display.getWidth();
		dh = Display.getHeight();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		groundTex = gFunc.loadTexture("ground-test");
		
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
		
		TrueTypeFont font;
		font = new TrueTypeFont(awtFont, false);
		
		player = new Player(60, 200, 30, 30, 1, 1, 1, "player_character");
		player.name = args[0];
		player.points = Integer.parseInt(args[1]);
		player.lives = Integer.parseInt(args[2]);
		ArrayList<CollisionObject> enemies = new ArrayList<>();
		enemies.add(new CollisionObject(120, dh - 50, 30, 30, 1, 1, 1, "enemy"));
		
		ArrayList<CollisionObject> obs = new ArrayList<>();
		// Top right platform - END
		obs.add(new CollisionObject(dw - 120, 140, 120, 30, 1, 1, 1, "stone-platform"));
		// First platform - bottom left
		obs.add(new CollisionObject(100, dh - 100, 120, 30, 1, 1, 1, "stone-platform"));
		// Middle
		obs.add(new CollisionObject(250, dh - 200, 120, 30, 1, 1, 1, "stone-platform"));
		obs.add(new CollisionObject(550, dh - 200, 120, 30, 1, 1, 1, "stone-platform"));
		
		CollisionObject end = new CollisionObject(dw - 120, 100, 30, 30, 1, 1, 1, "treasure");
		
		ArrayList<Coin> coins = new ArrayList<>();
//		coins.add(new Coin(dw - 120, 100, 10, 10, 1, 1, 0));
		coins.add(new Coin(230, dh - 240, 10, 10, 1, 1, 0));
		coins.add(new Coin(250, dh - 240, 10, 10, 1, 1, 0));
		coins.add(new Coin(270, dh - 240, 10, 10, 1, 1, 0));
		
		for(int i = 0; i < 15; i++){
			coins.add(new Coin(240 + (20 * i), dh - 60, 10, 10, 1, 1, 0));
		}
		
		while(!Display.isCloseRequested()){
			setCamera();
			drawBackground();
			end.draw();
			if(end.isCollided(player)){
				args[1] = String.valueOf(player.points);
				args[2] = String.valueOf(player.lives);
				Level2.main(args);
			}
			player.draw();
			if(!player.isAlive && player.y > dh + 50)
				player.resetPosition();
			for(CollisionObject co : obs){
				co.draw();
				co.checkCollision(player);
			}
			
			for(Coin c : coins){
				if(!c.collected){
					c.animate(0, 0, 0, 10, 0.25f, true);
					c.draw();
					if(c.isCollided(player)){
						c.collect(player);
					}
				}
			}
			
			for(CollisionObject enemy : enemies){
				enemy.draw();
				enemy.animate(0, 200, 1, true);
				if(enemy.isCollided(player) && player.isAlive){
					if(player.yspeed > 0){
						enemy.y = -10;
						player.yspeed = -4;
						player.points += 5;
					}else{
						player.kill();
					}
				}
			}
			
			font.drawString(0, 0, "Score: " + player.points, Color.black);
			font.drawString(0, 30, "Player: " + player.name, Color.black);
			font.drawString(0, 60, "Lives: " + player.lives, Color.black);
			
			TextureImpl.bindNone();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
	public static void setCamera(){
		glClear(GL_COLOR_BUFFER_BIT);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void drawBackground(){
		//Drawing the sky
		glBegin(GL_QUADS);
		
		glColor3d(0.7, 0.8, 0.9);
		glVertex2d(dw, dh);
		glVertex2d(0, dh);
		
		glColor3d(0.6, 0.7, 0.8);
		glVertex2d(0, 0);
		glVertex2d(dw, 0);
		
		glEnd();
		
		//Ground
		groundTex.bind();
		glBegin(GL_QUADS);
		
		glColor3d(1, 1, 1);
		glTexCoord2f(1, 1);
		glVertex2d(dw, dh);
		
		glTexCoord2f(0, 1);
		glVertex2d(0, dh);
		
		glTexCoord2f(0, 0);
		glVertex2d(0, dh - 50);
		
		glTexCoord2f(1, 0);
		glVertex2d(dw, dh - 50);
		
		glEnd();
	}
}