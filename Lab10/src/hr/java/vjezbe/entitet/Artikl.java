/**
 * @author Ivan Segota
 */
package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Scanner;;

public abstract class Artikl extends Entitet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Stanje stanje;

	public Artikl(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.setStanje(stanje);
	}

	public String getNaslov() {
		return naslov;
	}


	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cijena == null) ? 0 : cijena.hashCode());
		result = prime * result + ((naslov == null) ? 0 : naslov.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((stanje == null) ? 0 : stanje.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikl other = (Artikl) obj;
		if (cijena == null) {
			if (other.cijena != null)
				return false;
		} else if (!cijena.equals(other.cijena))
			return false;
		if (naslov == null) {
			if (other.naslov != null)
				return false;
		} else if (!naslov.equals(other.naslov))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (stanje != other.stanje)
			return false;
		return true;
	}

	public abstract String tekstOglasa();

	
	public static Artikl unosArtikla(Scanner unos, int i, int br) {
		return null;
/*
		if (i == 1) {
			System.out.println("Unesite naslov " + (br + 1) + " oglasa artikla: ");
			String _naslov = unos.next();
			System.out.println("Unesite opis " + (br + 1)+ " oglasa artikla: ");
			String _opis = unos.next();
			unos.nextLine();
			System.out.println("Unesite cijenu " + (br + 1) + " oglasa artikla:");
			BigDecimal cijena = Datoteke.UnosBigDecimala(unos);
			System.out.println("Unesite stanje " + (br + 1) + " oglasa artikla: ");
			Stanje _stanje = Datoteke.unosStanja(unos);
			return new Usluga((long) (br + 1),_naslov, _opis, cijena, _stanje);
		} else if (i == 2) {
			System.out.println("Unesite naslov " + (br + 1) + ". oglasa automobila: ");
			String naslov = unos.next();
			System.out.println("Unesite opis " + (br + 1) + ". oglasa automobila: ");
			String opis = unos.next();
			System.out.println("Unesite snagu " + (br + 1) + ". (u ks) oglasa automobila: ");
			int snaga =Datoteke.UnosBroja(unos);
			BigDecimal _snaga = new BigDecimal(snaga);
			System.out.println("Unesite cijenu " + (br + 1) + ". oglasa automobila: ");
			BigDecimal _cijena = Datoteke.UnosBigDecimala(unos);
			System.out.println("Unesite stanje " + i + " oglasa artikla: ");
			Stanje _stanje = Datoteke.unosStanja(unos);
			return new Automobil((long) (br + 1),naslov, opis, _cijena, _snaga, _stanje);
		} else if (i == 3) {
			System.out.println("Unesite naslov " + (br + 1) + ". stana: ");
			String naslov = unos.next();
			System.out.println("Unesite opis " + (br + 1) + ". stana: ");
			String opis = unos.next();
			System.out.println("Unesite cijenu " + (br + 1) + ". stana: ");
			BigDecimal _cijena = Datoteke.UnosBigDecimala(unos);
			System.out.println("Unesite kvadraturu " + (br + 1) + ". stana");
			int kvadratura = Datoteke.UnosBroja(unos);
			System.out.println("Unesite stanje " + i + " oglasa artikla: ");
			Stanje _stanje = Datoteke.unosStanja(unos);
			return new Stan((long) (br + 1),naslov, opis, _cijena, kvadratura, _stanje);
		} else {
			return null;
		}
		*/

	}

}
