package com.assignment2.game;

public class Coin extends CollisionObject {
	
	public boolean collected = false;
	
	public Coin(double x, double y, double width, double height, double r, double g, double b) {
		super(x, y, width, height, r, g, b, "coin");
		// TODO Auto-generated constructor stub
	}
	
	public void draw(){
		if(!collected)
			super.draw();
	}
	public void collect(Player player){
		y = -10;
		Music coinSound = new Music("coin", false);
		coinSound.start();
		player.points ++;
		collected = true;
	}

}
