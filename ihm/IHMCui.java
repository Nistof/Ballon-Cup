/* IHMCui
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;
import BallonCup.Jeu;
import BallonCup.Constantes;
import BallonCup.util.*;
import java.util.*;

public class IHMCui implements Ihm {
	private Jeu jeu;

	public IHMCui (Jeu jeu) {
		this.jeu = jeu;
	}

	public void afficherTuiles () {
		String s = "";
		for( int i=0; i< jeu.getNbTuiles() ; i++ )
			s += jeu.afficheTuile(i) + "\n\n";	
		s += "------------------------------------------------------\n";
		System.out.println(s);
	}	

	public void afficherJoueurs () {
		String s = "",s2 ="";
		for ( int i = 0; i < jeu.getNbJoueurs(); i++) {
			s2 = "";
			for ( int j = 0; j < jeu.getNbCartesJoueur(i); j++)
				s2 += TexteUtil.centrer( ""+(j+1), 6);
			s += TexteUtil.centrer( jeu.getNomJoueur(i)                      , 55) + "\n" +
				 TexteUtil.centrer("         " + s2                          , 55) + "\n" +
				 TexteUtil.centrer("Cartes : " + jeu.getMainJoueur (i)    , 55) + "\n" + 
				 TexteUtil.centrer("Cubes  : " + jeu.getCubesJoueur (i)   , 55) + "\n" +
				 TexteUtil.centrer("Trophees:" + jeu.getTropheesJoueur (i) , 55) + "\n\n";
		}
		System.out.println(s);	
	}


	public void demanderCarte   ( ){
		try {
			Scanner sc = new Scanner(System.in);
			int indiceCarte;
			do {
				System.out.print("Entrez l'index de la carte : ");
				indiceCarte = sc.nextInt();
				sc.nextLine();
			} while (indiceCarte < 1 || indiceCarte > 8);
			jeu.action('C', indiceCarte);
		}
		catch(Exception e) {

		}
	}

	public void demanderTuile ( ){
		try {
			Scanner sc = new Scanner(System.in);
			int indiceTuile;;
			do {
				System.out.print("Entrez l'index de la tuile : ");
				indiceTuile = sc.nextInt();
				sc.nextLine();
			} while (indiceTuile < 1 || indiceTuile > jeu.getNbTuiles());
			jeu.action('T', indiceTuile);
		}
		catch(Exception e) {

		}
	}

	public void demanderCote ( ){
		try {
			Scanner sc = new Scanner(System.in);
			char cote;
			do {
				System.out.print("Entrez le cote (G/D) : ");
				cote = sc.nextLine().toUpperCase().charAt(0);
			} while ( cote != 'G' && cote != 'D');
			jeu.action('S', (int)cote);
		}
		catch(Exception e) {

		}
	}

	public void demanderEchange ( ) {
		try {
			Scanner sc = new Scanner(System.in);
			String choix;
			do {
				System.out.println("Echanger des cubes? (O/N) ");
				choix = sc.nextLine().toUpperCase();
			} while( choix.charAt(0) != 'O' && choix.charAt(0) != 'N' );
			if ( choix.charAt(0) == 'O' ) {
				Couleur c1, c2;
				do {
					System.out.println("Couleur à échanger (R J V B G) : ");
					choix = sc.nextLine().toUpperCase();
					c1 = Couleur.getCouleur( choix.charAt( 0));
					System.out.println("Couleur voulue (R J V B G) : ");
					choix = sc.nextLine().toUpperCase();
					c2 = Couleur.getCouleur( choix.charAt( 0));
				} while( !jeu.echanger( c1, c2) );
			}
		}
		catch(Exception e) {

		}
		
	}

	public void demanderDefausse () {
		try {
			Scanner sc = new Scanner (System.in);
			int nb, indice;
			do {
				System.out.print("Combien de cartes voulez vous defaussez : ");
				nb = sc.nextInt();
			}while(nb < 1 && nb > 4);
			for(int i = 0 ; i < nb ; i++) {
				do {
					System.out.println("Donnez l'indice de la carte n°" + (i+1) + " : ");
					indice = sc.nextInt();
				}while(indice < 1 && indice > Constantes.NB_CARTE_MAX);
				jeu.action('D', indice);
			}
			jeu.action('P', nb);
		}
		catch(Exception e ){

		}
	}
	
	public static void main (String[] a) {
		IHMCui ihm = new IHMCui(new Jeu());
		System.out.println(ihm);
	}
}
