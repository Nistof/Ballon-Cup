/* Classe: Joueur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 2 du 15/06/2014
**/

package metier;

import java.util.ArrayList;
import util.Couleur;

public class Joueur {
	public final static int NB_CARTE_MAX       = 8;
	private final static int NB_TROPHE_VICTOIRE = 3;
	
	private ArrayList<Carte>  jeu  ;
	private ArrayList<Cube>   cubes;	
	private ArrayList<Trophee> trophee;
	private String			  nom  ;
	private char			  cote ;
	
	public Joueur( String nom, char cote ) {
		this.nom    = nom;
		this.cote   = cote;
		this.jeu    = new ArrayList<Carte>();
		this.cubes  = new ArrayList<Cube>();
		this.trophee = new ArrayList<Trophee>();
	}
	
	public String afficherMain() {
		return this.jeu.toString();
	}
	
	public String afficherCube() {
		return this.cubes.toString();
	}
	
	public String afficherTrophee() {
		return this.trophee.toString();
	}
	
	public ArrayList<Couleur> getCartesCouleurs () {
		ArrayList<Couleur> alTemp = new ArrayList<Couleur>();
		for(Carte c : jeu)
			if(!alTemp.contains(c.getCouleur()))
				alTemp.add(c.getCouleur());
		return alTemp;
	}

	public ArrayList<Couleur> getCubesCouleurs () {
		ArrayList<Couleur> alTemp = new ArrayList<Couleur>();
		for(Cube c : cubes)
			if(!alTemp.contains(c.getCouleur()))
				alTemp.add(c.getCouleur());
		return alTemp;		
	}


	// On ajoute un trophee si et seulement si il y a le bon nombre de cubes de meme couleur
	public void ajouterTrophee( Trophee t ) {
		trophee.add(t);
		for(int i = 0,j = 0 ; i < cubes.size() && j < t.getValeur(); i++) {
			if(cubes.get(i).getCouleur().equals(t.getCouleur())) {
				cubes.remove(i--);
				j++;
			}
		}
	}
	
	// Si le joueur possede 3 trophee, il gagne la partie
	public boolean aGagne() {
		if( this.trophee.size() == Joueur.NB_TROPHE_VICTOIRE )
			return true;
			
		return false;
	}
	
	//Ajoute une carte dans la main du joueur seulement si sa
	//main n'est pas pleine
	public boolean ajouterCarte( Carte c ) {
		if( this.jeu.size()<Joueur.NB_CARTE_MAX && c != null) {
			this.jeu.add( c );
			return true;
		}
		
		return false;
	}
	
	//Méthode utile lorsque le joueur souhaite retirer une carte de sa main.
	//Cette méthode doit être appellée pour se défausser une carte
	//sinon pour jouer une carte, il faut utiliser jouerCarte
	public Carte retirerCarte( int i ) {
		if( this.jeu.size() > i && i >= 0 )
			return this.jeu.remove(i);

		return null;
	}
	
	//Joue une carte sur une tuile en fonction de l'indice dans la main,
	//le cote sur lequel le joueur veut jouer.
	//Si le joueur ne peut pas jouer cette carte alors, la méthode renvoie false
	public boolean jouerCarte(int i, char cote, Tuile t) {
		Carte c = retirerCarte(i);
		
		if (t.ajouterCarte(cote, c)) {
			return true;
		} else {
			ajouterCarte( c);
			return false;
		}
	}
	
	//Ajoute un cube au joueur
	public void ajouterCube (Cube c){
		if ( c != null)
			cubes.add(c);
	}
	
	public String getNom() { return this.nom; }
	public char getCote() { return this.cote; }
	public int getNbCubes (Couleur c) {
		int nb = 0;
		for(int i = 0 ; i < cubes.size(); i++) 
			if(cubes.get(i).getCouleur().equals(c))
				nb++;
		return nb;
	}
	
	public String toString () {
		int i=0;
	
		String s = nom + "\nCartes :\n";
		for(Carte c : jeu) 
			s += "\t(Indice " + (i++) + ") " + c + "\n";
			
		s += "Cubes :\n";
		
		for( Cube c : cubes )
			s += "\t" + c + "\n";
				
		return s;
	}
}
