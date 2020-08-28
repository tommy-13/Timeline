package tl.optionScreen;

import tl.basic.GameGlobals;
import tl.basic.StateOfGame;

public class PlayerFieldConstants implements FieldConstants {
	
	private static final int width	= 370;
	private static final int height = GameGlobals.OPTION_FIELD_HEIGHT;
	private static final int yGap 	= 10;
	
	private int playerNr;
	
	
	public PlayerFieldConstants(int playerNr) {
		this.playerNr = playerNr;
	}
	
	
	
	@Override
	public int getFieldX() {
		return GameGlobals.SCREEN_WIDTH - GameGlobals.OPTION_VIEW_SIDEGAP - width;
	}

	@Override
	public int getFieldY() {
		return GameGlobals.OPTION_VIEW_TOPGAP + (playerNr + 2) * (height + yGap);
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
		return "Spieler " + (playerNr + 1) + ":";
	}

	@Override
	public String getDrawingTextBack() {
		return StateOfGame.getInstance().getPlayerName(playerNr);
	}

	@Override
	public int getBackTextOffset() {
		return 185;
	}

}
