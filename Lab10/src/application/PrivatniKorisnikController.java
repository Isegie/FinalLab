package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrivatniKorisnikController {
	@FXML
	private TextField unosImeID;

	@FXML
	private TextField unosPrezimeID;

	@FXML
	private TextField unosEmailID;

	@FXML
	private TextField unosTelefonID;

	@FXML
	private TableView<PrivatniKorisnik> tableViewID;

	@FXML
	private TableColumn<PrivatniKorisnik, String> imeID;

	@FXML
	private TableColumn<PrivatniKorisnik, String> prezimeID;

	@FXML
	private TableColumn<PrivatniKorisnik, String> emailID;

	@FXML
	private TableColumn<PrivatniKorisnik, String> telefonID;

	@FXML
	private Button buttonPrivatniKorID;

	List<PrivatniKorisnik> listaPriv = null;
	PrivatniKorisnik pko;
	ObservableList<PrivatniKorisnik> _listaPrivatnihKorisnika;

	@FXML
	public void initialize() {
		try {
			listaPriv = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(pko);
			_listaPrivatnihKorisnika = FXCollections.observableArrayList(listaPriv);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			;
			alert.showAndWait();
			e.printStackTrace();
		}

		tableViewID.setItems(_listaPrivatnihKorisnika);

		imeID.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("Ime"));
		prezimeID.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("Prezime"));
		emailID.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("Email"));
		telefonID.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("Telefon"));
	}

	@FXML
	void pretragaPrivatnogKorisnika(ActionEvent event) {
		List<PrivatniKorisnik> pretrazivanjeList = listaPriv;
		if (unosImeID.getText() != null) {
			List<PrivatniKorisnik> ime = new ArrayList<>();

			Predicate<PrivatniKorisnik> privatniPredicate = privatni -> privatni.getIme().toLowerCase()
					.contains(unosImeID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(privatniPredicate).forEach(a -> ime.add(a));

			pretrazivanjeList = ime;

		}
		if (unosPrezimeID.getText() != null) {
			List<PrivatniKorisnik> prezime = new ArrayList<>();

			Predicate<PrivatniKorisnik> privatniPredicate = privatni -> privatni.getIme().toLowerCase()
					.contains(unosImeID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(privatniPredicate).forEach(a -> prezime.add(a));

			pretrazivanjeList = prezime;
		}
		if (unosEmailID.getText() != null) {
			List<PrivatniKorisnik> email = new ArrayList<>();

			Predicate<PrivatniKorisnik> privatniPredicate = privatni -> privatni.getEmail().toLowerCase()
					.contains(unosEmailID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(privatniPredicate).forEach(a -> email.add(a));

			pretrazivanjeList = email;
		}
		if (unosTelefonID.getText() != null) {
			List<PrivatniKorisnik> telefon = new ArrayList<>();
			
			Predicate<PrivatniKorisnik> privatniPredicate = privatni -> privatni.getTelefon().toLowerCase()
					.contains(unosTelefonID.getText().toLowerCase());
			
			pretrazivanjeList.stream().filter(privatniPredicate).forEach(a -> telefon.add(a));

			pretrazivanjeList = telefon;
		}

		ObservableList<PrivatniKorisnik> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);

		tableViewID.setItems(listaPretrage);
	}
}
