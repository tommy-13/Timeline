package tl.game.middlePanel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import tl.basic.GameColor;
import tl.basic.GameFont;
import tl.basic.GameGlobals;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.basic.graphics.Label;
import tl.game.GameView;
import tl.game.GameViewPanel;
import tl.model.CardModel;
import tl.model.DataBase;

public class MiddlePanel implements GameViewPanel {
	
	private GameView	gameView;
	private int			absolutX;

	private Rectangle 				background;
	private ClickableCardField[]	cardField;
	private List<Integer>			randomList;
	private int						selectedCardArrayNr;
	
	private boolean orderCorrect 		= true;
	private boolean areThereMoreCards 	= true;

	private Label		lblWrongAnswer;
	private Label		lblGameOver;
	private YesNoButton btContinue;
	private YesNoButton btNewGame;
	
	
	
	public MiddlePanel(GameView gameView, int absolutX) {
		this.gameView = gameView;
		
		this.absolutX = absolutX;
		cardField = new ClickableCardField[GameGlobals.NUMBER_OF_CARDS];
		randomList = new LinkedList<Integer>();
		selectedCardArrayNr = -1;
	}
	
	
	public void startNewGame() {
		randomList.clear();
		randomList.add(1);
		Random rand = new Random();
		for(int i=2; i<=StateOfGame.getInstance().getNrOfRemainingCards(); i++) {
			int pos = rand.nextInt(randomList.size() + 1);
			randomList.add(pos, i);
		}
		
		for(int i=0; i<GameGlobals.NUMBER_OF_CARDS; i++) {
			setNewCard(i);
		}
		
		orderCorrect = true;
		areThereMoreCards = true;
	}
	
	public void setNewCard(int nr) {
		if(randomList.isEmpty()) {
			cardField[nr].setCardModel(null);
		}
		else {
			DataBase dataBase = DataBase.getInstance();
			CardModel cm = dataBase.getCardModel( randomList.get(0) );
			randomList.remove(0);
			cardField[nr].setCardModel(cm);
			cardField[nr].unselect();
		}
		
		selectedCardArrayNr = -1;
	}
	
	public void replaceSelectedCard() {
		setNewCard(selectedCardArrayNr);
	}
	
	
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		/* fonts */
		Font fontCardId			 = new GameFont(GameFont.FONT10B);
		Font fontCardText		 = new GameFont(GameFont.FONT13);
		Font fontInfoMiddleLabel = new GameFont(GameFont.FONT32);
		
		/* background */
		background = new Rectangle(absolutX, 0, GameGlobals.MIDDLE_PANEL_WIDTH, GameGlobals.SCREEN_HEIGHT);
		
		/* cards */
		int left = absolutX + 32;
		int top  = 40;
		for(int i=0; i<cardField.length; i++) {
			cardField[i] = new ClickableCardField(
					left + (i % GameGlobals.CARD_PER_ROW) * (GameGlobals.CARD_WIDTH + 30),
					top + (i / GameGlobals.CARD_PER_ROW) * (GameGlobals.CARD_HEIGHT + 20),
					null,
					fontCardId,
					fontCardText);
		}
		
		
		/* for wrong answer / no more cards */
		int buttonX = absolutX + (GameGlobals.MIDDLE_PANEL_WIDTH - YesNoButton.WIDTH) / 2;
		lblWrongAnswer = new Label(
				absolutX + GameGlobals.MIDDLE_PANEL_WIDTH/2,
				GameGlobals.SCREEN_HEIGHT/2 - fontInfoMiddleLabel.getLineHeight() * 2,
				"Falsch!", fontInfoMiddleLabel, Label.CENTER);
		lblGameOver = new Label(
				absolutX + GameGlobals.MIDDLE_PANEL_WIDTH/2,
				GameGlobals.SCREEN_HEIGHT/2 - fontInfoMiddleLabel.getLineHeight(),
				"Karten weg -> Spiel aus.", fontInfoMiddleLabel, Label.CENTER);
		btContinue = new YesNoButton(
				buttonX,
				GameGlobals.SCREEN_HEIGHT/2 + fontInfoMiddleLabel.getLineHeight(),
				true, fontInfoMiddleLabel);
		btNewGame = new YesNoButton(
				buttonX,
				GameGlobals.SCREEN_HEIGHT/2 + fontInfoMiddleLabel.getLineHeight(),
				false, fontInfoMiddleLabel);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.setColor(GameColor.MIDDLE_PANEL);
		g.fill(background);

		for(int i=0; i<cardField.length; i++) {
			cardField[i].draw(g);
		}
		
		
		if(!orderCorrect && !areThereMoreCards) {
			g.setColor(GameColor.MIDDLEPANEL_BLOCKED);
			g.fillRect(absolutX, 0, GameGlobals.MIDDLE_PANEL_WIDTH, GameGlobals.SCREEN_HEIGHT);
			g.setColor(GameColor.BASIS_WRITTING);
			lblWrongAnswer.draw(g);
			lblGameOver.draw(g);
			btNewGame.draw(g);
		}
		else if(!orderCorrect) {
			g.setColor(GameColor.MIDDLEPANEL_BLOCKED);
			g.fillRect(absolutX, 0, GameGlobals.MIDDLE_PANEL_WIDTH, GameGlobals.SCREEN_HEIGHT);
			g.setColor(GameColor.BASIS_WRITTING);
			lblWrongAnswer.draw(g);
			btContinue.draw(g);
		}
		else if(!areThereMoreCards) {
			g.setColor(GameColor.MIDDLEPANEL_BLOCKED);
			g.fillRect(absolutX, 0, GameGlobals.MIDDLE_PANEL_WIDTH, GameGlobals.SCREEN_HEIGHT);
			g.setColor(GameColor.BASIS_WRITTING);
			lblGameOver.draw(g);
			btNewGame.draw(g);
		}
		
	}

	@Override
	public void update(GameContainer gameContainer,
			StateBasedGame stateBasedGame, int delta) throws SlickException {

		InputState inputState = InputState.getInstance();
		
		for(int i=0; i<cardField.length; i++) {
			if(orderCorrect && areThereMoreCards) {
				cardField[i].update(inputState, delta);
			}
			if(cardField[i].wasClicked()) {
				cardField[i].resetClicked();
				if(selectedCardArrayNr != -1) {
					cardField[selectedCardArrayNr].unselect();
				}
				selectedCardArrayNr = i;
				gameView.notifyCardSelection(cardField[i].getCardId());
			}
		}
		
		
		if(!orderCorrect && !areThereMoreCards) {
			btNewGame.update(inputState, delta);
			if(btNewGame.isClicked()) {
				btNewGame.resetClicked();
				gameView.startNewGame();
				orderCorrect = true;
				areThereMoreCards = true;
			}
		}
		else if(!orderCorrect) {
			btContinue.update(inputState, delta);
			if(btContinue.isClicked()) {
				btContinue.resetClicked();
				gameView.ContinueGame();
				orderCorrect = true;
				areThereMoreCards = true;
			}
		}
		else if(!areThereMoreCards) {
			btNewGame.update(inputState, delta);
			if(btNewGame.isClicked()) {
				btNewGame.resetClicked();
				gameView.startNewGame();
				orderCorrect = true;
				areThereMoreCards = true;
			}
		}
	}


	public void lockCardSelection(boolean orderCorrect, boolean areThereMoreCards) {
		this.orderCorrect = orderCorrect;
		this.areThereMoreCards = areThereMoreCards;
	}

}
