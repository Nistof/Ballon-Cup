/* Classe: Pioche
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 09/06/2014
**/

package metier;
import java.util.*;

public class Pioche extends Tas {
	public Pioche () {
		super();
	}

	public Pioche (ArrayList<Carte> alCartes) {
		super( alCartes);
	}

	public Carte enlever () {
		return getAlCartes().remove(0);
	}
}
