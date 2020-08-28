package tl.model;

public class CardModel {

	private int 	id;
	private int		year;
	private String	event;
	
	
	public CardModel(int id, int year, String event) {
		this.id		= id;
		this.year	= year;
		this.event	= event;
	}
	
	public CardModel(CardModel c) {
		this(c.getId(), c.getYear(), c.getEvent());
	}
	
	
	public int getId() {
		return id;
	}
	public int getYear() {
		return year;
	}
	public String getEvent() {
		return event;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setEvent(String event) {
		this.event = event;
	}

}
