
package hu.unideb.inf.lasersandmirrors.gameobject;

/**
 * Grafikai elemek alapinterfésze.
 * <p>
 * Kiterjesztik: {@link GraphicBitmap}, {@link GraphicMultiline}
 *
 * @author Kerekes Zoltán
 */
public interface Graphic {
	
	// Előre definiált mélységek.
	/** A háttér mélységértéke. */
	static final int DEPTH_BACKGROUND			= -1;
	/** A falak mélységértéke. */
	static final int DEPTH_WALL					= 1;
	/** A tükrök mélységértéke. */
	static final int DEPTH_MIRROR				= 2;
	/** A lézernyalábok mélységértéke. */
	static final int DEPTH_LASER_LINE			= 3;
	/** A lézernyalábok megtörési csillanásának mélységértéke. */
	static final int DEPTH_LASER_LINE_SHINE		= 4;
	/** A lézerek mélységértéke. */
	static final int DEPTH_LASER				= 5;
	/** A gyémántok mélységértéke. */
	static final int DEPTH_DIAMOND				= 6;
	/** A gyémántok csillanásának mélységértéke. */
	static final int DEPTH_DIAMOND_SHINE		= 7;
	
	/**
	 * A grafikai elem mélységét adja vissza. 
	 * <p>
	 * Minél kisebb, annál hátrébb van a nézőponttól, 
	 * így annál kisebb esélyel fog látszódni az előtte lévő elemek miatt.
	 * 
	 * @return A grafikai elem mélysége.
	 */
	int getDepth();
}
