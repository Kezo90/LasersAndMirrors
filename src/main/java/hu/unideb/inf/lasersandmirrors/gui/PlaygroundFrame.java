
package hu.unideb.inf.lasersandmirrors.gui;

import hu.unideb.inf.lasersandmirrors.Controller;
import hu.unideb.inf.lasersandmirrors.Game;
import hu.unideb.inf.lasersandmirrors.Settings;
import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 *
 * @author Kerekes Zoltán
 */
public class PlaygroundFrame extends javax.swing.JFrame {
	
	/**
	 * Creates new form PlaygroundFrame.
	 */
	public PlaygroundFrame() {
		initComponents();
		setSize(Settings.WINDOW_SIZE);
		setMenu(new LevelLoaderPanel());
		setLocationRelativeTo(null);
		Controller.setActivePanel(playgroundPanel);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lasers & Mirrors, by: Zoltán Kerekes");
        setBackground(new java.awt.Color(153, 153, 153));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setName("playgroundFrame"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                PlaygroundFrame.this.windowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Az ablak bezárásakor hívódik meg.
	 * 
	 * @param evt A kiváltott esemény leírója.
	 */
    private void windowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosing
        Game.exitGame();
    }//GEN-LAST:event_windowClosing
	
	public JPanel getMenu(){
		return this.menuPanel;
	}
	
	public final void setMenu(JPanel menu){
		// <editor-fold defaultstate="collapsed" desc="GroupLayout-os kódom (kikommentelve)">  
		/*
		remove(menuPanel);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		contentPane.setMaximumSize(Settings.WINDOW_SIZE);
		
		JTextField textField = new JTextField("asd");
		textField.setSize(100, 50);
		JButton button = new JButton("butt");
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(1, 1000));
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(textField)
					.addComponent(separator)
				)
				.addComponent(button)
		);
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(textField)
					.addComponent(separator)
				)
				.addComponent(button)
		);
		*/
		// </editor-fold>
		
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		contentPane.setLayout(layout);
		contentPane.setMaximumSize(Settings.WINDOW_SIZE);
		
		add(playgroundPanel);
		add(menu);
		
		pack();
	}
	
	private JPanel playgroundPanel = new PlaygroundPanel();
	private JPanel menuPanel = null;
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
