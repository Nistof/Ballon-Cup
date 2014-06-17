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
import java.util.ArrayList;


public class Jeu {
	public final static int    NB_TUILE       = 4;
	public final static String FICHIER_CARTES = "ressources/cartes";
	
	//Nombre de cubes de chaque couleur
	public final static int    NB_CUBE_ROUGE  = 1;//13
	public final static int    NB_CUBE_JAUNE  = 1;
	public final static int    NB_CUBE_VERT   =  1;
	public final static int    NB_CUBE_BLEU   =  1;
	public final static int    NB_CUBE_GRIS   =  1;
	
	//Valeurs des cartes trophee
	private final static int   TROPHEE_ROUGE  =  7;
	private final static int   TROPHEE_JAUNE  =  6;
	private final static int   TROPHEE_VERT   =  5;
	private final static int   TROPHEE_BLEU   =  4;
	private final static int   TROPHEE_GRIS   =  3;

	private ArrayList<Tuile> tuiles    ;
	private Joueur[]      joueurs      ;
	private Pioche<Carte> piocheCartes ;
	private Pioche<Cube>  piocheCubes  ;
	private Defausse      defausse     ;
	private ArrayList<Trophee> trophees;
	private int           dernierJoueur;
	
	public Jeu () {		
		this("Joueur Gauche", "Joueur Droite", new String[0], Jeu.chargerFichierCartes(), new String[0]);
		
		//Placement des cubes sur les tuiles
		for ( int i = 0; i < tuiles.size(); i++) 
			if(!placerCubes(i))
				i--;
	}
	
	public Jeu ( String nomJoueur1, String nomJoueur2, String[] etatTuiles, 
				 String etatPioche, String[] etatJoueur) {
		tuiles = new ArrayList<Tuile>();;
		joueurs = new Joueur[2];
		joueurs[0] = new Joueur( nomJoueur1, 'G');
		joueurs[1] = new Joueur( nomJoueur2, 'D');
		piocheCartes = new Pioche<Carte>();
		piocheCubes  = new Pioche<Cube> ();
		defausse = new Defausse();
		
		trophees = new ArrayList<Trophee>();
		trophees.add( new Trophee("ROUGE", TROPHEE_ROUGE));
		trophees.add( new Trophee("JAUNE", TROPHEE_JAUNE));
		trophees.add( new Trophee("VERT" , TROPHEE_VERT ));
		trophees.add( new Trophee("BLEU" , TROPHEE_BLEU ));
		trophees.add( new Trophee("GRIS" , TROPHEE_GRIS ));
		
		initialiserPiocheCartes( etatPioche);
		initialiserPiocheCubes( NB_CUBE_ROUGE, NB_CUBE_JAUNE, NB_CUBE_VERT, NB_CUBE_BLEU, NB_CUBE_GRIS);
		piocheCartes.melanger();
		piocheCubes.melanger();
		initialiserTuiles( etatTuiles);
		initialiserJoueurs( etatJoueur);
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
		String carte = "", couleur = "";
		Pattern p;
		Matcher m;
		ArrayList<Carte> cs = new ArrayList<Carte>();
		
		p = Pattern.compile("[RVBGJ](0[1-9]|1[0-3])");
		m = p.matcher( chaine);
		
		//Creation des cartes
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
			
			cs.add( new Carte( couleur, valeur));
		}
		
