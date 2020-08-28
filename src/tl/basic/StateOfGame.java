package tl.basic;


import java.io.File;
import java.io.IOException;

import tl.io.parseTree.TreeElement;
import tl.io.parseTree.XMLTreeReader;
import tl.io.parseTree.XMLTreeWriter;
import tl.io.safeLoad.ParseTreeStructureException;
import tl.io.safeLoad.ParseTreeXMLSettingsTranslator;
import tl.model.DataBase;
import tl.model.Player;

public class StateOfGame {

	private static StateOfGame uniqueStateOfGame = new StateOfGame();
	private StateOfGame() {
		highscore 		= 0;
		highscorePlayer = "Niemand";
		players 		= new Player[GameGlobals.MAX_PLAYER];
		for(int i=0; i<players.length; i++) {
			players[i] = new Player(i,"Spieler" + (i+1));
		}
		numberOfPlayers = 1;
		currentPlayer	= 0;
		nrOfLyingCards  = 0;
	}
	public static StateOfGame getInstance() {
		return uniqueStateOfGame;
	}
	
	
	private static final String settingsFilePath = "safe" + OSInformation.fileSeparator + "settings.usf"; // user settings file
	
	private int			highscore;
	private String		highscorePlayer;
	private Player[]	players;
	private int			numberOfPlayers;
	private int			currentPlayer;
	
	private int			remainingCards;
	private int			nrOfLyingCards;

	
	
	
	public void startNewGame() {
		currentPlayer = 0;
		for(Player p : players) {
			p.reset();
		}
		remainingCards = DataBase.getInstance().getSize();
		nrOfLyingCards = 0;
	}
	
	public int getNrOfRemainingCards() {
		return remainingCards;
	}
	public void decreaseRemainingCards() {
		remainingCards--;
	}
	public boolean areThereCardsLeft() {
		return remainingCards > 0;
	}
	
	public int getNrOfLyingCards() {
		return nrOfLyingCards;
	}
	public void increaseNrOfLyingCards() {
		nrOfLyingCards++;
	}
	public void clearNrOfLyingCards() {
		nrOfLyingCards = 0;
	}
	
	
	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}
	public void setNextPlayer() {
		currentPlayer = (currentPlayer + 1) % numberOfPlayers;
	}
	
	
	public void setHighscore(int score, String player) {
		if(score > highscore) {
			highscore = score;
			highscorePlayer = player;
		}
	}
	public int getHighscore() {
		return highscore;
	}
	public String getHigscorePlayer() {
		return highscorePlayer;
	}
	
	public void setPlayerName(int nr, String name) {
		players[nr].setName(name);
	}
	public String getPlayerName(int nr) {
		return players[nr].getName();
	}
	
	public Player getPlayer(int nr) {
		return players[nr];
	}

	public boolean hasPlayerName(String inputPlayerName) {
		for(Player p : players) {
			if(inputPlayerName.equals(p.getName())) {
				return true;
			}
		}
		return false;
	}

	public void setNumberOfPlayers(int nrOfPlayers) {
		this.numberOfPlayers = nrOfPlayers;
	}
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	
	
	
	// -------------------------------------------------------------------------------
	// load and safe
	// -------------------------------------------------------------------------------
	public void safe() {
		TreeElement treeRoot = ParseTreeXMLSettingsTranslator.createSettingsTree(this);
		XMLTreeWriter treeWriter = new XMLTreeWriter(settingsFilePath, treeRoot);
		try {
			treeWriter.write();
		} catch (IOException e) {}
	}
	
	public void load() {
		File loadedFile = new File(settingsFilePath);
		if(!loadedFile.exists()) {
			safe();
			return;
		}
		
		String loadPath = loadedFile.getAbsolutePath();
		
		TreeElement treeRoot = null;
		XMLTreeReader treeReader = new XMLTreeReader(loadPath);
		try {
			treeRoot = treeReader.read();
		} catch (IOException e) {}
		
		if(treeRoot == null) {
			return;
		}
		
		try {
			ParseTreeXMLSettingsTranslator.createSettings(this, treeRoot);
		} catch (ParseTreeStructureException e) {}
	}
	
}
