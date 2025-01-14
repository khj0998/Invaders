package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	private int y_speed;
	private int x_speed;
	/**
	 * Entity that shot a bullet
	 * 0 for enemy, 1 for first-player, 2 for second-player
	 */
	private int shooter;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed, final int shooter) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);
		this.y_speed = speed;
		this.x_speed = 0;
		this.shooter = shooter;
		setSprite();
	}

	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {
		if (y_speed < 0)
			this.spriteType = SpriteType.Bullet;
		else
			this.spriteType = SpriteType.EnemyBullet;
	}
	/**
	 * Sets correct sprite for the Bigger bullet, based on speed.
	 */
	public final void setBiggerSprite() {
		if (y_speed < 0)
			this.spriteType = SpriteType.BiggerBullet;
		else
			this.spriteType = SpriteType.BiggerEnemyBullet;
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		this.positionY += this.y_speed;
		this.positionX += this.x_speed;
	}

	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *            New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.y_speed = speed;
	}
	public final void setXspeed(final int xSpeed){this.x_speed = xSpeed;}
	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return this.y_speed;
	}

	public final int getShooter() {
		return this.shooter;
	}
}
