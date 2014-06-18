/* Jeu
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut 
 * Groupe I2
 * @version 1 du 17/06/2014
 */

import metier.*;
import util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;


public class Jeu {
	public final static int    NB_TUILE       = 4;

	public final static String FICHIER_CARTES = "ressources/cartes";
	
	//Nombre de cubes de chaque couleur { ROUGE, JAUNE, VERT, BLEU, GRIS}
	public final static int[]    NB_CUBES       = { 13, 11, 9, 7, 5 };
	
	//Valeurs des cartes trophee
	private final static int[] TROPHEES  =  {7, 6, 5, 4, 3};

	private ArrayList<Tuile>   tuiles       ;
	private Joueur[]           joueurs      ;
	private Pioche<Carte>      piocheCartes ;
	private Pioche<Cube>       piocheCubes  ;
	private Defausse           defausse     ;
	private ArrayList<Trophee> trophees     ;
	private int                dernierJoueur;
	

	public Jeu () {		
		this("Joueur Gauche", "Joueur Droite", new String[0], new String[0]);
		
		//Placement des cubes sur les tuiles
		for ( int i = 0; i < tuiles.size(); i++) 
			if(!placerCubes(i))
				i--;
	}
	
	public Jeu ( String nomJoueur1, String nomJoueur2, String[] etatTuiles, String[] etatJoueur) {
		tuiles = new ArrayList<Tuile>();
		joueurs = new Joueur[2];
		joueurs[0] = new Joueur( nomJoueur1, 'G');
		joueurs[1] = new Joueur( nomJoueur2, 'D');
		piocheCartes = new Pioche<Carte>();
		piocheCubes  = new Pioche<Cube> ();
		defausse = new Defausse();
		
		trophees = new ArrayList<Trophee>();

		for(Integer i : TROPHEES)
			trophees.add( new Trophee(Couleur.getCouleur(TROPHEES[0]-i), i)); // Maximum - celui en cours

		initialiserPiocheCartes( Jeu.chargerFichierCartes());
		initialiserPiocheCubes( NB_CUBES);

		piocheCartes.melanger();
		piocheCubes.melanger();
		initialiserTuiles( etatTuiles);
		initialiserJoueurs( etatJoueur);
		System.out.println("1");
	}
	
	private static String chargerFichierCartes() {
		//Pour l'initialisation de la pioche de cartes
		String fichier = "";
		try {			
			//Chargement du fichier
			Scanner sc = new Scanner( new File( FICHIER_CARTES));
			if ( sc.hasNext())
				fichier = sc.nextLine();
			sc.close();
		} catch ( FileNotFoundException e) { System.out.println("Le fichier n'existe pas."); System.exit(1); }
	
		return fichier;
	}
	
	//Methode qui initialise les tuiles dans un etat initial (Debut de jeu normal)
	private void initialiserTuiles() {
		for ( int i = 0; i < NB_TUILE; i++)
			if ( i%2 == 0)
				tuiles.add(new Tuile( i+1, Tuile.TYPES_PAYSAGE[0]));
			else
				tuiles.add(new Tuile( i+1, Tuile.TYPES_PAYSAGE[1]));
	}
	
	//Renvoie une liste de cartes en fonction d'une chaine donnee
	//etant de la forme : première lettre de la couleur suivi de 2 chiffres
	//Exemple : R01V11B09 renverra une liste avec une carte Rouge 1, Verte 11, Bleu 9
	private ArrayList<Carte> creerCartes( String chaine) {
		int valeur = 0;
		String carte = "";
		Couleur couleur;
		Pattern p;
		Matcher m;
		ArrayList<Carte> cs = new ArrayList<Carte>();
		
		p = Pattern.compile("[RVBGJ](0[1-9]|1[0-3])");
		m = p.matcher( chaine);
		
		//Creation des cartes
		while ( m.find()) {
			carte = m.group();
			couleur = Couleur.getCouleur( carte.charAt(0));
			valeur = Integer.parseInt( carte.substring(1));	
			cs.add( new Carte( couleur, valeur));
		}
		
		return cs;
	}

