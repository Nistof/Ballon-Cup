/* Classe Cube 
 * @author Quentin Thibaut
 * @author Marécal Thomas
 * @author Martin Florian
 * Groupe I2
 *
 */

package metier;
import util.Couleur;

public class Cube {

	private Couleur couleur;
	
	public Cube ( String couleur) {
		this.couleur = Couleur.getCouleur(couleur);
	}

	public Couleur getCouleur() { return couleur; }
	
	public String toString() {
		return "Cube " + this.couleur; 
	}

}
