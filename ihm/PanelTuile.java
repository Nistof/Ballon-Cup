/* PanelTuile 
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Groupe I2
 */

package ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import metier.*;
import util.Couleur;

public class PanelTuile extends JPanel implements MouseListener {
	private JLayeredPane gauche ;
	private JLayeredPane droite ;
	private JLayeredPane paysage;
	private Panel        cubes  ;
	private Tuile        tuile  ;
	private char         cote   ;

	public PanelTuile (Tuile tuile) {
		this.setLayout(new GridLayout(1,3));
		this.tuile = tuile;

		gauche = new JLayeredPane();
		droite = new JLayeredPane();
		paysage = new JLayeredPane();
		
		gauche.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*tuile.getNombre(), 
											   Constantes.CARTE_HAUTEUR+25));
		droite.setPreferredSize(new Dimension( Constantes.CARTE_LARGEUR*tuile.getNombre(), 
											   Constantes.CARTE_HAUTEUR+25));				
		paysage.setPreferredSize(new Dimension( Constantes.TUILE_LARGEUR, 
												Constantes.TUILE_HAUTEUR));
		
		gauche.addMouseListener(this);
		droite.addMouseListener(this);
		
   		actualiser();
   		
		this.add(gauche);
		this.add(paysage);
		this.add(droite);
	}

	public void actualiser() {
		gauche.removeAll();
		droite.removeAll();
		paysage.removeAll();
		
		//Affichage des cartes à gauche
		int i = 0;
		for (Carte c : tuile.getGauche()) {
			ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + c.getValeur() +
											c.getCouleur().name() + Constantes.FORMAT_IMG);
											
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
		for (Carte c : tuile.getDroite()) {
			ImageIcon icon = new ImageIcon( Constantes.CH_CARTES_IMG + c.getValeur() +
											c.getCouleur().name() + Constantes.FORMAT_IMG);
											
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
		for ( Cube c : tuile.getCubes()) {
			JLabel imgLab = new JLabel( new ImageIcon( Constantes.CH_CUBES_IMG + c.getCouleur().name()
													   + Constantes.FORMAT_IMG));
			p.add( imgLab);
		}
		p.setBounds( 60, 50, Constantes.TUILE_LARGEUR, Constantes.TUILE_HAUTEUR);
		
		//Affichage du paysage
		String str = "" + Character.toUpperCase( tuile.getPaysage().charAt(0)) +
				   tuile.getPaysage().substring(1).toLowerCase();
				   
		ImageIcon icon = new ImageIcon( Constantes.CH_TUILES_IMG + tuile.getNombre() + 
										str + Constantes.FORMAT_IMG);

		Image img = icon.getImage();
		img = img.getScaledInstance( Constantes.TUILE_LARGEUR, 
									 Constantes.TUILE_HAUTEUR,  
									 java.awt.Image.SCALE_SMOOTH);
									 
		JLabel imgLab = new JLabel( new ImageIcon( img));
		imgLab.setBounds( 0, -50, icon.getIconWidth(), icon.getIconHeight());
		paysage.add( imgLab, new Integer(0));
		paysage.add( p, new Integer(1));
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
		Tuile t = new Tuile(4,"PLAINE");
		t.ajouterCube( new Cube( Couleur.ROUGE));
		t.ajouterCube( new Cube( Couleur.VERT));
		t.ajouterCube( new Cube( Couleur.JAUNE));
		t.ajouterCube( new Cube( Couleur.BLEU));
		t.ajouterCube( new Cube( Couleur.ROUGE));
		t.ajouterCube( new Cube( Couleur.VERT));
		t.ajouterCube( new Cube( Couleur.JAUNE));
		t.ajouterCarte('G', new Carte( Couleur.ROUGE, 5));
		t.ajouterCarte('G', new Carte( Couleur.VERT,  1));
		t.ajouterCarte('G', new Carte( Couleur.JAUNE, 1));
		t.ajouterCarte('D', new Carte( Couleur.ROUGE, 6));
		t.ajouterCarte('D', new Carte( Couleur.VERT,  2));
		t.ajouterCarte('D', new Carte( Couleur.BLEU,  1));
		PanelTuile pt = new PanelTuile( t );
		f.add( pt);
		f.pack();
		f.setVisible(true);
	}
}
