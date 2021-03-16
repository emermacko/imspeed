package base.obj;

import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

import base.ButtonAction;
import base.Colors;
import base.Scenes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import static base.Utils.createText;

public class KeyButton extends StackPane {
	
	private Color primary;
	private Color secondary;
	private Text value;
	private boolean active = false;
	
	private static ArrayList<Shape> primaryColored = new ArrayList<>();
	private static ArrayList<Shape> secondaryColored = new ArrayList<>();
	
	public KeyButton(Scene scene, Pane root, KeyCode trigger, String val, int x, int y, int width, int height, int fontSize, int radius, ButtonAction callback) {
		this(scene, root, trigger, val, x, y, width, height, Colors.DARK_GREY_C, Color.WHITE, fontSize, radius, callback);
	}
	
	public KeyButton(Scene scene, Pane root, KeyCode trigger, String val, int x, int y, int width, int height, String primary, String secondary, int fontSize, int radius, ButtonAction callback) {
		this(scene, root, trigger, val, x, y, width, height, Color.web(primary), Color.web(secondary), fontSize, radius, callback);
	}
	
	public KeyButton(Scene scene, Pane root, KeyCode trigger, String val, int x, int y, int width, int height, Color primary, Color secondary, int fontSize, int radius, ButtonAction callback) {
		this.primary = primary;
		this.secondary = secondary;
		this.value = createText(val, secondary, Scenes.FONT_TEXT, fontSize);

		final int spacing = 2;
		
		Rectangle bg = new Rectangle(width, height, secondary);
			bg.setArcHeight(radius);
			bg.setArcWidth(radius);
		Rectangle innerBg = new Rectangle(width-spacing, height-spacing, primary);
			innerBg.setX(-spacing/2);
			innerBg.setY(-spacing/2);
			innerBg.setArcHeight(radius);
			innerBg.setArcWidth(radius);
		
		primaryColored.add(innerBg);
		secondaryColored.add(bg);
		
		setPrefSize(width, height);
		setAlignment(Pos.CENTER);
		setTranslateX(x);
		setTranslateY(y);
		getChildren().addAll(bg, innerBg, value);
				
		scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == trigger) {
				setPressed();
				callback.callback(root, active);
				active = !active;
			}
        });
		scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			if(e.getCode() == trigger) {
				setReleased();
			}
        });
	}
		
	public void setPressed() {
		primaryColored.forEach(node -> node.setFill(secondary));
		secondaryColored.forEach(node -> node.setFill(primary));
		value.setFill(primary);
	}
	
	public void setReleased() {
		primaryColored.forEach(node -> node.setFill(primary));
		secondaryColored.forEach(node -> node.setFill(secondary));
		value.setFill(secondary);
	}
	
	public void setPrimaryColor(Color color) {
		primaryColored.forEach(node -> node.setFill(color));
	}
	
	public void setSecondaryColor(Color color) {
		secondaryColored.forEach(node -> node.setFill(color));
	}
	
	public Color getPrimaryColor() {
		return primary;
	}
	
	public Color getSecondaryColor() {
		return secondary;
	}
	
}