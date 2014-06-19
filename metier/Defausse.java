/* Classe: Defausse
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package BallonCup.metier;
import java.util.*;

public class Defausse extends Tas<Carte> {

	public Defausse () {
		super();
	}	
	
	//Fait une copie de la défausse avant de la vider
	//puis renvoie cette copie.
	public ArrayList<Carte> transferer () {
		ArrayList<Carte> alTemp = new ArrayList<Carte>( getAlE());
		vider();
		return alTemp;	
	}

	private void vider () {
		getAlE().clear();
	}
	
	public String toString() {
		String s="Defausse :\n";
		
		for( Carte c : super.getAlE() )
			s += "\t" + c + "\n";
			
		return s;
	}

}
