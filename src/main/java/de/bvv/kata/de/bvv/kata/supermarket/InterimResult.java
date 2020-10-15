package de.bvv.kata.de.bvv.kata.supermarket;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Value-Object-Klasse zur Bündelung des bereits ermittelten
 * Teilpreises für eine bestimmten Anzahl von Exemplaren eines
 * Artikels mit der Anzahl der verbleibenden, noch nicht bepreisten
 * Anzahld der Exemplare.
 * 
 * @author bvv\b00359 (Tobias Zepter)
 *
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InterimResult {
	@Getter @Setter private double calculatedPrice;
	@Getter @Setter private int remainingCount;
}
