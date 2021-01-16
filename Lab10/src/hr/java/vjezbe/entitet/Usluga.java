package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Usluga extends Artikl {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
	}

	@Override
	public String toString() {
		return getNaslov() + ", " + getOpis() + ",cijena: " + getCijena() + ", stanje: " + getStanje().name();
	}

	@Override
	public String tekstOglasa() {

		return this.getNaslov() + ", " + this.getOpis() + ",cijena: " + this.getCijena() + ", stanje: "
				+ this.getStanje().name();
	}

}
