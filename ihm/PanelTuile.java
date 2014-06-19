/* PanelTuile 
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Groupe I2
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelTuile extends JPanel {

	/*private Image paysage;
	private Image[] gauche;
	private Image[] droite;
	private Image[] cubes;*/

	private JLabel[] gauche;
	private JLabel[] droite;
	private JLabel paysage;
	private JLabel[] cubes;


	public PanelTuile (int nb) {
		setLayout(new BorderLayout());
		gauche = new Image[nb];
		droite = new Image[nb];
		cubes = new Image[nb];
		
		// Cartes de gauche
		JPanel p1 = new JPanel(new GridLayout(1,4));
		this.gauche = new JLabel[nb];
		for( int i=0; i<gauche.length; i++ ) {
			this.gauche[i] = new JLabel();
			p1.add( this.gauche[i] );
		}
		gauche.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.add( gauche, BorderLayout.WEST );
		


		// Paysage avec les cubes
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel(new GrodLayout(1,4));
		this.paysage = new JLabel();
		this.cubes = new JLabel[nb];
		for( int i=0; i<this.cubes.length; i++ ) {
			this.cubes[i] = new 
		}
		

		// Cartes de droite	
		JPanel droite = new JPanel(new GridLayout(1,4));
		this.add( droite, BorderLayout.EAST );	
	}

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

}
