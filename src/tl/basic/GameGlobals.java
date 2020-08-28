package tl.basic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

import tl.game.DrawableWord;

public class GameGlobals {
	
	public static final int SCREEN_WIDTH = 1250;
	public static final int SCREEN_HEIGHT = 620;
	
	public static final int OPTION_FIELD_HEIGHT  = 40;
	public static final int OPTION_VIEW_TOPGAP	 = 140;
	public static final int OPTION_VIEW_SIDEGAP	 = 220;
	
	public static final int LEFT_SPACE = 10;
	public static final int MIDDLE_PANEL_WIDTH = 712;
	public static final int NUMBER_OF_CARDS = 8;
	public static final int CARD_WIDTH = 310;
	public static final int CARD_HEIGHT = 120;
	public static final int CARD_PER_ROW = 2;
	
	public static final int MAX_PLAYER = 6;
	
	public static int ERROR_TEXT_TIME = 10000;
	
	public static Transition transitionOut() {
		return new FadeOutTransition(Color.black, 500);
	}
	public static Transition transitionIn() {
		return new FadeInTransition(Color.black, 500);
	}
	
	
	public static void setConfiguration() {
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream("safe/config.properties"));
			properties.load(stream);
			stream.close();
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			ERROR_TEXT_TIME = Integer.parseInt(properties.getProperty("ERRORTEXTTIME"));
		} catch(NumberFormatException e) {
			ERROR_TEXT_TIME = 8000;
		}
	}
	
	
	
	public static List<DrawableWord> getLeftAlignedText(Font font, int offsetX, int offsetY, int width, String text) {
		
		List<DrawableWord> dWords = new LinkedList<DrawableWord>();
		
		int textWidth = font.getWidth(text);
		if(textWidth <= width) {
			dWords.add(new DrawableWord(offsetX, offsetY, text));
			return dWords;
		}
		
		
		String[] words = text.split(" ");
		String temp = "";
		int offset = offsetY;
		
		for(int i=0; i<words.length; i++) {
			temp += words[i];
			
			if(i == words.length-1) {
				dWords.add(new DrawableWord(offsetX, offset, temp));
				break;
			}
			
			int tempWidth = font.getWidth(temp + " " + words[i+1]);
			if(tempWidth > width) {
				dWords.add(new DrawableWord(offsetX, offset, temp));
				temp = "";
				offset += font.getLineHeight();
			}
			else {
				temp += " ";
			}
		}
		
		return dWords;
	}
	
}
