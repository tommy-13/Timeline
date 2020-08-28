package tl.optionScreen;

@SuppressWarnings("serial")
public class PlayerDeletingException extends Exception {

	public PlayerDeletingException() {
		super("player deleting not possible");
	}
	
	public PlayerDeletingException(String player) {
		super("player " + player + " couldn't be deleted");
	}
}
