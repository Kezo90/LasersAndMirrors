
package hu.unideb.inf.lasersandmirrors;

import math.geom2d.Point2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Kerekes Zoltán
 */
public class MyMathTest {
	
	private static final double DELTA = 1.0e-8;
	
	public MyMathTest() {
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
	public void testSquareDist1() {
		Point2D ptA = new Point2D(10, 10);
		Point2D ptB = new Point2D(20, 20);
		double expResult = 200;
		double result = MyMath.squareDist(ptA, ptB);
		assertEquals(expResult, result, DELTA);
	}
	
	@Test
	public void testSquareDist2() {
		Point2D ptA = new Point2D(-10, 10);
		Point2D ptB = new Point2D(20, 20);
		double expResult = 1000;
		double result = MyMath.squareDist(ptA, ptB);
		assertEquals(expResult, result, DELTA);
	}
	
	@Test
	public void testSquareDist3() {
		Point2D ptA = new Point2D(2.3, 6.8);
		Point2D ptB = new Point2D(3.8, 6.7);
		double expResult = 2.26;
		double result = MyMath.squareDist(ptA, ptB);
		assertEquals(expResult, result, DELTA);
	}
	
	@Test
	public void testSquareDist4() {
		Point2D ptA = new Point2D(2.3, 6.996);
		Point2D ptB = new Point2D(-3, -6.7);
		double expResult = 215.670416;
		double result = MyMath.squareDist(ptA, ptB);
		assertEquals(expResult, result, DELTA);
	}
	
	
	
	
	@Test
	public void testReflectionAngle_basic() {
		double rayAngle		= 350;
		double mirrorAngle	= 90;
		double expResult	= 190;
		double result = MyMath.reflectionAngle(
				Math.toRadians(rayAngle), Math.toRadians(mirrorAngle));
		assertEquals(Math.toRadians(expResult), result, DELTA);
	}
	
	@Test
	public void testReflectionAngle_fraction() {
		double rayAngle		= 350.3;
		double mirrorAngle	= 90;
		double expResult	= 189.7;
		double result = MyMath.reflectionAngle(
				Math.toRadians(rayAngle), Math.toRadians(mirrorAngle));
		assertEquals(Math.toRadians(expResult), result, DELTA);
	}
	
	@Test
	public void testReflectionAngle_backSide() {
		double rayAngle		= 180;
		double mirrorAngle	= 90;
		double expResult	= 0;
		double result = MyMath.reflectionAngle(
				Math.toRadians(rayAngle), Math.toRadians(mirrorAngle));
		assertEquals(Math.toRadians(expResult), result, DELTA);
	}
	
	@Test
	public void testReflectionAngle_rotatedMirror() {
		double rayAngle		= 90;
		double mirrorAngle	= 100.5;
		double expResult	= 111;
		double result = MyMath.reflectionAngle(
				Math.toRadians(rayAngle), Math.toRadians(mirrorAngle));
		assertEquals(Math.toRadians(expResult), result, DELTA);
	}
}