
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gameobject.InteractiveGO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
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
	}
	
	@After
	public void tearDown() {
		Controller.removeAllGameObjects();
	}

	@Test
	public void testRenderGame_modifiedSomething() {
		List<GameObject> gos = Arrays.asList(new GameObject[]{
			new GameObjectDiamond(34, 78, 43.3),
			new GameObjectLaser(5, -4, 3.33, Color.GREEN),
			new GameObjectDiamond(55, 232, 353),
			new GameObjectMirror(34.565, 23, 22.222)
		});
		Controller.addGameObjects(gos);
		
		final JPanel panel = new JPanel(){
			@Override
			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				Renderer.renderGame(g2d, this);
			}
		};
		panel.repaint();
		
		List<GameObject> returnedGOs = Controller.getGameObjects();
		
		assertTrue("Number of GameObjects in the Controller's storage modified!",
				gos.size() == returnedGOs.size());
		
		for (int i = 0; i < gos.size(); i++) {
			assertSame("Order of GameObjects in the Controller's storage modified!", 
					gos.get(i), returnedGOs.get(i));
		}
		
		// GameObject values changed or not
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