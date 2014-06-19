/* PanelJoueur
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package ihm;

import util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import metier.Joueur;
import metier.Carte;

public class PanelJoueur extends JPanel {
	
	private final static String cheminCubes = "ressources/images/cubes/cube_";
	private final static String cheminCartes = "ressources/images/cartes/carte_";

	private Joueur joueur;
	private JLabel[] cubes;
	private JLabel[] nbCubes;
	private JLabel[] cartes;
	private JLabel[] trophees;
	
	public PanelJoueur( Joueur joueur ) {
		setLayout(new BorderLayout());
		this.joueur = joueur;
		
		this.cubes   = new JLabel[5];
		this.nbCubes = new JLabel[5];
		
		// Cubes du joueur
		JPanel p = new JPanel( new GridLayout( 1, 10 ) );		

		for(Couleur c : Couleur.values()) {
			this.cubes[c.ordinal()] = new JLabel( new ImageIcon(cheminCubes +  c.name() + ".jpg") );
			this.nbCubes[c.ordinal()] = new JLabel( "0" );
		}	
		for( int i=0; i<this.nbCubes.length; i++ ) {
			p.add( this.cubes[i] );
			p.add( this.nbCubes[i] );
		}	
		this.add(p, BorderLayout.NORTH);	
			
		// Cartes du joueur
		JPanel p2 = new JPanel( new GridLayout( 1, Joueur.NB_CARTE_MAX ) );
		this.cartes = new JLabel[Joueur.NB_CARTE_MAX];
		for(int i = 0; i < cartes.length; i++) {
			if(joueur.getNomCarte(i) != null) {
				cartes[i] = new JLabel( new ImageIcon(cheminCartes + joueur.getNomCarte(i) + ".jpg"));
				p2.add( cartes[i] );
			}
		}
		this.add(p2, BorderLayout.SOUTH);

		// Cartes trophees du joueur
		JPanel p3 = new JPanel();
		this.trophees = new JLabel[Joueur.NB_TROPHE_VICTOIRE];
	}

	public static void main( String[] args ) {
		JFrame f = new JFrame();
		Joueur j = new Joueur("Thomas", 'G');
		j.ajouterCarte(new Carte(Couleur.ROUGE, 13));
		j.ajouterCarte(new Carte(Couleur.ROUGE, 13));
		j.ajouterCarte(new Carte(Couleur.ROUGE, 13));
		f.add(new PanelJoueur(j) );
		f.pack();
		f.setVisible(true);
	}
}
