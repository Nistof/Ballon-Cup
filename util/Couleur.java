/* Enumération: Couleur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 06/06/2014
**/

package BallonCup.util;
import java.util.*;

public enum Couleur {
	ROUGE ,
	JAUNE ,
	VERT  ,
	BLEU  ,
	GRIS  ;
	
	public static Couleur getCouleur ( String c) {
		Couleur couleur = null;
		
		for ( Couleur coul : Couleur.values())
			if ( coul.name().equals(c))
				couleur = coul;
		
		return couleur;
	}
	
	public static Couleur getCouleur ( char c) {
		Couleur couleur = null;
		
		for ( Couleur coul : Couleur.values())
			if ( coul.name().charAt(0) == c)
				couleur = coul;
		
		return couleur;
	}
	
	public static Couleur getCouleur ( int i) {
		Couleur couleur = null;
		
		for ( Couleur coul : Couleur.values())
			if ( coul.ordinal() == i)
				couleur = coul;
		
		return couleur;
	}
	
	public boolean equals (Couleur c) {
		return this.ordinal() == c.ordinal();
	}
};
