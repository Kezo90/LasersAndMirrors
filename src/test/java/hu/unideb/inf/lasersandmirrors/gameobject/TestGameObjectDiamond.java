
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
public class TestGameObjectDiamond {
	
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
		List<GameObjectDiamond> diamonds = Arrays.asList(new GameObjectDiamond[]{
			new GameObjectDiamond(),
			new GameObjectDiamond(),
			new GameObjectDiamond(20, 40, 93),
			new GameObjectDiamond(4, 55, 3),
			new GameObjectDiamond(),
			new GameObjectDiamond(455, 66, 22)
		});
		
		for (GameObjectDiamond go1 : diamonds) {
			for (GameObjectDiamond go2 : diamonds) {
				assertSame(go1.getImage(), go2.getImage());
			}
		}
	}
	
	@Test
	public void testDiamondShineGOGetImage_sameInstance() {
		GameObjectDiamond diamond1 = new GameObjectDiamond();
		GameObjectDiamond diamond2 = new GameObjectDiamond(45, 64, 234);
		List<GameObjectDiamond.DiamondShineGO> shines = 
				Arrays.asList(new GameObjectDiamond.DiamondShineGO[]{
					diamond1.new DiamondShineGO(),
					diamond2.new DiamondShineGO(),
					diamond1.new DiamondShineGO(),
					diamond1.new DiamondShineGO(),
					diamond2.new DiamondShineGO(),
		});
		
		for (GameObjectDiamond.DiamondShineGO go1 : shines) {
			for (GameObjectDiamond.DiamondShineGO go2 : shines) {
				assertSame(go1.getImage(), go2.getImage());
			}
		}
	}
	
	@Test
	public void testSetRotation(){
		Double delta = 1.0e-10;
		GameObjectDiamond go = new GameObjectDiamond();
		
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