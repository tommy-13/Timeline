package tl.game;

public class DrawableWord {
	
	private String	text;
	private int		xPos;
	private int		yPos;
	
	public DrawableWord(int xPos, int yPos, String text) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
	}
	
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	public String getText() {
		return text;
	}
}
