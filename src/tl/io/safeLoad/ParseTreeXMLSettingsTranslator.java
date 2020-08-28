package tl.io.safeLoad;

import java.util.List;

import tl.basic.GameGlobals;
import tl.basic.StateOfGame;
import tl.io.parseTree.Leaf;
import tl.io.parseTree.Node;
import tl.io.parseTree.TreeElement;


public class ParseTreeXMLSettingsTranslator {
	
	private static final String LabelSettings 			= "Settings";
	private static final String LabelHighscore			= "Highscore";
	private static final String LabelHighscorePlayer	= "HighscorePlayer";
	private static final String LabelPlayer				= "Player";
	private static final String LabelPlayerNr			= "Nr";
	
	
	public static TreeElement createSettingsTree(StateOfGame settings) {
		Node root = new Node(LabelSettings);
		root.setAttribute(LabelHighscore, Integer.toString(settings.getHighscore()));
		root.setAttribute(LabelHighscorePlayer, settings.getHigscorePlayer());
		
		for(int nr=0; nr<GameGlobals.MAX_PLAYER; nr++) {
			Leaf leaf = new Leaf(LabelPlayer);
			leaf.setAttribute(LabelPlayerNr, Integer.toString(nr));
			leaf.setText(settings.getPlayerName(nr));
			root.addChild(leaf);
		}
		
		return root;
	}
	
	
	public static void createSettings(StateOfGame settings, TreeElement root) throws ParseTreeStructureException {
		if(!root.getName().equals(LabelSettings) || root.isLeaf()) {
			throw new ParseTreeStructureException("Settings.Root");
		}
		
		Node nSettings = (Node) root;
		if(!nSettings.hasAttribute(LabelHighscore) || !nSettings.hasAttribute(LabelHighscorePlayer)) {
			throw new ParseTreeStructureException("Settings.Highscore");
		}
		if(!nSettings.hasChild(LabelPlayer)) {
			throw new ParseTreeStructureException("Settings.Player");
		}
		
		int highscore = 0;
		try {
			highscore = Integer.parseInt(root.getAttribute(LabelHighscore));
		} catch(NumberFormatException e) {
			throw new ParseTreeStructureException("Settings.HighscoreFormat");
		}
		settings.setHighscore(highscore, nSettings.getAttribute(LabelHighscorePlayer));
		
		List<TreeElement> tePlayers = nSettings.getChildren(LabelPlayer);
		for(TreeElement te : tePlayers) {
			if(!te.isLeaf()) {
				throw new ParseTreeStructureException("Settings.PlayerLeaf");
			}
			Leaf l = (Leaf) te;
			
			if(!l.hasAttribute(LabelPlayerNr)) {
				throw new ParseTreeStructureException("Settings.PlayerNr");
			}
			int nr = 0;
			try {
				nr = Integer.parseInt(l.getAttribute(LabelPlayerNr));
			} catch(NumberFormatException e) {
				throw new ParseTreeStructureException("Settings.PlayerNr");
			}
			
			settings.setPlayerName(nr, l.getText());
		}
	}
	
}
