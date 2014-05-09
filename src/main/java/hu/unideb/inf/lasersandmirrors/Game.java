
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import java.awt.Component;
import javax.swing.JPanel;
import hu.unideb.inf.lasersandmirrors.gui.PlaygroundFrame;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/*
 * PENDING: v0.2: falak, színek
 * 
 * TODO: Progkörny: JUnit (assertEquals("ennek kellene lennie", testMethod()), @Before, @Test)
 * TODO: Game osztály kipucolása/rendberakása
 */

/**
 * A program belépési pontja.
 *
 * @author Kerekes Zoltán
 */
public class Game {
	
	public static void main(String[] args) {
		DB.connect();
		
		/*
		// TODO: tesztobjektumok: maybe del it
		addGameObject(new GameObjectLaser(200, 200, 240, Color.RED));
		addGameObject(new GameObjectLaser(300, 200, 60, Color.RED));
		addGameObject(new GameObjectLaser(200, 400, 170, Color.YELLOW));
		addGameObject(new GameObjectDiamond(300, 130, 0));
		addGameObject(new GameObjectDiamond(300, 200, 26));
		addGameObject(new GameObjectMirror(350, 160, 10));

		DB.saveLevel("test");
		*/
		List<GameObject> level = DB.loadLevel("test");
		if(level != null){
			removeAllGameObjects();
			addGameObjects(level);
		}
		
		// PlaygroundFrame beállítása.
		PlaygroundFrame frame = new PlaygroundFrame();
		frame.setVisible(true);
		
		// A playgroundPanel megkeresése.
		JPanel playgroundPanel = null;
		Component[] components = frame.getContentPane().getComponents();
		for (Component component : components) {
			String name = component.getName();
			if(name != null && name.equals("playgroundPanel")){
				playgroundPanel = (JPanel)component;
			}
		}
		
		Controller.setActivePanel(playgroundPanel);
	}
	// </main>
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 * A pályán lévő {@link GameObject}-ek kollekciója.
	 */
	private static ArrayList<GameObject> gameObjects = new ArrayList<>();

	/**
	 * Új elem felvitele a pályára.
	 * 
	 * @param gameObject Az új elem.
	 */
	public static void addGameObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
	
	/**
	 * Új elemek felvitele a pályára.
	 * 
	 * @param newObjects Az új elemek.
	 */
	public static void addGameObjects(List<GameObject> newObjects){
		for (GameObject gameObject : newObjects) {
			gameObjects.add(gameObject);
		}
		
	}
	
	/**
	 * A pályán lévő {@link GameObject}-ek kollekcióját lehet lekérdezni.
	 * 
	 * @return A pályán lévő {@link GameObject}-ek.
	 */
	public static ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}
	
	/**
	 * Elem eltávolítása a pályáról. 
	 * 
	 * @param gameObject Az elem, melyet el akarunk tüntetni.
	 */
	public static void removeGameObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}
	
	/**
	 * Az összes objektum eltávolítása a pályáról.
	 */
	public static void removeAllGameObjects(){
		gameObjects.clear();
	}
	
	
	
	
	
	/**
	 * Az alkalmazás befejezése, erőforrások felszabadítása.
	 */
	public static void exitGame(){
		DB.close();
		System.exit(0);
	}
}
