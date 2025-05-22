package space;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Missile extends Bullet {
	// missile does 2 damage instead of 1
    private int damage = 2; 

	Image missile = new ImageIcon(this.getClass().getResource("/m.png")).getImage();
	// resized image of bullet
	Image newMissileImage = missile.getScaledInstance((int) bulletWidth, (int) bulletHeight, Image.SCALE_DEFAULT);
	// label we use to draw bullet
	JLabel label;
	
    public Missile(double x, double y, double deg) {
    	// share common properties
        super(x + Math.cos(Math.toRadians(deg)) * 20,  
              y + Math.sin(Math.toRadians(deg)) * 20,  
              deg); 
        // change bullet image to missile
        this.bullet = missile;
        this.newImage = newMissileImage;
        this.damage = 2;
    }
    
    public void goingTheSameWay() {
        x += velX * Math.cos(Math.toRadians(getDeg()));
        y += velY * Math.sin(Math.toRadians(getDeg())); 
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(newMissileImage, (int) x, (int) y, null);
    }
}