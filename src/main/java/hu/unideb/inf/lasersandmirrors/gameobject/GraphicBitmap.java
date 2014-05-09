
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Image;

/**
 * Rasztergrafikus elem.
 *
 * @author Kerekes Zoltán
 */
public interface GraphicBitmap extends Graphic {
	
	/**
	 * Az objektum X koordinátája.
	 */
	double getX();
	
	/**
	 * Az objektum X koordinátája.
	 */
	void setX(double val);
	
	/**
	 * Az objektum Y koordinátája.
	 */
	double getY();
	
	/**
	 * Az objektum Y koordinátája.
	 */
	void setY(double val);
	
	/**
	 * Az objektum elforgatása szögben mérve.
	 */
	double getRotation();
	
	/**
	 * Az objektum elforgatása szögben mérve.
	 */
	void setRotation(double val);
	
	/**
	 * A kép vizuális középpontját a bitkép ezen X koordinátáján találjuk meg.
	 */
	double getBitmapCenterX();

	/**
	 * A kép vizuális középpontját a bitkép ezen Y koordinátáján találjuk meg.
	 */
	double getBitmapCenterY();

	/**
	 * Ennyiszeresére kell skálázni a bitképet mielőtt megrajzoljuk.
	 * <p>
	 * Intervalluma: [0, +inf]
	 */
	double getScale();
	
	/**
	 * Ennyiszeresére kell skálázni a bitképet mielőtt megrajzoljuk.
	 * <p>
	 * Intervalluma: [0, +inf]
	 */
	void setScale(double val);
	
	/**
	 * Az objektumot reprezentáló bitkép.
	 */
	Image getImage();
}
