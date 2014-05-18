
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.util.Arrays;
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
public class GameObjectMirrorTest {
	
	public GameObjectMirrorTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testGetImage() {
		List<GameObjectMirror> mirrors = Arrays.asList(new GameObjectMirror[]{
			new GameObjectMirror(),
			new GameObjectMirror(),
			new GameObjectMirror(45, 2, 73),
			new GameObjectMirror(2, 435644, 234),
			new GameObjectMirror(),
			new GameObjectMirror(256, 2, 235)
		});
		
		for (GameObjectMirror go1 : mirrors) {
			for (GameObjectMirror go2 : mirrors) {
				assertSame(go1.getImage(), go2.getImage());
			}
		}
	}

	@Test
	public void testIsSelectable() {
		GameObjectMirror go = new GameObjectMirror();
		go.setDraggable(false);
		go.setRotatable(false);
		assertFalse(go.isSelectable());
		
		go.setDraggable(true);
		assertTrue(go.isSelectable());
		
		go.setDraggable(false);
		go.setRotatable(false);
		assertFalse(go.isSelectable());
		
		go.setRotatable(true);
		assertTrue(go.isSelectable());
	}
	
	@Test
	public void testSetRotation(){
		Double delta = 1.0e-10;
		GameObjectMirror go = new GameObjectMirror();
		
		go.setRotation(42.0);
		assertEquals(42.0, go.getRotation(), delta);
		
		go.setRotation(370.2);
		assertEquals(10.2, go.getRotation(), delta);
		
		go.setRotation(-20.5);
		assertEquals(339.5, go.getRotation(), delta);
		
		go.setRotation(-3603.4);
		assertEquals(356.6, go.getRotation(), delta);
	}
}