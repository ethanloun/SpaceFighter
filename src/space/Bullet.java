package space;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;

public class Bullet {

	// x and y cords for bullet
	public double x;
	public double y;
	// x and y velocity for bullet
	public double velX = 5;
	public double velY = 5;
	// degree of bullet
	public double Deg = 0;
	// dimensions of bullet
	public double bulletWidth = 80;
	public double bulletHeight = 48;

	// image we use for bullet
	Image bullet = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
	// resized image of bullet
	Image newImage = bullet.getScaledInstance((int) bulletWidth, (int) bulletHeight, Image.SCALE_DEFAULT);
	// label we use to draw bullet
	JLabel label;
	// to instantiate the variables of the bullet
	public Bullet(double x, double y, double Deg) {
		this.x = x;
		this.y = y;
		this.Deg = Deg;
	}
	// to draw the bullet
	public void draw(Graphics g) {
		g.drawImage(newImage, (int) x, (int) y, null);
	}
	// to make sure the bullets fire in the same way the planes are facing
	public void goingTheSameWay() {
		x += velX * Math.cos(Math.toRadians(Deg));
		y += velY * Math.sin(Math.toRadians(Deg));

	}
	// to get x midpoint of the bullet image
	public double getMidX() {
		return (newImage.getWidth(label) / 2) + x;
	}
	// to get y midpoint of the bullet image
	public double getMidY() {
		return (newImage.getHeight(label) / 2) + y;
	}
	// to get x cord of the bullet
	public double getX() {
		return x;
	}
	// to get y cord of the bullet
	public double getY() {
		return y;
	}
	// to get the bullet image
	public Image getImage() {
		return newImage;
	}
	// to get the degree of bullet
	public double getDeg() {
		return Deg;
	}
	// to set the bounds of the bullet hitbox
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) bulletWidth, (int) bulletHeight);
	}

}