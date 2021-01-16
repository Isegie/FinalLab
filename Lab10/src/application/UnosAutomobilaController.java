package application;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UnosAutomobilaController {

	@FXML
	private TextField unosNaslovText;

	@FXML
	private TextField unosOpisText;

	@FXML
	private TextField unosSnagaText;

	@FXML
	private TextField unosCijenaText;

	@FXML
	private ChoiceBox<Stanje> stanjeChoiceBoxID;

	@FXML
	private Button unesiButtonID;


	
	List<Automobil> lisAuto = null;
	Automobil autos;
	
	@FXML
	public void initialize() {

		stanjeChoiceBoxID.setItems(FXCollections.observableArrayList(Stanje.values()));
		try {
			lisAuto = BazaPodataka.dohvatiAutomobilePremaKriterijima(autos);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n"+e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void unesiAutomobil(ActionEvent event) {
	
		OptionalLong maxId = lisAuto.stream().mapToLong(Entitet::getId).max();
		boolean provjera = true;
		if (unosNaslovText.getText().length() == 0 || unosOpisText.getText().length() == 0
				|| unosCijenaText.getText().length() == 0 || unosSnagaText.getText().length() == 0) {
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
			if (unosSnagaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Snaga je obavezan podatak!\n");
			}

			alert.showAndWait();
		}
		if (provjera) {
			Automobil auto = new Automobil(maxId.getAsLong() + 1, unosNaslovText.getText(), unosOpisText.getText(),
					new BigDecimal(unosCijenaText.getText()), new BigDecimal(unosSnagaText.getText()),
					stanjeChoiceBoxID.getValue());
			try {
				BazaPodataka.pohraniNoviAutomobil(auto);
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
