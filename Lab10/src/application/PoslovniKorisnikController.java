package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
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

public class PoslovniKorisnikController {
	@FXML
	private TextField unosEmailID;

	@FXML
	private TextField unosTelID;

	@FXML
	private TextField unosNazivID;

	@FXML
	private TextField unosWebID;

	@FXML
	private Button buttonPretraziID;

	@FXML
	private TableView<PoslovniKorisnik> tableViewID;

	@FXML
	private TableColumn<PoslovniKorisnik, String> emailColID;

	@FXML
	private TableColumn<PoslovniKorisnik, String> telefonColID;

	@FXML
	private TableColumn<PoslovniKorisnik, String> nazivColID;

	@FXML
	private TableColumn<PoslovniKorisnik, String> webColID;

	List<PoslovniKorisnik> listaPoslovnih = null;
	PoslovniKorisnik poslovniKor;
	ObservableList<PoslovniKorisnik> _listaPoslovnihKorisnika;

	@FXML
	public void initialize() {
		try {
			listaPoslovnih = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovniKor);
			_listaPoslovnihKorisnika = FXCollections.observableArrayList(listaPoslovnih);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
		tableViewID.setItems(_listaPoslovnihKorisnika);

		nazivColID.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("Naziv"));
		emailColID.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("Email"));
		webColID.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("Web"));
		telefonColID.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("Telefon"));
	}

	@FXML
	void pretragaPoslovnihKorisnika(ActionEvent event) {
		List<PoslovniKorisnik> pretrazivanjeList = listaPoslovnih;

		if (unosNazivID.getText() != null) {
			List<PoslovniKorisnik> naziv = new ArrayList<>();

			Predicate<PoslovniKorisnik> poslovniPredicate = poslovni -> poslovni.getNaziv().toLowerCase()
					.contains(unosNazivID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(poslovniPredicate).forEach(a -> naziv.add(a));

			pretrazivanjeList = naziv;

		}
		if (unosEmailID.getText() != null) {
			List<PoslovniKorisnik> email = new ArrayList<>();

			Predicate<PoslovniKorisnik> poslovniPredicate = poslovni -> poslovni.getEmail().toLowerCase()
					.contains(unosEmailID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(poslovniPredicate).forEach(a -> email.add(a));

			pretrazivanjeList = email;
		}
		if (unosTelID.getText() != null) {
			List<PoslovniKorisnik> telefon = new ArrayList<>();

			Predicate<PoslovniKorisnik> poslovniPredicate = poslovni -> poslovni.getTelefon().toLowerCase()
					.contains(unosTelID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(poslovniPredicate).forEach(a -> telefon.add(a));
			pretrazivanjeList = telefon;
		}
		if (unosWebID.getText() != null) {
			List<PoslovniKorisnik> web = new ArrayList<>();

			Predicate<PoslovniKorisnik> poslovniPredicate = poslovni -> poslovni.getWeb().toLowerCase()
					.contains(unosWebID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(poslovniPredicate).forEach(a -> web.add(a));

			pretrazivanjeList = web;
		}

		ObservableList<PoslovniKorisnik> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);

		tableViewID.setItems(listaPretrage);
	}
}
