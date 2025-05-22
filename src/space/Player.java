package space;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player implements Runnable, KeyListener {
	// x and y cords for player
	private double x, y;
	// player id(1 = player 1, 2 = player 2)
	private int id;
	// degree of the player
	private double Deg = 0;
	// the amount of degree change per keyboard input
	private double degree1;
	private double degree2;
	// the speed of the players moving forward
	private double speedX = 3;
	private double speedY = 3;
	// players width and height
	private double playerWidth = 100;
	private double playerHeight = 100;
	// variable to store which key is pressed
	private int key;
	// boolean variables for each key to know that they are pressed(true = pressed, false = released)
	// we need this to get input when there is 2 keys pressed at once
	private boolean left, right, D, A, F, shift, E, slash;
	// arraylist with type bullet to keep track of bullets
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	// arraylist with type missile to keep track of missiles
	private ArrayList<Missile> missile = new ArrayList<Missile>();
	// bullet object to store the values of the bullet arraylist
	private Bullet b;
	// missile object to store the values of the missile arraylist
	private Missile m;
	// long objects to create a cool down for shooting bullets
	private long lastAttack = 0;
	private long lastMissileAttack = 0;
	private long cooldown = 500;
	// Screen dimensions
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    // label object to use when painting the planes
  	JLabel label;
  	// image for plane
  	Image plane;
  	// resized image of plane
  	Image newImage;
    // initialize the player and set its starting point and the players id
    public Player(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        
		// assign the correct image for player 1 and 2
		if (id == 1) {

			plane = new ImageIcon(this.getClass().getResource("/jet1.png")).getImage();
			newImage = plane.getScaledInstance((int) playerWidth, (int) playerHeight, Image.SCALE_DEFAULT);

		} 
		else if (id == 2) {

			plane = new ImageIcon(this.getClass().getResource("/jet2.png")).getImage();
			newImage = plane.getScaledInstance((int) playerWidth, (int) playerHeight, Image.SCALE_DEFAULT);
		}

	}
	// return the mid point of the x cord of the plane
	public double getMidX() {
		return (newImage.getWidth(label) / 2) + x;
	}
	// return the mid point of the x cord of the plane
	public double getMidY() {
		return (newImage.getHeight(label) / 2) + y;
	}
    // to get the bounds of the player rectangle, hit box
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) playerWidth, (int) playerHeight);
    }
	// to draw the players and rotate them
	public void draw(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		//going the same why method to makes plane fly in the way they are facing
		goingTheSameWay();
		//rotate plane
		g2D.rotate(Math.toRadians(Deg), (newImage.getWidth(label) / 2) + x, (newImage.getHeight(label) / 2) + y);
		//draw plane
		g2D.drawImage(newImage, (int) x, (int) y, null);
		//reset the rotation so that the plane is the only thing rotating
		g2D.rotate(-Math.toRadians(Deg), (newImage.getWidth(label) / 2) + x, (newImage.getHeight(label) / 2) + y);
		
		//run a for loop through the bullet arraylist to draw the and make sure they are going the same way
		for (int i = 0; i < bullet.size(); i++) {

			b = bullet.get(i);
			b.goingTheSameWay();
			g2D.rotate(Math.toRadians(b.getDeg()), (newImage.getWidth(label) / 2) + b.getX(),
					(newImage.getHeight(label) / 2) + b.getY());
			g2D.drawImage(b.getImage(), (int) ((newImage.getWidth(label) / 2) + b.getX()),
					(int) ((newImage.getHeight(label) / 2) + b.getY()), null);
			g2D.rotate(-Math.toRadians(b.getDeg()), (newImage.getWidth(label) / 2) + b.getX(),
					(newImage.getHeight(label) / 2) + b.getY());
		}
		
		//run a for loop through the missile arraylist to draw them and make sure they are going the same way
		for (int i = 0; i < missile.size(); i++) {

			m = missile.get(i);
			m.goingTheSameWay();
			g2D.rotate(Math.toRadians(m.getDeg()), (newImage.getWidth(label) / 2) + m.getX(),
					(newImage.getHeight(label) / 2) + m.getY());
			g2D.drawImage(m.getImage(), (int) ((newImage.getWidth(label) / 2) + m.getX()),
					(int) ((newImage.getHeight(label) / 2) + m.getY()), null);
			g2D.rotate(-Math.toRadians(m.getDeg()), (newImage.getWidth(label) / 2) + m.getX(),
					(newImage.getHeight(label) / 2) + m.getY());
		}
	}
    // make the players go the same way the are facing, x = rcos(theta), y = rsin(theta)
	public void goingTheSameWay() {
		x += speedX * Math.cos(Math.toRadians(Deg));
		y += speedY * Math.sin(Math.toRadians(Deg));

	}
	public void shoot() {
		// add a new bullet object to bullet array
		bullet.add(new Bullet(x, y, Deg));
	}
	public void shootMissile() {
		// add a new missile object to missile array
		missile.add(new Missile(x, y, Deg));
	}
	// return the bullet arraylist
	public ArrayList<Bullet> getBullets() {
		return bullet;
	}
	// return the missile arraylist
	public ArrayList<Missile> getMissiles() {
		return missile;
	}
	// detect if any keys are pressed and set the boolean variables to true or false
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        if (key == KeyEvent.VK_D) 
        	D = true;
        if (key == KeyEvent.VK_A) 
        	A = true;
        if (key == KeyEvent.VK_LEFT) 
        	left = true;
        if (key == KeyEvent.VK_RIGHT) 
        	right = true;
        if (key == KeyEvent.VK_F) 
        	F = true;
        if (key == KeyEvent.VK_SHIFT) 
        	shift = true;
        if (key == KeyEvent.VK_E)
        	E = true;
        if (key == KeyEvent.VK_SLASH)
        	slash = true;
        // should move when keys are pressed
        degree1 = 2;
        degree2 = 2; 
    }
    // to detect if any keys are released and set the boolean variables to true or false
    // degree set to 0 to stop moving
    @Override
    public void keyReleased(KeyEvent e) {
        try {
            int key = e.getKeyCode();

            // handle known keys
            if (key == KeyEvent.VK_LEFT) {
                left = false;
                degree1 = 0;
            } else if (key == KeyEvent.VK_RIGHT) {
                right = false;
                degree1 = 0;
            } else if (key == KeyEvent.VK_D) {
                D = false;
                degree2 = 0;
            } else if (key == KeyEvent.VK_A) {
                A = false;
                degree2 = 0;
            } else if (key == KeyEvent.VK_F) {
                F = false;
            } else if (key == KeyEvent.VK_SHIFT) {
                shift = false;
            } else if (key == KeyEvent.VK_E) {
                E = false;
            } else if (key == KeyEvent.VK_SLASH) {
                slash = false;
            } else {
                // throw an exception if invalid key is released
                throw new InvalidKey("Invalid key: " + KeyEvent.getKeyText(key));
            }
        } catch (InvalidKey ex) {
            // handle the exception and print a message
            System.out.println("Error: " + ex.getMessage());
        }
    }
    // run if we create a new thread and start it
	public void run() {
		// if plater id is 2, use arrow keys to move and shift to shoot
		if (id == 2) {
			if (right) {
				Deg += degree1;

			}
			if (left) {
				Deg -= degree1;
			}
			if (shift) {
				long time = System.currentTimeMillis();
				if (time > (lastAttack + cooldown)) {
					shoot();
					lastAttack = time;
				}
			}
			if (slash) {
				if (System.currentTimeMillis() - lastMissileAttack > cooldown) {
					lastMissileAttack = System.currentTimeMillis();
					shootMissile();
				}
			}
		}
		// if plater id is 1, use wasd keys to move and g to shoot
		if (id == 1) {
			if (D) {
				Deg += degree2;
			}
			if (A) {
				Deg -= degree2;
			}
			if (F) {
				long time = System.currentTimeMillis();
				if (time > (lastAttack + cooldown)) {
					shoot();
					lastAttack = time;
				}
			}
			if (E) {
				if (System.currentTimeMillis() - lastMissileAttack > cooldown) {
					lastMissileAttack = System.currentTimeMillis();
					shootMissile();
				}
			}
		}
		// plane cannot go outside of screen
		if (x <= 0)
			x = 0;
		if (x >= (int) size.getWidth() - 100)
			x = (int) size.getWidth() - 100;
		if (y <= 0)
			y = 0;
		if (y >= (int) size.getHeight() - 150)
			y = (int) size.getHeight() - 150;
	}
	// not using
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}