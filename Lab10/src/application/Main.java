package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private static Stage mainStage;
	private static BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			root = (BorderPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 600, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image(getClass().getResourceAsStream("tvzlogo.png"));
			mainStage.getIcons().add(icon);
			mainStage.setScene(scene);
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void setCenterPane(BorderPane bp) {
		root.setCenter(bp);
	}

	public static Stage getMainStage() {
		return mainStage;
	}
}
