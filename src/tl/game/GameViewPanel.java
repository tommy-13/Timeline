package tl.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public interface GameViewPanel {

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException;
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException;
	
}
