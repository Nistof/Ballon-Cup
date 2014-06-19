/* IHMCui
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;
import BallonCup.Jeu;
import BallonCup.util.*;

public class IHMCui {
	private Jeu jeu;

	public IHMCui (Jeu jeu) {
		this.jeu = jeu;
	}

	public String toString() {
		String s="";
		String s2 = "";
		
		for( int i=0; i< jeu.getNbTuiles() ; i++ )
			s += jeu.afficheTuile(i) + "\n\n";
		
		s += "------------------------------------------------------\n";
		
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
		
		return s;
	}
	
	public static void main (String[] a) {
		IHMCui ihm = new IHMCui(new Jeu());
		System.out.println(ihm);
	}

}
