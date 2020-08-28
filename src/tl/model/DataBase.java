package tl.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import tl.io.parseTree.TreeElement;
import tl.io.parseTree.XMLTreeReader;
import tl.io.safeLoad.ParseTreeStructureException;
import tl.io.safeLoad.ParseTreeXMLDataBaseTranslator;

public class DataBase {
	
	private static DataBase uniqueDataBase = new DataBase();
	public static DataBase getInstance() {
		return uniqueDataBase;
	}
	
	
	private CardModels	cardModels;
	private int			size;
	
	private DataBase() {
		cardModels = new CardModels();
		size = 0;
	}
	
	public void setCardModels(List<CardModel> cs) {
		cardModels.clear();
		this.size = cs.size();
		for(CardModel c : cs) {
			cardModels.addCardModel(c);
		}
	}

	
	public int getSize() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	
	
	
	/**
	 * Returns the copy of the CardModel with the id <code>id</code>.
	 * @param id
	 * @return
	 */
	public CardModel getCardModel(int id) {
		CardModel c = cardModels.getCardModel(id);
		return new CardModel(c);
	}
	
	public CardModel[] getAllCardModels() {
		return cardModels.getAllCardModels();
	}
	
	


	// -------------------------------------------------------------------------------
	// load
	// -------------------------------------------------------------------------------
	public boolean load(String fileName) {
		File loadedFile = new File(fileName);
		if(!loadedFile.exists()) {
			return false;
		}
		
		String loadPath = loadedFile.getAbsolutePath();
		
		TreeElement treeRoot = null;
		XMLTreeReader treeReader = new XMLTreeReader(loadPath);
		try {
			treeRoot = treeReader.read();
		} catch (IOException e) {
			return false;
		}
		
		if(treeRoot == null) {
			return false;
		}
		
		try {
			ParseTreeXMLDataBaseTranslator.createDataBase(treeRoot);
		} catch (ParseTreeStructureException e) {
			return false;
		}
		
		return true;
	}
	
}
