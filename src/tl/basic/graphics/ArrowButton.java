package tl.basic.graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import tl.basic.InputState;

/**
 * button in a game
 * @author tommy
 *
 */

public class ArrowButton extends GraphicObject {
	
	private Image imgNormal;
	private Image imgEntered;
	private Image imgActive;
	
	private boolean clicked = false;
	
	
	public ArrowButton(int x, int y, Image imgNormal, Image imgEntered) {
		super(x,y);
		
		this.imgNormal  = imgNormal;
		this.imgEntered = imgEntered;
		imgActive = imgNormal;
		
		collisionMask = new Rectangle(x, y, imgActive.getWidth(), imgActive.getHeight());
	}

	

	@Override
	public void draw(Graphics g) {
		imgActive.draw(x,y);
	}

	@Override
	public void update(InputState inputState, int delta) {
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		boolean mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		
		
		if(mouseEntered) {
			imgActive = imgEntered;
			if(mouseClicked) {
				clicked = true;
			}
		}
		else {
			imgActive = imgNormal;
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
