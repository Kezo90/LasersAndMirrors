
package hu.unideb.inf.lasersandmirrors.gui;

import javax.swing.JPanel;

/**
 * @author Kerekes Zoltán
 */
public interface GameFrame {
	
	/**
	 * Az aktuális menü kérdezhető le.
	 * 
	 * @return Az aktuális menu referenciája.
	 */
	GameMenu getMenu();
	
	/**
	 * Új menü állítható be.
	 * 
	 * @param menu Az új menü.
	 */
	void setMenu(GameMenu menu);
	
	/**
	 * Az ablak láthatóvá/láthatatlanná tétele.
	 * 
	 * @param visible Az ablak láthatósága.
	 * @see JPanel#setVisible(boolean) 
	 */
	void setVisible(boolean visible);
	
	/**
	 * A rajzterület állítható be.
	 * 
	 * @param gameArea Az új rajzterület.
	 */
	void setGameArea(JPanel gameArea);
	
	/**
	 * Az aktuális rajzterület kérdezhető le.
	 * 
	 * @return Az aktuális rajzterület.
	 */
	JPanel getGameArea();
	
}
