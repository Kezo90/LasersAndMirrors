
package hu.unideb.inf.lasersandmirrors.userinput;

import java.awt.geom.Point2D;

/**
 * Pont visszanyerése.
 * 
 * @author Kerekes Zoltán
 */
public interface CallbackPoint2D {
	
	/**
	 * A visszahívandó függvény.
	 * 
	 * @param pt A visszaküldendő pont információ.
	 */
	public void callback(Point2D pt);
}
