package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

public class UslugaController {
	@FXML
	private TextField unosNaslovID;

	@FXML
	private TextField unosOpisID;

	@FXML
	private TextField unosCijenaID;

	@FXML
	private Button buttonPretraziID;

	@FXML
	private TableView<Usluga> tableViewID;

	@FXML
	private TableColumn<Usluga, String> naslovColID;

	@FXML
	private TableColumn<Usluga, String> opisColID;

	@FXML
	private TableColumn<Usluga, String> cijenaColID;

	@FXML
	private TableColumn<Usluga, String> stanjeColID;

	List<Usluga> lisUs = null;
	Usluga uslug;
	ObservableList<Usluga> listaUsluge;

	@FXML
	public void initialize() {
		try {
			lisUs = BazaPodataka.dohvatiUslugePremaKriterijima(uslug);
			listaUsluge = FXCollections.observableArrayList(lisUs);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}

		tableViewID.setItems(listaUsluge);

		naslovColID.setCellValueFactory(new PropertyValueFactory<Usluga, String>("Naslov"));
		opisColID.setCellValueFactory(new PropertyValueFactory<Usluga, String>("Opis"));
		cijenaColID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> Cijena) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Cijena.getValue().getCijena().toString());
						return property;
					}
				});
		stanjeColID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Usluga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Usluga, String> Stanje) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Stanje.getValue().getStanje().toString());
						return property;
					}
				});

	}

	@FXML
	void pretragaUsluga(ActionEvent event) {
		List<Usluga> pretrazivanjeList = lisUs;
		if (unosNaslovID.getText() != null) {
			List<Usluga> nas = new ArrayList<>();

			Predicate<Usluga> uslugaPredicate = usluga -> usluga.getNaslov().toLowerCase()
					.contains(unosNaslovID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(uslugaPredicate).forEach(a -> nas.add(a));

			pretrazivanjeList = nas;

		}
		if (unosOpisID.getText() != null) {
			List<Usluga> opi = new ArrayList<>();
			
			Predicate<Usluga> uslugaPredicate = usluga -> usluga.getOpis().toLowerCase()
					.contains(unosOpisID.getText().toLowerCase());
			
			pretrazivanjeList.stream().filter(uslugaPredicate).forEach(a -> opi.add(a));

			pretrazivanjeList = opi;

		}
		if (unosCijenaID.getText() != null) {
			List<Usluga> kvadr = new ArrayList<>();

			for (Usluga usluga : pretrazivanjeList) {
				String sn = "" + usluga.getCijena();
				if (sn.toLowerCase().contains(unosCijenaID.getText().toLowerCase())) {
					kvadr.add(usluga);
				}
			}
			pretrazivanjeList = kvadr;
		}

		ObservableList<Usluga> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);

		tableViewID.setItems(listaPretrage);
	}
}
