package com.assignment2.game;

public class CollisionObject extends GameObject {
	public CollisionObject(double x, double y, double width, double height, double r, double g, double b) {
		super(x, y, width, height, r, g, b);
		// TODO Auto-generated constructor stub
	}
	
	public CollisionObject(double x, double y, double width, double height, double r, double g, double b, String texName) {
		super(x, y, width, height, r, g, b, texName);
	}
	
	public boolean isCollided(GameObject g){
		return (Math.abs(x - g.x) <= (width / 2) + (g.width / 2) && Math.abs((y - height) - (g.y - g.height)) < Math.max(height, g.height));
	}
	
	public int getCollidedSide(Player g){
		if(Math.abs(x - g.x) <= (width / 2) + (g.width / 2) && Math.abs((y - height) - (g.y - g.height)) < Math.max(height, g.height)){
			if(g.y <= y){
				return 1;
			}else if(g.x + (g.width / 2) >= x + (width / 2) ){
				return 2;
			}else if(g.x + (g.width / 2) <= x - (width / 2)){
				return -2;
			}else{
				return -1;
			}
		}else{
			return 0; 
		}
	}
	
	public void checkCollision(Player p){
		double xStarting = x - (width / 2);
		double xEnding = x + (width / 2);
		
		if(p.x + (p.width / 2) >= xStarting && p.x - (p.width / 2) <= xEnding && p.y > y - height && p.y - p.height <= y){
			p.OnCollisionStart(this);
		}else{
			if(this == p.currentContact)
				p.OnCollisionEnd(this);
		}
		
	}

}
