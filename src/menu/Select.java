package menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.obj.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import base.Window;

public class Select {
	
	public static List<File> selected_lng_files= new ArrayList<File>();
	public static List<String> selected_lng_names = new ArrayList<String>();
	
	private static int x=0;
	private static Stage window = Window.window;
	
	/* set difficulty */
	public static void selectDifficulty(Pane root) {
		
		x=0;
		root.setStyle("-fx-background-color: rgb(14, 14, 14)");
		Scene scene = new Scene(root);
	
		Text header = new Text("DIFFICULTY");
			header.setTranslateX(143);
			header.setTranslateY(130);
			header.setFill(Color.WHITE);
			header.setStyle("-fx-font-family: 'Grixel Kyrou 7 Wide Bold'; -fx-font-size: 50;");
		
		Option[] diff = new Option[5];		
		for(int i=0; i<5; i++) {
			int calcY = 220 + 25*i;
			diff[i] = new Option(calcY, Words.loadDifficulties()[i], "diff", x==i);
		}
		
		root.getChildren().add(header);
		root.getChildren().addAll(diff);
		window.setScene(scene);
		
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			
				case UP: if(x>0) x--;
					
					setScene(root, scene, "diff", diff);
					break;
					
				case DOWN: if(x<4) x++;
					setScene(root, scene, "diff", diff);
					break;
					
				case ENTER:
					Window.DIFFICULTY = x+1;
					Window.setLang();
					break;
					
				default: break;			
			}
			
		});
	}

	/* select languages */
	public static void selectLanguage(Pane root) {
		
		x=0; selected_lng_files.clear(); selected_lng_names.clear();
		
		Scene scene = new Scene(root);
		root.setStyle("-fx-background-color: rgb(14, 14, 14)");
		
		Text header = new Text("LANGUAGES");
		header.setTranslateX(157); header.setTranslateY(130);header.setFill(Color.WHITE);
		header.setStyle("-fx-font-family: 'Grixel Kyrou 7 Wide Bold'; -fx-font-size: 50;");
		
		/* load all available languages */
		Option[] lngs = new Option[5];
		for(int i=0; i<5; i++) {
			int calcY = 220 + 25*i;
			lngs[i] = new Option(calcY, Words.loadLanguages()[i], "lng", x==i);
		}
		
		root.getChildren().add(header); root.getChildren().addAll(lngs);
		window.setScene(scene);
		
		/* menu movement key listener */
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			
				case ESCAPE: x=0;
					Window.setDiff();
					break;
			
				case UP: if(x>0) x--;
					setScene(root, scene, "lng", lngs);
					break;
					
				case DOWN: if(x<4) x++;
					setScene(root, scene, "lng", lngs);
					break;
					
				case SPACE:
					if(selected_lng_files.contains(Words.listOfFiles[x])) {
						selected_lng_files.remove(Words.listOfFiles[x]);
						selected_lng_names.remove(Words.lngsNames.get(x));
					} else {
						if(!isEmpty(x)) {
							selected_lng_names.add(Words.lngsNames.get(x));
							selected_lng_files.add(Words.listOfFiles[x]);
						}
					}
					setScene(root, scene, "lng", lngs);
					break;
					
				case ENTER:
					if(!selected_lng_files.contains(Words.listOfFiles[x]) && selected_lng_files.size() == 0) {
						if(!isEmpty(x)) {
							selected_lng_names.add(Words.lngsNames.get(x));
							selected_lng_files.add(Words.listOfFiles[x]);
						}
					}
					
					setScene(root, scene, "lng", lngs);
					
					if(!Words.loadWords(selected_lng_files).isEmpty()) {
						Window.startGame(selected_lng_files);
					}
					break;
					
				default: break;			
			}
		});
	}
	
	/* method for refreshing the view */
	static void setScene(Pane root, Scene scene, String type, Option[] option) {
		
		root.getChildren().removeAll(option);
		
		if(type.equals("lng")) {
			for(int i=0; i<5; i++) {
				int calcY = 220 + 25*i;
				option[i] = new Option(calcY, Words.loadLanguages()[i], "lng", i==x);
			}
		}
		
		if(type.equals("diff")) {
			for(int i=0; i<5; i++) {
				int calcY = 220 + 25*i;
				option[i] = new Option(calcY, Words.loadDifficulties()[i], "diff", i==x);
			}
		}
		
		root.getChildren().addAll(option);
		window.setScene(scene);
	}
	
	static boolean isEmpty(int x) {
		try {
			if(Files.lines(Paths.get(Words.listOfFiles[x].toString())).count() > 1) {
				return false;				
			} else {
				return true;
			}
		} catch (IOException er) {
			System.out.println(er);
			return true;
		}
	}
}