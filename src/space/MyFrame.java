package space;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MyFrame extends JFrame implements ActionListener {
    // button for resetting the game
    JButton resetButton;
    // manager object
    Component gm;
    
    // constructor sets up the JFrame and adds components
    MyFrame() {
    	Manager gm = new Manager();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gm); 
        
        this.pack();
        this.setLocationRelativeTo(null);
        // create button to play again
        resetButton = new JButton("Play Again!");
        resetButton.setSize(100, 50);
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        gm.add(resetButton);
        this.setVisible(true);
    }
    
	// main method
    public static void main(String[] args) {
        new space.MyFrame();
    }
    
	// ActionEvent e that checks if the button is pressed
	public void actionPerformed(ActionEvent e) {
		// if pressed reset game
		if (e.getSource() == resetButton) {
			this.remove(gm);
			gm = new Manager();
			this.add(gm);
			gm.setFocusable(true);
			//gm.add(resetButton);
		}
	}
}