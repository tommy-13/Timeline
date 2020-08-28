package tl.game.rightPanel;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import tl.basic.GameColor;
import tl.basic.InputState;
import tl.basic.graphics.GraphicObject;

/**
 * button in a game
 * @author tommy
 *
 */

public class InterCardArrow extends GraphicObject {
	
	public static int HEIGHT = 30;
	public static int WIDTH	 = 30;
	
	private Polygon triangle;
	private boolean mouseEntered	= false;
	private boolean wasClicked		= false;
	private boolean visible			= true;
	
	
	public InterCardArrow(int x, int y) {
		this.x = x;
		this.y = y;
		
		float[] points = {0f, 0f, 0f, HEIGHT, WIDTH, HEIGHT/2};
		triangle = new Polygon(points);
		collisionMask = triangle.copy();
		setPosition(x, y);
	}
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
		triangle.setLocation(x, y);
	}
	
	public void setYPosition(int y) {
		setY(y);
		triangle.setLocation(x, y);
	}

	

	@Override
	public void draw(Graphics g) {
		if(!visible) {
			return;
		}
		
		if(mouseEntered) {
			g.setColor(GameColor.INTERARROW_ENTERED);
		}
		else {
			g.setColor(GameColor.INTERARROW_NORMAL);
		}

		g.fill(triangle);
		g.setColor(GameColor.BASIS_WRITTING);
		g.draw(triangle);
	}

	@Override
	public void update(InputState inputState, int delta) {
		if(!visible) {
			return;
		}
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		boolean mouseRightClicked = inputState.mouseButtonRight.isClicked();
		
		
		if(mouseEntered) {
			if(mouseClicked) {
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
