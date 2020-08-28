package tl.optionScreen;

import tl.basic.GameGlobals;
import tl.basic.StateOfGame;

public class PlayerNumberFieldClickBehaviour implements ClickBehaviour {

	private int			nrOfPlayers;
	
	public PlayerNumberFieldClickBehaviour() {
		this.nrOfPlayers	= 1;
	}
	
	@Override
	public void rightClicked() {
		if(nrOfPlayers == 1) {
			nrOfPlayers = GameGlobals.MAX_PLAYER;
		}
		else {
			nrOfPlayers--;
		}
		StateOfGame.getInstance().setNumberOfPlayers(nrOfPlayers);
	}

	@Override
	public void leftClicked() {
		if(nrOfPlayers >= GameGlobals.MAX_PLAYER) {
			nrOfPlayers = 1;
		}
		else {
			nrOfPlayers++;
		}
		StateOfGame.getInstance().setNumberOfPlayers(nrOfPlayers);
	}

}
