package tl.optionScreen;

import tl.basic.CardFiles;

public class CardFileFieldClickBehaviour implements ClickBehaviour {

	private int			nrOfFile;
	
	public CardFileFieldClickBehaviour() {
		this.nrOfFile		= 0;
	}
	
	@Override
	public void rightClicked() {
		CardFiles cardFiles = CardFiles.getInstance();
		if(!cardFiles.hasFiles()) {
			return;
		}
		if(nrOfFile == 0) {
			nrOfFile = cardFiles.getNumberOfFiles()-1;
		}
		else {
			nrOfFile--;
		}
		cardFiles.setCurrentFile(nrOfFile);
	}

	@Override
	public void leftClicked() {
		CardFiles cardFiles = CardFiles.getInstance();
		if(!cardFiles.hasFiles()) {
			return;
		}
		if(nrOfFile >= cardFiles.getNumberOfFiles()-1) {
			nrOfFile = 0;
		}
		else {
			nrOfFile++;
		}
		cardFiles.setCurrentFile(nrOfFile);
	}

}
