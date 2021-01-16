package application;

import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UnosPoslovnogController {
	@FXML
	private TextField unosNazivText;

	@FXML
	private TextField unosEmailText;

	@FXML
	private TextField unosWebText;

	@FXML
	private TextField unosTelefonText;

	@FXML
	private Button unosPoslovnogButton;

	List<PoslovniKorisnik> listaPoslovnih = null;
	PoslovniKorisnik poslovniKor;

	@FXML
	public void initialize() {
		try {
			listaPoslovnih = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovniKor);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n"+e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void unosPoslovnogKorisnika(ActionEvent event) {
		boolean provjera = true;

		OptionalLong maxId = listaPoslovnih.stream().mapToLong(Entitet::getId).max();
		if (unosNazivText.getText().length() == 0 || unosEmailText.getText().length() == 0
				|| unosTelefonText.getText().length() == 0 || unosWebText.getText().length() == 0) {
			provjera = false;
		}
		if (!provjera) {
			Alert alert = new Alert(AlertType.ERROR);

			if (unosNazivText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Naziv je obavezan podatak!\n");
			}
			if (unosEmailText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "E-mail je obavezan podatak!\n");
			}
			if (unosTelefonText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Telefon je obavezan podatak!\n");
			}
			if (unosWebText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Web je obavezan podatak!\n");
			}
			alert.showAndWait();
		}
		if (provjera) {

			PoslovniKorisnik poslovni = new PoslovniKorisnik(maxId.getAsLong() + 1, unosEmailText.getText(),
					unosTelefonText.getText(), unosNazivText.getText(), unosWebText.getText());

			try {
				BazaPodataka.pohraniNovogPoslovnogKorisnika(poslovni);
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