	// Initialisation des tuiles dans un etat donnee
	// Format de etatTuile:
	// Chaque ligne correspond a une tuile, la taille du tableau doit etre == a NB_TUILE
	// Cartes a gauche : paysage : cartes a droite : cubes
	// Cartes : Premier caractere de la couleur suivi de 2 chiffres
	// Paysage: PLAINE ou MONTAGNE
	// Cubes:   Premier caractere de la couleur suivi du nombre de cube entre 1 et 4
	// Exemple: R10V04:PLAINE:G04J02:R1V2J1
	private boolean initialiserTuiles( String[] etatTuiles ) {
		if( etatTuiles.length != NB_TUILE ) { initialiserTuiles(); return (etatTuiles.length==0); }
		int trouve;
		ArrayList<Carte> listeC;
		char cote;
		Pattern p;
		Matcher m;
		
		for( int i=0; i<etatTuiles.length; i++ ) {		
			// 0 : Cote gauche
			// 1 : Paysage
			// 2 : Cote droit
			// 3 : Cubes
			String[] chTuiles = etatTuiles[i].split(":");			
			this.tuiles.add(new Tuile( i+1, chTuiles[1] ));
			
			// Ajout des cubes
			p = Pattern.compile( "[RVBGJ][0-" + (NB_TUILE>0 && NB_TUILE<=9?NB_TUILE:4) + "]" );
			m = p.matcher( chTuiles[3] );
			
			int nbCube=0;
			String cube = "";
			Couleur couleur;
			while( m.find() ) {
				cube = m.group();
				couleur = Couleur.getCouleur( cube.charAt(0));
				
				nbCube = Integer.parseInt( "" + cube.charAt(1) );
				
				for( int j=0; j<nbCube; j++ )
					this.tuiles.get(i).ajouterCube( new Cube( couleur ) );
			}

			// Ajout des cartes cote gauche et cote droit
			for ( int j = 0; j < 2; j++) {
				cote = (j == 0?'G': 'D');
				listeC = (j == 0?creerCartes( chTuiles[0]): creerCartes( chTuiles[2]));
				trouve = 0;
				while ( !listeC.isEmpty() && trouve != 2) {
					Carte tmp = piocheCartes.piocher();
					if ( listeC.get(0).equals( tmp)) {
						tuiles.get(i).ajouterCarte(cote, tmp);
						listeC.remove(0);
						trouve = 0;
					} else {
						defausse.ajouter( tmp);
					}
					if ( piocheCartes.estVide()) {
						piocheCartes.ajouter( defausse.transferer());
						trouve++;
					}
				}
			}
		}

		return true;
	}
	
	//Format de etat joueur :
	// Indice 0 = joueur 1
	//   "  " 1 = joueur 2
	// Chaine: Cubes:Cartes:Trophée
	// Exemple: R6J5:J06R13G05:RG
	public void initialiserJoueurs( String[] etatJoueurs ) {
		if( etatJoueurs.length != 2 ) initialiserJoueurs();
		
		Pattern p;
		Matcher m;
		ArrayList<Carte> listeC;
		int trouve;
		
		for( int i=0; i<etatJoueurs.length; i++ ) {		
			// 0 : Cubes
			// 1 : Cartes
			// 2 : Trophées
			String[] chJoueur = etatJoueurs[i].split(":");
			
			// Ajout des cubes
			p = Pattern.compile( "[RVBGJ][1-9]" );
			m = p.matcher( chJoueur[0] );
			
			int nbCube=0;
			String cube = "";
			Couleur couleur;
			while( m.find() ) {
				cube = m.group();
				couleur = Couleur.getCouleur( cube.charAt(0));
				
				nbCube = Integer.parseInt( "" + cube.charAt(1) );
				
				for( int j=0; j<nbCube; j++ )
					this.joueurs[i].ajouterCube( new Cube( couleur ) );
			}
			
			//Ajout des cartes
			listeC = creerCartes( chJoueur[1]);
			trouve = 0;
			while ( !listeC.isEmpty() && trouve != 2) {
				Carte tmp = piocheCartes.piocher();
				if ( listeC.get(0).equals( tmp)) {
					joueurs[i].ajouterCarte( tmp);
					listeC.remove(0);
					trouve = 0;
				} else {
					defausse.ajouter( tmp);
				}
				if ( piocheCartes.estVide()) {
					piocheCartes.ajouter( defausse.transferer());
					trouve++;
				}
			}
			
			//Ajout des Trophées
			while( chJoueur[2].length() != 0)
				for ( Trophee t : trophees)
					if (t.getCouleur().equals( Couleur.getCouleur( chJoueur[2].charAt(0)))) {
						joueurs[i].ajouterTrophee( t);
						trophees.remove(t);
						chJoueur[2] = chJoueur[2].substring(1);
					}
		}
	}

	
	//Méthode qui initialise la pioche selon la chaine passée en paramètre
	private void initialiserPiocheCartes ( String cartes) {
		ArrayList<Carte> cs = creerCartes ( cartes);
		while ( !cs.isEmpty() )
			piocheCartes.ajouter( cs.remove(0));
	}
	
