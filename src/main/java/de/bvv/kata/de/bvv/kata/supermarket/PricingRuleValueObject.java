package de.bvv.kata.de.bvv.kata.supermarket;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Value-Object-Klasse mit allen relevanten Informationen für eine
 * Preisregel. Es sind dies:
 * <ul>
 * <li><b>articleName</b>: der Name des Artikels in dem Gebinde</li>
 * <li><b>packageSize</b>: die Anzahl der Exemplare des Artikels im Gebinde</li>
 * <li><b>packagePrice</b>: der Preis des Gebindes</li>
 * </ul>
 * 
 * @author bvv/b00359 (Tobias Zepter)
 *
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PricingRuleValueObject {
	@Getter @Setter(AccessLevel.PRIVATE) private char articleName;
	@Getter @Setter(AccessLevel.PRIVATE) private int packageSize;
	@Getter @Setter(AccessLevel.PRIVATE) private double packagePrice;
}
