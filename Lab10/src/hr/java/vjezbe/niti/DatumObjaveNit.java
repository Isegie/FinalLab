package hr.java.vjezbe.niti;

import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatumObjaveNit implements Runnable {

	Alert alert = new Alert(AlertType.INFORMATION);
	Prodaja zadnjaProdaja = null;
	List<Prodaja> temp;

	@Override
	public void run() {

		try {
			temp = BazaPodataka.dohvatiZadnjuProdaju(zadnjaProdaja);

		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}

		zadnjaProdaja = temp.get(0);
		
		alert.setHeaderText(
				zadnjaProdaja.getArtikl().toString() + ", " + zadnjaProdaja.getKorisnik().dohvatiKontakt()
						+ ", " + zadnjaProdaja.getDatumObjave().toString());
		alert.show();


	}
}
