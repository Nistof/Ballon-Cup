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


public class IHMGui extends JFrame {

	private Jeu jeu;
	private PanelTuile[] tuiles;
	private PanelPioche pioche;
	private PanelJoueur[] joueurs;

	public IHMGui (Jeu jeu) {
		this.jeu = jeu;
		tuiles = new PanelTuile[Constantes.NB_TUILE];
		pioche = new PanelPioche();
		joueurs = new PanelJoueur[2];
	}
}
