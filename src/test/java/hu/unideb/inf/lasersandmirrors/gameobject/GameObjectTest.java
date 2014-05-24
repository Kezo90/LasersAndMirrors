
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Kerekes Zoltán
 */
public class GameObjectTest {
	
	List<GameObject> gameObjects;

	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		gameObjects = new ArrayList<>();
		
		gameObjects.add(new GameObjectDiamond());
		gameObjects.add(new GameObjectDiamond());
		gameObjects.add(new GameObjectLaser());
		gameObjects.add(new GameObjectMirror());
		gameObjects.add(new GameObjectLaser());
		gameObjects.add(new GameObjectDiamond());
		gameObjects.add(new GameObjectLaser());
		gameObjects.add(new GameObjectMirror());
		gameObjects.add(new GameObjectDiamond());
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testSortGameObjectsByDepth_reversedOrder(){
		for (int i = 0; i < gameObjects.size() - 1; i++) {
			if(gameObjects.get(i).getDepth() > gameObjects.get(i+1).getDepth()){
				return;
			}
		}
		fail("Tester list already sorted.");
	}
	
	@Test
	public void testSortGameObjectsByDepth_alreadySorted() {
		GameObject.sortGameObjectsByDepth(gameObjects);
		
		for (int i = 0; i < gameObjects.size() - 1; i++) {
			if(gameObjects.get(i).getDepth() > gameObjects.get(i+1).getDepth()){
				fail("It's shuffling the list, not ordering.");
			}
		}
	}
}
