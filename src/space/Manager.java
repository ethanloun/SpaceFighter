package space;


import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Manager extends JPanel implements ActionListener {
	// dimensions for the screen
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	// timer to control how fast the planes move with delay
	private Timer timer;
	// players 1 and 2
	private Player p1;
	private Player p2;
	// player Health points
	private int p1HP = 5;
	private int p2HP = 5;
	// how much damage the players do
	private int p1Dmg = 1;
	private int p2Dmg = 1;
	// Jlabel for planes and the background image
	JLabel label, background;
	// background image
	Image space = new ImageIcon(this.getClass().getResource("/space2.jpg")).getImage();
	// modify the background image size to fit the screen
	Image newSpace = space.getScaledInstance((int) size.getWidth(), (int) size.getHeight(), Image.SCALE_DEFAULT);

	// manage starting points, health, timer, flowlayout, keylistener
	public Manager() {
		p1 = new Player(900, 500, 2);
		p2 = new Player(0, 0, 1);
		label = new JLabel("Player 1 HP: " + p1HP + "     ||      Player 2 HP: " + p2HP);

		this.setPreferredSize(new Dimension(600, 600));
		// this.setBackground(Color.BLACK);
		timer = new Timer(1, this);
		timer.start();
		label.setFont(new Font("Space Grotesk", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(label, BorderLayout.PAGE_START);
		addKeyListener(p1);
		addKeyListener(p2);
		setFocusable(true);
	}
	
	// to paint everything in the game
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(newSpace, 0, 0, null);
		// draw players 1 and 2
		p1.draw(g);
		p2.draw(g);
		// create threads for player 1 and 2
		Thread player1 = new Thread(p1);
		Thread player2 = new Thread(p2);
		// start threads for player 1 and 2
		player1.start();
		player2.start();
		// check after drawing
		checkCollision();
	}
	
	// to check if the bullets have hit the players
    public void checkCollision() {
        // create hitbox for player 1
        Rectangle player1 = new Rectangle(
        	// centered x position
            (int)p1.getMidX() - 50, 
            // centered y position, width and height
            (int)p1.getMidY() - 50,100,100
        );
        // create hitbox for player 2
        Rectangle player2 = new Rectangle(
            // centered x position
            (int)p2.getMidX() - 50, 
            // centered y position, width and height
            (int)p2.getMidY() - 50,100,100
        );
        
        // check p2's bullets hitting p1
        for (int i = p2.getBullets().size() - 1; i >= 0; i--) {
            Bullet bullet = p2.getBullets().get(i);
            // create hitbox for bullet
            Rectangle bulletBounds = new Rectangle(
            	// centered x position
                (int)bullet.getMidX() - 40,
                // centered y position, width and height
                (int)bullet.getMidY() - 24,80,48
            );
            if (player1.intersects(bulletBounds)) {
            	// remove bullet if hits
                p2.getBullets().remove(i);
                // remove health
                p1HP -= p2Dmg;
                // update player health
                label.setText("Player 1 HP: " + p1HP + "     ||      Player 2 HP: " + p2HP);
            } 
        }
        
        // check p2's missiles hitting p1 (missile damage = 2)
        for (int i = p2.getMissiles().size() - 1; i >= 0; i--) {
            Missile missile = p2.getMissiles().get(i);
            Rectangle missileBounds = new Rectangle(
                (int)missile.getMidX() - 40,
                (int)missile.getMidY() - 24, 80, 48
            );
            if (player1.intersects(missileBounds)) {
                p2.getMissiles().remove(i);
                // missile does 2 damage
                p1HP -= 2; 
                label.setText("Player 1 HP: " + p1HP + "     ||      Player 2 HP: " + p2HP);
            }
        }
        
        // check p1's bullets hitting p2
        for (int i = p1.getBullets().size() - 1; i >= 0; i--) {
            Bullet bullet = p1.getBullets().get(i);
            // create hitbox for bullet
            Rectangle bulletBounds = new Rectangle(
            	// centered x position
                (int)bullet.getMidX() - 40,
                // centered y position, width and height
                (int)bullet.getMidY() - 24,80,48
            );
            
            if (player2.intersects(bulletBounds)) {
            	// remove bullet if hits
                p1.getBullets().remove(i);
                // remove health
                p2HP -= p1Dmg;
                // update player health
                label.setText("Player 1 HP: " + p1HP + "     ||      Player 2 HP: " + p2HP);
            }
        }
        // check p1's missiles hitting p2 
        for (int i = p1.getMissiles().size() - 1; i >= 0; i--) {
            Missile missile = p1.getMissiles().get(i);
            Rectangle missileBounds = new Rectangle(
                (int)missile.getMidX() - 40,
                (int)missile.getMidY() - 24, 80, 48
            );
            if (player2.intersects(missileBounds)) {
                p1.getMissiles().remove(i);
                // missile does 2 damage
                p2HP -= 2; 
                label.setText("Player 1 HP: " + p1HP + "     ||      Player 2 HP: " + p2HP);
            }
        }
        
        // Check if game is over
        if (p1HP <= 0) {
            Winner(2);
            timer.stop();
        } else if (p2HP <= 0) {
            Winner(1);
            timer.stop();
        }
    }
    // display winner message
    public void Winner(int winner) {
        label.setFont(new Font("Space Grotesk", Font.BOLD, 54));
        label.setText("GAME OVER, PLAYER " + winner + " WINS");
    }

    // repaint so that the players can see the planes moving
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
