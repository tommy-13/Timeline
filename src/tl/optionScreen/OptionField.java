package tl.optionScreen;

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

public class OptionField extends GraphicObject {
	
	private int width, height, textY;
	private boolean mouseEntered = false;
	
	private Font   font;
	
	private ClickBehaviour clickBehaviour;
	private FieldConstants fieldConstants;
	
	
	public OptionField(ClickBehaviour clickBehaviour, FieldConstants fieldConstants, Font font) {
		this.clickBehaviour = clickBehaviour;
		this.fieldConstants = fieldConstants;
		
		this.font = font;
		
		this.width  = fieldConstants.getFieldWidth();
		this.height = fieldConstants.getFieldHeight();
		this.x = fieldConstants.getFieldX();
		this.y = fieldConstants.getFieldY();
		this.textY = y + (height - font.getLineHeight()) / 2;
		
		collisionMask = new Rectangle(x, y, width, height);
	}

	

	@Override
	public void draw(Graphics g) {
		if(mouseEntered) {
			g.setColor(GameColor.OPTIONFIELD_ENTERED);
		}
		else {
			g.setColor(GameColor.OPTIONFIELD_NORMAL);
		}

		g.fillRect(x, y, width, height);
		g.setColor(GameColor.BASIS_WRITTING);
		g.drawRect(x, y, width, height);
		
		g.setFont(font);

		g.drawString(fieldConstants.getDrawingTextFront(), x+10, textY);
		g.drawString(fieldConstants.getDrawingTextBack(), x+fieldConstants.getBackTextOffset(), textY);
	}

	@Override
	public void update(InputState inputState, int delta) {
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		boolean mouseRightClicked = inputState.mouseButtonRight.isClicked();
		
		
		if(mouseEntered) {
			if(mouseClicked) {
				clickBehaviour.leftClicked();
			}
			else if(mouseRightClicked) {
				clickBehaviour.rightClicked();
			}
		}
	}

}
