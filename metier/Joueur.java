/* Classe: Joueur
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 04/06/2014
**/

package metier;

import java.util.ArrayList;

public class Joueur {
	private final static int NB_CARTE_MAX=8; 
	
	private ArrayList<Carte> jeu;
	private ArrayList<Cube> cubes;
	private String nom;
	private char cote;
	
	
	public Joueur( String nom, char cote ) {
		this.nom = nom;
		this.jeu = new ArrayList<Carte>();
		this.cote = cote;
	}
	
	public boolean ajouterCarte( Carte c ) {
		if( this.jeu.size()<Joueur.NB_CARTE_MAX ) {
			this.jeu.add( c );
			return true;
		}
		
		return false;
	}
	
	public Carte retirerCarte( int i ) {
		if( this.jeu.size() > i && i >= 0 )
			return this.jeu.remove(i);

		return null;
	}
	
	public boolean jouerCarte(int i, Table t) {
		return t.ajouterCarte(cote, retirerCarte(i));
	}
	public void ajouterCube (Cube c){
		cubes.add(c);
	}
}