	//Les joueurs recuperent chacun 8 cartes
	private void initialiserJoueurs() {
		for ( int i = 0; i < Joueur.NB_CARTE_MAX; i++){
			joueurs[0].ajouterCarte( piocheCartes.piocher());
			joueurs[1].ajouterCarte( piocheCartes.piocher());
		}
	}
	
	private void initialiserPiocheCubes ( int[] couleurs) {
		for(int i = 0 ; i < couleurs.length ; i++)
			for ( int j = 0; j < couleurs[i]; j++ )
				piocheCubes.ajouter( new Cube(Couleur.getCouleur(i)));	
	}
	
	//Place les cubes sur la tuile passée en indice
	public boolean placerCubes ( int indTuile) {
		if(this.tuiles.get(indTuile).getNombre() <= piocheCubes.taille()) {
			for ( int i = 0; i < tuiles.get(indTuile).getNombre(); i++)
				tuiles.get(indTuile).ajouterCube( piocheCubes.piocher());
			return true;
		}
		else {
			enleverTuiles(indTuile);
			return false;
		}
	}

	public int getNbTuile () { return this.tuiles.size(); }	
	
	public boolean jouerCarte(char cote, int indCarte, int indTuile ) {
		Joueur j = ( joueurs[dernierJoueur].getCote() == 'G')?joueurs[0]:joueurs[1];
		if(j.jouerCarte( indCarte, cote, this.tuiles.get(indTuile) )) {
			j.ajouterCarte( piocheCartes.piocher());
			return true;
		}
		return false;
	}
	
	public void enleverTuiles (int index) {
		this.tuiles.remove(index);
	}

	public boolean continuer() {
		if( !this.joueurs[1].aGagne() && !this.joueurs[0].aGagne() )
			return true;	
		return false;
	}
	
	public String toString() {
		String s="";
		String s2 = "";
		
		for( int i=0; i< this.tuiles.size(); i++ )
			s += this.tuiles.get(i).toString() + "\n\n";
		
		
		
		s += "------------------------------------------------------\n";
		
		for ( int i = 0; i < joueurs.length; i++) {
			s2 = "";
			for ( int j = 0; j < joueurs[i].getNbCarte(); j++)
				s2 += TexteUtil.centrer( ""+(j+1), 6); 
			s += TexteUtil.centrer( joueurs[i].getNom()                      , 55) + "\n" +
				 TexteUtil.centrer("         " + s2                          , 55) + "\n" +
				 TexteUtil.centrer("Cartes : " + joueurs[i].afficherMain   (), 55) + "\n" + 
				 TexteUtil.centrer("Cubes  : " + joueurs[i].afficherCube   (), 55) + "\n" +
				 TexteUtil.centrer("Trophees:" + joueurs[i].afficherTrophee(), 55) + "\n\n";
		}
		
		return s;
	}
		
	public String compterTuiles () {
		String s = "";
		for(Tuile t : tuiles) {
			int coteGagnant = 0;
			switch(t.gagnant()) {
				case 'G':
					coteGagnant = 0;
					break;
				case 'D':
					coteGagnant = 1;
					break;
				case 'N':
					coteGagnant = dernierJoueur;
					break;
				default:
					coteGagnant = -1;
					break;
			}
			if(coteGagnant != -1) {
				s += joueurs[coteGagnant].getNom() + " gagne " + t.getNombre() + " cubes";
				t.oterCubes(joueurs[coteGagnant]);
				t.oterCartes(defausse);
				placerCubes(t.getNombre()-1);
				dernierJoueur = coteGagnant;
			}
		}
		return s;
	}
	
	public boolean enleverCarteMain( int i ) {
		if( i<=8 && i>=1 ) {
			this.defausse.ajouter( joueurs[dernierJoueur].retirerCarte(i-1) );
			return true;			
		}
		return false;
	}
	
	public boolean ajouterCarteMain() {
		if( this.joueurs[dernierJoueur].ajouterCarte( this.piocheCartes.piocher() ) )
			return true;
			
		return false;
	} 

	public String  getNomJoueur () {
		return joueurs[dernierJoueur].getNom();
	}

	public void changerJoueur () {
		dernierJoueur = 1 -dernierJoueur;
	}

