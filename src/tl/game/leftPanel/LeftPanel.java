package tl.game.leftPanel;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import tl.basic.GameColor;
import tl.basic.GameGlobals;
import tl.basic.GameWindow;
import tl.basic.GameFont;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.basic.graphics.ArrowButton;
import tl.game.GameViewPanel;

public class LeftPanel implements GameViewPanel {

	private int rightPosition;
	
	private HighScoreLabel		highscoreLabel;
	private NrCardsLabel		nrOfCardsLabel;
	private PlayerScoreLabel[]	playerScoreLabel;
	private int					numberOfPlayer;
	private Font				fontInfo;
	private ArrowButton			backButton;
	private Rectangle			background;
	
	
	public LeftPanel() {
		numberOfPlayer = 1;
	}
	
	
	public int getRightPosition() {
		return rightPosition;
	}
	
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		/* fonts */
		fontInfo = new GameFont(GameFont.FONT16);
		
		/* labels */
		highscoreLabel = new HighScoreLabel(136, fontInfo);
		nrOfCardsLabel = new NrCardsLabel(10, fontInfo);
		
		playerScoreLabel = new PlayerScoreLabel[GameGlobals.MAX_PLAYER];
		StateOfGame settings = StateOfGame.getInstance();
		for(int i=0; i<GameGlobals.MAX_PLAYER; i++) {
			playerScoreLabel[i] = new PlayerScoreLabel(210 + i * 52, settings.getPlayer(i), fontInfo);
		}
		
		/* background */
		rightPosition = playerScoreLabel[0].getX() * 2 + playerScoreLabel[0].getWidth();
		background = new Rectangle(0, 0, rightPosition, GameGlobals.SCREEN_HEIGHT);
		
		/* sprites */
		Image backNormal = new Image("res/spr/leftArrowNormal.png");
		Image backRollover = new Image("res/spr/leftArrowRollover.png");
		backButton = new ArrowButton(0, GameGlobals.SCREEN_HEIGHT - 32, backNormal, backRollover);
		
		/* error label */
//		errorLabel = new Label(GameGlobals.SCREEN_WIDTH/2, GameGlobals.SCREEN_HEIGHT - 36,
//				"Fehler", fontError, Label.CENTER);
		
		/* sound */
//		moveStone = new Sound("res/sound/moveStone.wav");

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.setColor(GameColor.LEFT_PANEL);
		g.fill(background);

		highscoreLabel.draw(g);
		nrOfCardsLabel.draw(g);
		for(int i=0; i<numberOfPlayer; i++) {
			playerScoreLabel[i].draw(g);
		}
		
		backButton.draw(g);
		
	}

	@Override
	public void update(GameContainer gameContainer,
			StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		InputState inputState = InputState.getInstance();
		
		StateOfGame settings = StateOfGame.getInstance();
		numberOfPlayer = settings.getNumberOfPlayers();
		
		
		highscoreLabel.update(inputState, delta);
		nrOfCardsLabel.update(inputState, delta);
		for(int i=0; i<GameGlobals.MAX_PLAYER; i++) {
			playerScoreLabel[i].update(inputState, delta);
		}
		
		backButton.update(inputState, delta);
		if(backButton.isClicked()) {
			backButton.resetClicked();
			stateBasedGame.enterState(GameWindow.OPTION_SCREEN, GameGlobals.transitionOut(), GameGlobals.transitionIn());
		}
		
	}

}
