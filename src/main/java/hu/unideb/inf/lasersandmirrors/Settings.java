
package hu.unideb.inf.lasersandmirrors;

import java.awt.Color;
import java.awt.Dimension;

/**
 * A játékra globálisan vonatkozó beállításokat tartalmazó osztály.
 *
 * @author Kerekes Zoltán
 */
public class Settings {
	
	/** A játék ablakának mérete. */
	public static final Dimension WINDOW_SIZE = new Dimension(1024, 650);
	
	/** Az objektumok alapértelemezett színe. */
	public static final Color DEFAULT_COLOR = Color.RED;
	
	/** Frissítési időköz ezredmásodpercben. */
	public static final int UPDATE_INTERVAL = 1000/100; // 100 FPS
	
	/** A lézersugarak maximális hossza. */
	public static final double LASERLINE_LENGTH = 10000.0;
	
	/** Az objektumok kijelölhető körlapjának sugara. */
	public static final double GO_SELECTION_RADIUS = 20.0;
	
	/** Az objektumok forgatásának sebessége. */
	public static final double ROTATION_SPEED = 0.5;
	
	/** Üres listaelembeli szöveg. */
	public static final String EMPTY_LIST_ITEM_STRING = "<Empty>";
	
}
