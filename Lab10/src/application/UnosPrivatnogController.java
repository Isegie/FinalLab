package application;

import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UnosPrivatnogController {
	@FXML
	private TextField unosImenaText;

	@FXML
	private TextField unosPrezimenaText;

	@FXML
	private TextField unosEmailText;

	@FXML
	private TextField unosTelefonText;

	@FXML
	private Button unosPrivatnogButton;

	List<PrivatniKorisnik> listaPriv = null;
	PrivatniKorisnik pko;

	@FXML
	public void initialize() {
		try {
			listaPriv = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(pko);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n"+e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void unosPrivatnogKorisnika(ActionEvent event) {
		boolean provjera = true;

		OptionalLong maxId = listaPriv.stream().mapToLong(Entitet::getId).max();
		if (unosImenaText.getText().length() == 0 || unosPrezimenaText.getText().length() == 0
				|| unosEmailText.getText().length() == 0 || unosTelefonText.getText().length() == 0) {
			provjera = false;
		}
		if (!provjera) {
			Alert alert = new Alert(AlertType.ERROR);

			if (unosImenaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Ime je obavezan podatak!\n");
			}
			if (unosPrezimenaText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Prezime je obavezan podatak!\n");
			}
			if (unosEmailText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "E-mail je obavezan podatak!\n");
			}
			if (unosTelefonText.getText().length() == 0) {
				alert.setContentText(alert.getContentText() + "Telefon je obavezan podatak!\n");
			}

			alert.showAndWait();
		}
		if (provjera) {

			PrivatniKorisnik privatni = new PrivatniKorisnik(maxId.getAsLong() + 1, unosImenaText.getText(),
					unosPrezimenaText.getText(), unosEmailText.getText(), unosTelefonText.getText());

			try {
				BazaPodataka.pohraniNovogPrivatnogKorisnika(privatni);
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
