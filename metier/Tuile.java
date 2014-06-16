/* Classe : Tuile
 * @author MARECAL Thomas 
 * @author MARTIN Florian
 * @author QUENTIN Thibaut
 * Group : I2
 */

package metier;
import java.util.*;

public class Tuile {
	public  final static String[] TYPES_PAYSAGE = {"PLAINE","MONTAGNE"};
	private ArrayList<Carte> gauche;
	private ArrayList<Carte> droite;
	private ArrayList<Cube>  cubes ;
	private int nombre;
	private String paysage;

	public Tuile (int nb, String paysage) {
		gauche = new ArrayList<Carte>();
		droite = new ArrayList<Carte>();
		cubes  = new ArrayList<Cube> ();
		nombre = nb;
		this.paysage = paysage;
	}

	//Ajoute une Carte sur le côté de la Tuile séléctionné
	//seulement si ce côté n'est pas plein
	public boolean ajouterCarte (char cote, Carte c) {
		int cubeCouleur = 0 , cartesCouleur = 0;
		ArrayList<Carte> cartes;
	
		if(c == null)
			return false;
		
		//Récupération du nombre de cube de la même couleur que la carte
		for(Cube cu : cubes)
			if(cu.getCouleur().equals(c.getCouleur()))
				cubeCouleur++;
			
		//Si il y a des cubes de la bonne couleur sur la tuile
		if(cubeCouleur > 0) {
			//On récupère les cartes correspondant au côté choisi
			if(cote == 'G')
				cartes = gauche;
			else if(cote == 'D')
				cartes = droite;
			else
				return false;
			
			//On compte le nombre de cartes de la même couleur que celle
			//passée en paramètre déjà présentes sur la tuile
			for(Carte ca : cartes)
					if(ca.getCouleur().equals(c.getCouleur()))
						cartesCouleur++;
			
			//Si le nombre de cartes (Avec celle passée en paramètre)
			//est supérieur au nombre de cube alors on n'ajoute pas la carte.
			if(cartesCouleur+1 > cubeCouleur) // +1 car on va en ajouter une
				return false;
				
			cartes.add(c);
			return true;
		}
		return false;
	}
	
	//Retire toutes les Cartes de la Tuile et les place
	//dans l'objet de type Tas passé en paramètre.
	public void oterCartes( Tas<Carte> tas ) {
		while( !this.gauche.isEmpty() )
			tas.ajouter( this.gauche.remove(0) );
		
		while( !this.droite.isEmpty() )
			tas.ajouter( this.droite.remove(0) );
		
	} 
	
	//Donne à un joueur tout les cubes présents sur cette tuile
	public void oterCubes (Joueur j) {
		while( !this.cubes.isEmpty() )
			j.ajouterCube(cubes.remove(0));
	}
	
	//Permet de changer le paysage de la tuile à la fin d'une
	//manche sur celle-ci
	public void changerPaysage() {
		if ( paysage.equals( TYPES_PAYSAGE[0]))
			paysage = TYPES_PAYSAGE[1];
		else
			paysage = TYPES_PAYSAGE[0];
	}
	
	//Ajoute un Cube sur la Tuile seulement si
	//le nombre de Cube possible n'est pas atteint
	public boolean ajouterCube( Cube c) {
		if ( cubes.size() < nombre) {
			cubes.add(c);
			return true;
		}
		
		return false;
	}
	
	//Renvoie le côté du joueur gagnant.
	public char gagnant() {
		char coteG = ' ';
		int scoreJ1 = 0, scoreJ2 = 0;
		
		if ( gauche.size() == nombre &&
			 droite.size() == nombre &&
			 cubes.size()  == nombre ) {
		
			for ( Carte c : gauche)
				scoreJ1 += c.getValeur();
				
			for ( Carte c : droite)
				scoreJ2 += c.getValeur();
			
			//Si le paysage est une plaine
			if ( paysage.equals( TYPES_PAYSAGE[0])) {
				if ( scoreJ1 < scoreJ2 )
					coteG = 'G';
				else if ( scoreJ1 > scoreJ2 )
					coteG = 'D';
			} else { //Si c'est une montagne
				if ( scoreJ1 < scoreJ2 )
					coteG = 'G';
				else if ( scoreJ1 > scoreJ2 )
					coteG = 'D';
			}
		}
		
		return coteG;
	}
	
	public String getPaysage() { return this.paysage; }
	public int    getNombre () { return this.nombre ; }
	
	public String toString () {
		String cartes = "",tuile = "";
		tuile += this.paysage + " : " + cubes.size() + " cubes -> ";
		for(Cube c : cubes)
			tuile += c.getCouleur() + " ";
		cartes += "Gauche : " + gauche + "\n";
		cartes += "Droite : " + droite + "\n";
		return tuile+"\n"+cartes;
	}	
}
