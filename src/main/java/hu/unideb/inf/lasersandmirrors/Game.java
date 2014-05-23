
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gui.GameFrame;
import hu.unideb.inf.lasersandmirrors.gui.PlaygroundFrame;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * PENDING: v0.2: falak, színek
 * TODO: pályák legyártása
 */

/**
 * A pályán lévő objektumokat tároló osztály.
 *
 * @author Kerekes Zoltán
 */
public class Game {
	
	/** Az adott osztály naplózója. */
	private static final Logger log = LoggerFactory.getLogger(Game.class);
	
	/** Az aktuális ablak. */
	public static GameFrame frame;
	
	/**
	 * A program belépési pontja.
	 * 
	 * @param args Parancssori argumentumok. (Nincs használva.)
	 */
	public static void main(String[] args) {
		DB.connect();
		
		// Globális tooltip beállítások.
		ToolTipManager tooltipManager = ToolTipManager.sharedInstance();
		tooltipManager.setInitialDelay(0);
		tooltipManager.setDismissDelay(15_000);
		
		// ablak csinosítása
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException 
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			log.warn("Failed to load LookAndFeel.");
		}
		// ablak megnyitása
		frame = new PlaygroundFrame();
		frame.setVisible(true);
		
		// TODO: tesztobjektumok
		Controller.getCurrentLevel().addGameObject(new GameObjectLaser(10, 20, 50, Color.RED));
		Controller.getCurrentLevel().addGameObject(new GameObjectDiamond(300, 320, 0));
		Controller.getCurrentLevel().addGameObject(new GameObjectMirror(500, 200, 40));
		Controller.getCurrentLevel().setName("newTest");
		Controller.saveCurrentLevel();
		
		// billentyűleütés kezelő regisztrálása
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeystrokeHandler());
	}
}
