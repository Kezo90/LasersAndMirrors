
package hu.unideb.inf.lasersandmirrors.gui;

/**
 * Listaelem, mely az értéket és a megjelenítendő szöveget tárolja.
 * 
 * JList-hez használatos.
 * 
 * @author Kerekes Zoltán
 */
public class ListItem{
	
	/** A listaeem értéke. */
	private String value;
	
	/** A listaelem megjelenítendő szövege. */
	private String text;

	/**
	 * Új listaelem létrehozása.
	 * 
	 * @param value A listaelem logikai feldolgozás szerinti értéke.
	 * @param text A megjelenítendő szöveg.
	 */
	public ListItem(String value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * A listaelem értéke kérdezhető le.
	 * 
	 * @return A lista neve.
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * A lista értéke állítható be.
	 * 
	 * @param value A listaelem új értéke.
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	 * A listaelem megjelenítendő szövege kérdezhető le.
	 * 
	 * @return A lista szövege.
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * A lista megjelenítendő szövege állítható be.
	 * 
	 * @param text A listaelem új szövege.
	 */
	public void setText(String text){
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
