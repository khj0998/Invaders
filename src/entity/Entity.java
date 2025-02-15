package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a generic game entity.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Entity {

	/** Position in the x-axis of the upper left corner of the entity. */
	protected int positionX;
	/** Position in the y-axis of the upper left corner of the entity. */
	protected int positionY;
	/** Width of the entity. */
	protected int width;
	/** Height of the entity. */
	protected int height;
	/** Original color of the entity. */
	private Color origin_color;
	/** Color of the entity. */
	private Color color;
	/** Sprite type assigned to the entity. */
	protected SpriteType spriteType;
	/** 겜블-빠칭코에서 엔티티의 스프라이트가 확정됐는지 확인 */
	private boolean isDecideSprite;
	/** 겜블-빠칭코에서 엔티티의 스프라이트 번호*/
	private int spriteNumber;

	/**
	 * Constructor, establishes the entity's generic properties.
	 * 
	 * @param positionX
	 *            Initial position of the entity in the X axis.
	 * @param positionY
	 *            Initial position of the entity in the Y axis.
	 * @param width
	 *            Width of the entity.
	 * @param height
	 *            Height of the entity.
	 * @param color
	 *            Color of the entity.
	 */
	public Entity(final int positionX, final int positionY, final int width,
			final int height, final Color color) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.origin_color = color;
		this.color = color;
	}

	public Entity(final int positionX, final int positionY, final int width,
				  final int height, final Color color, boolean isDecideSprite, int spriteNumber) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.origin_color = color;
		this.color = color;
		this.isDecideSprite = isDecideSprite;
		this.spriteNumber = spriteNumber;
	}

	/**
	 * Getter for the color of the entity.
	 * 
	 * @return Color of the entity, used when drawing it.
	 */
	public final Color getColor() {
		return color;
	}

	/**
	 * Setter for the color of the entity.
	 *
	 * @param c
	 *            New color of the entity.
	 */
	public final void setColor(Color c) {this.color = c;}

	/**
	 * Re-setter for the color of the entity.

	 */
	public final void resetColor() {this.color = this.origin_color;}

	/**
	 * Getter for the X axis position of the entity.
	 * 
	 * @return Position of the entity in the X axis.
	 */
	public final int getPositionX() {
		return this.positionX;
	}

	/**
	 * Getter for the Y axis position of the entity.
	 * 
	 * @return Position of the entity in the Y axis.
	 */
	public final int getPositionY() {
		return this.positionY;
	}

	/**
	 * Setter for the X axis position of the entity.
	 * 
	 * @param positionX
	 *            New position of the entity in the X axis.
	 */
	public final void setPositionX(final int positionX) {
		this.positionX = positionX;
	}

	/**
	 * Setter for the Y axis position of the entity.
	 * 
	 * @param positionY
	 *            New position of the entity in the Y axis.
	 */
	public final void setPositionY(final int positionY) {
		this.positionY = positionY;
	}

	/**
	 * Getter for the sprite that the entity will be drawn as.
	 * 
	 * @return Sprite corresponding to the entity.
	 */
	public final SpriteType getSpriteType() {
		return this.spriteType;
	}

	/**
	 * Getter for the width of the image associated to the entity.
	 * 
	 * @return Width of the entity.
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * Getter for the height of the image associated to the entity.
	 * 
	 * @return Height of the entity.
	 */
	public final int getHeight() {
		return this.height;
	}
	public final void changeColor(Color color) {
		this.color = color;
	}
	public void changeEntitySprite(SpriteType sprite){this.spriteType = sprite;}
	public boolean isDecideSprite(){return this.isDecideSprite;}
	public void setDecideSprite(boolean isDecideSprite){this.isDecideSprite = isDecideSprite;}
	public int getSpriteNumber(){return this.spriteNumber;}
	public void setSpriteNumber(int spriteNumber){this.spriteNumber = spriteNumber;}

}
