/**
 * 
 * @author Ivan Segota
 * 
 */
package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Kategorija<T extends Artikl> extends Entitet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String naziv;
	private List<T> artikli;

	public Kategorija(Long id, String naziv, List<T> artikli) {
		super(id);
		this.naziv = naziv;
		this.artikli = artikli;
	};

	public Kategorija(Long id) {
		super(id);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<T> getArtikli() {
		return artikli;
	}

	public void setArtikli(List<T> artikli) {
		this.artikli = artikli;
	}

	public static Kategorija<Artikl> unosKategorije(Scanner unos, int broj) {
		/*
		 * System.out.println("Unesite naziv " + broj + " kategorije:"); String _naziv =
		 * unos.next(); System.out.
		 * println("Unesite broj artikala koji želite unijeti za unesenu kategoriju: "
		 * );
		 * 
		 * int brojArtikala = 0; brojArtikala = Datoteke.UnosBroja(unos); List<Artikl>
		 * listaArtikla = new ArrayList<>(); Kategorija<Artikl> ktg = new
		 * Kategorija<Artikl>((long) 0);
		 * 
		 * for (int j = 0; j < brojArtikala; j++) { System.out.println("Unesite tip " +
		 * (j + 1) + ". artikla"); System.out.println("1. Usluga");
		 * System.out.println("2. Automobil"); System.out.println("3. Stan");
		 * System.out.println("Odabir >>"); int odabir = Datoteke.UnosBroja(unos); if
		 * (odabir == 1) { Usluga usluga = (Usluga) Artikl.unosArtikla(unos, odabir, j);
		 * ktg.dodajArtikl(usluga,listaArtikla); } else if (odabir == 2) { Automobil
		 * auto = (Automobil) Artikl.unosArtikla(unos, odabir, j);
		 * ktg.dodajArtikl(auto,listaArtikla); } else if (odabir == 3) { Stan stan =
		 * (Stan) Artikl.unosArtikla(unos, odabir, j);
		 * ktg.dodajArtikl(stan,listaArtikla); } } ktg = new
		 * Kategorija<>((long)(broj-1),_naziv, listaArtikla);
		 * 
		 * Datoteke.mapaKategorijaIartikala.put(ktg,listaArtikla);
		 * 
		 * return ktg;
		 */
		return null;
	}

	public void dodajArtikl(T element, List<T> lista) {
		lista.add(element);
	}

	public T dohvatiArtikl(int index) {
		return artikli.get(index);
	}

//	public static <T extends Artikl> List<?> vratiListu(Kategorija<Artikl> kat) {
//		for (Kategorija<Artikl> t : Datoteke.listaKategorija) {
//			if (t.equals(kat)) {
//				return (List<?>) t.getArtikli();
//			}
//		}
//		return null;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikli == null) ? 0 : artikli.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
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
		Kategorija<?> other = (Kategorija<?>) obj;
		if (artikli == null) {
			if (other.artikli != null)
				return false;
		} else if (!artikli.equals(other.artikli))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

}
