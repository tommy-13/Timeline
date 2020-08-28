package tl.game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import tl.basic.GameGlobals;
import tl.basic.GameWindow;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.game.leftPanel.LeftPanel;
import tl.game.middlePanel.MiddlePanel;
import tl.game.rightPanel.RightPanel;

public class GameView extends BasicGameState implements GameState {
	
	private LeftPanel	leftPanel;
	private MiddlePanel middlePanel;
	private RightPanel	rightPanel;
	
	
	public GameView() {
		super();
	}
	
	
	public void startNewGame() {
		StateOfGame.getInstance().startNewGame();
		middlePanel.startNewGame();
		rightPanel.startNewGame();
		StateOfGame.getInstance().startNewGame();
	}
	

	

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {

		this.leftPanel  = new LeftPanel();
		leftPanel.init(gameContainer, stateBasedGame);
		this.middlePanel = new MiddlePanel(this, leftPanel.getRightPosition());
		middlePanel.init(gameContainer, stateBasedGame);
		this.rightPanel = new RightPanel(this, leftPanel.getRightPosition() + GameGlobals.MIDDLE_PANEL_WIDTH);
		rightPanel.init(gameContainer, stateBasedGame);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		leftPanel.render(gc, sbg, g);
		middlePanel.render(gc, sbg, g);
		rightPanel.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
			throws SlickException {
		
		Input input = gameContainer.getInput();
		InputState inputState = InputState.getInstance();
		
		inputState.setMousePosition(input.getMouseX(), input.getMouseY());
		inputState.mouseButtonLeft.setMouseState(input.isMousePressed(Input.MOUSE_LEFT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));
		inputState.mouseButtonRight.setMouseState(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON));
		inputState.setMouseWheel(Mouse.getDWheel());
		inputState.setKeyDown(input.isKeyPressed(Input.KEY_DOWN));
		inputState.setKeyUp(input.isKeyPressed(Input.KEY_UP));
		
		leftPanel.update(gameContainer, stateBasedGame, delta);
		middlePanel.update(gameContainer, stateBasedGame, delta);
		rightPanel.update(gameContainer, stateBasedGame, delta);
	}

	@Override
	public int getID() {
		return GameWindow.GAME;
	}


	public void notifyCardSelection(int cardId) {
		rightPanel.setSelectedCard(cardId);
	}


	public void notifyCardPutDown(boolean orderCorrect) {
		middlePanel.replaceSelectedCard();
		StateOfGame stateOfGame = StateOfGame.getInstance();
		stateOfGame.decreaseRemainingCards();

		if(orderCorrect) {
			// add points, next player
			stateOfGame.getCurrentPlayer().addPoints(getPlayerPoints());
			if(stateOfGame.areThereCardsLeft()) {
				stateOfGame.setNextPlayer();
			}
			else {
				showYears(true);
				middlePanel.lockCardSelection(true, false);
			}
		}
		else {
			// show years, wait for user input, then go on
			showYears(true);
			middlePanel.lockCardSelection(false, stateOfGame.areThereCardsLeft());
		}
	}
	
	private int getPlayerPoints() {
		int cards = StateOfGame.getInstance().getNrOfLyingCards();
		switch(cards) {
		case 0:
			return 0;
		case 1: case 2: case 3: case 4: case 5:
			return 1;
		case 6: case 7: case 8:	case 9:
			return 2;
		case 10: case 11: case 12: case 13:
			return 3;
		case 14: case 15: case 16:
			return 4;
		case 17: case 18: case 19:
			return 5;
		case 20: case 21: case 22:
			return 6;
		case 23: case 24:
			return 7;
		case 25: case 26:
			return 8;
		case 27: case 28:
			return 9;
		case 29: case 30:
			return 10;
		default: return cards - 20;
		}
	}
	
	public void showYears(boolean show) {
		rightPanel.showYears(show);
	}


	public void ContinueGame() {
		rightPanel.clearCardList();
		rightPanel.showYears(false);
		StateOfGame.getInstance().setNextPlayer();
	}

}
