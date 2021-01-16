/** 
 * @author Ivan Segota
 */
package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

public class Automobil extends Artikl implements Vozilo, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((snagaKs == null) ? 0 : snagaKs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automobil other = (Automobil) obj;
		if (snagaKs == null) {
			if (other.snagaKs != null)
				return false;
		} else if (!snagaKs.equals(other.snagaKs))
			return false;
		return true;
	}

//	private static final Logger logger = LoggerFactory.getLogger(Datoteke.class);
	private BigDecimal snagaKs;

	public Automobil(Long id, String naslov, String opis, BigDecimal cijena, BigDecimal snagaKs, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.snagaKs = snagaKs;
	}

	public BigDecimal getSnagaKs() {
		return snagaKs;
	}

	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}

	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		if (izracunajKw(snagaKs).intValue() <= 50 && izracunajKw(snagaKs).intValue() > 0) {
			return new BigDecimal(1);
		} else if (izracunajKw(snagaKs).intValue() > 50 && izracunajKw(snagaKs).intValue() < 150) {
			return new BigDecimal(2);
		} else if (izracunajKw(snagaKs).intValue() > 150 && izracunajKw(snagaKs).intValue() < 200) {
			return new BigDecimal(3);
		} else if (izracunajKw(snagaKs).intValue() > 200 && izracunajKw(snagaKs).intValue() < 300) {
			return new BigDecimal(4);
		} else if (izracunajKw(snagaKs).intValue() > 300 && izracunajKw(snagaKs).intValue() < 800) {
			return new BigDecimal(5);
		} else {
			throw new NemoguceOdreditiGrupuOsiguranjaException("PreviÅ¡e kw, ne mogu odrediti grupu osiguranja.");
		}

	}

	@Override
	public String tekstOglasa() {

		try {
			String oglas = this.getNaslov() + ", " + this.getOpis() + ", snaga: " + ", "
					+ izracunajKw(this.snagaKs).intValue() + ", " + " cijena: " + this.getCijena() + ", stanje: "
					+ this.getStanje().name();
			return oglas;
		} catch (Throwable e) {

		}

		return "Naslov automobila: " + this.getNaslov() + "\n" + "Opis automobila: " + this.getOpis() + "\n"
				+ "Snaga automobila: " + izracunajKw(this.snagaKs).intValue() + "\n" + "Izračun osiguranja automobila: "
				+ "Previse kw, ne mogu odrediti grupu osiguranja." + "\n" + "Cijena automobila: " + this.getCijena()
				+ "\n" + "Stanje automobila: " + this.getStanje();
	}

	@Override
	public String toString() {
		return getNaslov() + ", " + getOpis() + ", snaga: " + ", " + izracunajKw(snagaKs).intValue() + ", "
				+ " cijena: " + getCijena() + ", stanje: " + getStanje().name();
	}

}
