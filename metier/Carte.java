/* Classe: Carte
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package BallonCup.metier;

import BallonCup.util.Couleur;

public class Carte {
	private Couleur couleur;
	private int valeur;
	
	public Carte ( Couleur couleur, int valeur) {
		this.couleur = couleur;				
		this.valeur = valeur;
	}
	
	public Couleur getCouleur () { return this.couleur; }
	public int     getValeur  () { return this.valeur;  }
	
	public boolean equals( Carte c ) {
		return (this.couleur.equals(c.couleur) && this.valeur==c.valeur);
	}
	
	public String toString() {
		return this.couleur.name().charAt(0) + ":" + String.format( "%02d", this.valeur ); 
	}
}
