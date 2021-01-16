
package hr.java.vjezbe.entitet;

public class PoslovniKorisnik extends Korisnik {

	private static final long serialVersionUID = 1L;
	private String naziv;
	private String web;

	public PoslovniKorisnik(Long id, String email, String telefon) {
		super(id, email, telefon);
	}

	public PoslovniKorisnik(Long id, String email, String telefon, String naziv, String web) {
		super(id, email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public String dohvatiKontakt() {
		String dohvat = this.getNaziv() + ", mail: " + this.getEmail() + ",web: " + this.getWeb() + ",tel: "
				+ this.getTelefon();
		return dohvat;
	}

	@Override
	public String toString() {
		return getNaziv() + ", mail: " + getEmail() + ",web: " + getWeb() + ",tel: " + getTelefon();
	}

}
