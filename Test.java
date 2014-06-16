/* Classe: Test
 * @author MARECAL Thomas
 * @author QUENTIN Thibault
 * @author MARTIN Florian
 * Groupe I2
 * @version 1 du 11/06/2014
**/

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;
import metier.*;

public class Test {
	public static void main ( String[] args) {
		String nom = "";
		String fichier = "";
		String carte, couleur;
		Joueur j = new Joueur("Truc", 'G');
		Table  t = new Table (3, "PLAINE");
		Defausse d = new Defausse();
		int valeur;
		Pattern p;
		Matcher m;
		Pioche<Carte> pioche = new Pioche<Carte>();
		
		try {
			File rep = new File("ressources/");
			
			//Choix du fichier
			System.out.println("Liste des fichiers disponibles pour initialiser la pioche:");
			for ( File f : rep.listFiles()) {
				System.out.println( f.getName());
			}
		
			System.out.print("\nChoix : ");
			
			Scanner sc = new Scanner( System.in);
			nom = sc.nextLine();
			sc.close();
		
			//Chargement du fichier
			sc = new Scanner( new File("ressources/" + nom));
			if ( sc.hasNext())
				fichier = sc.nextLine();
			
		} catch ( FileNotFoundException e) { System.out.println("Le fichier n'existe pas."); System.exit(1); }
		  
		p = Pattern.compile("[RVBGJ](0[1-9]|1[0-3])");
		m = p.matcher( fichier);
		
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
			
			pioche.ajouter( new Carte( couleur, valeur));
		}
		
		System.out.println(pioche);
		System.out.println("Pioche une carte :" + pioche.piocher());
		System.out.println(pioche);
		System.out.println("Nombre de cartes : " + pioche.taille());
		System.out.println("Est vide? " + pioche.estVide());
		
		System.out.println("Melange : " );
		pioche.melanger();
		System.out.println(pioche);
		System.out.println("Pioche une carte :" + pioche.piocher());
		System.out.println(pioche);
		
		for ( int i = 0; i < 10; i++)
			j.ajouterCarte( pioche.piocher());
		System.out.println( "Main du joueur :\n" + j);
		
		System.out.println( "Ajout de cubes sur la table" );
		t.ajouterCube( new Cube("ROUGE"));
		t.ajouterCube( new Cube("VERT" ));
		t.ajouterCube( new Cube("BLEU" ));
		t.ajouterCube( new Cube("GRIS" ));
		
		System.out.println( "Le joueur joue des cartes" );
		for ( int i = 0; i < 8; i++) {
			j.jouerCarte(i, 'D', t);
			j.jouerCarte(i, 'G', t);
		}
		
		System.out.println( "Table :\n" + t);
		System.out.println(pioche);
		System.out.println("Main du joueur :\n" + j);
		
		System.out.println( "Ajout des cartes du joueur et de la table dans la defausse" );
		d.ajouter(j.retirerCarte(0));
		d.ajouter(j.retirerCarte(0));
		d.ajouter(j.retirerCarte(0));
		d.ajouter(j.retirerCarte(0));
		t.oterCartes( d);
		
		System.out.println( "Transfert de la defausse dans la pioche" );
		pioche.ajouter( d.transferer() );
		pioche.melanger();
		
		System.out.println( "Transfert des cubes dans un joueur" );
		t.oterCubes( j );
		
		System.out.println( "Table :\n" + t);
		System.out.println(pioche);
		System.out.println("Main du joueur :\n" + j);
		
		//Test de la méthode gagnant()
		t.ajouterCube(new Cube("ROUGE"));
		t.ajouterCube(new Cube("VERT"));
		t.ajouterCube(new Cube("BLEU"));
		t.ajouterCarte('D', new Carte("ROUGE", 10));
		t.ajouterCarte('D', new Carte("VERT", 2));
		t.ajouterCarte('D', new Carte("BLEU", 11));
		t.ajouterCarte('G', new Carte("ROUGE", 7));
		t.ajouterCarte('G', new Carte("VERT", 5));
		t.ajouterCarte('G', new Carte("BLEU", 1));
		System.out.println("Gagnant? " + t.gagnant() );
		
	}
}
