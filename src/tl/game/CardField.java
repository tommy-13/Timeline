package tl.game;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import tl.basic.GameGlobals;
import tl.basic.InputState;
import tl.basic.graphics.GraphicObject;
import tl.model.CardModel;

/**
 * button in a game
 * @author tommy
 *
 */

public abstract class CardField extends GraphicObject {
	
	protected int					cardId;
	protected List<DrawableWord> 	cardText;
	protected boolean				visible;
	protected Font					fontId;
	protected Font   				fontText;
	
	protected int width, height;
	protected int idXRelative, idYRelative;
	
	
	
	public CardField(int x, int y, CardModel cardModel, Font fontId, Font fontText) {
		cardText = new LinkedList<DrawableWord>();
		this.visible	= false;
		this.fontId		= fontId;
		this.fontText	= fontText;
		
		this.width  = GameGlobals.CARD_WIDTH;
		this.height = GameGlobals.CARD_HEIGHT;
		this.x = x;
		this.y = y;
		collisionMask = new Rectangle(x, y, width, height);
		
		setCardModel(cardModel);
	}
	
	public void setCardModel(CardModel cm) {
		if(cm == null) {
			visible = false;
			cardText.clear();
		}
		else {
			visible = true;
			cardId = cm.getId();
			cardText = GameGlobals.getLeftAlignedText(
					fontText, 5, 5, GameGlobals.CARD_WIDTH-10, cm.getEvent());
			idXRelative = width - fontId.getWidth("" + cardId) - 2;
			idYRelative = height - fontId.getLineHeight() + 2;
		}
	}
	
	public int getCardId() {
		return cardId;
	}

	

	@Override
	public abstract void draw(Graphics g);

	@Override
	public abstract void update(InputState inputState, int delta);

}
