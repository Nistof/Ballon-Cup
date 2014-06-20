/* main
 * @author MARTIN Florian
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * Groupe I2
 */
 
package BallonCup;
 
import java.util.Scanner;
 
public class main {
	public static void main ( String[] args) {
		try {
			String choix;
			Scanner sc = new Scanner(System.in);
			do {
				System.out.print( "Jeu avec etat [N]ormal ou avec etat [I]nitialisé : " );
				choix = sc.nextLine().toUpperCase();
			} while( choix.charAt(0)!='N' && choix.charAt(0)!='I' );
			
			if( choix.charAt(0) == 'N' )
				new Jeu();
				
			else {
				String joueur1, joueur2;
				String[] etatTuile  = new String[4];		
				String[] etatJoueur = new String[2];
				
				System.out.print( "Nom du joueur 1 : " );
				joueur1 = sc.nextLine();
				
				System.out.print( "Nom du joueur 2 : " );
				joueur2 = sc.nextLine();
				
				System.out.println( "\nExemple pour initialiser une Tuile => R10V04:PLAINE:V04V02:R1V2J1" );
				for( int i = 0; i < Constantes.NB_TUILE; i++ ) {
					System.out.print( "Initialiser la tuile " + (i+1) + " : " );
					etatTuile[i] = sc.nextLine();
				}
				
				System.out.println( "\nExemple pour initialiser un Joueur => R4V2:R10V04J13G04B13J06J01B03:RG" );
				for( int i=0; i<2; i++ ) {
					System.out.print( "Initialiser le joueur " + (i+1) + " : " );
					etatJoueur[i] = sc.nextLine();
				}
				
				new Jeu( joueur1, joueur2, etatTuile, etatJoueur);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}