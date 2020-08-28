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
import tl.model.Player;

/**
 * button in a game
 * @author tommy
 *
 */

public class PlayerScoreLabel extends GraphicObject {
	
	private int width;
	private int height;
	
	private Player	player;
	private Label	playerName;
	private Label	playerScore;
	
	
	public PlayerScoreLabel(int y, Player player, Font font) {

		this.player = player;
		
		this.x = GameGlobals.LEFT_SPACE;
		this.y = y;
		
		int textHeight = font.getLineHeight();
		height		= 2 * textHeight;
		width		= font.getWidth("wwwwwwwwwwww");
		int right	= x + width;
		playerName	= new Label(x + 5, y, player.getName(), font, Label.LEFT);
		playerScore = new Label(right - 5, y + textHeight, "" + player.getPoints(), font, Label.RIGHT);
		
		collisionMask = new Rectangle(x, y, width, height);
	}
	
	public int getWidth() {
		return width;
	}
	

	@Override
	public void draw(Graphics g) {
		if(player.getId() == StateOfGame.getInstance().getCurrentPlayer().getId()) {
			g.setColor(GameColor.PLAYERLABEL_MARKED);
		}
		else {
			g.setColor(GameColor.PLAYERLABEL_UNMARKED);
		}
		g.fillRect(x, y, width, height);
		g.setColor(GameColor.BASIS_WRITTING);
		g.drawRect(x, y, width, height);
		
		playerName.draw(g);
		playerScore.draw(g);
	}

	@Override
	public void update(InputState inputState, int delta) {
		playerName.setText(player.getName());
		playerScore.setText("" + player.getPoints());
	}

	
	
}
