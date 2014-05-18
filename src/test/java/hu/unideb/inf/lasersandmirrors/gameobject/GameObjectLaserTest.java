
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Color;
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
public class GameObjectLaserTest {
	
	public GameObjectLaserTest() {
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
	public void testGetImage_sameInstance() {
		List<GameObjectLaser> lasers = Arrays.asList(new GameObjectLaser[]{
			new GameObjectLaser(),
			new GameObjectLaser(),
			new GameObjectLaser(45, 256, 123, Color.YELLOW),
			new GameObjectLaser(46, 24, 345, Color.RED),
			new GameObjectLaser(),
			new GameObjectLaser(36, 2, 7842, Color.BLUE)
		});
		
		for (GameObjectLaser go1 : lasers) {
			for (GameObjectLaser go2 : lasers) {
				assertSame(go1.getImage(), go2.getImage());
			}
		}
	}
	
	@Test
	public void testIsSelectable() {
		GameObjectLaser go = new GameObjectLaser();
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
		GameObjectLaser go = new GameObjectLaser();
		
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
