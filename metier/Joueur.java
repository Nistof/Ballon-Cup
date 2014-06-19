/* Classe: Joueur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 2 du 15/06/2014
**/

package BallonCup.metier;

import java.util.ArrayList;
import BallonCup.util.Couleur;
import BallonCup.Constantes;

public class Joueur {	
	private ArrayList<Carte>  jeu  ;
	private ArrayList<Cube>   cubes;	
	private ArrayList<Trophee> trophees;
	private String			  nom  ;
	private char			  cote ;
	
	public Joueur( String nom, char cote ) {
		this.nom    = nom;
		this.cote   = cote;
		this.jeu    = new ArrayList<Carte>();
		this.cubes  = new ArrayList<Cube>();
		this.trophees = new ArrayList<Trophee>();
	}
	
	public String afficherMain() {
		return this.jeu.toString();
	}
	
	public String afficherCubes() {
		int r = 0, j = 0, v = 0, b = 0, g = 0;
		for ( Cube c : cubes)
			if (      c.getCouleur().equals( Couleur.ROUGE )) r++;
			else if ( c.getCouleur().equals( Couleur.JAUNE )) j++;
			else if ( c.getCouleur().equals( Couleur.VERT  )) v++;
			else if ( c.getCouleur().equals( Couleur.BLEU  )) b++;
			else if ( c.getCouleur().equals( Couleur.GRIS  )) g++;
		
		return "[" +  ((r!=0)?r + " ROUGE ":"") + ((j!=0)?j + " JAUNE ":"") +
					  ((v!=0)?v + " VERT " :"") + ((b!=0)?b + " BLEU " :"") +
					  ((g!=0)?g + " GRIS" :"") + "]";
	}
	
	public String afficherTrophees() {
		return this.trophees.toString();
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


	// On ajoute un trophees si et seulement si il y a le bon nombre de cubes de meme couleur
	public void ajouterTrophee( Trophee t ) {
		trophees.add(t);
		retirerCubes(t.getCouleur(), t.getValeur());
	}
	
	// Si le joueur possede 3 trophees, il gagne la partie
	public boolean aGagne() {
		if( this.trophees.size() == Constantes.NB_TROPHE_VICTOIRE )
			return true;
			
		return false;
	}
	
	//Ajoute une carte dans la main du joueur seulement si sa
	//main n'est pas pleine
	public boolean ajouterCarte( Carte c ) {
		if( this.jeu.size()<Constantes.NB_CARTE_MAX && c != null) {
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

	public String getNomCarte (int i ) {
		if( this.jeu.size() > i && i >= 0 )
			return this.jeu.get(i).getValeur()+this.jeu.get(i).getCouleur().name();
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
	

	public void retirerCubes (Couleur c, int n) {
		for(int i = 0, j = 0 ; i < cubes.size() && j < n; i++) {
			if(cubes.get(i).getCouleur().equals(c)) {
				cubes.remove(i--);
				j++;
			}
		}
	}

	//Ajoute un cube au joueur
	public void ajouterCube (Cube c){
		if ( c != null)
			cubes.add(c);
	}
	
	public String getNom() { return this.nom; }
	public char getCote() { return this.cote; }
	public int  getNbCarte() { return this.jeu.size(); }
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
