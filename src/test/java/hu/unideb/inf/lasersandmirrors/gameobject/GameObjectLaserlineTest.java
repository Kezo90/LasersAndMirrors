
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Color;
import java.awt.geom.Point2D;
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
public class GameObjectLaserlineTest {
	
	public GameObjectLaserlineTest() {
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
	public void testGetColor(){
		GameObjectLaser go = new GameObjectLaser();
		go.setColor(Color.yellow);
		assertEquals(go.getColor(), Color.yellow);
		
		go.setColor(Color.PINK);
		assertEquals(go.getColor(), go.getLaserLine().getColor());
		assertEquals(go.getColor(), Color.PINK);
	}

	
	
	
	
	
	//--
	
	@Test
	public void testGetPoints() {
		System.out.println("getPoints");
		GameObjectLaserline instance = null;
		List expResult = null;
		List result = instance.getPoints();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	@Test
	public void testAddPoint_jagPoint2D() {
		System.out.println("addPoint");
		Point2D point = null;
		GameObjectLaserline instance = null;
		instance.addPoint(point);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	@Test
	public void testAddPoint_mgPoint2D() {
		System.out.println("addPoint");
		math.geom2d.Point2D point = null;
		GameObjectLaserline instance = null;
		instance.addPoint(point);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	@Test
	public void testClearPoints() {
		System.out.println("clearPoints");
		GameObjectLaserline instance = null;
		instance.clearPoints();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
}