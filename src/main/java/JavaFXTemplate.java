import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class JavaFXTemplate extends Application {
	GridPane grid;
	Game g1;
	MenuBar m1;
	Menu menu1;
	Menu menu2;
	Menu menu3;
	MenuItem item1;
	MenuItem item2;
	MenuItem item3;
	MenuItem item4;
	MenuItem item5;
	MenuItem item6;
	MenuItem item7;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Connect Four!");
		primaryStage.setScene(startScene(primaryStage));
		primaryStage.show();
	}
	
	
	
	public Scene startScene(Stage primaryStage) {
		Image logo = new Image("ConnectFour.jpeg");
		ImageView logoview = new ImageView();
		logoview.setImage(logo);
		Button start = new Button("New Game");
		start.setOnAction(e -> primaryStage.setScene(gameScene(primaryStage)));
		Scene scene = new Scene(new VBox(start, logoview), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	
	public Scene gameScene(Stage primaryStage) {
		grid = new GridPane();
		grid.setMaxSize(600, 600);
		
		ListView<String> whoseMove = new ListView<>();
		whoseMove.setPrefSize(100, 100);
		
		ListView<String> moveLog = new ListView<>();
		moveLog.setPrefSize(100, 100);
		
		m1 = new MenuBar();
		menu1 = new Menu("Game Play");
		menu2 = new Menu("Themes");
		menu3 = new Menu("Options");
		item1 = new MenuItem("reverse move");
		item2 = new MenuItem("Default");
		item3 = new MenuItem("Dark");
		item4 = new MenuItem("Cool");
		item5 = new MenuItem("how to play");
		item6 = new MenuItem("new game");
		item7 = new MenuItem("exit");
		menu1.getItems().add(item1);
		menu2.getItems().add(item2);
		menu2.getItems().add(item3);
		menu2.getItems().add(item4);
		menu3.getItems().add(item5);
		menu3.getItems().add(item6);
		menu3.getItems().add(item7);
		m1.getMenus().add(menu1);
		m1.getMenus().add(menu2);
		m1.getMenus().add(menu3);
		
		g1 = new Game();
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Pair<Integer, Integer> lastMove = g1.reverse();
				if (lastMove != null) {
					whoseMove.getItems().clear();
					whoseMove.getItems().add("Player" + g1.getTurn() + "'s turn");
					whoseMove.getItems().add("Move made to (" + lastMove.getKey() + ", " + lastMove.getValue() + ") by Player" + 
							g1.getTurn() + " reversed.");
					moveLog.getItems().remove(moveLog.getItems().size() - 1);
				}
			}
		});
		
		item2.setOnAction(e -> changeTheme("Default"));
		item3.setOnAction(e -> changeTheme("Dark"));
		item4.setOnAction(e -> changeTheme("Cool"));
		changeTheme("Default");
		item6.setOnAction(e -> primaryStage.setScene(gameScene(primaryStage)));
		item7.setOnAction(e -> primaryStage.close());
		whoseMove.getItems().add("Player" + g1.getTurn() + "'s turn");
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				GameButton b1 = new GameButton();
				b1.setPrefSize(60, 60);
				Pair <Integer, Integer> index = new Pair<>(i, j);
				b1.setIndex(index);
				b1.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Pair<Integer, Integer> moveIndex = ((GameButton)event.getSource()).getIndex();
						if (!g1.makeMove(moveIndex)) {
							whoseMove.getItems().add("P" + g1.getTurn() + " to (" + moveIndex.getKey() +
									"," + moveIndex.getValue() + "). Invalid move. Try again");
							return;
						}
						whoseMove.getItems().clear();
						whoseMove.getItems().add("Player" + g1.otherPlayer() + "'s turn");
						moveLog.getItems().add("P" + g1.getTurn() + " to (" + moveIndex.getKey() +
								"," + moveIndex.getValue() + ")");
						if (g1.checkWin(moveIndex)) {
							g1.disable();
							primaryStage.setScene(endScene(primaryStage, g1.getTurn(), grid));
						}
					}
				});
				grid.add(b1, j, i);
				g1.gameBoard[i][j] = b1;
				g1.legalMoves.add(new Pair<>(5, j));
			}
		}
		Scene scene = new Scene(new VBox(m1, whoseMove, moveLog, grid), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	
	public Scene instrScene() {
		Scene scene = new Scene(new VBox(), 700,700);
		return scene;
	}
	
	public Scene endScene(Stage primaryStage, int turn, GridPane grid) {
		TextField t1 = new TextField();
		Button b1 = new Button("new game");
		Button b2 = new Button("quit");
		b1.setOnAction(e -> primaryStage.setScene(gameScene(primaryStage)));
		b2.setOnAction(e -> primaryStage.close());
		if (turn == 0) {
			t1.setText("Draw");
		} else if (turn == 1) {
			t1.setText("Player 1 Won");
		} else {
			t1.setText("Player 2 Won");
		}
		Scene scene = new Scene(new VBox(b1, b2, t1, grid), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	
	
	public void changeTheme(String theme) {
		g1.changeTheme(theme);
		if (theme == "Default") {
			grid.setStyle("-fx-color: White");
		} else if (theme == "Dark") {
			grid.setStyle("-fx-color: Black");
		} else {
			
		}
	}
	

}
