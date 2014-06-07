/* Enumération: Couleur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 06/06/2014
**/

package util;

public enum Couleur {
	ROUGE ,
	JAUNE ,
	VERT  ,
	BLEU  ,
	GRIS  ,
	DEFAUT;
	
	public static Couleur getCouleur ( String c) {
		Couleur couleur = Couleur.DEFAUT;
		
		for ( Couleur coul : Couleur.values())
			if ( coul.name().equals(c))
				couleur = coul;
		
		return couleur;
	}
};
