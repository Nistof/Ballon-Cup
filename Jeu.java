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

	private Tuile[]       tuiles      ;
	private Joueur        joueurG     ;
	private Joueur        joueurD     ;
	private Pioche<Carte> piocheCartes;
	private Pioche<Cube>  piocheCubes ;
	private Defausse      defausse    ;
	
	public Jeu () {
		tuiles = new Tuile[4];
		joueurG = new Joueur( "Joueur Gauche", 'G');
		joueurD = new Joueur( "Joueur Droite", 'D');
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
			joueurG.ajouterCarte( piocheCartes.piocher());
			joueurD.ajouterCarte( piocheCartes.piocher());
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
	
	public void placerCubes ( int indTuile) {
		for ( int i = 0; i < tuiles[indTuile].getNombre(); i++)
			tuiles[indTuile].ajouterCube( piocheCubes.piocher());
	}
	
	public void jouerCarte( char cote, int indCarte, int indTuile ) {
		if( cote == 'D' )
			joueurD.jouerCarte( indCarte, cote, this.tuiles[indTuile] );
		else
			joueurG.jouerCarte( indCarte, cote, this.tuiles[indTuile] );
	}

	public String afficherMain( char cote ) {
		if( cote == 'D' )
			return this.joueurD.afficherMain();
		else
			return this.joueurG.afficherMain();
	}
	
	public boolean continuer() {
		if( !this.joueurD.aGagne() && !this.joueurG.aGagne() )
			return true;
			
		return false;
	}
	
	public String toString() {
		String s="";
		
		for( int i=0; i<this.tuiles.length; i++ )
			s += this.tuiles[i].toString() + "\n\n";
			
		s += "------------------------------------------------------\n" +
			 TexteUtil.centrer("JOUEUR 1", 55) + "\n" +
			 TexteUtil.centrer("Cartes : " + joueurG.afficherMain  (), 55) + "\n" + 
			 TexteUtil.centrer("Cubes  : " + joueurG.afficherCube  (), 55) + "\n" +
			 TexteUtil.centrer("Trophees:" + joueurG.afficherTrophe(), 55) + "\n\n" +
			 TexteUtil.centrer("JOUEUR 2", 55) + "\n" +
			 TexteUtil.centrer("Cartes : " + joueurD.afficherMain  (), 55) + "\n" + 
			 TexteUtil.centrer("Cubes  : " + joueurD.afficherCube  (), 55) + "\n" +
			 TexteUtil.centrer("Trophees:" + joueurD.afficherTrophe(), 55) + "\n\n";
		
		return s;
	}
		
	public String compterTuiles (char dernierCote) {
		String s = "";
		for(Tuile t : tuiles) {
			switch(t.gagnant()) {
				case 'G':
					s += "JoueurG gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurG);
					t.oterCartes(defausse);
					break;
				case 'D':
					s += "JoueurD gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurD);
					t.oterCartes(defausse);
					break;
				case 'N':
					if(dernierCote == 'G') {
						s += "JoueurG gagne " + t.getNombre() + " cubes";
						t.oterCubes(joueurG);
						t.oterCartes(defausse);
					}
					else {
						s += "JoueurD gagne " + t.getNombre() + " cubes";
						t.oterCubes(joueurD);
						t.oterCartes(defausse);
					}
					break;
				default:
					break;
			}
		}
		if(s.equals(""))
			s += "Aucun gagnant";
		return s;
	}


	public static void main (String[] a) {
		Jeu j = new Jeu();
		while(j.continuer()) {
			try {
				int carte , tuile;
				System.out.println(j);
				Scanner sc = new Scanner(System.in);
				System.out.println("Joueur 1 : Jouez une carte");
				System.out.println("Choisissez l'index de la carte : ");
				carte = sc.nextInt();
				System.out.println("Choisissez la tuile : ");
				tuile = sc.nextInt();
				j.jouerCarte('G', carte, tuile);
				System.out.println(j.compterTuiles('G'));
				System.out.println(j);
				System.out.println("Joueur 2 : Jouez une carte");
				System.out.println("Choisissez l'index de la carte : ");
				carte = sc.nextInt();
				System.out.println("Choisissez la tuile : ");
				tuile = sc.nextInt();
				j.jouerCarte('D',carte, tuile);
				System.out.println(j.compterTuiles('D'));
				System.out.println(j);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
