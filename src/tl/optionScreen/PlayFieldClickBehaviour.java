package tl.optionScreen;

public class PlayFieldClickBehaviour implements ClickBehaviour {

	private OptionView	optionView;
	
	public PlayFieldClickBehaviour(OptionView optionView) {
		this.optionView		= optionView;
	}
	
	@Override
	public void rightClicked() {}

	@Override
	public void leftClicked() {
		optionView.handlePlayButtonClick();
	}

}
