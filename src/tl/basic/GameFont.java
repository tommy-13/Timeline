package tl.basic;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

public class GameFont extends AngelCodeFont {
	
	public static final String FONT10B = "res/fonts/SourceCodePro10b";
	public static final String FONT13  = "res/fonts/SourceCodePro13";
	public static final String FONT16  = "res/fonts/SourceCodePro16";
	public static final String FONT24  = "res/fonts/SourceCodePro24";
	public static final String FONT26  = "res/fonts/SourceCodePro26";
	public static final String FONT32  = "res/fonts/SourceCodePro32";
	public static final String FONT40  = "res/fonts/SourceCodePro40";
	public static final String FONT48B = "res/fonts/SourceCodePro48b";
	public static final String FONT64b = "res/fonts/SourceCodePro64b";
	
	
	public GameFont(String font) throws SlickException {
		super(font + ".fnt", font + ".png");
	}
}
