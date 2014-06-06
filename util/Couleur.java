/* Enumération: Couleur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 06/06/2014
**/

package util;

public enum Couleur {
	ROUGE ( 16711680),
	JAUNE ( 16776960),
	VERT  ( 65280   ),
	BLEU  ( 255     ),
	GRIS  ( 8421504 ),
	DEFAUT( 0       );
	
	private int valeur;
	
	private Couleur ( int valeur) {
		this.valeur = valeur;
	}
	
	public int getValeur () { return this.valeur; }
	
	public static Couleur getCouleur ( String c) {
		Couleur couleur = Couleur.DEFAUT;
		
		for ( Couleur coul : Couleur.values())
			if ( coul.name().equals(c))
				couleur = coul;
		
		return couleur;
	}
};
