package tl.basic;

public class InputState {
	
	private static InputState uniqueInputState = new InputState();
	public static InputState getInstance() {
		return uniqueInputState;
	}

	public MouseState mouseButtonLeft;
	public MouseState mouseButtonRight;
	
	private int mousePositionX;
	private int mousePositionY;
	
	private boolean mouseWheelDown;
	private boolean mouseWheelUp;
	
	private boolean keyDown;
	private boolean keyUp;
	
	
	private InputState() {
		mouseButtonLeft		= new MouseState();
		mouseButtonRight	= new MouseState();
		mousePositionX		= -1;
		mousePositionY		= -1;
		keyDown				= false;
		keyUp				= false;
	}
	
	public void reset() {
		mousePositionX = -1;
		mousePositionY = -1;
		mouseButtonLeft.reset();
		mouseButtonRight.reset();
		keyDown = false;
		keyUp	= false;
	}
	
	
	public void setMousePosition(int x, int y) {
		mousePositionX = x;
		mousePositionY = y;
	}
	
	public int getMouseX() {
		return mousePositionX;
	}
	
	public int getMouseY() {
		return mousePositionY;
	}

	public void setMouseWheel(int dWheel) {
		mouseWheelDown = false;
		mouseWheelUp = false;
		if(dWheel < 0) {
			mouseWheelDown = true;
		}
		if(dWheel > 0) {
			mouseWheelUp = true;
		}
	}
	
	public boolean getMouseWheelDown() {
		return mouseWheelDown;
	}
	public boolean getMouseWheelUp() {
		return mouseWheelUp;
	}
	
	
	public void setKeyDown(boolean pressed) {
		keyDown = pressed;
	}
	public void setKeyUp(boolean pressed) {
		keyUp = pressed;
	}
	public boolean isKeyDownPressed() {
		return keyDown;
	}
	public boolean isKeyUpPressed() {
		return keyUp;
	}
	
}
