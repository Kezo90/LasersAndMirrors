/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.lasersandmirrors.gui;

import hu.unideb.inf.lasersandmirrors.Controller;
import hu.unideb.inf.lasersandmirrors.DB;
import hu.unideb.inf.lasersandmirrors.Settings;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ToolTipManager;

/**
 * A főmenü.
 *
 * @author Kerekes Zoltán
 */
public class LevelLoaderPanel extends javax.swing.JPanel {

	/**
	 * A pályákat tartalmazó lista egy-egy eleme.
	 */
	class ListItem{
		private String value;
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
		 * A listaelem neve kérdezhető le.
		 * 
		 * @return A lista neve.
		 */
		public String getName(){
			return value;
		}

		@Override
		public String toString() {
			return text;
		}
	}
	
	/**
	 * Creates new form LevelLoaderPanel.
	 */
	@SuppressWarnings("unchecked")
	public LevelLoaderPanel() {
		initComponents();
		
		// tooltip
		listTitleLabel.setToolTipText(
				"<html>"
					+ "<p style=\"margin-bottom:7px\">"
						+ "<strong>Numbers in brackets:</strong> Lasers, Mirrors and Diamonds on the level."
					+ "</p>"
					+ "<p>"
						+ "<strong>Star at the end:</strong> level completed."
					+ "</p>"
				+ "</html>");
		ToolTipManager tooltipManager = ToolTipManager.sharedInstance();
		tooltipManager.setInitialDelay(0);
		tooltipManager.setDismissDelay(15_000);
		
		// PENDING: v0.2: Editor
		editButton.setToolTipText("Not yet supported!");
		editButton.setEnabled(false);
		
		// listaelemek módosíthatóvá tétele
		levelsListItems = new DefaultListModel<>();
		levelsList.setModel(levelsListItems);
		
		// listaelemek legyártása
		List<DB.LevelInfo> levelInfos = DB.loadLevelInfos();
		if(levelInfos != null){
			for (DB.LevelInfo levelInfo : levelInfos) {
				levelsListItems.addElement(new ListItem(levelInfo.name, String.format("%s%s (%s, %s, %s)", 
						levelInfo.completed ? "*" : "" ,
						levelInfo.name,
						levelInfo.laserCount, 
						levelInfo.mirrorCount, 
						levelInfo.diamondCount)));
			}
		}
		levelsListItems.add(0, new ListItem(null, Settings.EMPTY_LIST_ITEM_STRING));
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
        levelsPanel = new javax.swing.JPanel();
        listTitleLabel = new javax.swing.JLabel();
        levelsListScrolPane = new javax.swing.JScrollPane();
        levelsList = new javax.swing.JList();
        editButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        exitGameButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(227, 227, 227));
        setPreferredSize(new java.awt.Dimension(224, 650));

        titleLabel.setBackground(new java.awt.Color(102, 102, 102));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Main menu");

        levelsPanel.setOpaque(false);

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

        javax.swing.GroupLayout levelsPanelLayout = new javax.swing.GroupLayout(levelsPanel);
        levelsPanel.setLayout(levelsPanelLayout);
        levelsPanelLayout.setHorizontalGroup(
            levelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(levelsListScrolPane)
            .addComponent(listTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        levelsPanelLayout.setVerticalGroup(
            levelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(levelsPanelLayout.createSequentialGroup()
                .addComponent(listTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(levelsListScrolPane, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
        );

        editButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        playButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        exitGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exitGameButton.setForeground(new java.awt.Color(155, 25, 25));
        exitGameButton.setText("Exit Game");
        exitGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(levelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exitGameButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addComponent(levelsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                .addComponent(exitGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * A kilépés gombot megnyomjuk: kilép a program.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void exitGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitGameButtonActionPerformed
        Controller.exitGame();
    }//GEN-LAST:event_exitGameButtonActionPerformed

	/**
	 * A Play gombot megnyomjuk: betöltia  kijelölt pályát a játék.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
		Controller.loadLevel( ((ListItem)levelsList.getSelectedValue()).getName() );
    }//GEN-LAST:event_playButtonActionPerformed

	/**
	 * Betölti a pályát szerkesztésre vagy csak átlép a szerkezstő menübe.
	 * 
	 * <strong>Még nincs implementálva a szerkesztő.</strong>
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // PENDING: Edit button (v0.2)
    }//GEN-LAST:event_editButtonActionPerformed

	/**
	 * A listában kijelöltünk valamit: a Play gomb elszürkülhet, 
	 * mert az új (szerkesztésnek fenntartott) üres páylát nem tölthetjük be.
	 * 
	 * @param evt A kiváltó esemény.
	 */
    private void levelsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_levelsListValueChanged
		JList list = (JList)evt.getSource();
		String value = ((ListItem)list.getSelectedValue()).getName();
		if(value == null){
			playButton.setEnabled(false);
		} else {
			playButton.setEnabled(true);
		}
    }//GEN-LAST:event_levelsListValueChanged

	/**
	 * A listaelemek, melyeket a lista létezésekor is tudunk szerkeszteni.
	 */
	DefaultListModel<ListItem> levelsListItems;
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JButton exitGameButton;
    private javax.swing.JList levelsList;
    private javax.swing.JScrollPane levelsListScrolPane;
    private javax.swing.JPanel levelsPanel;
    private javax.swing.JLabel listTitleLabel;
    private javax.swing.JButton playButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
