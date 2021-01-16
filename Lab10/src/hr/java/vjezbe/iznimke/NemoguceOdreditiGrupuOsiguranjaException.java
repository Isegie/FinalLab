package hr.java.vjezbe.iznimke;

public class NemoguceOdreditiGrupuOsiguranjaException extends Exception {

	private static final long serialVersionUID = 2970828597421605572L;

	public NemoguceOdreditiGrupuOsiguranjaException() {
		super("Dogodila se pogreska u radu programa!");
	}

	public NemoguceOdreditiGrupuOsiguranjaException(String poruka) {
		super(poruka);
	}

	public NemoguceOdreditiGrupuOsiguranjaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}

	public NemoguceOdreditiGrupuOsiguranjaException(Throwable uzrok) {
		super(uzrok);
	}
}
