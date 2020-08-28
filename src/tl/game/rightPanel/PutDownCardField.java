package tl.game.rightPanel;

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

public class PutDownCardField extends CardField {
	
	private int 	cardYear;
	private boolean showYear = false;
	private int		yearOffsetX, yearOffsetY;
	private Font	fontYear;

	
	public PutDownCardField(int x, int y, CardModel cardModel, Font fontId, Font fontText, Font fontYear) {
		super(x, y, cardModel, fontId, fontText);
		cardYear = cardModel.getYear();
		this.fontYear = fontYear;
		yearOffsetX = (width - fontYear.getWidth("" + cardYear)) / 2;
		yearOffsetY = (height - fontYear.getLineHeight()) / 2;
	}

	

	@Override
	public void draw(Graphics g) {
		if(visible) {
			g.setColor(GameColor.CARD_NORMAL);

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
			
			
			if(showYear) {
				g.setColor(GameColor.CARD_YEAR);
				g.setFont(fontYear);
				g.drawString("" + cardYear, x + yearOffsetX, y + yearOffsetY);
			}
		}
	}

	@Override
	public void update(InputState inputState, int delta) {}



	public int getCardYear() {
		return cardYear;
	}



	public void showYear(boolean show) {
		showYear = show;
	}

}