		return cs;
	}

	// Initialisation des tuiles dans un etat donnee
	// Format de etatTuile:
	// Chaque ligne correspond a une tuile, la taille du tableau doit etre == a NB_TUILE
	// Cartes a gauche | paysage | cartes a droite | cubes
	// Cartes : Premier caractere de la couleur suivi de 2 chiffres
	// Paysage: PLAINE ou MONTAGNE
	// Cubes:   Premier caractere de la couleur suivi du nombre de cube entre 1 et 4
	// Exemple: R10V04|PLAINE|G04J02|R1V2J1
	private boolean initialiserTuiles( String[] etatTuiles ) {
		if( etatTuiles.length != NB_TUILE ) { initialiserTuiles(); return (etatTuiles.length==0); }
		
		int deb, fin, trouve;
		ArrayList<Carte> gauche, droite;
		Pattern p;
		Matcher m;
		
		for( int i=0; i<etatTuiles.length; i++ ) {
			// Decoupe de la chaine en 4 sous chaines
			// Cote gauche
			deb = 0;
			fin = etatTuiles[i].indexOf( "|", deb );
			String cartesGauche = etatTuiles[i].substring( deb, fin );
			
			// Paysage
			deb = fin;
			fin = etatTuiles[i].indexOf( "|", deb );
			String paysage = etatTuiles[i].substring( deb, fin );
			
			// Cote droit
			deb = fin;
			fin = etatTuiles[i].indexOf( "|", deb );
			String cartesDroite = etatTuiles[i].substring( deb, fin );
			
			// Cubes
			deb = fin;
			String cubes = etatTuiles[i].substring( deb );
			
			
			this.tuiles.add(new Tuile( i+1, paysage ));
			
			// Ajout des cubes
			p = Pattern.compile( "[RVBGJ][0-" + NB_TUILE + "]" );
			m = p.matcher( cubes );
			
			int nbCube=0;
			String couleur="";
			while( m.find() ) {
				switch ( cubes.charAt(0)){
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
				
				nbCube = Integer.parseInt( ""+cubes.charAt(1) );
				
				for( int j=0; j<nbCube; j++ )
					this.tuiles.get(i).ajouterCube( new Cube( couleur ) );
			}
			
			// Ajout des cartes cote gauche
			trouve = 0;
			gauche = this.creerCartes( cartesGauche );
			while ( !gauche.isEmpty() && trouve != 2) {
				Carte tmp = piocheCartes.piocher();
				if ( gauche.get(0).equals( tmp)) {
					tuiles.get(i).ajouterCarte('G', tmp);
					gauche.remove(0);
					trouve = 0;
				} else {
					defausse.ajouter( tmp);
				}
				if ( piocheCartes.estVide()) {
					piocheCartes.ajouter( defausse.transferer());
					trouve++;
				}
			}
				
			// Ajout des cartes cote droit
			trouve = 0;
			droite = this.creerCartes( cartesDroite );
			while ( !droite.isEmpty()) {
				Carte tmp = piocheCartes.piocher();
				if ( droite.get(0).equals( tmp)) {
					tuiles.get(i).ajouterCarte('D', tmp);
					droite.remove(0);
				} else {
					defausse.ajouter( tmp);
				}
				if ( piocheCartes.estVide())
					piocheCartes.ajouter( defausse.transferer());
			}
		}
		
		return true;
	}
	
	public void initialiserJoueurs( String[] etatJoueurs ) {
		if( etatJoueurs.length<2 ) initialiserJoueurs();	
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
		if( joueurs[dernierJoueur].getCote() == 'D' ) {
			if(joueurs[1].jouerCarte( indCarte, cote, this.tuiles.get(indTuile) )) {
				joueurs[1].ajouterCarte( piocheCartes.piocher());
				return true;
			}
		} else {
			if(joueurs[0].jouerCarte( indCarte, cote, this.tuiles.get(indTuile))) {
				joueurs[0].ajouterCarte( piocheCartes.piocher());
				return true;
			}
		}
		return false;
	}
	
	public void enleverTuiles (int index) {
		System.out.println("Par la");
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
		
		for ( int i = 0; i < Joueur.NB_CARTE_MAX; i++)
			s2 += TexteUtil.centrer( ""+(i+1), 6); 
		
		s += "------------------------------------------------------\n";
		
		for ( int i = 0; i < joueurs.length; i++) {
			s += TexteUtil.centrer( joueurs[i].getNom()                     , 55) + "\n" +
				 TexteUtil.centrer("         " + s2                         , 55) + "\n" +
				 TexteUtil.centrer("Cartes : " + joueurs[i].afficherMain  (), 55) + "\n" + 
				 TexteUtil.centrer("Cubes  : " + joueurs[i].afficherCube  (), 55) + "\n" +
				 TexteUtil.centrer("Trophees:" + joueurs[i].afficherTrophee(), 55) + "\n\n";
		}
		
		
		return s;
	}
		
	public String compterTuiles () {
		String s = "";
		for(Tuile t : tuiles) {
			switch(t.gagnant()) {
				case 'G':
					s += joueurs[0].getNom() + " gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurs[0]);
					t.oterCartes(defausse);
					placerCubes(t.getNombre()-1);
					break;
				case 'D':
					s += joueurs[1].getNom() + " gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurs[1]);
					t.oterCartes(defausse);
					placerCubes(t.getNombre()-1);
					break;
				case 'N':
					s += joueurs[dernierJoueur].getNom() + " gagne " + t.getNombre() + " cubes";
					t.oterCubes(joueurs[dernierJoueur]);
					t.oterCartes(defausse);
					placerCubes(t.getNombre()-1);
					break;
				default:
					break;
			}
		}
		return s;
	}

	public String  getNomJoueur () {
		return joueurs[dernierJoueur].getNom();
	}

	public void changerJoueur () {
		dernierJoueur = 1 -dernierJoueur;
	}

	public void distribuerTrophee () {
		for(int i = 0 ; i < trophees.size() ; i++) {
			joueurs[dernierJoueur].ajouterTrophee(trophees.get(i));
			trophees.remove(i);
		}
	}

	public static void main (String[] a) {
		Jeu j = new Jeu();
		System.out.println(j);
		
		while(j.continuer()) {
			try {
				char cote;
				int carte , tuile;
				Scanner sc = new Scanner(System.in);
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
				System.out.println(j);
				j.distribuerTrophee();
				j.changerJoueur();	
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
	}
}
