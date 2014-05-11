
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gui.PlaygroundFrame;
import java.awt.Color;

/*
 * PENDING: v0.2: falak, színek, jobb képek
 * 
 * TODO: Progkörny: JUnit (assertEquals("ennek kellene lennie", testMethod()), @Before, @Test)
 * TODO: Game osztály kipucolása/rendberakása
 * TODO: Swing-es komponensek javadoc-ja.
 */

/**
 * A pályán lévő objektumokat tároló osztály.
 *
 * @author Kerekes Zoltán
 */
public class Game {
	
	/**
	 * A program belépési pontja.
	 * 
	 * @param args Parancssori argumentumok. (Nincs használva.)
	 */
	public static void main(String[] args) {
		DB.connect();
		
		/*
		// TODO: tesztobjektumok: maybe del it
		Controller.addGameObject(new GameObjectLaser(200, 200, 240, Color.RED));
		Controller.addGameObject(new GameObjectLaser(300, 200, 60, Color.RED));
		Controller.addGameObject(new GameObjectLaser(200, 400, 170, Color.YELLOW));
		Controller.addGameObject(new GameObjectDiamond(300, 130, 0));
		Controller.addGameObject(new GameObjectDiamond(300, 200, 26));
		Controller.addGameObject(new GameObjectMirror(350, 160, 10));		
		Controller.addGameObject(new GameObjectMirror(300, 160, 10));
		Controller.addGameObject(new GameObjectMirror(250, 160, 10));
		Controller.addGameObject(new GameObjectMirror(200, 160, 10));

		DB.saveLevel("test");
		*/
		
		// ablak megnyitása.
		PlaygroundFrame frame = new PlaygroundFrame();
		frame.setVisible(true);
	}
}
