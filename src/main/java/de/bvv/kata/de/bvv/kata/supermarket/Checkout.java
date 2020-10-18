package de.bvv.kata.de.bvv.kata.supermarket;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse stellt die Funktionalität der Supermarkt-Kasse. Sie kann mit
 * Preisregeln "gefüttert" werden, scannt seriell einen Artikel nach dem anderen
 * und kann zu jedem Zeitpunkt Auskunft über den minimalen aktuellen Preis der
 * bereits gescannten Artikel geben. Zur Vereinfachung werden die bereits
 * gescannten Artikel nach Abrechnung nicht gelöscht, sondern für den
 * Scan-Vorgang eines neuen Warenkorbes einfach eine neue Instanz der Klasse
 * gebildet.
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
		if (null == pricingRules)
			throw new IllegalStateException("PricingRules are missing");

		if (!pricingRules.isPriceAvailableFor(articleName))
			throw new IllegalArgumentException(String.format("No price available for articleName %s", articleName));
		if (scannedArticles.containsKey(articleName))
			scannedArticles.put(articleName, 1 + scannedArticles.get(articleName));
		else
			scannedArticles.put(articleName, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getTotalPrice() throws IllegalStateException {
		if (null == pricingRules)
			throw new IllegalStateException("PricingRules are missing");
		double priceToReturn = 0d;

		if (0 == scannedArticles.size())
			return 0d;

		for (Character articleName : scannedArticles.keySet()) {
			Integer remainingArticles = scannedArticles.get(articleName);
			while (remainingArticles > 0) {
				InterimResult ir;
				try {
					ir = pricingRules.getPriceForNEqualItems(articleName, remainingArticles);
				} catch (IllegalArgumentException iae) {
					throw new IllegalStateException(iae.getMessage());
				}
				priceToReturn += ir.getCalculatedPrice();
				remainingArticles = ir.getRemainingCount();
			}
		}
		return priceToReturn;
	}

	/**
	 * interne Datenhaltung für die aktuellen Preisregeln
	 */
	PricingRulesInterface pricingRules;

	/**
	 * interne Datenhaltung für gescannte Artikel
	 */
	Map<Character, Integer> scannedArticles = new HashMap<Character, Integer>();

}
