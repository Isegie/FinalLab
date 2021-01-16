package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stan;
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

public class StanController {
	@FXML
	private TextField unosNaslovID;

	@FXML
	private TextField unosOpisID;

	@FXML
	private TextField unosCijenaID;

	@FXML
	private TextField unosKvadraturaID;

	@FXML
	private Button buttonPretražiID;

	@FXML
	private TableView<Stan> tableViewID;

	@FXML
	private TableColumn<Stan, String> naslovColID;

	@FXML
	private TableColumn<Stan, String> opisColID;

	@FXML
	private TableColumn<Stan, String> cijenaColID;

	@FXML
	private TableColumn<Stan, String> kvadraturaColID;

	@FXML
	private TableColumn<Stan, String> stanjeColID;

	List<Stan> lisStan = null;
	Stan st;
	ObservableList<Stan> listaStanova;

	@FXML
	public void initialize() {

		try {
			lisStan = BazaPodataka.dohvatiStanovePremaKriterijima(st);
			listaStanova = FXCollections.observableArrayList(lisStan);
		} catch (BazaPodatakaException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Neuspješno čitanje podataka!\n" + e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
		tableViewID.setItems(listaStanova);

		naslovColID.setCellValueFactory(new PropertyValueFactory<Stan, String>("Naslov"));
		opisColID.setCellValueFactory(new PropertyValueFactory<Stan, String>("Opis"));
		cijenaColID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stan, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Stan, String> Cijena) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Cijena.getValue().getCijena().toString());
						return property;
					}
				});
		kvadraturaColID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stan, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Stan, String> Kvadratura) {
						SimpleStringProperty property = new SimpleStringProperty();
						String kvadratura = Kvadratura.getValue().getKvadratura() + "";
						property.setValue(kvadratura);
						return property;
					}
				});
		stanjeColID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stan, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Stan, String> Stanje) {
						SimpleStringProperty property = new SimpleStringProperty();
						property.setValue(Stanje.getValue().getStanje().toString());
						return property;
					}
				});
	}

	@FXML
	void pretragaStanova(ActionEvent event) {
		List<Stan> pretrazivanjeList = lisStan;
		if (unosNaslovID.getText() != null) {
			List<Stan> nas = new ArrayList<>();

			Predicate<Stan> stanPredicate = stan -> stan.getNaslov().toLowerCase()
					.contains(unosNaslovID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(stanPredicate).forEach(a -> nas.add(a));

			pretrazivanjeList = nas;

		}
		if (unosOpisID.getText() != null) {
			List<Stan> opi = new ArrayList<>();

			Predicate<Stan> stanPredicate = stan -> stan.getOpis().toLowerCase()
					.contains(unosOpisID.getText().toLowerCase());

			pretrazivanjeList.stream().filter(stanPredicate).forEach(a -> opi.add(a));

			pretrazivanjeList = opi;
		}
		if (unosKvadraturaID.getText() != null) {
			List<Stan> kvadr = new ArrayList<>();

			for (Stan stan : pretrazivanjeList) {
				String sn = "" + stan.getKvadratura();
				if (sn.toLowerCase().contains(unosKvadraturaID.getText().toLowerCase())) {
					kvadr.add(stan);
				}
			}
			pretrazivanjeList = kvadr;
		}
		if (unosCijenaID.getText() != null) {
			List<Stan> cijena = new ArrayList<>();
			
			Predicate<Stan> stanPredicate = stan -> stan.getCijena().toString().toLowerCase()
					.contains(unosCijenaID.getText().toLowerCase());
			
			pretrazivanjeList.stream().filter(stanPredicate).forEach(a -> cijena.add(a));

			pretrazivanjeList = cijena;
		}

		ObservableList<Stan> listaPretrage = FXCollections.observableArrayList(pretrazivanjeList);

		tableViewID.setItems(listaPretrage);
	}

}
