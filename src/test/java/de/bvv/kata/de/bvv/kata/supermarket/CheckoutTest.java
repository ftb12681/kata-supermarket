package de.bvv.kata.de.bvv.kata.supermarket;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CheckoutTest extends TestCase {
	
	PricingRulesInterface pricingRules;
	Checkout cut;
	/**
	 * TEST auf nicht initialisierte Preisregeln, die bei Scannen auffallen 
	 */
	public void testMissingPrices() {
		cut = new Checkout();
		try {
			cut.scan('A');
			fail("IllegalStateException expected");			
		}
		catch( IllegalStateException e ) { }			
		catch ( Exception e) {
			fail(String.format("IllegalStateException expected, but '%s' got", e.getClass().getName()));
		}			
	}
	/**
	 * TEST auf die korrekten Preise verschiedener Warenkörbe
	 */
	public void testManyCasesOfBasketsYieldToTheCorrectTotals() {
		assertEquals( 0.0d, getPriceOfNewBasket( "" ));
		assertEquals( 50.0d, getPriceOfNewBasket( "A" ));
		assertEquals( 80.0d, getPriceOfNewBasket( "AB" ));
		assertEquals( 115.0d, getPriceOfNewBasket( "CDBA" ));
		assertEquals( 90.0d, getPriceOfNewBasket( "AA" ));
		assertEquals( 130.0d, getPriceOfNewBasket( "AAA" ));
		assertEquals( 180.0d, getPriceOfNewBasket( "AAAA" ));
		assertEquals( 220.0d, getPriceOfNewBasket( "AAAAA" ));
		assertEquals( 260.0d, getPriceOfNewBasket( "AAAAAA" ));
		assertEquals( 160.0d, getPriceOfNewBasket( "AAAB" ));
		assertEquals( 175.0d, getPriceOfNewBasket( "AAABB" ));
		assertEquals( 190.0d, getPriceOfNewBasket( "AAABBD" ));
		assertEquals( 190.0d, getPriceOfNewBasket( "DABABA" ));
		assertEquals( 30.0d, getPriceOfNewBasket( "FFFF" ));
		assertEquals( 50.0d, getPriceOfNewBasket( "FFFFFFFF" ));
		assertEquals( 60.0d, getPriceOfNewBasket( "FFFFFFFFFF" ));
		assertEquals( 75.0d, getPriceOfNewBasket( "FFFFFDFFFFF" ));
		assertEquals( 45.0d, getPriceOfNewBasket( "EEEDE" ));	
	}
	/**
	 * TEST auf die Entwicklung der Preisbildung eines Warenkorbes
	 * im Ablauf eines Scan-Vorgangs. Hier repräsentiert sich die 
	 * Anwendung unterschiedlicher Preisregeln zu einem Artikel. 
	 */
	public void testIncrementalsOnCheckoutYieldsToCorrectDiscounts() {
		cut = new Checkout();
		cut.setPricingRules(pricingRules);
		assertEquals(0.0d, cut.getTotalPrice());
		cut.scan('A');
		assertEquals(50.0d, cut.getTotalPrice());
		cut.scan('B');
		assertEquals(80.0d, cut.getTotalPrice());
		cut.scan('A');
		assertEquals(120.0d, cut.getTotalPrice());
		cut.scan('A');
		assertEquals(160.0d, cut.getTotalPrice());
		cut.scan('B');
		assertEquals(175.0d, cut.getTotalPrice());		
	}
	/**
	 * TEST für den Scan eines in den Preisregeln unbekannten Artikels
	 */
	public void testUnknownArticle() {
        cut = new Checkout();
		cut.setPricingRules(pricingRules);
		try {
			cut.scan('X');
			fail("IllegalArgumentException expected");			
		}
		catch( IllegalArgumentException e ) { }			
		catch ( Exception e) {
			fail(String.format("IllegalArgumentException expected, but '%s' got", e.getClass().getName()));
		}		
	}
	/**
	 * TEST auf die Inkonsistenz der Preisregeln bezogen auf eine bestimmte Menge
	 * eines Artikels, der zwar in den Regeln vorkommt, aber nicht in der notwendigen Gebindegröße
	 */
	public void testOnePieceOfAnArticleWithoutSinglePriceYieldsToAnException() {
        cut = new Checkout();
		cut.setPricingRules(pricingRules);
		cut.scan('E');
		try {
			cut.getTotalPrice();
			fail("IllegalStateException expected");			
		}
		catch( IllegalStateException e ) { }			
		catch ( Exception e) {
			fail(String.format("IllegalStateException expected, but '%s' got", e.getClass().getName()));
		}
	}
	/** 
	 * TEST auf die Ausnahme, die bei Preisabfrage ohne Preisregeln geworfen wird.
	 * (Das ist eine technische Entscheidung, man hätte auch null zurückgeben können.)
	 */
	public void testTotalPriceCalculationWithoutPricingRulesYieldsToAnException() {
		cut = new Checkout();
		try {
			cut.getTotalPrice();
			fail("IllegalStateException expected");			
		}
		catch( IllegalStateException e ) { }			
		catch ( Exception e) {
			fail(String.format("IllegalStateException expected, but '%s' got", e.getClass().getName()));
		}		
	}
	/**
	 * TEST auf inkonsistente Preisregeln. Diese Situation tritt auf, wenn Waren 
	 * teurer werden, obwohl man mehr von ihnen kauft. 
	 */
	public void testCheckIncreasingPriceListYieldsToException() {
		PricingRulesInterface pRules = new PricingRules();
        //check will be positive		
		ArrayList<PricingRuleValueObject> ruleList = 
			new ArrayList<>(Arrays.asList(
				new PricingRuleValueObject('A', 1, 50.0d),
				new PricingRuleValueObject('A', 2, 90.0d),
				new PricingRuleValueObject('B', 1, 30.0d)
			));
		pRules.init(ruleList);
		pRules.checkConsistency();
        //now check will be negative
		ruleList.add(new PricingRuleValueObject('A', 10, 451.0d));
		pRules.init(ruleList);
		try {
			pRules.checkConsistency();
		} 
		catch (IllegalStateException e){
			if (!e.getMessage().contains("evolve")) {
				fail("IllegalArgumentException message does not contain the word 'evolve' but should do");
			}
		}
		catch (Exception e) {
			fail(String.format("IllegalArgumentException expected, but '%s' got", e.getClass().getName()));			
		}
	}
	/**
	 * Hilfsmethode, die den Preis eines Warenkorbes berechnet, der durch einen String angegeben wird.
	 * Das ist möglich, weil die Artikelnamen Characters sind.
	 * 
	 * @param basketLine String, der die Folge der Artikel repräsentiert
	 * @return Gesamtpreis
	 * @throws IllegalStateException wenn die Preisregeln nicht zum Warenkorb passen
	 */
	private double getPriceOfNewBasket(String basketLine) throws IllegalStateException {
		cut = new Checkout();
		cut.setPricingRules(pricingRules);
		basketLine.chars().forEachOrdered(c -> cut.scan((char)c));
		return cut.getTotalPrice();
	}
    public static Test suite() {
        return new TestSuite( CheckoutTest.class );
    }
    /**
     * Initialisierung der Beispiel-Preisregeln
     */
    @Override
    public void setUp() throws Exception {
    	pricingRules = new PricingRules();
    	pricingRules.init(
			new ArrayList<PricingRuleValueObject>(Arrays.asList(
				new PricingRuleValueObject('A', 1, 50.0d),
				new PricingRuleValueObject('A', 2, 90.0d),
				new PricingRuleValueObject('A', 3, 130.0d),
				new PricingRuleValueObject('B', 1, 30.0d),
				new PricingRuleValueObject('B', 2, 45.0d),
				new PricingRuleValueObject('C', 1, 20.0d),
				new PricingRuleValueObject('D', 1, 15.0d),
				new PricingRuleValueObject('E', 2, 15.0d),
				new PricingRuleValueObject('F', 1, 10.0d),
				new PricingRuleValueObject('F', 3, 20.0d),
				new PricingRuleValueObject('F', 6, 30.0d)
			)));
    }
}
