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
		System.out.println(j);
		
		t.ajouterCube( new Cube("ROUGE"));
		t.ajouterCube( new Cube("VERT" ));
		t.ajouterCube( new Cube("BLEU" ));
		t.ajouterCube( new Cube("GRIS" ));
		
		j.jouerCarte(0, 'D', t);
		j.jouerCarte(1, 'D', t);
		j.jouerCarte(2, 'D', t);
		j.jouerCarte(3, 'D', t);
		j.jouerCarte(4, 'D', t);
		j.jouerCarte(0, 'G', t);
		j.jouerCarte(1, 'G', t);
		j.jouerCarte(2, 'G', t);
		j.jouerCarte(3, 'G', t);
		j.jouerCarte(4, 'G', t);
		
		System.out.println(t);
		
		d.ajouter(j.retirerCarte(0));
		d.ajouter(j.retirerCarte(1));
		d.ajouter(j.retirerCarte(2));
		d.ajouter(j.retirerCarte(3));
		
		while ( !pioche.estVide())
			d.ajouter(pioche.piocher());
		
		pioche = new Pioche( d.transferer());
		pioche.melanger();
		System.out.println(pioche);
	}
}
