package tl.game.middlePanel;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import tl.basic.GameColor;
import tl.basic.InputState;
import tl.game.CardField;
import tl.game.DrawableWord;
import tl.model.CardModel;

/**
 * button in a game
 * @author tommy
 *
 */

public class ClickableCardField extends CardField {
	
	private boolean		selected		= false;
	private boolean 	mouseEntered 	= false;
	private boolean		wasClicked		= false;
	
	
	
	public ClickableCardField(int x, int y, CardModel cardModel, Font fontId, Font fontText) {
		super(x, y, cardModel, fontId, fontText);
	}
	
	public void unselect() {
		this.selected = false;
	}

	

	@Override
	public void draw(Graphics g) {
		if(visible) {
			if(selected) {
				g.setColor(GameColor.CARD_SELECTED);
			}
			else {
				if(mouseEntered) {
					g.setColor(GameColor.CARD_ENTERED);
				}
				else {
					g.setColor(GameColor.CARD_NORMAL);
				}
			}

			g.fillRect(x, y, width, height);
			g.setColor(GameColor.BASIS_WRITTING);
			g.drawRect(x, y, width, height);
			
			g.setFont(fontText);
			for(DrawableWord dw : cardText) {
				g.drawString(dw.getText(), x + dw.getX(), y + dw.getY());
			}

			g.setColor(GameColor.CARD_ID);
			g.setFont(fontId);
			g.drawString("" + cardId,  x + idXRelative, y + idYRelative);
		}
	}

	@Override
	public void update(InputState inputState, int delta) {
		
		if(!visible || selected) {
			return;
		}
		
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		boolean mouseRightClicked = inputState.mouseButtonRight.isClicked();
		
		
		if(mouseEntered) {
			if(mouseClicked) {
				selected = true;
				wasClicked = true;
			}
			else if(mouseRightClicked) {
			}
		}
	}
	
	public boolean wasClicked() {
		return wasClicked;
	}
	public void resetClicked() {
		wasClicked = false;
	}

}
