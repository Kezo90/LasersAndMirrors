
package hu.unideb.inf.lasersandmirrors.gameobject;

/**
 * Mozgatható és/vagy forgathaató objektum.
 * 
 * @author Kerekes Zoltán
 */
public interface InteractiveGO {
	
	/////////////////////////////////////////////////////////////
	// <copied from GraphicBitmap.> /////////////////////////////
	/////////////////////////////////////////////////////////////
	
	/**
	 * Az objektum X koordinátája.
	 * 
	 * @return Az objektum X koordinátája.
	 */
	double getX();
	
	/**
	 * Az objektum X koordinátája.
	 * 
	 * @param val Az objektum X koordinátája.
	 */
	void setX(double val);
	
	/**
	 * Az objektum Y koordinátája.
	 * 
	 * @return Az objektum Y koordinátája.
	 */
	double getY();
	
	/**
	 * Az objektum Y koordinátája.
	 * 
	 * @param val Az objektum Y koordinátája.
	 */
	void setY(double val);
	
	/**
	 * Az objektum elforgatása szögben mérve.
	 * 
	 * @return Az objektum elforgatása szögben mérve.
	 */
	double getRotation();
	
	/**
	 * Az objektum elforgatása szögben mérve.
	 * 
	 * @param val Az objektum elforgatása szögben mérve.
	 */
	void setRotation(double val);
	
	/////////////////////////////////////////////////////////////
	// </copied from GraphicBitmap.> ////////////////////////////
	/////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 * Kijelölhető az objektum?
	 * (Attól függ, hogy valamilyen egérműveletre képes-e az objektum.)
	 * @see #isRotatable() 
	 * @see #isDraggable() 
	 * 
	 * @return Igaz, ha kijelölhető; egyébként hamis.
	 */
	boolean isSelectable();
	
	/**
	 * Forgatható az objektum?
	 * 
	 * @return Igaz, ha forgatható; hamis egyénként.
	 */
	boolean isRotatable();
	
	/**
	 * A forgathatóságát tudjuk beállítani.
	 * 
	 * @param val Ha igaz, akkor az objektum mozgathatóvá válik; 
	 * egyénként nem lesz mozgatható.
	 */
	void setRotatable(boolean val);
	
	/**
	 * Mozgatható az objektum?
	 * 
	 * @return Igaz, ha mozgatható, hamis egyébként.
	 */
	boolean isDraggable();
	
	/**
	 * A mozgathatóságát tudjuk beállítani.
	 * 
	 * @param val Ha igaz, akkor az objektum mozgathatóvá válik; 
	 * egyénként nem lesz mozgatható.
	 */
	void setDraggable(boolean val);
	
}
