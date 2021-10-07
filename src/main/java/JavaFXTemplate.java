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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class JavaFXTemplate extends Application {
	
	
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
		Button start = new Button("New Game");
		start.setOnAction(e -> primaryStage.setScene(gameScene(primaryStage)));
		Scene scene = new Scene(new VBox(start), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	
	public Scene gameScene(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setMaxSize(600, 600);
		
		ListView<String> myView = new ListView<>();
		myView.setPrefSize(100, 100);
		
		MenuBar m1 = new MenuBar();
		Menu menu1 = new Menu("Game Play");
		Menu menu2 = new Menu("Themes");
		Menu menu3 = new Menu("Options");
		MenuItem item1 = new MenuItem("reverse move");
		MenuItem item5 = new MenuItem("how to play");
		MenuItem item6 = new MenuItem("new game");
		MenuItem item7 = new MenuItem("exit");
		menu1.getItems().add(item1);
		menu3.getItems().add(item5);
		menu3.getItems().add(item6);
		menu3.getItems().add(item7);
		m1.getMenus().add(menu1);
		m1.getMenus().add(menu2);
		m1.getMenus().add(menu3);
		
		Game g1 = new Game();
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Pair<Integer, Integer> lastMove = g1.reverse();
				if (lastMove != null) {
					myView.getItems().clear();
					myView.getItems().add("Move made to " + lastMove.getKey() + ",  " + lastMove.getValue() + " by Player" + 
							g1.getTurn() + " reversed. Move again");
				}
			}
		});
		item6.setOnAction(e -> primaryStage.setScene(gameScene(primaryStage)));
		item7.setOnAction(e -> primaryStage.close());
		myView.getItems().add("Player" + g1.getTurn() + "'s turn");
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				GameButton b1 = new GameButton();
				b1.setPrefSize(60, 60);
				Pair <Integer, Integer> index = new Pair<>(i, j);
				b1.setIndex(index);
				//b1.setText(b1.getIndex().getKey().toString() + ", "+ b1.getIndex().getValue().toString());
				b1.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Pair<Integer, Integer> moveIndex = ((GameButton)event.getSource()).getIndex();
						if (!g1.makeMove(moveIndex)) {
							myView.getItems().add("Player" + g1.getTurn() + " moved to " + moveIndex.getKey() +
									", " + moveIndex.getValue() + ". This is not a valid move. Pick one again");
							return;
						}
						myView.getItems().clear();
						myView.getItems().add("Player" + g1.getTurn() + " moved to " + moveIndex.getKey() +
								", " + moveIndex.getValue() + ". Player" + g1.otherPlayer() + "'s turn");
						if (g1.checkWin(moveIndex)) {
							primaryStage.setScene(endScene(primaryStage, g1.getTurn()));
						}
					}
				});
				grid.add(b1, j, i);
				g1.gameBoard[i][j] = b1;
				g1.legalMoves.add(new Pair<>(5, j));
			}
		}
		Scene scene = new Scene(new VBox(m1, myView, grid), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	
	public Scene instrScene() {
		Scene scene = new Scene(new VBox(), 700,700);
		return scene;
	}
	
	public Scene endScene(Stage primaryStage, int turn) {
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
		Scene scene = new Scene(new VBox(b1, b2, t1), 700,700);
		scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
		return scene;
	}
	

}
