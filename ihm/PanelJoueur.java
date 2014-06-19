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
import BallonCup.metier.*;
import BallonCup.Constantes;

public class PanelJoueur extends JPanel {
	private Joueur       joueur;
	private JLabel[]     cubes;
	private JLabel[]     nbCubes;
	private JLayeredPane cartes;
	private JLayeredPane trophees;
	
	private JPanel       p;
	
	public PanelJoueur( Joueur joueur ) {
		setLayout(new BorderLayout());
		this.joueur = joueur;
		
		cubes   = new JLabel[5];
		nbCubes = new JLabel[5];
		p = new JPanel( new GridLayout( 1, 10 ) );
		
		cartes = new JLayeredPane();
		cartes.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*Joueur.NB_CARTE_MAX, 
											   Constantes.CARTE_HAUTEUR+25));
		trophees = new JLayeredPane();
		trophees.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR, 
											    (Constantes.CARTE_HAUTEUR*Joueur.NB_TROPHE_VICTOIRE)/2+25));
		
		actualiser();
		
		add(p, BorderLayout.NORTH);
		add(cartes);
		if ( joueur.getCote() == 'G')
			add(trophees, BorderLayout.WEST);
		else
			add(trophees, BorderLayout.EAST);
	}
	
	public void actualiser() {
		trophees.removeAll();
		cartes.removeAll();
		p.removeAll();
	
		//Main du joueur
		int i = 0;
		for(int j = 0; j < joueur.getNbCarte(); j++) {
			if(joueur.getNomCarte(j) != null) {
				ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + joueur.getNomCarte(j) + 
												Constantes.FORMAT_IMG);
											
				Image img = icon.getImage();
				img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
											 Constantes.CARTE_HAUTEUR,
											 java.awt.Image.SCALE_SMOOTH);
										 
				JLabel imgLab = new JLabel( new ImageIcon( img));
				imgLab.setBounds(( Constantes.CARTE_LARGEUR/2)*i, -50, 
							   icon.getIconWidth(), icon.getIconHeight());
							   
				cartes.add( imgLab, new Integer(j));
				i++;
			}
		}
		
		// Cubes du joueur
		for(Couleur c : Couleur.values()) {
			this.cubes[c.ordinal()] = new JLabel( new ImageIcon(Constantes.CH_CUBES_IMG +  c.name() + 
																Constantes.FORMAT_IMG) );
			this.nbCubes[c.ordinal()] = new JLabel( "0" );
		}
		
		for( int j = 0; j < this.nbCubes.length; j++ ) {
			p.add( cubes[j] );
			p.add( nbCubes[j] );
		}
		
		//Trophées
		i = 0;
		for(int j = 0; j < joueur.getNbTrophee(); j++) {
			if(joueur.getNomCarte(j) != null) {
				ImageIcon icon = new ImageIcon( Constantes.CH_TROPHEES_IMG + joueur.getNomTrophee(j) + 
												Constantes.FORMAT_IMG);
														
				Image img = icon.getImage();
				img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
											 Constantes.CARTE_HAUTEUR,
											 java.awt.Image.SCALE_SMOOTH);
										 
				JLabel imgLab = new JLabel( new ImageIcon( img));
				imgLab.setBounds( -50, (Constantes.CARTE_HAUTEUR/2)*i-50, 
							      icon.getIconWidth(), icon.getIconHeight());
							   
				trophees.add( imgLab, new Integer(j));
				i++;
			}
		}
	}

	public static void main( String[] args ) {
		JFrame f = new JFrame();
		Joueur j = new Joueur("Thomas", 'G');
		j.ajouterTrophee( new Trophee( Couleur.ROUGE, 7));
		j.ajouterTrophee( new Trophee( Couleur.JAUNE, 6));
		j.ajouterTrophee( new Trophee( Couleur.VERT , 5));
		j.ajouterCube( new Cube (Couleur.ROUGE));
		j.ajouterCarte(new Carte(Couleur.ROUGE, 13));
		j.ajouterCarte(new Carte(Couleur.VERT , 13));
		j.ajouterCarte(new Carte(Couleur.BLEU , 13));
		f.add(new PanelJoueur(j) );
		f.pack();
		f.setVisible(true);
	}
}
