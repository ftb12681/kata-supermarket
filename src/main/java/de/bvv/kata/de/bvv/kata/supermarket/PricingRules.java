package de.bvv.kata.de.bvv.kata.supermarket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die Klasse implementiert Funktionalitäten auf Basis des Interfaces
 * {@link PricingRulesInterface}. Preisregeln für Artikel gibt es mit
 * unterschiedlicher Rabattierung für verschiedene Gebindegrößen. Die Klasse
 * überprüft diese Preisregeln und kann sie für bestimmte Artikel in der Weise
 * anwenden, dass der geringstmögliche Preis entsteht.
 * 
 * @author bvv/b00359 (Tobias Zepter)
 */
public class PricingRules implements PricingRulesInterface {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(List<PricingRuleValueObject> pricingRulesList) {
		this.pricingRulesList = pricingRulesList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPriceAvailableFor(char articleName) {
		for (PricingRuleValueObject pricingRule : pricingRulesList) {
			if (articleName == pricingRule.getArticleName())
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InterimResult getPriceForNEqualItems(char articleName, int articleCount) {

		// We try to find a PricingRuleValueObjects for the given article name with
		// the largest possible package size.
		// We use a helper value 'largestCount' which initially has the same value as
		// articleCount and which will be reduced by -1 in each iteration.
		// If a PricingRuleValueObject for the given articleName and a package size >=0
		// exist, then we return a new InterimResult object which has the package price
		// of that Pricing Rule and a remaining count of (articleCount - largestCount).
		//
		// Otherwise we return an IllegalArgumentException as the pricingRulesList does
		// not contain any pricing rules for the given articleName and a package size
		// equal or smaller than 'articleCount'.

		int largestCount = articleCount;
		while (largestCount > 0) {
			for (PricingRuleValueObject pricingRule : pricingRulesList) {
				if (articleName == pricingRule.getArticleName() && largestCount == pricingRule.getPackageSize()) {
					return new InterimResult(pricingRule.getPackagePrice(), articleCount - largestCount);
				}
			}
			largestCount--;
		}
		if (0 == largestCount) {
			throw new IllegalArgumentException(String.format("No price available for articleName %s", articleName));
		}

		InterimResult nullReturner = new InterimResult(0.0d, articleCount);
		return nullReturner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkConsistency() throws IllegalStateException {

		// 1) pricingRulesList must not be null or empty
		if (null == pricingRulesList || 0 == pricingRulesList.size())
			throw new IllegalStateException("pricingRulesList is not set or empty");

		// 2) The pricingRulesList must not contain several entries with the same
		// articleName and same package size, but with a different price. (Same price
		// can be tolerated)
		// To guarantee that, the following code uses a Map<String, Double> with the
		// articleName/packageSize tuple as the key and the packagePrice as the value.
		// When adding all articleName/packageSize tuples into the map
		// a IllegalStateException will be thrown if an entry with the same key but
		// different value exists.
		Map<String, Double> pricingRulesMap = new HashMap<String, Double>();
		for (PricingRuleValueObject pricingRule : pricingRulesList) {
			String key = pricingRule.getArticleName() + "/" + pricingRule.getPackageSize();
			Double price = pricingRule.getPackagePrice();

			if (pricingRulesMap.containsKey(key) && !pricingRulesMap.get(key).equals(price)) {
				throw new IllegalStateException(
						String.format("Pricing Rules contain two prices for articleName=%s and packageSize=%d",
								pricingRule.getArticleName(), pricingRule.getPackageSize()));
			}
			pricingRulesMap.put(key, price);
		}
	}

	/**
	 * interne Repräsentation der Preisliste
	 */
	List<PricingRuleValueObject> pricingRulesList;
}
