package application;

import java.io.IOException;

import hr.java.vjezbe.niti.DatumObjaveNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainController {
	@FXML
	private MenuItem autoPretragaID;

	@FXML
	private MenuItem unosAutomobilaID;

	@FXML
	private MenuItem pretragaStanID;

	@FXML
	private MenuItem unosStanovaId;

	@FXML
	private MenuItem pretragaUslID;

	@FXML
	private MenuItem unosUslugeID;

	@FXML
	private MenuItem pretragaPrivID;

	@FXML
	private MenuItem unosPrivatnogID;

	@FXML
	private MenuItem pretragaPoslID;

	@FXML
	private MenuItem unosPoslovnogID;

	@FXML
	private MenuItem pretragaProdajeId;

	@FXML
	private MenuItem unosProdajeId;

	@FXML
	void pokreniNit(ActionEvent event) {

		Timeline prikazZadnjeProdaje = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new DatumObjaveNit());
			}
		}));
		prikazZadnjeProdaje.setCycleCount(Timeline.INDEFINITE);
		prikazZadnjeProdaje.play();

	}

	@FXML
	void pretragaAutomobila(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Automobili.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pretragaPoslovnihKorisnika(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("PoslovniKorisnici.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pretragaPrivatnihKorisnika(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("PrivatniKorisnici.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pretragaStanova(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Stanovi.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pretragaUsluga(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Usluge.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prikaziMainEkran() {

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 600, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.getMainStage().setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// UNOSI VRIJEDNOSTI
	@FXML
	void unosAutomobila(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosAutomobila.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void unosPoslovnogKorisnika(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosPoslovnogKorisnika.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void unosPrivatnogKorisnika(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosPrivatnogKorisnika.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void unosStanova(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosStana.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void unosUsluge(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosUsluga.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pretragaProdaje(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("PretragaProdaje.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void unosProdaje(ActionEvent event) {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosProdaje.fxml"));
			Main.setCenterPane(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
