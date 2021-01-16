/**
 * @author Ivan Segota
 */
package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

public interface Nekretnina {

	default BigDecimal izracunajPorez(BigDecimal osnovica) throws CijenaJePreniskaException {
		BigDecimal p = osnovica.multiply(BigDecimal.valueOf(0.03));
		if (osnovica.intValue() < 10000) {
			throw new CijenaJePreniskaException("Unijeli ste cijenu manju od 10 000kn!");
		}
		return p;
	}
}
