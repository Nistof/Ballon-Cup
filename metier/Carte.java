/* Classe: Carte
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;

public class Carte {
	private static String[] COULEURS = { "ROUGE", "JAUNE", "VERT", "BLEU", "GRIS"};

	private String couleur = "NULL";
	private int valeur;
	
	public Carte ( String couleur, int valeur) {
		for ( String ch : COULEURS)
			if ( ch.equals(couleur))
				this.couleur = couleur;
				
		this.valeur = valeur;
	}
}
