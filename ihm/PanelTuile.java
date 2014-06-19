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

	/*private Image paysage;
	private Image[] gauche;
	private Image[] droite;
	private Image[] cubes;*/
	private final static String CH_CARTES_IMG = "ressources/images/cartes/carte_";
	private final static String CH_TUILES_IMG = "ressources/images/tuiles/tuile" ;
	private final static String CH_CUBES_IMG  = "ressources/images/cubes/cube_"  ;
	private final static String FORMAT_IMG    = ".jpg";

	private final static int POURC            = 50;
	private final static int CARTE_HAUTEUR    = (int)(252-(POURC*252/100)); //252 : Hauteur de l'image de base
	private final static int CARTE_LARGEUR    = (int)(162-(POURC*162/100)); //162 : Largeur de l'image de base
	private final static int TUILE_HAUTEUR    = (int)(250-(POURC*250/100)); //252 : Hauteur de l'image de base
	private final static int TUILE_LARGEUR    = (int)(245-(POURC*245/100)); //162 : Largeur de l'image de base

	private JLayeredPane gauche ;
	private JLayeredPane droite ;
	private JLayeredPane paysage;
	private Panel        cubes  ;
	private Tuile        tuile  ;

	public PanelTuile (Tuile tuile) {
		this.setLayout(new GridLayout(1,3));
		this.tuile = tuile;

		gauche = new JLayeredPane();
		droite = new JLayeredPane();
		paysage = new JLayeredPane();
		
		gauche.setPreferredSize(new Dimension( CARTE_LARGEUR*tuile.getNombre(), CARTE_HAUTEUR+25));
		droite.setPreferredSize(new Dimension( CARTE_LARGEUR*tuile.getNombre(), CARTE_HAUTEUR+25));				
		paysage.setPreferredSize(new Dimension( TUILE_LARGEUR, TUILE_HAUTEUR));
		
   		actualiser();
   		
		this.add(gauche);
		this.add(paysage);
		this.add(droite);
	}

	public void actualiser() {
		gauche.removeAll();
		droite.removeAll();
		paysage.removeAll();
		
		int i = 0;
		for (Carte c : tuile.getGauche()) {
			ImageIcon icon = new ImageIcon( CH_CARTES_IMG + c.getValeur() + c.getCouleur().name() + FORMAT_IMG);
			Image img = icon.getImage();
			img = img.getScaledInstance( CARTE_LARGEUR, CARTE_HAUTEUR,  java.awt.Image.SCALE_SMOOTH);
			JLabel imgLab = new JLabel( new ImageIcon( img));
			imgLab.setBounds((CARTE_LARGEUR/2)*i, -50, icon.getIconWidth(), icon.getIconHeight());
			gauche.add( imgLab);
			i++;
		}
		
		i = 0;
		for (Carte c : tuile.getDroite()) {
			ImageIcon icon = new ImageIcon( CH_CARTES_IMG + c.getValeur() + c.getCouleur().name() + FORMAT_IMG);
			Image img = icon.getImage();
			img = img.getScaledInstance( CARTE_LARGEUR, CARTE_HAUTEUR,  java.awt.Image.SCALE_SMOOTH);
			JLabel imgLab = new JLabel( new ImageIcon( img));
			imgLab.setBounds((CARTE_LARGEUR/2)*i, -50, icon.getIconWidth(), icon.getIconHeight());
			droite.add( imgLab, new Integer(i), 0);
			i++;
		}
		

		JPanel p = new JPanel( new FlowLayout());
		for ( Cube c : tuile.getCubes()) {
			JLabel imgLab = new JLabel( new ImageIcon( CH_CUBES_IMG + c.getCouleur().name() + FORMAT_IMG));
			p.add( imgLab);
		}
		
		String str = "" + Character.toUpperCase( tuile.getPaysage().charAt(0)) +
				   tuile.getPaysage().substring(1).toLowerCase();
		ImageIcon icon = new ImageIcon( CH_TUILES_IMG + tuile.getNombre() + str + FORMAT_IMG);
		Image img = icon.getImage();
		img = img.getScaledInstance( TUILE_LARGEUR, TUILE_HAUTEUR,  java.awt.Image.SCALE_SMOOTH);
		JLabel imgLab = new JLabel( new ImageIcon( img));
		imgLab.setBounds( 0, -50, icon.getIconWidth(), icon.getIconHeight());
		paysage.add( imgLab);
		paysage.add( p);
	}

	public void mouseClicked (MouseEvent e) {
		//this.gauche[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void mouseExited (MouseEvent e) {
		//this.gauche[0].setBorder(null);
	}
	
	public void mouseEntered (MouseEvent e) {
		//this.gauche[0].setBorder(BorderFactory.createLineBorder(Color.PINK));	
	}
	
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}

	public void ajouterImage(char objet, Image i) {
		switch(Character.toUpperCase(objet)) {
			case 'G':
			
			
				break;
			case 'D':
			
			
				break;
			case 'C':
			
			
				break;
		}
		
		
	}
	
	public static void main( String[] args ) {
		JFrame f = new JFrame();
		f.setTitle( "Test PanelTuile" );
		//f.setSize(1000,1000);
		Tuile t = new Tuile(4,"PLAINE");
		PanelTuile pt = new PanelTuile( t );
		t.ajouterCube( new Cube( Couleur.ROUGE));
		t.ajouterCube( new Cube( Couleur.VERT));
		t.ajouterCube( new Cube( Couleur.JAUNE));
		t.ajouterCube( new Cube( Couleur.BLEU));
		t.ajouterCarte('G', new Carte( Couleur.ROUGE, 5));
		t.ajouterCarte('G', new Carte( Couleur.VERT,  1));
		t.ajouterCarte('G', new Carte( Couleur.JAUNE, 1));
		t.ajouterCarte('D', new Carte( Couleur.ROUGE, 6));
		t.ajouterCarte('D', new Carte( Couleur.VERT,  2));
		t.ajouterCarte('D', new Carte( Couleur.JAUNE, 2));
		pt.actualiser();
		f.add( pt);
		//f.setResizable(false);
		f.pack();
		f.setVisible(true);
		
	}
}
