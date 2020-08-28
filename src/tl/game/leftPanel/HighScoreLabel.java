package tl.game.leftPanel;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import tl.basic.GameColor;
import tl.basic.GameGlobals;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.basic.graphics.GraphicObject;
import tl.basic.graphics.Label;

/**
 * button in a game
 * @author tommy
 *
 */

public class HighScoreLabel extends GraphicObject {
	
	private int width;
	private int height;
	private int textHeight;
	
	private Label	highscore;
	private Label	playerName;
	private Label	playerScore;
	
	
	public HighScoreLabel(int y, Font font) {
		
		this.x = GameGlobals.LEFT_SPACE;
		this.y = y;
		
		textHeight = font.getLineHeight();
		height		= 3 * textHeight;
		width		= font.getWidth("wwwwwwwwwwww");
		int right	= x + width;
		highscore	= new Label(x + width / 2, y, "Highscore", font, Label.CENTER);
		playerName	= new Label(x + 5, y + textHeight, "", font, Label.LEFT);
		playerScore = new Label(right - 5, y + 2* textHeight, "" + "", font, Label.RIGHT);
		
		collisionMask = new Rectangle(x, y, width, height);
	}
	
	public int getWidth() {
		return width;
	}
	

	@Override
	public void draw(Graphics g) {
		g.setColor(GameColor.HIGHSCORE_LABEL);
		g.fillRect(x, y, width, height);
		g.setColor(GameColor.BASIS_WRITTING);
		g.drawRect(x, y, width, height);
		g.drawLine(x, y + textHeight, x+width, y + textHeight);
		
		highscore.draw(g);
		playerName.draw(g);
		playerScore.draw(g);
	}

	@Override
	public void update(InputState inputState, int delta) {
		StateOfGame settings = StateOfGame.getInstance();
		playerName.setText(settings.getHigscorePlayer());
		playerScore.setText("" + settings.getHighscore());
	}

	
	
}
