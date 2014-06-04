/* Classe: Defausse
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;
import java.util.*;

public class Defausse extends Tas {

	public Defausse () {
		super();
	}	
		
	public ArrayList<Carte> transferer () {
		ArrayList<Carte> alTemp = (ArrayList<Carte>)this.melanger().clone();
		vider();
		return alTemp;	
	}

	private ArrayList<Carte>  melanger () {
		Collections.shuffle(getAlCartes());
		return getAlCartes();
	}

	private void vider () {
		getAlCartes().clear();
	}

}
