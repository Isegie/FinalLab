package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
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

public class AutomobilController {

	@FXML
	private TextField autoNasID;

	@FXML
	private TextField autoOpisID;

	@FXML
	private TextField snagaAutID;

	@FXML
	private TextField autoCijenaID;

	@FXML
	private Button buttonPretraziAuto;

	@FXML
	private TableView<Automobil> tableViewID;

	@FXML
	private TableColumn<Automobil, String> naslovCol;

	@FXML
	private TableColumn<Automobil, String> opisIdCol;

	@FXML
	private TableColumn<Automobil, String> snagaIdCol;

	@FXML
	private TableColumn<Automobil, String> cijenaIdCol;

	@FXML
	private TableColumn<Automobil, String> stanjeIDCol;

	List<Automobil> lisAuto = null;
	Automobil autos;
	ObservableList<Automobil> listaAutom;

	@FXML
	public void initialize() {
		try {
			lisAuto = BazaPodataka.dohvatiAutomobilePremaKriterijima(autos);
			listaAutom = FXCollections.observableArrayList(lisAuto);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
		tableViewID.setItems(listaAutom);

		naslovCol.setCellValueFactory(new PropertyValueFactory<Automobil, String>("Naslov"));
		opisIdCol.setCellValueFactory(new PropertyValueFactory<Automobil, String>("Opis"));
		snagaIdCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Automobil, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Automobil, String> Snaga) {
						SimpleStringProperty property = new SimpleStringProperty();
						String snaga = Snaga.getValue().getSnagaKs().toString() + "";
						property.setValue(snaga);
						return property;
					}
				});
		cijenaIdCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Automobil, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Automobil, String> Cijena) {
						SimpleStringProperty property = new SimpleStringProperty();
						String cijena = Cijena.getValue().getCijena().toString() + "";
						property.setValue(cijena);
						return property;
					}
				});
		stanjeIDCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Automobil, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Automobil, String> Stanje) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Stanje.getValue().getStanje().toString());
						return property;
					}
				});
	}

	@FXML
	void pretragaAutomobila(ActionEvent event) {
		List<Automobil> pretrazivanjeList = lisAuto;
		if (autoNasID.getText() != null) {
			List<Automobil> nas = new ArrayList<>();

			Predicate<Automobil> filterAuto = automobil -> automobil.getNaslov().toLowerCase()
					.contains(autoNasID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(filterAuto).forEach(a -> nas.add(a));

			pretrazivanjeList = nas;
		}
		if (autoOpisID.getText() != null) {
			List<Automobil> opi = new ArrayList<>();

			Predicate<Automobil> filterAuto = automobil -> automobil.getOpis().toLowerCase()
					.contains(autoOpisID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(filterAuto).forEach(a -> opi.add(a));

			pretrazivanjeList = opi;
		}
		if (snagaAutID.getText() != null) {
			List<Automobil> snaga = new ArrayList<>();

			for (Automobil automobil : pretrazivanjeList) {
				String sn = "" + automobil.getSnagaKs();
				if (sn.toLowerCase().contains(snagaAutID.getText().toLowerCase())) {
					snaga.add(automobil);
				}
			}
			pretrazivanjeList = snaga;
		}
		if (autoCijenaID.getText() != null) {
			List<Automobil> cijena = new ArrayList<>();

			Predicate<Automobil> filterAuto = automobil -> automobil.getCijena().toString().toLowerCase()
					.contains(autoCijenaID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(filterAuto).forEach(a -> cijena.add(a));

			pretrazivanjeList = cijena;
		}

		ObservableList<Automobil> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);

		tableViewID.setItems(listaPretrage);

	}

}
