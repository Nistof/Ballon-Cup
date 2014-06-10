/* Classe: Defausse
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;
import java.util.*;

public class Defausse extends Tas<Carte> {

	public Defausse () {
		super();
	}	
		
	public ArrayList<Carte> transferer () {
		ArrayList<Carte> alTemp = new ArrayList<Carte>( this.melanger());
		vider();
		return alTemp;	
	}

	private ArrayList<Carte>  melanger () {
		Collections.shuffle(getE());
		return getE();
	}

	private void vider () {
		getE().clear();
	}

}
