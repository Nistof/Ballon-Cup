/* Trophe
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * @version 1 du 17/06/14
 *
 */
 
package metier;
 
public class Trophe extends Carte {
	
	public String toString() {
		return "Trophe " + super.getCouleur() + " : " + super.getValeur(); 
	}
}
