/* PanelJoueur
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import BallonCup.Constantes;
import BallonCup.util.*;

public class PanelJoueur extends JPanel implements MouseListener {
	private char         cote;
	private JLabel[]     cartesImages;
	private JLabel[]     cubes;
	private JLabel[]     nbCubes;
	private JLayeredPane cartes;
	private JLayeredPane trophees;
	private int          selection;	
	private JPanel       p;
	
	public PanelJoueur( char cote) {
		this.cote = cote;
		setLayout(new BorderLayout());
		
		cubes   = new JLabel[5];
		nbCubes = new JLabel[5];
		cartesImages = new JLabel[8];
		p = new JPanel( new GridLayout( 1, 10 ) );
		
		cartes = new JLayeredPane();
		cartes.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*(Constantes.NB_CARTE_MAX+1), 
											   Constantes.CARTE_HAUTEUR+25));
		trophees = new JLayeredPane();
		trophees.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR, 
											    (Constantes.CARTE_HAUTEUR*Constantes.NB_TROPHE_VICTOIRE)/2+25));
		
		for(Couleur c : Couleur.values()) {
			this.cubes[c.ordinal()] = new JLabel( new ImageIcon(Constantes.CH_CUBES_IMG +  c.name() + 
																Constantes.FORMAT_IMG) );
			this.nbCubes[c.ordinal()] = new JLabel( "0" );
		}
		
		for(int i = 0 ; i < cartesImages.length; i++) {
			cartesImages[i] = new JLabel();
			cartesImages[i].addMouseListener(this);
		}

		for( int j = 0; j < this.nbCubes.length; j++ ) {
			p.add( cubes[j] );
			p.add( nbCubes[j] );
		}
		
		add(p, BorderLayout.NORTH);
		add(cartes);
		if ( cote == 'G')
			add(trophees, BorderLayout.WEST);
		else
			add(trophees, BorderLayout.EAST);

	}
	
	public void actualiser( String mainJ, String cubesJ, String trophee) {
		trophees.removeAll();
		cartes.removeAll();
	
		//Main du joueur
		int i = 0;
		String[] mTab = mainJ.split(":");
		for( String s : mTab ) {
			if ( s != "") {
				ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + s + 
												Constantes.FORMAT_IMG);
											
				Image img = icon.getImage();
				img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
											 Constantes.CARTE_HAUTEUR,
											 java.awt.Image.SCALE_SMOOTH);
										 
				cartesImages[i].setIcon(new ImageIcon( img));
				cartesImages[i].setBounds(( Constantes.CARTE_LARGEUR/3)*i, -50, 
							   icon.getIconWidth(), icon.getIconHeight());
							   
				cartes.add( cartesImages[i], new Integer(i));
				i++;
			}
		}
		
		// Cubes du joueur
		String[] cTab = cubesJ.split(":");
		for(Couleur c : Couleur.values()) {
			nbCubes[c.ordinal()].setText( "0" );
		}
		for ( String s : cTab ) {
			if ( s != "") {
				int ind = Couleur.getCouleur( s).ordinal();
				System.out.println(ind + "  " + (Integer.parseInt(this.nbCubes[ind].getText())+1));
				this.nbCubes[ind].setText(""+(Integer.parseInt(this.nbCubes[ind].getText())+1));
			}
		}
		
		
		//Trophées
		i = 0;
		String[] tTab = trophee.split(":");
		for( String s : tTab ) {
			if( s != "") {
				ImageIcon icon = new ImageIcon( Constantes.CH_TROPHEES_IMG + s + 
												Constantes.FORMAT_IMG);
														
				Image img = icon.getImage();
				img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
											 Constantes.CARTE_HAUTEUR,
											 java.awt.Image.SCALE_SMOOTH);
										 
				JLabel imgLab = new JLabel( new ImageIcon( img));
				imgLab.setBounds( -50, (Constantes.CARTE_HAUTEUR/2)*i, 
							      icon.getIconWidth(), icon.getIconHeight());
							   
				trophees.add( imgLab, new Integer(i));
				i++;
			}
		}
	}

	public int getCarte () {
		return selection;
	}

	public void mouseClicked (MouseEvent e) {	
		System.out.println(cartes.getIndexOf((Component)e.getSource()));
	}
	
	public void mouseExited (MouseEvent e) {
	}
	
	public void mouseEntered (MouseEvent e) {
	}
	
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}

	public static void main( String[] args ) {
		JFrame f = new JFrame();
		PanelJoueur pj = new PanelJoueur('G');
		pj.actualiser("1ROUGE:13VERT:13BLEU:13GRIS","ROUGE:ROUGE:ROUGE", "ROUGE");
		f.add( pj);
		f.pack();
		f.setVisible(true);
	}
}
