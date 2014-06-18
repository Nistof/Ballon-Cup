/* Classe Cube 
 * @author Quentin Thibaut
 * @author Marécal Thomas
 * @author Martin Florian
 * Groupe I2
 * @version 2 du 15/06/2014
 */

package metier;
import util.Couleur;

public class Cube {

	private Couleur couleur;
	
	public Cube ( Couleur couleur) {
		this.couleur = couleur;
	}

	public Couleur getCouleur() { return couleur; }
	
	public String toString() {
		return "Cube " + this.couleur; 
	}

}
