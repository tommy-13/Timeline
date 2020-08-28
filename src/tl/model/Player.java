package tl.model;

import tl.basic.StateOfGame;

public class Player {
	
	public static final int MAX_PLAYER_NAME_LENGTH = 10;
	
	private int		id;
	private String	name;
	private int		points;
	
	
	public Player(int id, String name) {
		this.id		= id;
		this.name	= name;
		this.points = 0;
	}

	public void reset() {
		points = 0;
	}
	
	
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	public int getPoints() {
		return points;
	}
	public void addPoints(int points) {
		this.points += points;
		StateOfGame stateOfGame = StateOfGame.getInstance();
		if(this.points > stateOfGame.getHighscore()) {
			stateOfGame.setHighscore(this.points, name);
			stateOfGame.safe();
		}
	}

}
