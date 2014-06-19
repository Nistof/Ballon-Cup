/* Classe: TexteUtil
 * @author MARECAL Thomas
 * @author QUENTIN Thibaut
 * @author MARTIN Florian
 * Groupe I2
 * @version 2 du 17/06/2014
**/

package BallonCup.util;

import java.lang.StringBuilder;

public class TexteUtil {
   private TexteUtil () {}
   
   //Prend en paramètre le texte à centrer et l'espace max disponible
   public static String centrer ( String texte, int taille) {    
      int tailleTexte = texte.length();
      int espaceGauche = 0, espaceDroite = 0;
      
      if ( taille == tailleTexte || taille < tailleTexte)
         return texte;
      
      espaceGauche = (int)(taille-tailleTexte)/2;
      espaceDroite = (taille-tailleTexte)-espaceGauche;
   
      StringBuilder strBuild = new StringBuilder(texte);
      for ( int i = 0; i < espaceGauche; i++)
         strBuild.insert( 0, ' ');
      for ( int i = 0; i < espaceDroite; i++)
         strBuild.append( ' ');
      
      return strBuild.toString();
   }
}
