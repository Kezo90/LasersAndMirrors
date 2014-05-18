
package hu.unideb.inf.lasersandmirrors;

import math.geom2d.Point2D;

/**
 * Máshol nem található matematikai függvények gyűjteménye.
 *
 * @author Kerekes Zoltán
 */
public class MyMath {
	
	/**
	 * A két pont távolságának négyzetét adja vissza.
	 * <p>
	 * 
	 * @param ptA Az egyik pont.
	 * @param ptB A másik pont.
	 * @return A két pont távolságának négyzete.
	 */
	public static double squareDist(Point2D ptA, Point2D ptB){
		return Math.pow(ptA.x() - ptB.x(), 2)
				+ Math.pow(ptA.y() - ptB.y(), 2);
	}
	
	/**
	 * A szögnek a felületi egyenesről visszaverődő szögét adja vissza radiánban mérve.
	 * 
	 * @param rayAngle A kezdeti szög raidánban.
	 * @param mirrorAngle A felület szöge radiánban.
	 * @return A visszaverődő szög radiánban.
	 */
	public static double reflectionAngle(double rayAngle, double mirrorAngle){
		double reflectionAngle = 2 * mirrorAngle - rayAngle;
		reflectionAngle %= Math.PI * 2;
		if(reflectionAngle < 0){
			reflectionAngle += Math.PI * 2;
		}
		return reflectionAngle;
	}
	
}
