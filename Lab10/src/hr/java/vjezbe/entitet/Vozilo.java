package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

public interface Vozilo {

	default BigDecimal izracunajKw(BigDecimal konjskaSnaga) {
		BigDecimal hp = new BigDecimal(0.745);
		BigDecimal kiloWat = hp.multiply(konjskaSnaga);
		return kiloWat;
	}

	BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;

	default BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		BigDecimal povrat;
		BigDecimal vrijednost = izracunajGrupuOsiguranja();
		int v = vrijednost.intValue();
		switch (v) {
		case 1:
			povrat = new BigDecimal(500);
			break;
		case 2:
			povrat = new BigDecimal(2000);
			break;
		case 3:
			povrat = new BigDecimal(8000);
			break;
		case 4:
			povrat = new BigDecimal(14000);
			break;
		case 5:
			povrat = new BigDecimal(20000);
			break;
		default:
			povrat = new BigDecimal(0);
			break;
		}
		return povrat;
	}
}
