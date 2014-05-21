
package hu.unideb.inf.lasersandmirrors.gui;

/**
 * @author Kerekes Zoltán
 */
public interface GameMenu {
	
	/**
	 * Visszalép a szülő menübe.
	 */
	void goBack();
	
	/**
	 * A menü neve kérdezhető le.
	 * 
	 * @return A menü neve.
	 * @see javax.swing.JPanel#getName() 
	 */
	String getName();
	
}
