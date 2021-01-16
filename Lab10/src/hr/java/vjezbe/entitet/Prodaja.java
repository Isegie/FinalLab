/**
 * @author Ivan Segota
 */
package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;

public class Prodaja extends Entitet implements Serializable {

	private static final long serialVersionUID = 1L;
	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	static Long id;

	public Prodaja(Long id, Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super(id);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}

	public Prodaja(Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super(id);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}

	public Artikl getArtikl() {
		return artikl;
	}

	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public LocalDate getDatumObjave() {
		return datumObjave;
	}

	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}

//	public static Prodaja prodaja(Scanner unos, List<Kategorija<Artikl>> listaKategorija,
//			List<Korisnik> listaKorisnika) {
//
//		Artikl artikl = null;
//		Korisnik korisnik = null;
//		Kategorija<Artikl> kategorija = new Kategorija<Artikl>((long) 0);
//
//		System.out.println("Odaberite korisnika: ");
//		for (int j = 0; j < listaKorisnika.size(); j++) {
//			if (listaKorisnika.get(j) instanceof PrivatniKorisnik) {
//				System.out.println((j + 1) + ". Osobni podaci prodavatelja: " + listaKorisnika.get(j).dohvatiKontakt());
//			} else if (listaKorisnika.get(j) instanceof PoslovniKorisnik) {
//				System.out.println((j + 1) + ". Naziv tvrtke: " + listaKorisnika.get(j).dohvatiKontakt());
//			}
//		}
//		int odabirKorisnika = unos.nextInt();
//		unos.nextLine();
//		for (int j = 1; j <= listaKorisnika.size(); j++) {
//			if (odabirKorisnika == j) {
//				if (listaKorisnika.get(j - 1) instanceof PrivatniKorisnik) {
//					korisnik = listaKorisnika.get(j - 1);
//				} else if (listaKorisnika.get(j - 1) instanceof PoslovniKorisnik) {
//					korisnik = listaKorisnika.get(j - 1);
//				}
//			}
//		}
//
//		List<?> artk = new ArrayList<>();
//		System.out.println("Odaberite kategoriju: ");
//
//		for (int i = 0; i < listaKategorija.size(); i++) {
//			System.out.println((i + 1) + "." + listaKategorija.get(i).getNaziv());
//		}
//		Long id = (long) 0;
//		int odabirKategorije = unos.nextInt();
//		unos.nextLine();
//		for (int k = 1; k <= listaKategorija.size(); k++) {
//			if (odabirKategorije == k) {
//
//				kategorija = (Kategorija<Artikl>) listaKategorija.get(k - 1);
//
//				System.out.println("Odaberite artikl: ");
//
//				artk = Kategorija.vratiListu(kategorija);
//
//				for (int i = 0; i < artk.size(); i++) {
//					System.out.println((i + 1) + "." + ((Artikl) artk.get(i)).getNaslov());
//				}
//				int odabirArtikla = unos.nextInt();
//				unos.nextLine();
//
//				for (int p = 1; p <= artk.size(); p++) {
//					if (odabirArtikla == p) {
//						artikl = (Artikl) kategorija.dohvatiArtikl(p - 1);
//						id = (long) (p - 1);
//					}
//				}
//
//			}
//
//		}
//		return new Prodaja(id, artikl, korisnik, LocalDate.now());
//		
//		return null;
//	}

}
