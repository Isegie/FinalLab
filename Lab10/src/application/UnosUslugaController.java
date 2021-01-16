package application;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UnosUslugaController {

	@FXML
	private TextField unosNaslovText;

	@FXML
	private TextField unosOpisText;

	@FXML
	private TextField unosCijenaText;

	@FXML
	private ChoiceBox<Stanje> stanjeOdabirID;

	@FXML
	private Button unosUslugaButton;

	List<Usluga> lisUs = null;
	Usluga uslug;

	@FXML
	public void initialize() {

		stanjeOdabirID.setItems(FXCollections.observableArrayList(Stanje.values()));

		try {
			lisUs = BazaPodataka.dohvatiUslugePremaKriterijima(uslug);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n"+e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void unesiUslugu(ActionEvent event) {
		OptionalLong maxId = lisUs.stream().mapToLong(Entitet::getId).max();
		boolean provjera = true;
		if (unosNaslovText.getText().length() == 0 || unosOpisText.getText().length() == 0
				|| unosCijenaText.getText().length() == 0) {
			provjera = false;
		}
		if (!provjera) {
			Alert alert = new Alert(AlertType.ERROR);

			if (unosNaslovText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Naslov je obavezan podatak!\n");
			}
			if (unosOpisText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Opis je obavezan podatak!\n");
			}
			if (unosCijenaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Cijena je obavezan podatak!\n");
			}

			alert.showAndWait();
		}
		if (provjera) {

			Usluga usluga = new Usluga(maxId.getAsLong() + 1, unosNaslovText.getText(), unosOpisText.getText(),
					new BigDecimal(unosCijenaText.getText()), stanjeOdabirID.getValue());
			
			try {
				BazaPodataka.pohraniNovuUslugu(usluga);
			} catch (BazaPodatakaException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Neuspješno spremanje podataka!\n"+e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Podaci uspješno uneseni!");
			alert.showAndWait();

		}
	}
}
