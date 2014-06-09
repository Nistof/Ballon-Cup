/* Classe: Tas
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;
import java.util.*;

public class Tas {
	ArrayList<Carte> alCartes;

	public Tas () {
		alCartes = new ArrayList<Carte>();
	}

	public Tas (ArrayList<Carte> alCartes) {
		this.alCartes = alCartes;
	}

	public void ajouter (Carte c) {
		alCartes.add(c);
	}

	public ArrayList<Carte> getAlCartes() {
		return alCartes;
	}

	public int taille() {
		return alCartes.size();
	}

}
