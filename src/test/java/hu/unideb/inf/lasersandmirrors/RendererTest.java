
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gameobject.InteractiveGO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Kerekes Zoltán
 */
public class RendererTest {
	
	private static final double DELTA = 1.0e-10;
	
	public RendererTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		Controller.startNewLevel("testingRenderer");
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testRenderGame_modifiedSomething() {
		// objektumok létrehozása
		List<GameObject> gos = new ArrayList<>();
		GameObjectDiamond go1 = new GameObjectDiamond(34, 78, 43.3);
		go1.setLightened(true);
		gos.add(go1);
		GameObjectLaser go2 = new GameObjectLaser(5, -4, 3.33, Color.GREEN);
		GameObjectLaser.Laserline go2Laserline = go2.getLaserLine();
		go2Laserline.addPoint(new Point2D.Double(40, 80));
		go2Laserline.addPoint(new Point2D.Double(80, 85));
		gos.add(go2);
		gos.add(new GameObjectDiamond(55, 232, 353));
		gos.add(new GameObjectMirror(34.565, 23, 22.222));
		
		Controller.getCurrentLevel().addAllGameObject(gos);
		
		// rajzterület
		JFrame frame = new JFrame("test", null);
		JPanel panel = new JPanel(true){
			@Override
			public void paint(Graphics g) {
				Renderer.renderGame(g, this);
			}
		};
		panel.setSize(Settings.WINDOW_SIZE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.repaint();
		
		// ********************************************************************
		// ellenőrzés innestől ************************************************
		List<GameObject> returnedGOs = Controller.getCurrentLevel().getAllGameObject();
		
		assertTrue("Number of GameObjects in the Controller's storage modified!",
				gos.size() == returnedGOs.size());
		
		for (int i = 0; i < gos.size(); i++) {
			assertSame("Order of GameObjects in the Controller's storage modified!", 
					gos.get(i), returnedGOs.get(i));
		}
		
		// GameObject értékek megváltoztak a kirajzolás után?
		assertEquals(((InteractiveGO)gos.get(0)).getX(), 34, DELTA);
		assertEquals(((InteractiveGO)gos.get(1)).getX(), 5, DELTA);
		assertEquals(((InteractiveGO)gos.get(2)).getX(), 55, DELTA);
		assertEquals(((InteractiveGO)gos.get(3)).getX(), 34.565, DELTA);

		assertEquals(((InteractiveGO)gos.get(0)).getY(), 78, DELTA);
		assertEquals(((InteractiveGO)gos.get(1)).getY(), -4, DELTA);
		assertEquals(((InteractiveGO)gos.get(2)).getY(), 232, DELTA);
		assertEquals(((InteractiveGO)gos.get(3)).getY(), 23, DELTA);

		assertEquals(((InteractiveGO)gos.get(0)).getRotation(), 43.3, DELTA);
		assertEquals(((InteractiveGO)gos.get(1)).getRotation(), 3.33, DELTA);
		assertEquals(((InteractiveGO)gos.get(2)).getRotation(), 353, DELTA);
		assertEquals(((InteractiveGO)gos.get(3)).getRotation(), 22.222, DELTA);

		assertEquals(((GameObjectLaser)gos.get(1)).getColor(), Color.GREEN);
	}
}