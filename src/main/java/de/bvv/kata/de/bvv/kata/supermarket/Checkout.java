package de.bvv.kata.de.bvv.kata.supermarket;

/**
 * Diese Klasse stellt die Funktionalität der Supermarkt-Kasse. Sie
 * kann mit Preisregeln "gefüttert" werden, scannt seriell einen Artikel nach dem
 * anderen und kann zu jedem Zeitpunkt Auskunft über den minimalen aktuellen
 * Preis der bereits gescannten Artikel geben. Zur Vereinfachung
 * werden die bereits gescannten Artikel nach Abrechnung nicht gelöscht, 
 * sondern für den Scan-Vorgang eines neuen Warenkorbes einfach eine neue
 * Instanz der Klasse gebildet.
 * 
 * @author bvv\b00359 (Tobias Zepter)
 */
public class Checkout implements CheckoutInterface {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPricingRules(PricingRulesInterface pricingRules) throws IllegalStateException {
		pricingRules.checkConsistency();
		this.pricingRules = pricingRules;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scan(char articleName) throws IllegalArgumentException {
		//...
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getTotalPrice() throws IllegalStateException {
		double priceToReturn = 0d;
		//...
		return priceToReturn;
	}
	/**
	 * interne Datenhaltung für die aktuellen Preisregeln
	 */
	PricingRulesInterface pricingRules;
}
