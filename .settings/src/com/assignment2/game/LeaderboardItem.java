package com.assignment2.game;

public class LeaderboardItem implements Comparable<LeaderboardItem> {
	private String name;
	private Integer score;
	public LeaderboardItem(String name, int score) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.score = score;
	}
	@Override
	public int compareTo(LeaderboardItem o) {
		// TODO Auto-generated method stub
		return score.compareTo(o.score);
	}
	
	public String toString(){
		return "Name: " + name + " Score: " + score;
	}

}
