package hr.java.vjezbe.iznimke;

public class BazaPodatakaException extends Exception {

	private static final long serialVersionUID = 1L;

	public BazaPodatakaException() {
		super("Dogodila se pogreska u radu programa!");
	}

	public BazaPodatakaException(String poruka) {
		super(poruka);
	}

	public BazaPodatakaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}

	public BazaPodatakaException(Throwable uzrok) {
		super(uzrok);
	}
}
