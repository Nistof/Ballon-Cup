/* IHMGui
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;
import BallonCup.metier.*;
import BallonCup.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class IHMGui extends JFrame implements Ihm{

	private Jeu jeu;
	private PanelTuile[] tuiles;
	private PanelPioche pioche;
	private PanelJoueur[] joueurs;

	public IHMGui (Jeu jeu) {
		setLayout(new GridLayput(5,1));
		this.jeu = jeu;
		tuiles = new PanelTuile[Constantes.NB_TUILE];
		pioche = new PanelPioche();
		joueurs = new PanelJoueur[2];
		for(int i = 0 ; i < tuiles.length ; i++) {
			tuiles[i] = new PanelTuile(jeu.getCartesTuile(i,'G'), jeu.getCartesTuile(i,'D'));
			add(tuiles[i]);
		}
		JPanel bas = new JPanel(new BorderLayout());
		for(int i = 0 ; i < joueurs.length ; i++) {
			joueurs[i] = new PanelJoueur(jeu.getCartesJoueurs(i), jeu.getCubesJoueur(i), jeu.getTropheesJoueur(i));
		}
		bas.add(joueurs[0],BorderLayout.WEST);
		bas.add(joueurs[1],BorderLayout.EAST);
		bas.add(pioche);
		add(bas);
		setResizable(false);
		setVisible(true);
	}
}
