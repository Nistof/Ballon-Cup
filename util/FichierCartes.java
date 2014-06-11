/* Classe: FichierCartes
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 2 du 11/06/2014
**/

package util;

import java.io.FileWriter;
import java.io.PrintWriter;

public class FichierCartes {
	private FichierCartes () {}
	
	public static void generer () {
		try {
			PrintWriter pw = new PrintWriter( new FileWriter("ressources/cartes"));
			int j;
			//Cartes rouges
			for ( int i = 1; i <= 13; i++)
				pw.append( String.format( "R%02d", i));
			
			//Cartes jaunes
			j = 3;
			for ( int i = 1; i <= 13; i++) {
				pw.append( String.format( "J%02d", i));
				if ( j%5 == 0)
					i++;
				j++;
			}
			
			//Cartes vertes
			j = 1;
			for ( int i = 1; i <= 13; i++) {
				pw.append( String.format( "V%02d", i));
				if ( j%4 == 2 || j%4 == 3)
					i++;
				j++;
			}
			
			//Cartes bleues
			for ( int i = 1; i <= 13; i++) {
				pw.append( String.format( "B%02d", i));
				i++;
			}
			
			//Cartes grises
			for ( int i = 1; i <= 13; i++) {
				pw.append( String.format( "G%02d", i));
				i+=2;
			}
			
			pw.close();
		} catch ( Exception e) { e.printStackTrace(); }
	}
	
	public static void main ( String[] args) {
		FichierCartes.generer();
	}
}
