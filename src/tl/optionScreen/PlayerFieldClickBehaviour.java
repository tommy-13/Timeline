package tl.optionScreen;

public class PlayerFieldClickBehaviour implements ClickBehaviour {

	private OptionView	optionView;
	private int			playerId;
	
	public PlayerFieldClickBehaviour(OptionView optionView, int playerId) {
		this.optionView = optionView;
		this.playerId	= playerId;
	}
	
	@Override
	public void rightClicked() {}

	@Override
	public void leftClicked() {
		optionView.changePlayerName(playerId);
	}

}
