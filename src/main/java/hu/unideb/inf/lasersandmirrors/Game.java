
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gui.PlaygroundFrame;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * PENDING: v0.2: falak, színek
 * 
 * TODO: Progkörny: JUnit (assertEquals("ennek kellene lennie", testMethod()), @Before, @Test)
 */

/**
 * A pályán lévő objektumokat tároló osztály.
 *
 * @author Kerekes Zoltán
 */
public class Game {
	
	/** Az adott osztály naplózója. */
	private static final Logger logger = LoggerFactory.getLogger(Game.class);
	
	/** Az aktuális ablak. */
	public static PlaygroundFrame frame;
	
	/**
	 * A program belépési pontja.
	 * 
	 * @param args Parancssori argumentumok. (Nincs használva.)
	 */
	public static void main(String[] args) {
		DB.connect();
		
		
		// TODO: tesztobjektumok: maybe del it
		/*Controller.addGameObject(new GameObjectLaser(200, 200, 240, Color.RED));
		Controller.addGameObject(new GameObjectLaser(300, 200, 60, Color.RED));
		Controller.addGameObject(new GameObjectLaser(200, 400, 170, Color.RED));
		Controller.addGameObject(new GameObjectDiamond(300, 130, 0));
		Controller.addGameObject(new GameObjectDiamond(300, 200, 26));
		Controller.addGameObject(new GameObjectMirror(350, 160, 10));		
		Controller.addGameObject(new GameObjectMirror(300, 160, 10));
		Controller.addGameObject(new GameObjectMirror(250, 160, 10));
		Controller.addGameObject(new GameObjectMirror(200, 160, 10));

		DB.saveLevel("test2");
		*/
		Controller.loadLevel("test");
		
		// ablak csinosítása
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException 
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			logger.warn("Failed to load LookAndFeel.");
		}
		// ablak megnyitása
		frame = new PlaygroundFrame();
		frame.setVisible(true);
	}
}
