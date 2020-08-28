package tl.optionScreen;

import tl.basic.GameGlobals;
import tl.basic.StateOfGame;

public class PlayerNumberFieldConstants implements FieldConstants {
	
	private static final int width = 290;
	private static final int height = GameGlobals.OPTION_FIELD_HEIGHT;
	private static final int yGap  = 10;
	
	
	public PlayerNumberFieldConstants() {
	}
	
	
	@Override
	public int getFieldX() {
		return GameGlobals.OPTION_VIEW_SIDEGAP;
	}

	@Override
	public int getFieldY() {
		return GameGlobals.OPTION_VIEW_TOPGAP + 2 * (height + yGap);
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
		return "Anzahl Spieler:";
	}

	@Override
	public String getDrawingTextBack() {
		return "" + StateOfGame.getInstance().getNumberOfPlayers();
	}


	@Override
	public int getBackTextOffset() {
		return 260;
	}

}
