package tl.optionScreen;

import tl.basic.GameGlobals;

public class PlayFieldConstants implements FieldConstants {
	
	private static final int width 	= 290;
	private static final int height = GameGlobals.OPTION_FIELD_HEIGHT;
	private static final int yGap 	= 10;
	
	
	public PlayFieldConstants() {
	}
	
	
	@Override
	public int getFieldX() {
		return GameGlobals.OPTION_VIEW_SIDEGAP;
	}

	@Override
	public int getFieldY() {
		return GameGlobals.OPTION_VIEW_TOPGAP + (GameGlobals.MAX_PLAYER + 1) * (GameGlobals.OPTION_FIELD_HEIGHT + yGap);
	}

	@Override
	public int getFieldWidth() {
		return width;
	}

	@Override
	public int getFieldHeight() {
		return height;
	}

	@Override
	public String getDrawingTextFront() {
		return "";
	}

	@Override
	public String getDrawingTextBack() {
		return "Spiel starten";
	}


	@Override
	public int getBackTextOffset() {
		return 42;
	}

}
