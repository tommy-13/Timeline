package tl.game.rightPanel;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import tl.basic.GameGlobals;
import tl.basic.InputState;
import tl.basic.StateOfGame;
import tl.model.CardModel;

public class PutDownCardList {
	
	private static final int CARD_GAP = 20;
	
	private RightPanel rightPanel;

	private Font fontId;
	private Font fontText;
	private Font fontYear;
	
	private List<PutDownCardField>	putDownCards;
	private List<InterCardArrow>	interCardArrows;
	private int x,y;
	
	CardModel selectedCard = null;
	
	
	public PutDownCardList(RightPanel rightPanel, int x, Font fontId, Font fontText, Font fontYear) {
		this.rightPanel = rightPanel;
		this.fontId		= fontId;
		this.fontText	= fontText;
		this.fontYear	= fontYear;
		
		putDownCards = new LinkedList<PutDownCardField>();
		interCardArrows = new LinkedList<InterCardArrow>();
		this.x = x;
		this.y = (GameGlobals.SCREEN_HEIGHT - InterCardArrow.HEIGHT) / 2;
		InterCardArrow ica = new InterCardArrow(x, y);
		interCardArrows.add(ica);
	}
	
	public void reset() {
		putDownCards.clear();
		interCardArrows.clear();
		StateOfGame.getInstance().clearNrOfLyingCards();
		y = (GameGlobals.SCREEN_HEIGHT - InterCardArrow.HEIGHT) / 2;
		InterCardArrow ica = new InterCardArrow(x, y);
		interCardArrows.add(ica);
		selectedCard = null;
	}
	
	
	public void addSelectedCard(int index) {
		if(selectedCard != null) {
			PutDownCardField pdcf = new PutDownCardField(
					x + InterCardArrow.WIDTH, 0, selectedCard, fontId, fontText, fontYear);
			putDownCards.add(index, pdcf);
			interCardArrows.add(new InterCardArrow(x, 0));
			StateOfGame.getInstance().increaseNrOfLyingCards();
			
			int nr = putDownCards.size();
			int dist = GameGlobals.CARD_HEIGHT + CARD_GAP;
			int h = nr * dist + InterCardArrow.HEIGHT;
			
			if(h <= GameGlobals.SCREEN_HEIGHT) {
				y = (GameGlobals.SCREEN_HEIGHT - h) / 2;
			}
			else {
				
			}
			updateGraphicObjects();
		}
	}
	
	public void setSelectedCard(CardModel cm) {
		selectedCard = cm;
	}
	
	public void scroll(boolean up, int times) {
		int gap = 16;
		int pixel = 8;
		if(up) {
			if(y < gap) {
				y += pixel * times;
				y = Math.min(y, gap);
				updateGraphicObjects();
			}
		}
		else {
			int nr = putDownCards.size();
			int dist = GameGlobals.CARD_HEIGHT + CARD_GAP;
			int h = nr * dist + InterCardArrow.HEIGHT;
			if(y + h > GameGlobals.SCREEN_HEIGHT - gap) {
				y -= pixel * times;
				y = Math.max(y, GameGlobals.SCREEN_HEIGHT - gap - h);
				updateGraphicObjects();
			}
		}
	}
	
	private void updateGraphicObjects() {
		int dist = GameGlobals.CARD_HEIGHT + CARD_GAP;
		for(int i=0; i<putDownCards.size(); i++) {
			PutDownCardField pdc = putDownCards.get(i);
			pdc.setY(y + (InterCardArrow.HEIGHT + CARD_GAP) / 2 + i * dist);
		}
		for (int i=0; i<interCardArrows.size(); i++) {
			InterCardArrow ica = interCardArrows.get(i);
			ica.setYPosition(y + i * dist);
		}
	}
	
	
	public void draw(Graphics g) {
		for(InterCardArrow ica : interCardArrows) {
			ica.draw(g);
		}
		for(PutDownCardField pdc : putDownCards) {
			pdc.draw(g);
		}
	}
	
	public void update(InputState inputState, int delta) {
		for(InterCardArrow ica : interCardArrows) {
			ica.update(inputState, delta);
			if(ica.wasClicked()) {
				ica.resetClicked();
				if(selectedCard != null) { 
					int index = interCardArrows.indexOf(ica);
					addSelectedCard(index);
					selectedCard = null;
					rightPanel.notifyCardPutDown(isOrderCorrect());
					return;
				}
			}
		}
	}
	
	
	
	public boolean isOrderCorrect() {
		int currentYear = Integer.MIN_VALUE;
		for(PutDownCardField pdc : putDownCards) {
			if(pdc.getCardYear() < currentYear) {
				return false;
			}
			currentYear = pdc.getCardYear();
		}
		return true;
	}

	public void showYears(boolean show) {
		for(InterCardArrow ica : interCardArrows) {
			ica.setVisible(!show);
		}
		for(PutDownCardField pdc : putDownCards) {
			pdc.showYear(show);
		}
	}
	
}
