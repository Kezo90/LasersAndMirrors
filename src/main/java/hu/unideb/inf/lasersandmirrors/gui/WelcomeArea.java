
package hu.unideb.inf.lasersandmirrors.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kezdőképernyő.
 *
 * @author Kerekes Zoltán
 */
public class WelcomeArea extends javax.swing.JPanel {
	
	/** Az adott osztály naplózója. */
	private static final Logger log = LoggerFactory.getLogger(WelcomeArea.class);
	
	/**
	 * Creates new form PlayArea.
	 */
	public WelcomeArea() {
		initComponents();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        developersName = new javax.swing.JLabel();
        gameNameLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(800, 650));
        setMinimumSize(new java.awt.Dimension(800, 650));
        setName("welcomeArea"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 650));

        developersName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        developersName.setForeground(new java.awt.Color(0, 153, 255));
        developersName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        developersName.setText("By: Zoltán Kerekes");
        developersName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        developersName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                developersNameMouseClicked(evt);
            }
        });
        developersName.setBounds(460, 350, 114, 17);
        jLayeredPane1.add(developersName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gameNameLabel.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        gameNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameNameLabel.setText("Lasers & Mirrors v0.1");
        gameNameLabel.setBounds(0, 0, 800, 650);
        jLayeredPane1.add(gameNameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * A készítő nevére rákattintottak: töltse be a honapot.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void developersNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_developersNameMouseClicked
        if(Desktop.isDesktopSupported()){
			Desktop browser = Desktop.getDesktop();
			try {
				browser.browse(new URI("https://github.com/Kezo90"));
			} catch (IOException | URISyntaxException ex) {
				log.warn("Can't open webpage.");
			}
		}
    }//GEN-LAST:event_developersNameMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel developersName;
    private javax.swing.JLabel gameNameLabel;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
