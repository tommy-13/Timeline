package tl.optionScreen;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import tl.basic.CardFiles;
import tl.basic.GameColor;
import tl.basic.GameFont;
import tl.basic.GameGlobals;
import tl.basic.GameWindow;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.basic.graphics.ArrowButton;
import tl.basic.graphics.Label;
import tl.game.GameView;
import tl.model.DataBase;
import tl.model.Player;

public class OptionView extends BasicGameState implements GameState {
	
	private OptionField[]	playerLabel;
	private OptionField		playerNumberLabel;
	private OptionField		cardFileLabel;
	private OptionField		playLabel;
	
	/* background */
	private Rectangle background;
	
	/* fonts */
	private Font fontInfo;
	private Font fontOptionField;
	private Font fontNewPlayerHead;
	private Font fontNewPlayerKeys;
	private Font fontError;
	
	/* new player creation */
	private ArrowButton	backButton;
	private boolean		creatingNewPlayer	= false;
	private int    		inputPlayerId 		= -1;
	private String 		inputPlayerName		= "";
	private TextButton[] letterButtons;
	private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
		"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "<", "OK"};
	
	/* error */
	private Label errorLabel;
	private int   errorCounter = 0;

	
	public OptionView() {
		super();
	}


	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		/* background */
		background = new Rectangle(0, 0, GameGlobals.SCREEN_WIDTH, GameGlobals.SCREEN_HEIGHT);
		
		/* fonts */
		fontOptionField = new GameFont(GameFont.FONT26);
		fontNewPlayerHead = new GameFont(GameFont.FONT48B);
		fontNewPlayerKeys = new GameFont(GameFont.FONT40);
		fontError = new GameFont(GameFont.FONT24);
		
		/* fields */
		cardFileLabel = new OptionField(new CardFileFieldClickBehaviour(),
				new CardFileFieldConstants(), fontOptionField);
		playerNumberLabel = new OptionField(new PlayerNumberFieldClickBehaviour(),
				new PlayerNumberFieldConstants(), fontOptionField);
		playerLabel = new OptionField[GameGlobals.MAX_PLAYER];
		for(int i=0; i<playerLabel.length; i++) {
			playerLabel[i] = new OptionField(new PlayerFieldClickBehaviour(this, i), new PlayerFieldConstants(i), fontOptionField);
		}
		playLabel = new OptionField(new PlayFieldClickBehaviour(this), new PlayFieldConstants(), fontOptionField);
		
		/* letters */
		letterButtons = new TextButton[28];
		for(int i=0; i<26; i++) {
			int row = i / 7;
			int col = i % 7;
			int x = 180 + col * (TextButton.WIDTH + TextButton.XGAP);
			int y = 170 + row * (TextButton.HEIGHT + TextButton.YGAP);
			letterButtons[i] = new TextButton(x, y,	letters[i], fontNewPlayerKeys);
		}
		int x = (int) (180 + 7.5 * (TextButton.WIDTH + TextButton.XGAP));
		letterButtons[26] = new TextButton(x, 170, letters[26], fontNewPlayerKeys, 140, TextButton.HEIGHT);
		int y = 170 + TextButton.HEIGHT + TextButton.YGAP;
		letterButtons[27] = new TextButton(x, y, letters[27], fontNewPlayerKeys, 140, TextButton.HEIGHT);
		
		Image backNormal = new Image("res/spr/leftArrowNormal.png");
		Image backRollover = new Image("res/spr/leftArrowRollover.png");
		backButton = new ArrowButton(0, GameGlobals.SCREEN_HEIGHT - 32, backNormal, backRollover);
		
