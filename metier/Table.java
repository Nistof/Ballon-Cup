/* Classe : Table
 * @author MARECAL Thomas 
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Group : I2
 */

package metier;
import java.util.*;

public class Table {
	private static String[] typesPaysage = {"PLAINE","MONTAGNE"};
	private ArrayList<Carte> gauche;
	private ArrayList<Carte> droite;
	private int nbMaximum;
	private String paysage;

	public Table (int nbMax, String paysage) {
		gauche = new ArrayList<Carte>();
		droite = new ArrayList<Carte>();
		nbMaximum = nbMax;
		this.paysage = paysage;
	}

	public boolean ajouter (char cote, Carte c) {
		if(cote == 'G' && gauche.size() < nbMaximum) {
			gauche.add(c);
			return true;
		}
		else if(cote == 'D' && droite.size() < nbMaximum) {
			droite.add(c);
			return true;
		}
		return false;
	}

	public void vider (Tas<Carte> t) {
		for(Carte c : gauche)
			t.ajouter(c);
		for(Carte c : droite)
			t.ajouter(c);
		droite.clear();
		gauche.clear();
	}
}
