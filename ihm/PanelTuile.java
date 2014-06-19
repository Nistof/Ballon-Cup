/* PanelTuile 
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Groupe I2
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelTuile extends JPanel implements MouseListener {

	/*private Image paysage;
	private Image[] gauche;
	private Image[] droite;
	private Image[] cubes;*/

	private JLabel[] gauche;
	private JLabel[] droite;
	private JLabel paysage;
	private JLabel[] cubes;


	public PanelTuile (int nb) {
		this.setLayout(new BorderLayout());
		/*gauche = new Image[nb];
		droite = new Image[nb];
		cubes = new Image[nb];*/
		
		// Cartes de gauche
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,4));
		this.gauche = new JLabel[nb];
		for( int i=0; i<gauche.length; i++ ) {
			this.gauche[i] = new JLabel(new ImageIcon( "../ressources/images/cartes/carte_1BLEU.jpg" ));
			p1.add( this.gauche[i] );
		}
		p1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.add(p1, BorderLayout.WEST );
		


		// Paysage avec les cubes
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel(new GridLayout(1,4));
		this.paysage = new JLabel(new ImageIcon( "../ressources/images/tuiles/tuile1Montagne.jpg"));
		this.cubes = new JLabel[nb];
		for( int i=0; i<this.cubes.length; i++ ) {
			this.cubes[i] = new JLabel(new ImageIcon("../ressources/images/cubes/cube_ROUGE.jpg"));
			p3.add( this.cubes[i] );
		}
		p2.add( p3, BorderLayout.SOUTH );
		p2.add( this.paysage, BorderLayout.CENTER );
		this.add( p2 );
		

		// Cartes de droite
		JPanel p4 = new JPanel(new GridLayout(1,4));
		this.droite = new JLabel[nb];
		for( int i=0; i<droite.length; i++ ) {
			this.droite[i] = new JLabel(new ImageIcon( "../ressources/images/cartes/carte_1BLEU.jpg" ) );
			p4.add( this.droite[i] );
		}
		this.add( p4, BorderLayout.EAST );	
		paysage.addMouseListener(this);
	}

	public void mouseClicked (MouseEvent e) {
		this.gauche[0].setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void mouseExited (MouseEvent e) {
		this.gauche[0].setBorder(null);
	}
	
	public void mouseEntered (MouseEvent e) {
		this.gauche[0].setBorder(BorderFactory.createLineBorder(Color.PINK));	
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
		
		f.add( new PanelTuile( 3 ) );
		f.pack();
		f.setVisible(true);
	}
}
