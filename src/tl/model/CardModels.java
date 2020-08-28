package tl.model;

import java.util.HashMap;
import java.util.Map;

public class CardModels {
	
	private Map<Integer, CardModel> cardsById;
			
	
	public CardModels() {
		cardsById = new HashMap<Integer, CardModel>();
	}
	
	public void clear() {
		cardsById.clear();
	}
	
	public void addCardModel(CardModel c) {
		cardsById.put(c.getId(), c);
	}
	
	public void deleteCardModel(CardModel c) {
		cardsById.remove(c.getId());
	}
	
	
	/**
	 * Returns the copy of the CardModel with the id <code>id</code>.
	 * @param id
	 * @return
	 */
	public CardModel getCardModel(int id) {
		return new CardModel(cardsById.get(id));
	}
	
	
	public CardModel[] getAllCardModels() {
		CardModel[] ts = new CardModel[cardsById.size()];
		int i=0;
		for(CardModel t : cardsById.values()) {
			ts[i] = new CardModel(t);
			i++;
		}
		return ts;
	}
	
}