	public void distribuerTrophee () {
		for(int i = 0 ; i < trophees.size() ; i++) {
			if(joueurs[dernierJoueur].getNbCubes(Couleur.getCouleur(i)) == trophees.get(i).getValeur()) {
				joueurs[dernierJoueur].ajouterTrophee(trophees.get(i));
				trophees.remove(i--);
			}
		}
	}

	public boolean peutJouer () {
		for(Tuile t : tuiles) 
			for(Couleur c : joueurs[dernierJoueur].getCartesCouleurs())
				if(t.couleurDispo(c))
					return true;
		return false;
	}

	public boolean echangePossible () {
		if(trophees.size() < TROPHEES.length)
			return false;
		for(Couleur c : joueurs[dernierJoueur].getCubesCouleurs()) 
			if(!trophees.contains(c))
				return true;
		return false;
	}

	public void echanger (Couleur c, Couleur c2) {
		if(joueurs[dernierJoueur].getCubesCouleurs().contains(c)) {
			if(joueurs[dernierJoueur].getNbCubes(c) > 3) {
				joueurs[dernierJoueur].retirerCubes(c, 3);	
			}
		}
	}

	public static void main (String[] a) {
		Jeu j;
		String choix;
		Scanner sc = new Scanner(System.in);
		
		// MENU
		try {
			do {
				System.out.print( "Jeu avec etats [N]ormal ou avec etats [I]nitialiser : " );
				choix = sc.nextLine().toUpperCase();
			} while( choix.charAt(0)!='N' && choix.charAt(0)!='I' );
			
			if( choix.charAt(0) == 'N' )
				j = new Jeu();
				
			else {
				String joueur1, joueur2;
				String[] etatTuile  = new String[4];		
				String[] etatJoueur = new String[2];
				
				System.out.print( "Nom du joueur 1 : " );
				joueur1 = sc.nextLine();
				
				System.out.print( "Nom du joueur 2 : " );
				joueur2 = sc.nextLine();
				
				System.out.println( "\nExemple pour initialiser une Tuile => R10V04:PLAINE:V04V02:R1V2J1" );
				for( int i=0; i<NB_TUILE; i++ ) {
					System.out.print( "Initialiser la tuile " + (i+1) + " : " );
					etatTuile[i] = sc.nextLine();
				}
				
				System.out.println( "\nExemple pour initialiser un Joueur => R4V2:R10V04J13G04B13J06J01B03" );
				for( int i=0; i<2; i++ ) {
					System.out.print( "Initialiser le joueur " + (i+1) + " : " );
					etatJoueur[i] = sc.nextLine();
				}
				
				j = new Jeu( joueur1, joueur2, etatTuile, etatJoueur);
			}
			
			
			// JEU
			while(j.continuer()) {
				System.out.println(j);
				char cote;
				int carte , tuile, nbCarteDefausse;

				if( !j.peutJouer() ) {
					do {
						System.out.print( "Combien de carte voulez vous defaussez : " );
						choix = sc.nextLine();
					} while( !choix.matches("[1-4]*") );
					
					nbCarteDefausse = Integer.parseInt(choix);
						
					// Defausse des cartes	
					for( int i=0; i<nbCarteDefausse; i++ ) {
						do {
							System.out.print( "Choisissez la carte " + (i+1) + " a defausser : " );
							choix = sc.nextLine();
						} while( !choix.matches("[1-8]*") );
						
						carte = Integer.parseInt( choix );
						
						j.enleverCarteMain(	carte );
					}
					
					// Pioche des cartes
					for( int i=0; i<nbCarteDefausse; i++ )
						j.ajouterCarteMain();
				}
				
				do {
					System.out.println(j.getNomJoueur() + " : Jouez une carte");
					do {
						System.out.println("Choisissez l'index de la carte : ");
						carte = sc.nextInt();
					}while(carte < 1 || carte > Joueur.NB_CARTE_MAX);
		
					do {
						System.out.println("Choisissez la tuile : ");
						tuile = sc.nextInt();
					}while(tuile < 1 || tuile > j.getNbTuile());
					sc.nextLine();
					do {
						System.out.println("Choisissez le cote ou vous voulez jouer : ");
						cote = sc.nextLine().charAt(0);
					}while(cote != 'D' && cote != 'G');
				}while(!j.jouerCarte( cote, carte-1, tuile-1));
				System.out.println(j.compterTuiles());
				j.distribuerTrophee();
				j.changerJoueur();	
			}
			
			sc.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
