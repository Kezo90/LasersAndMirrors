
package hu.unideb.inf.lasersandmirrors.gameobject;

/**
 * @author Kerekes Zoltán
 */
public abstract class BitmapGO extends GameObject implements GraphicBitmap {

	/** A rasztergrafikus kép középpontjának x koordinátája. */
	protected double bitmapCenterX;
	
	/** A rasztergrafikus kép középpontjának y koordinátája. */
	protected double bitmapCenterY;
	
	/** Az objektum rasztergrafikus képének és tényleges méretének aránya. */
	protected double scale;
	
	@Override
	public double getBitmapCenterX() {
		return bitmapCenterX;
	}

	@Override
	public double getBitmapCenterY() {
		return bitmapCenterY;
	}

	@Override
	public double getScale() {
		return scale;
	}
	
	@Override
	public void setScale(double val) {
		scale = val;
	}

}
