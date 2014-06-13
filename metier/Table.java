/* Classe : Table
 * @author MARECAL Thomas 
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Group : I2
 */

package metier;
import java.util.*;

public class Table {
	private final static String[] TYPES_PAYSAGE = {"PLAINE","MONTAGNE"};
	private ArrayList<Carte> gauche;
	private ArrayList<Carte> droite;
	private ArrayList<Cube>  cubes ;
	private int nombre;
	private String paysage;

	public Table (int nb, String paysage) {
		gauche = new ArrayList<Carte>();
		droite = new ArrayList<Carte>();
		cubes  = new ArrayList<Cube> ();
		nombre = nb;
		this.paysage = paysage;
	}

	//Ajoute une Carte sur le côté de la Table séléctionné
	//seulement si ce côté n'est pas plein
	public boolean ajouterCarte (char cote, Carte c) {
		if(cote == 'G' && gauche.size() < nombre) {
			gauche.add(c);
			return true;
		}
		else if(cote == 'D' && droite.size() < nombre) {
			droite.add(c);
			return true;
		}
		return false;
	}
	
	//Retire toutes les Cartes de la Table et les place
	//dans l'objet de type Tas passé en paramètre.
	public void oterCartes( Tas<Carte> tas ) {
		while( !this.gauche.isEmpty() )
			tas.ajouter( this.gauche.remove(0) );
		
		while( !this.droite.isEmpty() )
			tas.ajouter( this.droite.remove(0) );
		
	} 
	
	//Ajoute un Cube sur la Table seulement si
	//le nombre de Cube possible n'est pas atteint
	public boolean ajouterCube( Cube c) {
		if ( cubes.size() < nombre) {
			cubes.add(c);
			return true;
		}
		
		return false;
	}
}
