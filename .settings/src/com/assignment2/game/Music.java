package com.assignment2.game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music extends Thread {
	private Clip clip;
	//private	File path = new File("UpbeatFunk.wav");
	public String audioname;
	boolean loop;
//	public File path1 = new File("Blueboma_Action-160.wav");
	public Music(String audioname, boolean loop){
		this.audioname = audioname;
		this.loop = loop;
	}
	public void run() {
		try{
			URL a = ClassLoader.getSystemResource(audioname + ".wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(a);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			if(loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(clip.getMicrosecondLength() / 1000);
		}catch(IOException e){
			System.out.println("Error retrieving music file.");
			e.printStackTrace();
		
		 } catch (Exception e) {
			System.out.println("Error to play music.");
			e.printStackTrace();
		}
	}
}
