package de.bvv.kata.de.bvv.kata.supermarket;

import java.util.List;

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

		// We try finding a PricingRuleValueObject by iterating through all
		// PricingRuleValueObjects which matches the article names and has the largest
		// possible package size.
		// We use a helper value 'largestCount' which initially has the same value as
		// articleCount and which will be reduced by -1 in each iteration.
		// If such a PricingRuleValueObject exist, we return a new InterimResult object
		// which has the package price of that Pricing Rule and a remaining count of
		// (articleCount - largestCount).
		// Otherwise we return an IllegalArgumentException as the pricingRulesList does
		// not contain any pricing rules for the given articleName and
		// a package size of <= articleCount.

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
		if (null == pricingRulesList)
			throw new IllegalStateException("pricingRulesList are not set");
	}

	/**
	 * interne Repräsentation der Preisliste
	 */
	List<PricingRuleValueObject> pricingRulesList;
}
