/* Jeu
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut 
 * Groupe I2
 * @version 1 du 17/06/2014
 */

import metier.*;
import util.TexteUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Jeu {
	private final static int    NB_TUILE       = 4;
	private final static String FICHIER_CARTES = "ressources/cartes";
	private final static int    NB_CUBE_ROUGE  = 13;
	private final static int    NB_CUBE_JAUNE  = 11;
	private final static int    NB_CUBE_VERT   =  9;
	private final static int    NB_CUBE_BLEU   =  7;
	private final static int    NB_CUBE_GRIS   =  5;

	private Tuile[]       tuiles       ;
	private Joueur[]      joueurs      ;
	private Pioche<Carte> piocheCartes ;
	private Pioche<Cube>  piocheCubes  ;
	private Defausse      defausse     ;
	private int           dernierJoueur;
	
	public Jeu () {
		tuiles = new Tuile[4];
		joueurs = new Joueur[2];
		joueurs[0] = new Joueur( "Joueur Gauche", 'G');
		joueurs[1] = new Joueur( "Joueur Droite", 'D');
		piocheCartes = new Pioche<Carte>();
		piocheCubes  = new Pioche<Cube> ();
		defausse = new Defausse();
	
		initialiserTuiles();
		
		//Pour l'initialisation de la pioche de cartes
		String fichier = "";
		try {			
			//Chargement du fichier
			Scanner sc = new Scanner( new File( FICHIER_CARTES));
			if ( sc.hasNext())
				fichier = sc.nextLine();
			sc.close();
		} catch ( FileNotFoundException e) { System.out.println("Le fichier n'existe pas."); System.exit(1); }
		initialiserPiocheCartes( fichier);
		piocheCartes.melanger();
				
		initialiserJoueurs();
		initialiserPiocheCubes( NB_CUBE_ROUGE, NB_CUBE_JAUNE, NB_CUBE_VERT, NB_CUBE_BLEU, NB_CUBE_GRIS);
		piocheCubes.melanger();
		
		//Placement des cubes sur les tuiles
		for ( int i = 0; i < NB_TUILE; i++)
			placerCubes( i);
	}
	
	//Méthode qui initialise les tuiles dans un état initial
	private void initialiserTuiles() {
		//Initialisation des tuiles
		for ( int i = 0; i < NB_TUILE; i++)
			if ( i%2 == 0)
				tuiles[i] = new Tuile( i+1, Tuile.TYPES_PAYSAGE[0]);
			else
				tuiles[i] = new Tuile( i+1, Tuile.TYPES_PAYSAGE[1]);
	}
	
	//Méthode qui initialise la pioche selon la chaine passée en paramètre
	private void initialiserPiocheCartes ( String cartes) {
		//initialisation de la pioche de cartes
		int valeur = 0;
		String carte = "", couleur = "";
		Pattern p;
		Matcher m;
		
		p = Pattern.compile("[RVBGJ](0[1-9]|1[0-3])");
		m = p.matcher( cartes);
		
		//Création des cartes
		while ( m.find()) {
			carte = m.group();
			switch ( carte.charAt(0)){
				case 'R':
					couleur = "ROUGE";
					break;
				case 'G':
					couleur = "GRIS";
					break;
				case 'B':
					couleur = "BLEU";
					break;
				case 'V':
					couleur = "VERT";
					break;
				case 'J':
					couleur = "JAUNE";
					break;
				default:
					couleur = "DEFAUT";
			}
			valeur = Integer.parseInt( carte.substring(1));
			
			piocheCartes.ajouter( new Carte( couleur, valeur));
		}
	}
	
	//Les joueurs récupèrent chacun 8 cartes
	private void initialiserJoueurs() {
		for ( int i = 0; i < Joueur.NB_CARTE_MAX; i++){
			joueurs[0].ajouterCarte( piocheCartes.piocher());
			joueurs[1].ajouterCarte( piocheCartes.piocher());
		}
	}
	
	private void initialiserPiocheCubes ( int rouge, int jaune, int vert, int bleu, int gris) {
		//Cubes rouges
		for ( int i = 0; i < rouge; i++ )
			piocheCubes.ajouter( new Cube("ROUGE"));
		
		//Cubes jaunes
		for ( int i = 0; i < jaune; i++ )
			piocheCubes.ajouter( new Cube("JAUNE"));
		
		//Cubes verts
		for ( int i = 0; i < vert; i++ )
			piocheCubes.ajouter( new Cube("VERT"));
		
		//Cubes bleu
		for ( int i = 0; i < bleu; i++ )
			piocheCubes.ajouter( new Cube("BLEU"));
		
		//Cubes gris
		for ( int i = 0; i < gris; i++ )
			piocheCubes.ajouter( new Cube("GRIS"));
	}
	
	//Place les cubes sur la tuile passée en indice
	public void placerCubes ( int indTuile) {
		for ( int i = 0; i < tuiles[indTuile].getNombre(); i++)
			tuiles[indTuile].ajouterCube( piocheCubes.piocher());
	}
	
	
	public void jouerCarte( char coteJ, char cote, int indCarte, int indTuile ) {
		if( coteJ == 'D' ) {
			joueurs[1].jouerCarte( indCarte, cote, this.tuiles[indTuile] );
			joueurs[1].ajouterCarte( piocheCartes.piocher());
		} else {
			joueurs[0].jouerCarte( indCarte, cote, this.tuiles[indTuile] );
			joueurs[0].ajouterCarte( piocheCartes.piocher());
		}
	}
	
	public boolean continuer() {
		if( !this.joueurs[1].aGagne() && !this.joueurs[0].aGagne() )
			return true;
			
		return false;
	}
	
	public String toString() {
		String s="";
		String s2 = "";
		
		for( int i=0; i<this.tuiles.length; i++ )
			s += this.tuiles[i].toString() + "\n\n";
		
		for ( int i = 0; i < Joueur.NB_CARTE_MAX; i++)
			s2 += TexteUtil.centrer( ""+(i+1), 6); 
		
		s += "------------------------------------------------------\n";
		
		for ( int i = 0; i < joueurs.length; i++) {
			s += TexteUtil.centrer( joueurs[i].getNom()                     , 55) + "\n" +
				 TexteUtil.centrer("         " + s2                         , 55) + "\n" +
				 TexteUtil.centrer("Cartes : " + joueurs[i].afficherMain  (), 55) + "\n" + 
				 TexteUtil.centrer("Cubes  : " + joueurs[i].afficherCube  (), 55) + "\n" +
				 TexteUtil.centrer("Trophees:" + joueurs[i].afficherTrophe(), 55) + "\n\n";
		}
		
		
		return s;
	}
		
	public String compterTuiles (char dernierCote) {
		String s = "";
		for(Tuile t : tuiles) {
			switch(t.gagnant()) {
				case 'G':
					s += joueurs[0].getNom() + " gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurs[0]);
					t.oterCartes(defausse);
					break;
				case 'D':
					s += joueurs[1].getNom() + " gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurs[1]);
					t.oterCartes(defausse);
					break;
				case 'N':
					if(dernierCote == 'G') {
						s += joueurs[0].getNom() + " gagne " + t.getNombre() + " cubes";
						t.oterCubes(joueurs[0]);
						t.oterCartes(defausse);
					}
					else {
						s += joueurs[1].getNom() + " gagne " + t.getNombre() + " cubes";
						t.oterCubes(joueurs[1]);
						t.oterCartes(defausse);
					}
					break;
				default:
					break;
			}
		}
		return s;
	}


	public static void main (String[] a) {
		Jeu j = new Jeu();
		System.out.println(j);
		
		while(j.continuer()) {
			try {
				int carte , tuile;
				Scanner sc = new Scanner(System.in);
				System.out.println("Joueur 1 : Jouez une carte");
				System.out.println("Choisissez l'index de la carte : ");
				carte = sc.nextInt();
				System.out.println("Choisissez la tuile : ");
				tuile = sc.nextInt();
				j.jouerCarte('G', 'D', carte-1, tuile-1);//2ème argument à demander au joueur
				System.out.println(j.compterTuiles('G'));
				System.out.println(j);
				System.out.println("Joueur 2 : Jouez une carte");
				System.out.println("Choisissez l'index de la carte : ");
				carte = sc.nextInt();
				System.out.println("Choisissez la tuile : ");
				tuile = sc.nextInt();
				j.jouerCarte('D', 'G',carte-1, tuile-1); //2ème argument à demander au joueur
				System.out.println(j.compterTuiles('D'));
				System.out.println(j);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
