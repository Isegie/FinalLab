/**
 * @author Ivan Segota
 */
package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Stan extends Artikl implements Nekretnina {

	private static final long serialVersionUID = 1L;

	// private static final Logger logger = LoggerFactory.getLogger(Datoteke.class);

	private int kvadratura;

	public Stan(Long id, String naslov, String opis, BigDecimal cijena, int kvadratura, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.kvadratura = kvadratura;
	}

	public int getKvadratura() {
		return kvadratura;
	}

	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}

	@Override
	public String tekstOglasa() {
		try {
			return this.getNaslov() + ", " + this.getOpis() + ", " + this.getKvadratura() + ", " + this.getCijena()
					+ ", stanje: " + this.getStanje();
		} catch (Throwable t) {
			// logger.error(t.getMessage(), t.getCause());
		}
		return "Naslov nekretnine: " + this.getNaslov() + "\n" + "Opis nekretnine: " + this.getOpis() + "\n"
				+ "Kvadratura nekretnine: " + this.getKvadratura() + "\n" + "Porez na nekretnine: "
				+ " Cijena ne smije biti manja od 10000kn!" + "\n" + "Cijena nekretnine: " + this.getCijena() + "\n"
				+ "Stanje nekretnine: " + this.getStanje();

	}

	@Override
	public String toString() {
		return getNaslov() + ", " + getOpis() + ", " + getKvadratura() + ", " + getCijena() + ", stanje: "
				+ getStanje();
	}

}
