package de.bvv.kata.de.bvv.kata.supermarket;

/**
 * Dieses Interface stellt die Funktionalität der Supermarkt-Kasse. Sie
 * kann mit Preisregeln "gefüttert" werden, scannt seriell einen Artikel nach dem
 * anderen und kann zu jedem Zeitpunkt Auskunft über den minimalen aktuellen
 * Preis der bereits gescannten Artikel geben. Zur Vereinfachung
 * werden die bereits gescannten Artikel nach Abrechnung nicht gelöscht, 
 * sondern für den Scan-Vorgang eines neuen Warenkorbes einfach eine neue
 * Instanz der implementierenden Klasse gebildet.
 *  
 * @author bvv/b00359 (Tobias Zepter)
 */
public interface CheckoutInterface {
	/**
	 * Mit dieser Methode kann der Kasse - theoretisch auch mitten im Scanverlauf eines
	 * Warenkorbes - ein neues Set an Preisregeln untergeschoben werden. Diese Preisregeln
	 * werden benutzt, um den Preis der gescannten Waren zu berechnen.
	 * 
	 * @param pricingRules die neue Preisregeln
	 * @throws IllegalStateException bei der Initialisierung werden die Preisregeln 
	 * 		aufgefordert, sich auf Konsistenz zu prüfen. Inkonsitente Preisregeln 
	 * 		führen zum Werfen dieser Ausnahme
	 */
	public void setPricingRules(PricingRulesInterface pricingRules) throws IllegalStateException;
	/**
	 * Die Methode realsiert den Scan-Vorgang eines einzelnen Artikels. Die Reihenfolge
	 * des Scannens von Artikel ist ausdrücklich nicht von Bedeutung.
	 * 
	 * @param articleName Name des Artikels
	 * @throws IllegalArgumentException ist der Artikel nicht in durch die Preisregeln
	 * 		abgedeckt, wird die Ausnahme geworfen
	 */
	public void scan(char articleName) throws IllegalArgumentException;
	/**
	 * Diese Methode liefert den Gesamtpreis der bis zu diesem Zeitpunkt 
	 * gescannten Artikel. Ist die Preisbildung nicht möglich, sind offenbar
	 * die Preisregeln schlecht gewählt und es wird eine Ausnahme geworfen, die 
	 * die Inkonsistenz der Preisregeln anzeigt.
	 * 
	 * @return den Gesamtpreis der gescannten Warenkorbs
	 * @throws IllegalStateException zeigt die Inkonsistenz der Preisregeln 
	 * 		bzgl. des aktuell gescannten Warenkorbes an
	 */
	public double getTotalPrice() throws IllegalStateException;
}
