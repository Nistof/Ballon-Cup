/* PanelPioche 
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class PanelPioche extends JPanel {
	
	private JLabel pioche;
	private JLabel defausse;
	private ImageIcon  dosCarte;
	
	public PanelPioche() {
		this.setLayout( new GridLayout( 1, 2, 5, 0 ) );
		this.dosCarte = new ImageIcon("../ressources/images/cartes/dosCarte.jpg");
		pioche = new JLabel( dosCarte );
		defausse = new JLabel();
	
		pioche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add( this.pioche );

		defausse.setSize(pioche.getWidth(), pioche.getHeight());
		defausse.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add( this.defausse );
	}

	public void piocheVide () { pioche.setIcon(null); }
	public void piocheRemplie () { pioche.setIcon(dosCarte); }
	public void defausseVide () { defausse.setIcon(null); }
	public void defausseRemplie () { defausse.setIcon(dosCarte); }
	
	public static void main( String[] args ) throws Exception {
		JFrame f = new JFrame();
		PanelPioche p = new PanelPioche();
		f.add(p);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
	}

}
