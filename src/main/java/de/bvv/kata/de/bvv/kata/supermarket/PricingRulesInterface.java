package de.bvv.kata.de.bvv.kata.supermarket;

import java.util.List;
/**
 * Das Interface ist die API zu den Preisregeln. D.h. sie wird mit ihnen
 * initialisiert und beantwortet sinnvolle Fragen zu ihnen. 
 * 
 * @author bvv/b00359 (Tobias Zepter)
 */
public interface PricingRulesInterface {
	/**
	 * Die Methode initialsiert die Preisregeln durch eine Liste von Preisen
	 * von Gebinden von Artikeln. Fachlicher Schlüssel ist dabei die Kombination
	 * aus Artikelname und Gebindegröße.
	 * 
	 * @param pricingRulesList Liste von Preisregeln, repräsentiert durch
	 * 		Value-Objekte der Klasse {@link PricingRuleValueObject}
	 */
	public void init(List<PricingRuleValueObject> pricingRulesList);
	/**
	 * Die Methode beantwortet die Frage, ob ein bestimmter Artikel durch die
	 * mindestens eine Preisregel abgedeckt ist. Sind im pathologischen Fall
	 * die Preisregeln noch nicht intialisiert, ist die Antwort zu jedem 
	 * möglichen Artikel "nein".
	 * 
	 * @param articleName der angefragte Artikel, repräsentiert durch seinen
	 * 		Namen
	 * @return ein Wahrheitswert dafür, ob es eine Preisregel zu dem Artikel gibt
	 * 		 oder nicht
	 */
	public boolean isPriceAvailableFor(char articleName);
	/**
	 * Die Methode versucht, genau die Preisregel zu finden, die <b>einmal</b> - nicht mehrfach - 
	 * angewendet, die größtmögliche Anzahl des genannten Artikels gleich oder kleiner dem Parameter
	 * articleCount zum kleinstmöglichen Preis abdeckt. Dieser Preis wird zusammen mit der 
	 * verbleibenden Anzahl der nicht bepreisten Artikel-Exemplare im Rückgabewert {@link InterimResult}
	 * zurückgegeben.
	 *    
	 * @param articleName der gewünschte Artikelname
	 * @param articleCount die maximalen Anzahl der Exemplare des Artikels 
	 * @return ein Value-Objekt vom Typ {@link InterimResult}
	 */
	public InterimResult getPriceForNEqualItems(char articleName, int articleCount);
	/**
	 * Die Methode überprüft die Konsistenz der Preisregeln. Diese dürfen 
	 * zunächst einmal nicht leer sein, aber darüber hinaus benötigt unser
	 * hiesiger Algorithmus auch Regeln, bei denen ein größeres Gebinde 
	 * eines Artikels zu einem geringeren Einheitspreis führt.<br><br>
	 *
	 * @throws IllegalStateException falls die Konsistenz gestört ist
	 */
	public void checkConsistency() throws IllegalStateException;
}
