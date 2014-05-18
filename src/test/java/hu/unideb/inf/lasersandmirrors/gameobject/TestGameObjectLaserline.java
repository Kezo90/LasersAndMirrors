
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
public class TestGameObjectLaserline {
	
	private final double delta = 1.0e-10;
	private GameObjectLaser laser;
	private GameObjectLaserline laserline;
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		laser = new GameObjectLaser();
		laserline = laser.getLaserLine();
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

	@Test
	public void testGetPoints() {
		Point2D pt1 = new Point2D.Double(50, -5);
		laserline.addPoint(pt1);
		Point2D pt2 = new Point2D.Double(-33, 26);
		laserline.addPoint(pt2);
		Point2D pt3 = new Point2D.Double(67, 56);
		laserline.addPoint(pt3);
		
		List<Point2D> points = laserline.getPoints();
		
		assertEquals(pt1.getX(), points.get(0).getX(), delta);
		assertEquals(pt3.getX(), points.get(2).getX(), delta);
		assertEquals(pt2.getX(), points.get(1).getX(), delta);
		
		assertEquals(pt1.getY(), points.get(0).getY(), delta);
		assertEquals(pt3.getY(), points.get(2).getY(), delta);
		assertEquals(pt2.getY(), points.get(1).getY(), delta);
	}

	@Test
	public void testAddPoint_jagPoint2D() {
		Point2D pt1 = new Point2D.Double(0, 0);
		laserline.addPoint(pt1);
		Point2D pt2 = new Point2D.Double(-510.3, 55.3);
		laserline.addPoint(pt2);
		
		Point2D returnedPt2 = laserline.getPoints().get(1);
		assertEquals(pt2.getX(), returnedPt2.getX(), delta);
		assertEquals(pt2.getY(), returnedPt2.getY(), delta);
	}

	@Test
	public void testAddPoint_mgPoint2D() {
		math.geom2d.Point2D pt1 = new math.geom2d.Point2D(0, 0);
		laserline.addPoint(pt1);
		math.geom2d.Point2D pt2 = new math.geom2d.Point2D(-510.3, 55.3);
		laserline.addPoint(pt2);
		
		Point2D returnedPt2 = laserline.getPoints().get(1);
		assertEquals(pt2.x(), returnedPt2.getX(), delta);
		assertEquals(pt2.y(), returnedPt2.getY(), delta);
	}

	@Test
	public void testClearPoints() {
		Point2D pt1 = new Point2D.Double(50, -5);
		laserline.addPoint(pt1);
		Point2D pt2 = new Point2D.Double(-33, 26);
		laserline.addPoint(pt2);
		
		laserline.clearPoints();
		
		Point2D pt11 = new Point2D.Double(10, -5.3);
		laserline.addPoint(pt11);
		Point2D pt12 = new Point2D.Double(3.8, 2);
		laserline.addPoint(pt12);
		
		List<Point2D> points = laserline.getPoints();
		
		assertEquals(  10, points.get(0).getX(), delta);
		assertEquals(-5.3, points.get(0).getY(), delta);
		assertEquals( 3.8, points.get(1).getX(), delta);
		assertEquals(   2, points.get(1).getY(), delta);
	}
}