package hr.java.vjezbe.baza;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

public class BazaPodataka {

	private static final String DATOTEKA_BAZE = "src\\properties";
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

	private static Connection spajanjeNaBazu() throws SQLException, IOException {
		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATOTEKA_BAZE));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

		return veza;
	}

	public static List<Stan> dohvatiStanovePremaKriterijima(Stan stan) throws BazaPodatakaException {
		List<Stan> listaStanova = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, kvadratura, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Stan'");
			if (Optional.ofNullable(stan).isEmpty() == false) {
				if (Optional.ofNullable(stan).map(Stan::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + stan.getId());
				if (Optional.ofNullable(stan.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + stan.getNaslov() + "%'");
				if (Optional.ofNullable(stan.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + stan.getOpis() + "%'");
				if (Optional.ofNullable(stan).map(Stan::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + stan.getCijena());
				if (Optional.ofNullable(stan).map(Stan::getKvadratura).isPresent())
					sqlUpit.append(" AND artikl.kvadratura = " + stan.getKvadratura());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				Integer kvadratura = resultSet.getInt("kvadratura");
				String stanje = resultSet.getString("naziv");
				Stan newStan = new Stan(id, naslov, opis, cijena, kvadratura, Stanje.valueOf(stanje));
				listaStanova.add(newStan);

			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaStanova;
	}

	public static void pohraniNoviStan(Stan stan) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, Kvadratura, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 3);");
			preparedStatement.setString(1, stan.getNaslov());
			preparedStatement.setString(2, stan.getOpis());
			preparedStatement.setBigDecimal(3, stan.getCijena());
			preparedStatement.setInt(4, stan.getKvadratura());
			preparedStatement.setLong(5, (stan.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	public static List<Automobil> dohvatiAutomobilePremaKriterijima(Automobil auto) throws BazaPodatakaException {
		List<Automobil> listaAutomobila = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Automobil'");
			if (Optional.ofNullable(auto).isEmpty() == false) {
				if (Optional.ofNullable(auto).map(Automobil::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + auto.getId());
				if (Optional.ofNullable(auto.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + auto.getNaslov() + "%'");
				if (Optional.ofNullable(auto.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + auto.getOpis() + "%'");
				if (Optional.ofNullable(auto).map(Automobil::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + auto.getCijena());
				if (Optional.ofNullable(auto).map(Automobil::getSnagaKs).isPresent())
					sqlUpit.append(" AND artikl.snaga = " + auto.getSnagaKs());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				BigDecimal snagaK = resultSet.getBigDecimal("snaga");
				String stanje = resultSet.getString("naziv");
				Automobil noviAuto = new Automobil(id, naslov, opis, cijena, snagaK, Stanje.valueOf(stanje));
				listaAutomobila.add(noviAuto);

			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaAutomobila;
	}

	public static void pohraniNoviAutomobil(Automobil automobil) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, snaga, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 1);");
			preparedStatement.setString(1, automobil.getNaslov());
			preparedStatement.setString(2, automobil.getOpis());
			preparedStatement.setBigDecimal(3, automobil.getCijena());
			preparedStatement.setBigDecimal(4, automobil.getSnagaKs());
			preparedStatement.setLong(5, (automobil.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	public static List<Usluga> dohvatiUslugePremaKriterijima(Usluga usluga) throws BazaPodatakaException {
		List<Usluga> listaUsluga = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena,stanje.naziv "
					+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
					+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Usluga'");
			if (Optional.ofNullable(usluga).isEmpty() == false) {
				if (Optional.ofNullable(usluga).map(Usluga::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + usluga.getId());
				if (Optional.ofNullable(usluga.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + usluga.getNaslov() + "%'");
				if (Optional.ofNullable(usluga.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + usluga.getOpis() + "%'");
				if (Optional.ofNullable(usluga).map(Usluga::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + usluga.getCijena());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				Usluga usluge = new Usluga(id, naslov, opis, cijena, Stanje.valueOf(stanje));
				listaUsluga.add(usluge);

			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaUsluga;
	}

	public static void pohraniNovuUslugu(Usluga usluga) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into artikl(Naslov, Opis, Cijena,idStanje, idTipArtikla) " + "values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, usluga.getNaslov());
			preparedStatement.setString(2, usluga.getOpis());
			preparedStatement.setBigDecimal(3, usluga.getCijena());
			preparedStatement.setLong(4, (usluga.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	public static List<PrivatniKorisnik> dohvatiPrivatnogKorisnikaPremaKriterijima(PrivatniKorisnik privatni)
			throws BazaPodatakaException {
		List<PrivatniKorisnik> listaPrivatnih = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct korisnik.id, ime, prezime, email,telefon "
					+ "FROM korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
					+ " WHERE tipKorisnika.naziv = 'PrivatniKorisnik'");
			if (Optional.ofNullable(privatni).isEmpty() == false) {
				if (Optional.ofNullable(privatni).map(PrivatniKorisnik::getId).isPresent())
					sqlUpit.append(" AND korisnik.id = " + privatni.getId());
				if (Optional.ofNullable(privatni.getIme()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Ime LIKE '%" + privatni.getIme() + "%'");
				if (Optional.ofNullable(privatni.getPrezime()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Prezime LIKE '%" + privatni.getPrezime() + "%'");
				if (Optional.ofNullable(privatni.getEmail()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Email LIKE '%" + privatni.getEmail() + "%'");
				if (Optional.ofNullable(privatni.getTelefon()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Telefon LIKE '%" + privatni.getTelefon() + "%'");
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String ime = resultSet.getString("Ime");
				String prezime = resultSet.getString("Prezime");
				String email = resultSet.getString("Email");
				String telefon = resultSet.getString("Telefon");
				PrivatniKorisnik pk = new PrivatniKorisnik(id, ime, prezime, email, telefon);
				listaPrivatnih.add(pk);

			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaPrivatnih;
	}

	public static void pohraniNovogPrivatnogKorisnika(PrivatniKorisnik privatniKorisnik) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Ime, Prezime, Email,Telefon,idTipKorisnika) " + "values (?, ?, ?, ?,1);");
			preparedStatement.setString(1, privatniKorisnik.getIme());
			preparedStatement.setString(2, privatniKorisnik.getPrezime());
			preparedStatement.setString(3, privatniKorisnik.getEmail());
			preparedStatement.setString(4, privatniKorisnik.getTelefon());
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	public static List<PoslovniKorisnik> dohvatiPoslovnogKorisnikaPremaKriterijima(PoslovniKorisnik poslovniKorisnik)
			throws BazaPodatakaException {
		List<PoslovniKorisnik> listaPoslovnih = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct korisnik.id, email, telefon, korisnik.naziv,web "
					+ "FROM korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
					+ " WHERE tipKorisnika.Naziv = 'PoslovniKorisnik'");
			if (Optional.ofNullable(poslovniKorisnik).isEmpty() == false) {
				if (Optional.ofNullable(poslovniKorisnik).map(PoslovniKorisnik::getId).isPresent())
					sqlUpit.append(" AND korisnik.id = " + poslovniKorisnik.getId());
				if (Optional.ofNullable(poslovniKorisnik.getEmail()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Email LIKE '%" + poslovniKorisnik.getEmail() + "%'");
				if (Optional.ofNullable(poslovniKorisnik.getTelefon()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Telefon LIKE '%" + poslovniKorisnik.getTelefon() + "%'");
				if (Optional.ofNullable(poslovniKorisnik.getEmail()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Naziv LIKE '%" + poslovniKorisnik.getNaziv() + "%'");
				if (Optional.ofNullable(poslovniKorisnik.getWeb()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.Web LIKE '%" + poslovniKorisnik.getWeb() + "%'");
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String email = resultSet.getString("Email");
				String telefon = resultSet.getString("Telefon");
				String naziv = resultSet.getString("Naziv");
				String web = resultSet.getString("Web");
				PoslovniKorisnik poslovniK = new PoslovniKorisnik(id, email, telefon, naziv, web);
				listaPoslovnih.add(poslovniK);

			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaPoslovnih;
	}

	public static void pohraniNovogPoslovnogKorisnika(PoslovniKorisnik poslovniKorisnik) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Email, Telefon, Naziv,Web,idTipKorisnika) " + "values (?, ?, ?, ?,2);");
			preparedStatement.setString(1, poslovniKorisnik.getEmail());
			preparedStatement.setString(2, poslovniKorisnik.getTelefon());
			preparedStatement.setString(3, poslovniKorisnik.getNaziv());
			preparedStatement.setString(4, poslovniKorisnik.getWeb());
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}


	public static List<Artikl> dohvatiArtiklePoKriterijima(Artikl artikl) throws BazaPodatakaException {
		List<Artikl> artikli = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id as idArtikla, naslov, opis, cijena, snaga,\r\n"
							+ "kvadratura, stanje.naziv as stanje, tipArtikla.naziv as tipArtikla\r\n"
							+ "FROM artikl inner join\r\n" + "stanje on stanje.id = artikl.idStanje inner join\r\n"
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla");
			if (Optional.ofNullable(artikl).isEmpty() == false) {
				if (Optional.ofNullable(artikl).isPresent())
					sqlUpit.append(" AND artikl.id = " + artikl.getId());
				if (Optional.ofNullable(artikl.getNaslov()).isPresent())
					sqlUpit.append(" AND artikl.naslov = " + artikl.getNaslov());
				if (Optional.ofNullable(artikl.getOpis()).isPresent())
					sqlUpit.append(" AND artikl.opis = " + artikl.getOpis());
				if (Optional.ofNullable(artikl.getCijena()).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + artikl.getCijena());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {

				Artikl art = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					art = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					art = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					art = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getInt("kvadratura"), Stanje.valueOf(resultSet.getString("stanje")));
				}

				artikli.add(art);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return artikli;
	}

	public static List<Korisnik> dohvatiKorisnikePoKriterijima(Korisnik korisnik) throws BazaPodatakaException {
		List<Korisnik> korisnici = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct korisnik.id as idKorisnika, korisnik.naziv, web, email,\r\n"
							+ "telefon, ime, prezime, tipKorisnika.naziv as tipKorisnika\r\n"
							+ "from korisnik inner join\r\n"
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika");
			if (Optional.ofNullable(korisnik).isEmpty() == false) {
				if (Optional.ofNullable(korisnik).isPresent())
					sqlUpit.append(" AND korisnik.id = " + korisnik.getId());
				if (Optional.ofNullable(korisnik.getEmail()).isPresent())
					sqlUpit.append(" AND korisnik.email= " + korisnik.getEmail());
				if (Optional.ofNullable(korisnik.getTelefon()).isPresent())
					sqlUpit.append(" AND korisnik.telefon = " + korisnik.getTelefon());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {

				Korisnik kor = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					kor = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					kor = new PoslovniKorisnik(resultSet.getLong("idKorisnika"),
							resultSet.getString("tipKorisnika.naziv"), resultSet.getString("web"),
							resultSet.getString("telefon"), resultSet.getString("email"));
				}
				korisnici.add(kor);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return korisnici;
	}

	public static List<Prodaja> dohvatiProdajuPremaKriterijima(Prodaja prodaja) throws BazaPodatakaException {
		List<Prodaja> listaProdaje = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id as idKorisnika, tipKorisnika.naziv as tipKorisnika, \r\n"
							+ "korisnik.naziv as nazivKorisnika, web, email, telefon, \r\n"
							+ "korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla,\r\n"
							+ "artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura,\r\n"
							+ "artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave, artikl.id as idArtikla\r\n"
							+ "from korisnik inner join \r\n"
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join\r\n"
							+ "prodaja on prodaja.idKorisnik = korisnik.id inner join\r\n"
							+ "artikl on artikl.id = prodaja.idArtikl inner join\r\n"
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join\r\n"
							+ "stanje on stanje.id = artikl.idStanje where 1=1");
			if (Optional.ofNullable(prodaja).isEmpty() == false) {
				if (Optional.ofNullable(prodaja.getArtikl()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getArtikl().getId());
				if (Optional.ofNullable(prodaja.getKorisnik()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getKorisnik().getId());
				if (Optional.ofNullable(prodaja.getDatumObjave()).isPresent()) {
					sqlUpit.append(" AND prodaja.datumObjave = '"
							+ prodaja.getDatumObjave().format(DateTimeFormatter.ISO_DATE) + "'");

				}
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Korisnik korisnik = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"),
							resultSet.getString("nazivKorisnika"), resultSet.getString("web"),
							resultSet.getString("telefon"), resultSet.getString("email"));
				}
				Artikl artikl = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getInt("kvadratura"), Stanje.valueOf(resultSet.getString("stanje")));
				}
				Prodaja novaProdaja = new Prodaja(artikl, korisnik,
						resultSet.getTimestamp("datumObjave").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				listaProdaje.add(novaProdaja);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaProdaje;
	}

	public static void pohraniNovuProdaju(Prodaja prodaja) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into prodaja(idArtikl,idKorisnik,datumObjave) " + "values (?, ?, ?)");
			preparedStatement.setLong(1, prodaja.getArtikl().getId());
			preparedStatement.setLong(2, prodaja.getKorisnik().getId());
			preparedStatement.setString(3, prodaja.getDatumObjave().toString());

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	public static List<Prodaja> dohvatiZadnjuProdaju(Prodaja prodaja) throws BazaPodatakaException {

		List<Prodaja> zadnjaProdajaList = new ArrayList<>();
		
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id as idKorisnika,tipKorisnika.naziv as tipKorisnika,\r\n"
							+ "korisnik.naziv as nazivKorisnika, web, email, telefon,\r\n"
							+ "korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla,\r\n"
							+ "artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura,\r\n"
							+ "artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave,\r\n"
							+ "artikl.id as idArtikla\r\n" + "from korisnik inner join\r\n"
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join\r\n"
							+ "prodaja on prodaja.idKorisnik = korisnik.id inner join\r\n"
							+ "artikl on artikl.id = prodaja.idArtikl inner join\r\n"
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join stanje on stanje.id = artikl.idStanje\r\n"
							+ "order by datumObjave desc\r\n" + "limit 1 ");
			
			
			if (Optional.ofNullable(prodaja).isEmpty() == false) {
				if (Optional.ofNullable(prodaja.getArtikl()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getArtikl().getId());
				if (Optional.ofNullable(prodaja.getKorisnik()).isPresent())
					sqlUpit.append(" AND prodaja.idKorisnik = " + prodaja.getKorisnik().getId());
				if (Optional.ofNullable(prodaja.getDatumObjave()).isPresent()) {
					sqlUpit.append(" AND prodaja.datumObjave = '"
							+ prodaja.getDatumObjave().format(DateTimeFormatter.ISO_DATE) + "'");

				}
	
				
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Korisnik korisnik = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"),
							resultSet.getString("nazivKorisnika"), resultSet.getString("web"),
							resultSet.getString("telefon"), resultSet.getString("email"));
				}
				Artikl artikl = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getInt("kvadratura"), Stanje.valueOf(resultSet.getString("stanje")));
				}
				Prodaja zadnjaProdaja = new Prodaja(artikl, korisnik,
						resultSet.getTimestamp("datumObjave").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				 zadnjaProdajaList.add(zadnjaProdaja);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreske u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return zadnjaProdajaList;
	}

}
