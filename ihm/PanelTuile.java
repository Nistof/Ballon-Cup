/* PanelTuile 
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package BallonCup.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import BallonCup.metier.*;
import BallonCup.Constantes;
import BallonCup.util.Couleur;

public class PanelTuile extends JPanel implements MouseListener {
	private JLayeredPane gauche ;
	private JLayeredPane droite ;
	private JLayeredPane paysage;
	private Panel        cubes  ;
	private Tuile        tuile  ;
	private char         cote   ;
	private int          nombre ;

	public PanelTuile ( int nombre) {
		this.setLayout(new GridLayout(1,3));
		this.nombre = nombre;

		this.gauche = new JLayeredPane();
		this.droite = new JLayeredPane();
		this.paysage = new JLayeredPane();
		
		gauche.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*nombre, 
											   Constantes.CARTE_HAUTEUR+25));
		droite.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*nombre, 
											   Constantes.CARTE_HAUTEUR+25));				
		paysage.setPreferredSize(new Dimension( Constantes.TUILE_LARGEUR, 
												Constantes.TUILE_HAUTEUR));
		
		gauche.addMouseListener(this);
		droite.addMouseListener(this);
   		
		this.add(gauche);
		this.add(paysage);
		this.add(droite);
	}

	public void actualiser( String cartesG, String cartesD, String paysage, String cubes) {
		this.gauche.removeAll();
		this.droite.removeAll();
		this.paysage.removeAll();
		
		//Affichage des cartes à gauche
		int i = 0;
		String[] cgTab = cartesG.split(":");
		for (String s : cgTab) {
			ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + s
										  + Constantes.FORMAT_IMG);
											
			Image img = icon.getImage();
			img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
										 Constantes.CARTE_HAUTEUR,
										 java.awt.Image.SCALE_SMOOTH);
										 
			JLabel imgLab = new JLabel( new ImageIcon( img));
			imgLab.setBounds(( Constantes.CARTE_LARGEUR/2)*i, -50, 
							   icon.getIconWidth(), icon.getIconHeight());
							   
			gauche.add( imgLab);
			i++;
		}
		
		//Affichage des cartes à droite
		i = 0;
		String[] cdTab = cartesD.split(":");
		for (String s : cdTab) {
			ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + s
										  + Constantes.FORMAT_IMG);
											
			Image img = icon.getImage();
			img = img.getScaledInstance( Constantes.CARTE_LARGEUR, 
										 Constantes.CARTE_HAUTEUR,  
										 java.awt.Image.SCALE_SMOOTH);
										 
			JLabel imgLab = new JLabel( new ImageIcon( img));
			imgLab.setBounds(( Constantes.CARTE_LARGEUR/2)*i, -50, 
							   icon.getIconWidth(), icon.getIconHeight());
							   
			droite.add( imgLab, new Integer(i), 0);
			i++;
		}
		
		//Affichage des Cubes
		JLayeredPane p = new JLayeredPane();
		p.setPreferredSize( new Dimension( 100, 50));
		p.setLayout( new FlowLayout());
		String[] cTab = cubes.split(":");
		for ( String s : cTab ) {
			JLabel imgLab = new JLabel( new ImageIcon( Constantes.CH_CUBES_IMG + s
													   + Constantes.FORMAT_IMG));
			p.add( imgLab);
		}
		p.setBounds( 60, 50, Constantes.TUILE_LARGEUR, Constantes.TUILE_HAUTEUR);
		
		//Affichage du paysage
		String str = "" + Character.toUpperCase( paysage.charAt(0)) +
				   paysage.substring(1).toLowerCase();
				   
		ImageIcon icon = new ImageIcon( Constantes.CH_TUILES_IMG + nombre + 
										str + Constantes.FORMAT_IMG);

		Image img = icon.getImage();
		img = img.getScaledInstance( Constantes.TUILE_LARGEUR, 
									 Constantes.TUILE_HAUTEUR,  
									 java.awt.Image.SCALE_SMOOTH);
									 
		JLabel imgLab = new JLabel( new ImageIcon( img));
		imgLab.setBounds( 0, -50, icon.getIconWidth(), icon.getIconHeight());
		this.paysage.add( imgLab, new Integer(0));
		this.paysage.add( p, new Integer(1));
	}

	public void mouseClicked (MouseEvent e) {	
		if ( e.getSource() == gauche && cote != 'G') {
			this.gauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.droite.setBorder(null);
			cote = 'G';
		} else if ( e.getSource() == droite && cote != 'D') {
			this.droite.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.gauche.setBorder(null);
			cote = 'D';
		}
	}
	
	public void mouseExited (MouseEvent e) {
		if ( cote == 'G') {
			this.droite.setBorder(null);
		} else if ( cote == 'D' ) {
			this.gauche.setBorder(null);
		} else {
			this.droite.setBorder(null);
			this.gauche.setBorder(null);
		}
	}
	
	public void mouseEntered (MouseEvent e) {
		if ( e.getSource() == gauche && cote != 'G')
			this.gauche.setBorder(BorderFactory.createLineBorder(Color.PINK));
		else if ( e.getSource() == droite && cote != 'D')
			this.droite.setBorder(BorderFactory.createLineBorder(Color.PINK));
	}
	
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	
	public char getCote() { return this.cote; }
	public void deselectionner() { cote = ' '; }
	
	public static void main( String[] args ) {
		JFrame f = new JFrame();
		f.setTitle( "Test PanelTuile" );
		PanelTuile pt = new PanelTuile( 2 );
		pt.actualiser("13ROUGE","1ROUGE","MONTAGNE","ROUGE");
		f.add( pt);
		f.pack();
		f.setVisible(true);
	}
}
