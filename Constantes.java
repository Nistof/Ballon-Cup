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
	
	//Pour l'ihm GUI
	//Constantes pour les chemins
	public final static String CH_CARTES_IMG   = "ressources/images/cartes/carte_";
	public final static String CH_TROPHEES_IMG = "ressources/images/cartesTrophee/carte_Trophe";
	public final static String CH_TUILES_IMG   = "ressources/images/tuiles/tuile" ;
	public final static String CH_CUBES_IMG    = "ressources/images/cubes/cube_"  ;
	public final static String FORMAT_IMG      = ".jpg";
	
	//Constantes pour les tailles
	public final static int POURCENT           = 50; //100 = taille normale
	public final static int CARTE_HAUTEUR      = (int)(252-(POURCENT*252/100)); //252 : Hauteur de l'image de base
	public final static int CARTE_LARGEUR      = (int)(162-(POURCENT*162/100)); //162 : Largeur de l'image de base
	public final static int TUILE_HAUTEUR      = (int)(250-(POURCENT*250/100)); //250 : Hauteur de l'image de base
	public final static int TUILE_LARGEUR      = (int)(245-(POURCENT*245/100)); //245 : Largeur de l'image de base
}
