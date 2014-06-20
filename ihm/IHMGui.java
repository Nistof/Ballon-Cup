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


public class IHMGui extends JFrame implements Ihm, MouseListener {

	private Jeu jeu;
	private PanelTuile[]  tuiles;
	private PanelPioche   pioche;
	private PanelJoueur[] joueurs;
	private boolean       selectionTuile;
	private char          sCote;
	private int           sIdTuile;

	public IHMGui (Jeu jeu) {
		setSize(1200,750);
		setLayout(new GridLayout(5,1));
		this.jeu = jeu;
		selectionTuile = false;
		
		tuiles = new PanelTuile[Constantes.NB_TUILE];
		pioche = new PanelPioche();
		joueurs = new PanelJoueur[2];
		
		for(int i = 0 ; i < tuiles.length ; i++) {
			tuiles[i] = new PanelTuile(i+1);
			tuiles[i].actualiser(jeu.getCodeCartesTuile(i,'G'), jeu.getCodeCartesTuile(i,'D'), jeu.getPaysageTuile(i), jeu.getCodeCubesTuile(i));
			tuiles[i].addMouseListener(this);
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
		System.out.println("Demande Carte Debut");
		int c = joueurs[jeu.getJoueur()].getCarte();
		while( c == -1) {
			c = joueurs[jeu.getJoueur()].getCarte();
			System.out.print("");
		}
		jeu.action('C', c);
		System.out.println("Demande Carte Fin");
	}

	public void demanderTuile   ( ){
		System.out.println("Demande Tuile Debut");
		selectionTuile = true;
		for ( int i = 0; i < tuiles.length; i++)
			tuiles[i].setSelectionable( true);
		sIdTuile = -1;
			
		while (sIdTuile == -1) {
			System.out.print("");
		}	
		jeu.action('T', sIdTuile);
		
		selectionTuile = false;
		for ( int i = 0; i < tuiles.length; i++)
			tuiles[i].setSelectionable( false);
		sIdTuile = -1;
		System.out.println("Demande Tuile Fin");
	}
	
	public void demanderCote   ( ) {
		System.out.println("Demande Cote Debut");
		jeu.action('S', (int)sCote);
		sCote = ' ';
		System.out.println("Demande Cote Fin");
	}
	
	public void demanderEchange ( ){}

	
	public void mouseClicked (MouseEvent e) { }
	
	public void mouseExited (MouseEvent e) { }
	
	public void mouseEntered (MouseEvent e) { }
	
	public void mouseReleased (MouseEvent e) { }
	
	public void mousePressed (MouseEvent e) {
		if ( selectionTuile ) {
			for ( int i = 0; i < tuiles.length; i++)
				if ( e.getSource() == tuiles[i] && tuiles[i].getCote() != ' ') {
					sIdTuile = i+1;
					sCote = tuiles[i].getCote();
				} else {
					tuiles[i].deselectionner();
				}
		}
	}
}
