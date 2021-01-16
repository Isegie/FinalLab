package application;

import java.util.ArrayList;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ProdajaController {

	@FXML
	private DatePicker datePickerId;

	@FXML
	private Button buttonPretraziId;

	@FXML
	private TableView<Prodaja> tableViewId;

	@FXML
	private TableColumn<Prodaja, String> oglasColId;

	@FXML
	private TableColumn<Prodaja, String> korisnikColId;

	@FXML
	private TableColumn<Prodaja, String> datumColId;

	@FXML
	private ComboBox<String> artiklComboId;

	@FXML
	private ComboBox<String> korisnikComboId;
	
	List<Prodaja> lisProdaja = null;
	Prodaja prod;
	ObservableList<Prodaja> listaProdaja;

	List<Artikl> listaArt = null;
	Artikl art;
	ObservableList<String> tempArtikli;

	List<Korisnik> listKor = null;
	Korisnik kor;
	ObservableList<String> tempKorisnici;

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

		tableViewId.setItems(listaProdaja);

		oglasColId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prodaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Prodaja, String> Oglas) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Oglas.getValue().getArtikl().toString());
						return property;
					}
				});
		korisnikColId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prodaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Prodaja, String> Korisnik) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Korisnik.getValue().getKorisnik().toString());
						return property;
					}
				});
		datumColId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prodaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Prodaja, String> Datum) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Datum.getValue().getDatumObjave().toString());
						return property;
					}
				});
	}

	@FXML
	void pretragaProdaje(ActionEvent event) {
		List<Prodaja> pretrazivanjeList = lisProdaja;
		List<Prodaja> pretrazivanjeList1 = lisProdaja;
		List<Prodaja> pretrazivanjeList2 = lisProdaja;

		if (artiklComboId.getSelectionModel().selectedItemProperty().getValue() != null) {
			List<Prodaja> arts = new ArrayList<>();

			pretrazivanjeList.stream()
					.filter(a -> a.getArtikl().toString()
							.contains(artiklComboId.getSelectionModel().selectedItemProperty().getValue().toString()))
					.forEach(a -> arts.add(a));

			pretrazivanjeList = arts;

			ObservableList<Prodaja> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);
			tableViewId.setItems(listaPretrage);

		}

		if (korisnikComboId.getSelectionModel().selectedItemProperty().getValue() != null) {
			List<Prodaja> koris = new ArrayList<>();

			pretrazivanjeList1.stream()
					.filter(k -> k.getKorisnik().toString()
							.contains(korisnikComboId.getSelectionModel().selectedItemProperty().getValue().toString()))
					.forEach(kr -> koris.add(kr));

			pretrazivanjeList1 = koris;

			ObservableList<Prodaja> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList1);
			tableViewId.setItems(listaPretrage);
		}

		if (datePickerId.getValue() != null) {
			List<Prodaja> dats = new ArrayList<>();
			pretrazivanjeList2.stream()
					.filter(d -> d.getDatumObjave().toString().equals(datePickerId.getValue().toString()))
					.forEach(dt -> dats.add(dt));

			pretrazivanjeList2 = dats;

			ObservableList<Prodaja> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList2);
			tableViewId.setItems(listaPretrage);
		}

	}
}
