/* Jeu
 * @author MARECAL Thomas
 * @author MARTIN Florian
 * @author QUENTIN Thibaut 
 * @version 1 du 17/06/2014
 * 
 */

import metier.*;

public class Jeu {

	public static void main (String[] a) {
		Jeu j = new Jeu();
		while(j.continuer()) {
			try {
				System.out.println(j);
				Scanner sc = new Scanner(System.in);
				System.out.println("Joueur 1 : Jouez une carte");
				System.out.println("Voici vos cartes : " + j.montrerMain('G'));
				System.out.println("Choisissez l'index de la carte : ");
				j.jouerCarte('G',sc.nextInt());
				System.out.println(j);
				System.out.println("Joueur 2 : Jouez une carte");
				System.out.println("Voici vos cartes : " + j.montrerMain('D'));
				System.out.println("Choisissez l'index de la carte : ");
				j.jouerCarte('D',sc.nextInt());
				System.out.println(j);
			}
			catch(Exception e) {
				System.out.println(e);
			}

		}
	}
}
