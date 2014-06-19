/* IHMCui
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;
import BallonCup.Jeu;
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


	public void demanderCarte   ( ){}
	public void demanderTuile   ( ){}
	public void demanderEchange ( ){}

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
				}while(indice < 1 && indice > 8);
				jeu.action('D', indice);
			}
			jeu.action('P', nb);
		}
		catch(Exception e) {

		}
	}

	
	public static void main (String[] a) {
		IHMCui ihm = new IHMCui(new Jeu());
		System.out.println(ihm);
	}
}
