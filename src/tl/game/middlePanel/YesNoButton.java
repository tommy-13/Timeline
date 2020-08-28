package tl.game.middlePanel;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import tl.basic.GameColor;
import tl.basic.InputState;
import tl.basic.graphics.GraphicObject;

/**
 * button in a game
 * @author tommy
 *
 */

public class YesNoButton extends GraphicObject {
	
	public static final int WIDTH	= 240;
	public static final int HEIGHT	= 40;
	
	private boolean clicked = false;
	private boolean mouseEntered = false;
	
	private Font font;
	private String text;
	private int textX, textY;
	boolean yes;
	

	public YesNoButton(int x, int y, boolean yes, Font font) {
		super(x,y);
		this.yes = yes;
		this.text = yes ? "Weiter" : "Neues Spiel";
		this.font = font;
		textX = x + (WIDTH - font.getWidth(text)) / 2;
		textY = y + (HEIGHT - font.getLineHeight()) / 2;
		
		collisionMask = new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	
	public String getLetter() {
		return text;
	}
	

	@Override
	public void draw(Graphics g) {
		if(mouseEntered) {
			if(yes) {
				g.setColor(GameColor.BUTTON_CONTINUE_ENTERED);
			}
			else {
				g.setColor(GameColor.BUTTON_NEWGAME_ENTERED);
			}
		}
		else {
			if(yes) {
				g.setColor(GameColor.BUTTON_CONTINUE_NORMAL);
			}
			else {
				g.setColor(GameColor.BUTTON_NEWGAME_NORMAL);
			}
		}
		g.fillRect(x, y, WIDTH, HEIGHT);

		g.setColor(GameColor.BASIS_WRITTING);
		g.drawRect(x, y, WIDTH, HEIGHT);
		g.setFont(font);
		g.drawString(text, textX, textY);
	}

	
	@Override
	public void update(InputState inputState, int delta) {
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		
		
		if(mouseEntered) {
			if(mouseClicked) {
				clicked = true;
			}
		}
	}
	
	
	/**
	 * check if the mouse button was previously released
	 * @return true / false
	 */
	public boolean isClicked() {
		return clicked;
	}
	
	
	/**
	 * to be executed after button was released
	 */
	public void resetClicked() {
		clicked = false;
	}

}
