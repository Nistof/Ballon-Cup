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
	}
}
