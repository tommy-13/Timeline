package tl.game.rightPanel;

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
import tl.game.GameView;
import tl.game.GameViewPanel;
import tl.model.DataBase;

public class RightPanel implements GameViewPanel {
	
	private GameView gameView;
	
	private int		absolutX;
	private Rectangle 		background;
	private PutDownCardList	putDownCards;
	
	private Font fontCardText;
	private Font fontCardId;
	private Font fontCardYear;
	
	
	
	public RightPanel(GameView gameView, int absolutX) {
		this.gameView = gameView;
		
		this.absolutX 	= absolutX;
	}
	
	
	public void startNewGame() {
		putDownCards.reset();
	}
	
	public void setSelectedCard(int nr) {
		putDownCards.setSelectedCard(DataBase.getInstance().getCardModel(nr));
	}
	
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		/* fonts */
		fontCardId = new GameFont(GameFont.FONT10B);
		fontCardText = new GameFont(GameFont.FONT13);
		fontCardYear = new GameFont(GameFont.FONT64b);
		
		/* background */
		background = new Rectangle(absolutX, 0, GameGlobals.SCREEN_WIDTH - absolutX, GameGlobals.SCREEN_HEIGHT);

		putDownCards	= new PutDownCardList(this, absolutX + 20, fontCardId, fontCardText, fontCardYear);
		
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.setColor(GameColor.RIGHT_PANEL);
		g.fill(background);

		putDownCards.draw(g);
		
	}

	@Override
	public void update(GameContainer gameContainer,
			StateBasedGame stateBasedGame, int delta) throws SlickException {

		InputState inputState = InputState.getInstance();
		
		putDownCards.update(inputState, delta);
		
		int mouseX = inputState.getMouseX();
		if(mouseX >= absolutX) {
			if(inputState.getMouseWheelDown()) {
				putDownCards.scroll(false, 2);
			}
			if(inputState.getMouseWheelUp()) {
				putDownCards.scroll(true, 2);
			}
		}
		
		if(inputState.isKeyDownPressed()) {
			putDownCards.scroll(false, 10);
		}
		if(inputState.isKeyUpPressed()) {
			putDownCards.scroll(true, 10);
		}
		
	}


	public void notifyCardPutDown(boolean orderCorrect) {
		gameView.notifyCardPutDown(orderCorrect);
	}


	public void showYears(boolean show) {
		putDownCards.showYears(show);
	}


	public void clearCardList() {
		putDownCards.reset();
	}

}
