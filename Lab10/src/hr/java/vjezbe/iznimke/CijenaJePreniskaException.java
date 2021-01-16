package hr.java.vjezbe.iznimke;

public class CijenaJePreniskaException extends RuntimeException {
	private static final long serialVersionUID = 2555341954833609776L;

	public CijenaJePreniskaException() {
		super("Dogodila se pogreska u radu programa!");
	}

	public CijenaJePreniskaException(String poruka) {
		super(poruka);
	}

	public CijenaJePreniskaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}

	public CijenaJePreniskaException(Throwable uzrok) {
		super(uzrok);
	}
}
