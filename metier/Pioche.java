/* Classe: Pioche
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 09/06/2014
**/

package metier;
import java.util.*;

public class Pioche<E> extends Tas<E> {
	public Pioche () {
		super();
	}

	public Pioche (ArrayList<E> alE) {
		super( alE);
	}

	public E piocher () {
		return (super.taille() != 0)?getAlE().remove(taille()-1):null;
	}
	
	public void melanger () {
		Collections.shuffle(getAlE());
	}
	
	public String toString() {
		String s="Pioche :\n";
		
		for( E e : super.getAlE() )
			s += "\t" + e + "\n";
			
		return s;
	}

}
