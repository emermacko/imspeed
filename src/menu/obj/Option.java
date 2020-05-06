package menu.obj;

import base.Window;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Option extends StackPane {
	
	private final Text text;
	private final Rectangle background;
	
	public Option(int y, String value, String type, boolean highlighted) {	
				
		int textX=0; int bgX=0; int bgWidth=0;
		if(type.equals("lng")) {
			textX = 315;
			bgX = 315;
			bgWidth = 170;
		} if(type.equals("diff")) {
			textX = 290;
			bgX = 280;
			bgWidth = 240;
		}
		
		text = new Text(value);
		text.setTranslateX(textX);
		text.setTranslateY(y);
		text.setFont(Font.font("Courier new", 14));
		
		background = new Rectangle(bgWidth, 25, Color.WHITE);
		background.setTranslateX(bgX);
		background.setTranslateY(y);
		
		if(highlighted) {
			background.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		} else {
			background.setFill(Window.BACKGROUND);
			text.setFill(Color.WHITE);
		}
		
		setAlignment(text, Pos.CENTER_LEFT);
		getChildren().addAll(background, this.text);	
	}
}
