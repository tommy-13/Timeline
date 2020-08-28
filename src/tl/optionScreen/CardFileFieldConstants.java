package tl.optionScreen;

import tl.basic.CardFiles;
import tl.basic.GameGlobals;

public class CardFileFieldConstants implements FieldConstants {
	
	private static final int width = GameGlobals.SCREEN_WIDTH - 2 * GameGlobals.OPTION_VIEW_SIDEGAP;
	private static final int height = GameGlobals.OPTION_FIELD_HEIGHT;
	
	
	public CardFileFieldConstants() {
	}
	
	
	@Override
	public int getFieldX() {
		return GameGlobals.OPTION_VIEW_SIDEGAP;
	}

	@Override
	public int getFieldY() {
		return GameGlobals.OPTION_VIEW_TOPGAP;
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
		return "Kartenauswahl:";
	}

	@Override
	public String getDrawingTextBack() {
		return CardFiles.getInstance().getCurrentFileName();
	}


	@Override
	public int getBackTextOffset() {
		return 250;
	}

}
