package tl.basic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class CardFiles {

	private static CardFiles uniqueCardFiles = new CardFiles();
	private CardFiles() {
		tlfFiles = new ArrayList<String>();
		
		File folder = new File("Kartensätze");
		if(folder.isDirectory()) {
			File[] files = folder.listFiles(new FilenameFilter() {	
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".tlf");
				}
			});
			
			for(File f : files) {
				tlfFiles.add(f.getAbsolutePath());
			}
		}
	}
	public static CardFiles getInstance() {
		return uniqueCardFiles;
	}
	
	
	private List<String>	tlfFiles;
	private String			currentFilePath;
	private String			currentFileName;
	
	public boolean hasFiles() {
		return getNumberOfFiles() > 0;
	}
	public int getNumberOfFiles() {
		return tlfFiles.size();
	}
	
	public void setCurrentFile(int nr) {
		if(hasFiles()) {
			currentFilePath = tlfFiles.get(nr);
			currentFileName = currentFilePath.substring(currentFilePath.lastIndexOf(File.separator)+1);
			currentFileName = currentFileName.substring(0, currentFileName.indexOf("."));
			if(currentFileName.length() > 32) {
				currentFileName = currentFileName.substring(0, 32);
			}
		}
		else {
			currentFilePath = null;
			currentFileName = null;
		}
	}
	public String getCurrentFilePath() {
		return currentFilePath;
	}
	public String getCurrentFileName() {
		if(currentFileName == null) {
			return "keine Kartensätze vorhanden";
		}
		return currentFileName;
	}
	
}
