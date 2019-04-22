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

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

public class MainMenu extends Thread{
	// Set short variables to the Display / Screen
		private static int dw;
		private static int dh;
		private static Texture texture;
		
		public static void main(String args[]) throws Exception {
			if(!Display.isCreated()){
				Display.setDisplayMode(new DisplayMode(800, 480));
				Display.create();
			}
			Display.setTitle("Main Menu");
			dw = Display.getWidth();
			dh = Display.getHeight();
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			GameFunctions gFunc = new GameFunctions();
		    texture = gFunc.loadTexture("HomeScreen-test");
			while(!Display.isCloseRequested()){
				setCamera();
				drawBackground();
				
				while(Keyboard.next()){
					if(Keyboard.getEventKey() == Keyboard.KEY_1){
						if(Keyboard.getEventKeyState()){
							String name = JOptionPane.showInputDialog("Enter your name:");
							texture = gFunc.loadTexture("loading-test");
							drawBackground();
							if(args.length == 0){
								Music music = new Music("UpbeatFunk", true);
								music.start();
							}
							Display.update();
							String arr[] = new String[]{name, "0", "2"};
							Level1.main(arr);
						}
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_2){
						if(Keyboard.getEventKeyState()){
							texture = gFunc.loadTexture("loading-test");
							drawBackground();
							Display.update();
							ShowLeaderboard();
						}
					}
					
					if(Keyboard.isKeyDown(Keyboard.KEY_3)){
						System.exit(0);
					}
				}
				Display.update();
				Display.sync(60);
			}
			Display.destroy();
			System.exit(0);
		}
		
		private static void ShowLeaderboard() {
			// TODO Auto-generated method stub
			try {
				Leaderboard.main(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if(texture != null){
				texture.bind();
				glBegin(GL_QUADS);
				glColor3d(1, 1, 1);
				glTexCoord2f(1, 1);
				glVertex2d(dw, dh);
				glTexCoord2f(0, 1);
				glVertex2d(0, dh);
				glTexCoord2f(0, 0);
				glVertex2d(0, 0);
				glTexCoord2f(1, 0);
				glVertex2d(dw, 0);
				
				glEnd();
			}
		}
}
