package tl.io.safeLoad;

import java.util.LinkedList;
import java.util.List;

import tl.io.parseTree.Leaf;
import tl.io.parseTree.Node;
import tl.io.parseTree.TreeElement;
import tl.model.CardModel;
import tl.model.DataBase;

public class ParseTreeXMLDataBaseTranslator {
	
	private static final String LabelDataBase	= "DataBase";
	private static final String LabelCard		= "Card";
	private static final String LabelId			= "Id";
	private static final String LabelYear		= "Year";

	
	public static TreeElement createDataBaseTree() {
		DataBase dataBase = DataBase.getInstance();
		Node root = new Node(LabelDataBase);
		
		// safe transactions
		CardModel[] cs = dataBase.getAllCardModels();
		for(CardModel t : cs) {
			root.addChild(createCardNode(t));
		}
		return root;
	}
	
	private static Leaf createCardNode(CardModel cm) {
		Leaf leaf = new Leaf(LabelCard);
		leaf.setAttribute(LabelId, Integer.toString(cm.getId()));
		leaf.setAttribute(LabelYear, Integer.toString(cm.getYear()));
		leaf.setText(cm.getEvent());
		return leaf;
	}
	
	
	
	public static void createDataBase(TreeElement root) throws ParseTreeStructureException {
		if(!root.getName().equals(LabelDataBase) || root.isLeaf()) {
			throw new ParseTreeStructureException("DataBase.Root");
		}
		
		Node nDataBase = (Node) root;
		
		List<CardModel> ts = new LinkedList<CardModel>();
		for(TreeElement e : nDataBase.getChildren(LabelCard)) {
			ts.add(createCardModel(e));
		}
		
		DataBase.getInstance().setCardModels(ts);
	}
	
	private static CardModel createCardModel(final TreeElement e) throws ParseTreeStructureException {
		if(!e.isLeaf()) {
			throw new ParseTreeStructureException("DataBase.Card");
		}
		Leaf leaf = (Leaf) e;
		
		if(!leaf.hasAttribute(LabelId)) {
			throw new ParseTreeStructureException("Card.Id");
		}
		if(!leaf.hasAttribute(LabelYear)) {
			throw new ParseTreeStructureException("Card.Year");
		}
		if(!leaf.hasText()) {
			throw new ParseTreeStructureException("Card.Event");
		}
		
		int id, year;
		try {
			id = Integer.parseInt(leaf.getAttribute(LabelId));
			year = Integer.parseInt(leaf.getAttribute(LabelYear));
		} catch(NumberFormatException ex) {
			throw new ParseTreeStructureException("Card.ParseInt");
		}
		String event = leaf.getText();
		
		return new CardModel(id, year, event);
	}

}
