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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class Leaderboard {
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
		    texture = gFunc.loadTexture("LeaderboardScreen");
		    ArrayList<LeaderboardItem> leaderboards = new ArrayList<>();
		    
		    Font awtFont = new Font("Times New Roman", Font.PLAIN, 28);
			
			TrueTypeFont font;
			font = new TrueTypeFont(awtFont, false);
			System.out.println("Leaderboard---");
			try {
				FileReader fr = new FileReader("leaderboard.txt");
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine()) != null){
					String parts[] = line.split("\\;");
					leaderboards.add(new LeaderboardItem(parts[0], Integer.parseInt(parts[1])));
					Display.update();
				}
				br.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Play first so you can view the leaderbaord !");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Collections.sort(leaderboards, Collections.reverseOrder());
			
			for(LeaderboardItem l : leaderboards){
				System.out.println(l);
			}
		    
			while(!Display.isCloseRequested()){
				setCamera();
				drawBackground();
				
				while(Keyboard.next()){
					if(Keyboard.getEventKey() == Keyboard.KEY_Q){
						if(Keyboard.getEventKeyState()){
							MainMenu.main(new String[]{"ALIVE"});
						}
					}
				}
				
				if(leaderboards.size() > 0){
					int lines = 0;
					for(LeaderboardItem l : leaderboards){
						// Show only the latest 5 (n-1)
						if(lines <= 4){
							font.drawString((dw / 2) - 140, 160 + (30 * lines),(lines + 1) + " - " +  l.toString());
							lines ++;
						}
					}
				}
				
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
