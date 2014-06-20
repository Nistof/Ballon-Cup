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
		setSize(1200,750);
		setLayout(new GridLayout(5,1));
		setSize(500,500);
		this.jeu = jeu;
		tuiles = new PanelTuile[Constantes.NB_TUILE];
		pioche = new PanelPioche();
		joueurs = new PanelJoueur[2];
		for(int i = 0 ; i < tuiles.length ; i++) {
			tuiles[i] = new PanelTuile(i+1);
			tuiles[i].actualiser(jeu.getCodeCartesTuile(i,'G'), jeu.getCodeCartesTuile(i,'D'), jeu.getPaysageTuile(i), jeu.getCodeCubesTuile(i));
			add(tuiles[i]);
		}
		JPanel bas = new JPanel(new GridLayout(1,3));
		joueurs[0] = new PanelJoueur('G');
		joueurs[1] = new PanelJoueur('D');
		joueurs[0].actualiser(jeu.getCodeCartesJoueur(0), jeu.getCodeCubesJoueur(0), jeu.getCodeTropheesJoueur(0));
		joueurs[1].actualiser(jeu.getCodeCartesJoueur(1), jeu.getCodeCubesJoueur(1), jeu.getCodeTropheesJoueur(1));
		bas.add(joueurs[0],BorderLayout.WEST);
		bas.add(pioche);
		bas.add(joueurs[1],BorderLayout.EAST);
		add(bas);
		setResizable(false);
		setVisible(true);
	}

	public void afficherTuiles  (){
		for(int i = 0 ; i < tuiles.length ; i++) 
			tuiles[i].actualiser(jeu.getCodeCartesTuile(i,'G'), jeu.getCodeCartesTuile(i,'D'), jeu.getPaysageTuile(i), jeu.getCodeCubesTuile(i));
	}

	public void afficherJoueurs  (){
		for(int i = 0 ; i < joueurs.length ; i++)
			joueurs[i].actualiser(jeu.getCodeCartesJoueur(i), jeu.getCodeCubesJoueur(i), jeu.getCodeTropheesJoueur(i));
	}
	public void demanderDefausse ( ){}
	public void demanderCarte   ( ){
		while(joueurs[jeu.getJoueur()].getCarte()==-1) {
			
		}
	}
	public void demanderTuile   ( ){}
	public void demanderCote   ( ){}
	public void demanderEchange ( ){}

}
