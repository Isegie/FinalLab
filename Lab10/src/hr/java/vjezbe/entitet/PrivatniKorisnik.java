package hr.java.vjezbe.entitet;

import java.io.Serializable;

public class PrivatniKorisnik extends Korisnik implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ime;
	private String prezime;

	public PrivatniKorisnik(Long id, String ime, String prezime, String email, String telefon) {
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String dohvatiKontakt() {
		String dohvat = this.getIme() + " " + this.getPrezime() + ", mail: " + this.getEmail() + ", tel:"
				+ this.getTelefon();
		return dohvat;
	}

	@Override
	public String toString() {
		return getIme() + " " + getPrezime() + ", mail: " + getEmail() + ", tel:" + getTelefon();
	}
}
