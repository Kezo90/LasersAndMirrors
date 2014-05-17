
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.util.List;
import org.junit.*;

/**
 *
 * @author KZ  
 */
public class GameObjectTest extends TestCase {
	
	public GameObjectTest(String testName) {
		super(testName);
	}
	
	@Before
	public void init(){
		
	}

	/**
	 * Test of sortGameObjectsByDepth method, of class GameObject.
	 */
	public void testSortGameObjectsByDepth() {
		System.out.println("sortGameObjectsByDepth");
		List<GameObject> gameObjects = null;
		GameObject.sortGameObjectsByDepth(gameObjects);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
}