		/* error */
		errorLabel = new Label(GameGlobals.SCREEN_WIDTH/2, GameGlobals.SCREEN_HEIGHT - 36,
				"Fehler", fontError, Label.CENTER);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame,
			Graphics g) throws SlickException {
		
		/* background */
		g.setColor(GameColor.OPTIONVIEW_BACKGROUND);
		g.fill(background);
		
		if(creatingNewPlayer) {
			g.setColor(GameColor.BASIS_WRITTING);
			g.setFont(fontNewPlayerHead);
			g.drawString("Spieler umbenennen:",
					(GameGlobals.SCREEN_WIDTH - fontNewPlayerHead.getWidth("Spieler umbenennen::")) / 2, 10);
			
			g.setColor(GameColor.NEW_PLAYER_NAME);
			g.drawString(inputPlayerName,
					(GameGlobals.SCREEN_WIDTH - fontNewPlayerHead.getWidth(inputPlayerName)) / 2, 70);
			
			g.setColor(GameColor.BASIS_WRITTING);
			g.setFont(fontNewPlayerKeys);
			for(TextButton tb : letterButtons) {
				tb.draw(g);
			}
			
			backButton.draw(g);
		}
		else {
			g.setColor(GameColor.BASIS_WRITTING);
			g.setFont(fontNewPlayerHead);
			g.drawString("Einstellungen",
					(GameGlobals.SCREEN_WIDTH - fontNewPlayerHead.getWidth("Einstellungen")) / 2, 30);
			
			
			if(fontInfo != null) {
				g.setFont(fontInfo);
			}

			playerNumberLabel.draw(g);
			for(OptionField mpc : playerLabel) {
				mpc.draw(g);
			}
			cardFileLabel.draw(g);
			playLabel.draw(g);
		}

		
		if(errorCounter > 0) {
			g.setFont(fontError);
			errorLabel.draw(g);
		}
		
	}

	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame,
			int delta) throws SlickException {

		Input input = gameContainer.getInput();
		InputState inputState = InputState.getInstance();
		inputState.setMousePosition(input.getMouseX(), input.getMouseY());
		inputState.mouseButtonLeft.setMouseState(input.isMousePressed(Input.MOUSE_LEFT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));
		inputState.mouseButtonRight.setMouseState(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON));
		
		
		/* esc: end the game */
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}
		
		
		if(errorCounter > 0) {
			errorCounter -= delta;
		}
		

		if(startGame) {
			startGame = false;
			catchInput(input);
			((GameView) stateBasedGame.getState(GameWindow.GAME)).startNewGame();
			stateBasedGame.enterState(GameWindow.GAME, GameGlobals.transitionOut(), GameGlobals.transitionIn());
			return;
		}

		
		if(creatingNewPlayer) {
			
			for(TextButton tb : letterButtons) {
				tb.update(inputState, delta);
				
				if(tb.isClicked()) {
					tb.resetClicked();
					if(handleLetter(tb.getLetter())) {
						creatingNewPlayer = false;
						errorCounter = 0;
						inputPlayerId = -1;
					}
					return;
				}
			}
			
			backButton.update(inputState, delta);
			if(backButton.isClicked()) {
				backButton.resetClicked();
				inputPlayerId = -1;
				creatingNewPlayer = false;
				errorCounter = 0;
			}
		}
		else { 
			// !creatingNewPlayer
			playerNumberLabel.update(inputState, delta);
			for(OptionField mpc : playerLabel) {
				mpc.update(inputState, delta);
			}
			cardFileLabel.update(inputState, delta);
			playLabel.update(inputState, delta);
		}
		
		
		catchInput(input);
	}
	
	public void catchInput(Input input) {
		input.isKeyPressed(Input.KEY_LEFT);
		input.isKeyPressed(Input.KEY_UP);
		input.isKeyPressed(Input.KEY_HOME);
		input.isKeyPressed(Input.KEY_PRIOR);
		input.isKeyPressed(Input.KEY_RIGHT);
		input.isKeyPressed(Input.KEY_DOWN);
		input.isKeyPressed(Input.KEY_END);
		input.isKeyPressed(Input.KEY_NEXT);
		Mouse.getDWheel();
	}
	
	public void changePlayerName(int playerId) {
		creatingNewPlayer = true;
		errorCounter = 0;
		inputPlayerId = playerId;
		inputPlayerName = StateOfGame.getInstance().getPlayerName(inputPlayerId);
	}
	
	
	

	@Override
	public int getID() {
		return GameWindow.OPTION_SCREEN;
	}
	

	private boolean handleLetter(String letter) {
		int len = inputPlayerName.length();
		
		if(letter.equals(letters[26])) { // <
			inputPlayerName = (len <= 1) ? "" : inputPlayerName.substring(0, len-1);
		}
		else if(letter.equals(letters[27])) { // ok
			if(inputPlayerName.length() == 0) {
				errorCounter = GameGlobals.ERROR_TEXT_TIME;
				errorLabel.setText("Bitte gib einen Namen ein.");
				return false;
			}
			
			if(StateOfGame.getInstance().getPlayerName(inputPlayerId).equals(inputPlayerName)) {
				return true;
			}
			
			if(StateOfGame.getInstance().hasPlayerName(inputPlayerName)) {
				errorCounter = GameGlobals.ERROR_TEXT_TIME;
				errorLabel.setText("Dieser Name existiert bereits.");
				return false;
			}
			createNewPlayer();
			return true;
		}
		else {
			if(len < Player.MAX_PLAYER_NAME_LENGTH) {
				inputPlayerName += len == 0 ? letter : letter.toLowerCase();
			}
			else {
				errorCounter = GameGlobals.ERROR_TEXT_TIME;
				errorLabel.setText("Es sind maximal " + Player.MAX_PLAYER_NAME_LENGTH + " Buchstaben möglich.");
			}
		}
		return false;
	}
	
	private void createNewPlayer() {
		StateOfGame settings = StateOfGame.getInstance();
		settings.setPlayerName(inputPlayerId, inputPlayerName);
		settings.safe();
	}



	boolean startGame = false;
	public void handlePlayButtonClick() {
		String path = CardFiles.getInstance().getCurrentFilePath();
		if(path == null) {
			errorCounter = GameGlobals.ERROR_TEXT_TIME;
			errorLabel.setText("Kein gültiger Kartensatz ausgewählt.");
			return;
		}
		
		startGame = DataBase.getInstance().load(path);
		if(!startGame) {
			errorCounter = GameGlobals.ERROR_TEXT_TIME;
			errorLabel.setText("Kein gültiger Kartensatz ausgewählt.");
		}
		else {
			errorCounter = 0;
		}
	}


}
