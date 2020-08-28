package tl.basic;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import tl.game.GameView;
import tl.model.Player;
import tl.optionScreen.OptionView;

public class TimelineStarter extends StateBasedGame {
	
	private final static String gameName = "Timeline";

	
	public TimelineStarter() throws SlickException {
		super(gameName);

		/* create folder if necessary */
		new File("Kartensätze").mkdir();
		new File("safe").mkdir();
		
		GameGlobals.setConfiguration();
		CardFiles.getInstance().setCurrentFile(0);
		StateOfGame.getInstance().load();
		
		/* player */
		Player[] players = new Player[GameGlobals.MAX_PLAYER];
		for(int i=0; i<players.length; i++) {
			players[i] = null;
		}
		
		
		/* add states (screens) */
		this.addState(new OptionView());
		this.addState(new GameView());
	}
	

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		gameContainer.setShowFPS(false);
		
		/* initialise screens */
		this.getState(GameWindow.GAME).init(gameContainer, this);
		this.getState(GameWindow.OPTION_SCREEN).init(gameContainer, this);
		
		/* set first screen */
		this.enterState(GameWindow.OPTION_SCREEN);
	}

	
	
	public static void main(String[] args) {
		
		/* create window */
		AppGameContainer appGameContainer;
		try {
			appGameContainer = new AppGameContainer(new TimelineStarter());
			appGameContainer.setIcon("res/spr/icon32.png");
			appGameContainer.setDisplayMode(GameGlobals.SCREEN_WIDTH, GameGlobals.SCREEN_HEIGHT, false);
			appGameContainer.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
