package application;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stan;
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

public class UnosStanaController {
	@FXML
	private TextField unosNaslovaText;

	@FXML
	private TextField unosOpisaText;

	@FXML
	private TextField unosKvadratureText;

	@FXML
	private ChoiceBox<Stanje> stanjeChoiceId;

	@FXML
	private TextField unosCijenaText;

	@FXML
	private Button unesiStanButtonID;

	List<Artikl> listItems;

	List<Stan> lisStan = null;
	Stan stn;

	@FXML
	public void initialize() {

		stanjeChoiceId.setItems(FXCollections.observableArrayList(Stanje.values()));
		try {
			lisStan = BazaPodataka.dohvatiStanovePremaKriterijima(stn);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n"+e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void unosStana(ActionEvent event) {


		OptionalLong maxId = lisStan.stream().mapToLong(Entitet::getId).max();

		boolean provjera = true;
		if (unosNaslovaText.getText().length() == 0 || unosOpisaText.getText().length() == 0
				|| unosCijenaText.getText().length() == 0 || unosKvadratureText.getText().length() == 0) {
			provjera = false;
		}
		if (!provjera) {
			Alert alert = new Alert(AlertType.ERROR);

			if (unosNaslovaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Naslov je obavezan podatak!\n");
			}
			if (unosOpisaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Opis je obavezan podatak!\n");
			}
			if (unosCijenaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Cijena je obavezan podatak!\n");
			}
			if (unosKvadratureText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Kvadratura je obavezan podatak!\n");
			}

			alert.showAndWait();
		}
		if (provjera) {
				Stan stan = new Stan(maxId.getAsLong() + 1, unosNaslovaText.getText(), unosOpisaText.getText(),
						new BigDecimal(unosCijenaText.getText()),
						Integer.parseInt(unosKvadratureText.getText().toString()), stanjeChoiceId.getValue());
				
				try {
					BazaPodataka.pohraniNoviStan(stan);
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
