
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Egy játékbeli objektumot jelöl.
 *
 * @author Kerekes Zoltán
 */
public abstract class GameObject implements Graphic{
	
	/**
	 * Az elemek rendezése mélység (láthatóság) szerint. 
	 * A legalsó kerül legelőre.
	 * 
	 * @param gameObjects A rendezendő elemek.
	 */
	public static void sortGameObjectsByDepth(List<GameObject> gameObjects){
		Collections.sort(gameObjects, new Comparator<GameObject>(){
			@Override
			public int compare(GameObject go1, GameObject go2) {
				return go1.getDepth() - go2.getDepth();
			}
		});
	}
}
