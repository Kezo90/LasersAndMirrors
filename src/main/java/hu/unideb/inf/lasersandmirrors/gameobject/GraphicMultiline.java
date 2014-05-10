
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Törtvonalat reprezentáló grafikai elem.
 *
 * @author Kerekes Zoltán
 */
public interface GraphicMultiline extends Graphic {
	
	/**
	 * A törtvonalat reprezentáló pontok rendezett halmaza.
	 * 
	 * @return Törtvonal pontjai.
	 */
	List<Point2D> getPoints();
	
	/**
	 * Új pont hozzáfűzése a törtvonal végéhez.
	 * 
	 * @param point A törtvonal végétől eddig a pontig rajzolódik majd ki egy szakasz.
	 */
	void addPoint(Point2D point);
	
	/**
	 * Az összes pont kitörlése.
	 */
	void clearPoints();
	
	/**
	 * A vonal színe.
	 * 
	 * @return A vonal színe.
	 */
	Color getColor();
	
	/**
	 * Új szín beállítása.
	 * 
	 * @param color A lézersugár új színe.
	 */
	void setColor(Color color);
}
