package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class UnosProdajeController {
	@FXML
	private ComboBox<String> artiklComboId;

	@FXML
	private ComboBox<String> korisnikComboId;

	@FXML
	private DatePicker datumPickerId;

	@FXML
	private Button buttonUnosProdajeId;

	List<Prodaja> lisProdaja = null;
	Prodaja prod;
	ObservableList<Prodaja> listaProdaja;

	List<Artikl> listaArt = null;
	Artikl art;
	ObservableList<String> tempArtikli;

	List<Korisnik> listKor = null;
	Korisnik kor;
	ObservableList<String> tempKorisnici;

	
	Artikl at;
	Korisnik kr;
	LocalDate dt;

	@FXML
	public void initialize() {
		try {
			lisProdaja = BazaPodataka.dohvatiProdajuPremaKriterijima(prod);
			listaProdaja = FXCollections.observableArrayList(lisProdaja);

			listaArt = BazaPodataka.dohvatiArtiklePoKriterijima(art);
			listKor = BazaPodataka.dohvatiKorisnikePoKriterijima(kor);

			List<String> tempAr = new ArrayList<>();
			listaArt.forEach(s -> tempAr.add(s.toString()));

			tempArtikli = FXCollections.observableArrayList(tempAr);

			List<String> tempKr = new ArrayList<>();
			listKor.forEach(s -> tempKr.add(s.toString()));

			tempKorisnici = FXCollections.observableArrayList(tempKr);

		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}

		artiklComboId.setItems(tempArtikli);

		korisnikComboId.setItems(tempKorisnici);
	}

	@FXML
	void unosProdaje(ActionEvent event) {
		boolean provjera = true;
		if (artiklComboId.getSelectionModel().getSelectedItem().isEmpty()
				|| korisnikComboId.getSelectionModel().getSelectedItem().isEmpty()) {
			provjera = false;
		}
		if (!provjera) {
			Alert alert = new Alert(AlertType.ERROR);

			if (artiklComboId.getSelectionModel().isEmpty()) {
				alert.setContentText(alert.getContentText() + "Artikl je obavezan podatak!\n");
			}
			if (korisnikComboId.getSelectionModel().isEmpty()) {
				alert.setContentText(alert.getContentText() + "Korisnik je obavezan podatak!\n");
			}
			alert.showAndWait();
		}
		if (provjera) {
			
			for (Artikl a : listaArt) {
				if (a.toString().equals(artiklComboId.getSelectionModel().getSelectedItem().toString())) {
					at = a;
				}
			}

			for (Korisnik kor : listKor) {
				if (kor.toString().equals(korisnikComboId.getSelectionModel().getSelectedItem().toString())) {
					kr = kor;
				}
			}
			
			dt = datumPickerId.getValue();

			Prodaja prodaja = new Prodaja(at, kr, dt);

			try {
				BazaPodataka.pohraniNovuProdaju(prodaja);
			} catch (BazaPodatakaException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Neuspješno spremanje podataka!\n" + e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Podaci uspješno uneseni!");
			alert.showAndWait();

		}
	}
}
