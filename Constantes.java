/* Constantes
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */
 
package BallonCup;
 
public class Constantes {
	//Infos relatives au Joueur
	public final static int NB_CARTE_MAX       = 8;
	public final static int NB_TROPHE_VICTOIRE = 3;
	
	//Infos relatives a Tuile
	public  final static String[] TYPES_PAYSAGE = {"PLAINE","MONTAGNE"};
	
	//Infos relatives au Jeu
	public final static String FICHIER_CARTES = "ressources/cartes";
	public final static int    NB_TUILE       = 4;
	//**** Nombre de cubes de chaque couleur { ROUGE, JAUNE, VERT, BLEU, GRIS}
	public final static int[]    NB_CUBES       = { 13, 11, 9, 7, 5 };
	//**** Valeurs des cartes trophee
	public final static int[] TROPHEES  =  {7, 6, 5, 4, 3};
}
