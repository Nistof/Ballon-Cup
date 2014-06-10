/* Classe: Tas
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;
import java.util.*;

public class Tas<E> {
	ArrayList<E>> alE;

	public Tas () {
		alE = new ArrayList<E>();
	}

	public Tas (ArrayList<E> alE) {
		this.alE = alE;
	}

	public void ajouter (E e) {
		alE.add(e);
	}

	public ArrayList<E> getAlE() {
		return alE;
	}

	public int taille() {
		return alE.size();
	}

}
