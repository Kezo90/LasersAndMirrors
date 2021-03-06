
package hu.unideb.inf.lasersandmirrors.gui;

import hu.unideb.inf.lasersandmirrors.Controller;
import hu.unideb.inf.lasersandmirrors.DB;
import hu.unideb.inf.lasersandmirrors.Game;
import hu.unideb.inf.lasersandmirrors.Settings;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Játék menü.
 *
 * @author Kerekes Zoltán
 */
public class PlayMenu extends javax.swing.JPanel implements GameMenu {

	/**
	 * Creates new form PlayMenu.
	 */
	@SuppressWarnings("unchecked")
	public PlayMenu() {
		initComponents();
		
		// A kezdeti állapotban ne tudjunk betölteni a semmit
		playButton.setEnabled(false);
		
		// tooltip
		listTitleLabel.setToolTipText(
				"<html>"
					+ "<p style=\"margin-bottom:7px\">"
						+ "<strong>Numbers in brackets:</strong> Lasers, Mirrors and Diamonds on the level."
					+ "</p>"
					+ "<p>"
						+ "<strong>Star at the beginning:</strong> level completed."
					+ "</p>"
				+ "</html>");
		
		// listaelemek módosíthatóvá tétele
		levelsListItems = new DefaultListModel<>();
		levelsList.setModel(levelsListItems);
		
		// listaelemek legyártása
		List<DB.LevelInfo> levelInfos = DB.loadLevelInfos();
		if(levelInfos != null){
			for (DB.LevelInfo levelInfo : levelInfos) {
				levelsListItems.addElement(new ListItem(levelInfo.name, String.format("%s%s (%s, %s, %s)", 
						levelInfo.completed ? Settings.COMPLETED_LEVEL_MARKER : "" ,
						levelInfo.name,
						levelInfo.laserCount, 
						levelInfo.mirrorCount, 
						levelInfo.diamondCount)));
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        levelsContainer = new javax.swing.JPanel();
        listTitleLabel = new javax.swing.JLabel();
        levelsListScrolPane = new javax.swing.JScrollPane();
        levelsList = new javax.swing.JList();
        playButton = new javax.swing.JButton();
        goBackButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(227, 227, 227));
        setName("playMenu"); // NOI18N
        setPreferredSize(new java.awt.Dimension(224, 650));

        titleLabel.setBackground(new java.awt.Color(102, 102, 102));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Play Menu");

        levelsContainer.setOpaque(false);

        listTitleLabel.setText("Available levels");

        levelsList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        levelsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        levelsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        levelsList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        levelsList.setDoubleBuffered(true);
        levelsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                levelsListValueChanged(evt);
            }
        });
        levelsListScrolPane.setViewportView(levelsList);

        javax.swing.GroupLayout levelsContainerLayout = new javax.swing.GroupLayout(levelsContainer);
        levelsContainer.setLayout(levelsContainerLayout);
        levelsContainerLayout.setHorizontalGroup(
            levelsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(levelsListScrolPane)
            .addComponent(listTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        levelsContainerLayout.setVerticalGroup(
            levelsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(levelsContainerLayout.createSequentialGroup()
                .addComponent(listTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(levelsListScrolPane, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
        );

        playButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        goBackButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        goBackButton.setForeground(new java.awt.Color(155, 25, 25));
        goBackButton.setText("Main menu");
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(levelsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(goBackButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addComponent(levelsContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                .addComponent(goBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Visszalépés a főmenübe.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        goBack();
    }//GEN-LAST:event_goBackButtonActionPerformed

	/**
	 * A Play gombot megnyomjuk: betölti a kijelölt pályát a játék.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
		Controller.loadLevel( ((ListItem)levelsList.getSelectedValue()).getValue() );
    }//GEN-LAST:event_playButtonActionPerformed

	/**
	 * A listában kijelöltünk valamit: a Play gomb elszürkülhet, 
	 * mert az új (szerkesztésnek fenntartott) üres páylát nem tölthetjük be.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void levelsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_levelsListValueChanged
		JList list = (JList)evt.getSource();
		String value = ((ListItem)list.getSelectedValue()).getValue();
		if(value == null){
			playButton.setEnabled(false);
		} else {
			playButton.setEnabled(true);
		}
    }//GEN-LAST:event_levelsListValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goBackButton;
    private javax.swing.JPanel levelsContainer;
    private javax.swing.JList levelsList;
    private javax.swing.JScrollPane levelsListScrolPane;
    private javax.swing.JLabel listTitleLabel;
    private javax.swing.JButton playButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

	/**
	 * A listaelemek, melyeket a lista létezésekor is tudunk szerkeszteni.
	 */
	private DefaultListModel<ListItem> levelsListItems;
	
	/**
	 * A pályaválasztó menü elemei kérdezhető le.
	 * 
	 * @return A pályaválasztó menü elemei.
	 */
	public DefaultListModel<ListItem> getLevelsListItems(){
		return this.levelsListItems;
	}
	
	@Override
	public void goBack() {
		Controller.startNewLevel(null);
		Game.frame.setGameArea(new WelcomeArea());
		Game.frame.setMenu(new MainMenu());
	}
}
