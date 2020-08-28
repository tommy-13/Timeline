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

public class NrCardsLabel extends GraphicObject {
	
	private int width;
	private int height;
	private int textHeight;
	
	private Label	lblCards;
	private Label	lblLaid;
	private Label	nrCards;
	private Label	lblRemaining;
	private Label	nrRemaining;
	
	
	public NrCardsLabel(int y, Font font) {
		
		this.x = GameGlobals.LEFT_SPACE;
		this.y = y;
		
		textHeight = font.getLineHeight();
		height		= 5 * textHeight;
		width		= font.getWidth("wwwwwwwwwwww");
		int right	= x + width;
		lblCards	= new Label(x + width/2, y, "Karten", font, Label.CENTER);
		lblLaid		= new Label(x + 5, y + textHeight, "gelegt", font, Label.LEFT);
		nrCards = new Label(right - 5, y + 2 * textHeight, "", font, Label.RIGHT);
		lblRemaining = new Label(x + 5, y + 3 * textHeight, "verbleibend", font, Label.LEFT);
		nrRemaining = new Label(right - 5, y + 4 *textHeight, "", font, Label.RIGHT);
		
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
		g.drawLine(x, y + 3* textHeight, x+width, y + 3 * textHeight);
		
		lblCards.draw(g);
		lblLaid.draw(g);
		nrCards.draw(g);
		lblRemaining.draw(g);
		nrRemaining.draw(g);
	}

	@Override
	public void update(InputState inputState, int delta) {
		StateOfGame settings = StateOfGame.getInstance();
		nrCards.setText("" + settings.getNrOfLyingCards());
		nrRemaining.setText("" + settings.getNrOfRemainingCards());
	}

	
	
}
