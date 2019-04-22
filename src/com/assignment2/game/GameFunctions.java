package com.assignment2.game;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class GameFunctions {

	public Texture loadTexture(String img){
		Texture tmpTex = null;
		try {
			tmpTex = TextureLoader.getTexture("PNG", ClassLoader.getSystemResourceAsStream(img + ".png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpTex;
	}

}
