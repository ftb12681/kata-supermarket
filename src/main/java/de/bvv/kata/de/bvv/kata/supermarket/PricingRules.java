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
		InterimResult nullReturner = new InterimResult(0.0d, articleCount);
		// ...
		return nullReturner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkConsistency() throws IllegalStateException {
		if (null == pricingRulesList)
			throw new IllegalStateException("nix implementiert");
	}

	/**
	 * interne Repräsentation der Preisliste
	 */
	List<PricingRuleValueObject> pricingRulesList;
}
