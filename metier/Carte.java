/* Classe: Carte
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;

import util.Couleur;

public class Carte {
	private Couleur couleur;
	private int valeur;
	
	public Carte ( String couleur, int valeur) {
		this.couleur = Couleur.getCouleur( couleur);				
		this.valeur = valeur;
	}
	
	public Couleur getCouleur () { return this.couleur; }
	public int     getValeur  () { return this.valeur;  }
	
	public String toString() {
		return this.couleur.name().charAt(0) + ":" + this.valeur; 
	}
}
