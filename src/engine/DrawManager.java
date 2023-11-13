package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.lang.Integer;

import screen.Screen;
import entity.Entity;
import entity.Ship;
import screen.SettingScreen;

/**
 * Manages screen drawing.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class DrawManager {

	/** Singleton instance of the class. */
	private static DrawManager instance;
	/** Current frame. */
	private static Frame frame;
	/** FileManager instance. */
	private static FileManager fileManager;
	/** Application logger. */
	private static Logger logger;
	/** Graphics context. */
	private static Graphics graphics;
	/** Buffer Graphics. */
	private static Graphics backBufferGraphics;
	/** Buffer image. */
	private static BufferedImage backBuffer;
	/** Normal sized font. */
	private static Font fontRegular;
	/** Normal sized font properties. */
	private static FontMetrics fontRegularMetrics;
	/** Big sized font. */
	private static Font fontBig;
	/** Big sized font properties. */
	private static FontMetrics fontBigMetrics;

	/** Sprite types mapped to their images. */
	private static Map<SpriteType, boolean[][]> spriteMap;

	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		Ship,
		/** Destroyed player ship. */
		ShipDestroyed,
		/** Player bullet. */
		Bullet,
		/** Enemy bullet. */
		EnemyBullet,
		/** First enemy ship - first form. */
		EnemyShipA1,
		/** First enemy ship - second form. */
		EnemyShipA2,
		/** Second enemy ship - first form. */
		EnemyShipB1,
		/** Second enemy ship - second form. */
		EnemyShipB2,
		/** Third enemy ship - first form. */
		EnemyShipC1,
		/** Third enemy ship - second form. */
		EnemyShipC2,
		/** Bonus ship. */
		EnemyShipSpecial,
		/** Destroyed enemy ship. */
		Explosion
	};

	/**
	 * Private constructor.
	 */
	private DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");

		try {
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();

			spriteMap.put(SpriteType.Ship, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);

			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontRegular = fileManager.loadFont(14f);
			fontBig = fileManager.loadFont(24f);
			logger.info("Finished loading the fonts.");

		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}
	}

	/**
	 * Returns shared instance of DrawManager.
	 *
	 * @return Shared instance of DrawManager.
	 */
	protected static DrawManager getInstance() {
		if (instance == null)
			instance = new DrawManager();
		return instance;
	}

	/**
	 * Sets the frame to draw the image on.
	 *
	 * @param currentFrame
	 *            Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
	}

	/**
	 * First part of the drawing process. Initialize buffers, draws the
	 * background and prepares the images.
	 *
	 * @param screen
	 *            Screen to draw in.
	 */
	public void initDrawing(final Screen screen) {
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		graphics = frame.getGraphics();
		backBufferGraphics = backBuffer.getGraphics();

		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics
				.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
		fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);

		// drawBorders(screen);
		// drawGrid(screen);
	}

	/**
	 * Draws the completed drawing on screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * Draws an entity, using the appropriate image.
	 *
	 * @param entity
	 *            Entity to be drawn.
	 * @param positionX
	 *            Coordinates for the left side of the image.
	 * @param positionY
	 *            Coordinates for the upper side of the image.
	 */
	public void drawEntity(final Entity entity, final int positionX,
			final int positionY) {
		boolean[][] image = spriteMap.get(entity.getSpriteType());

		backBufferGraphics.setColor(entity.getColor());
		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j])
					backBufferGraphics.drawRect(positionX + i * 2, positionY
							+ j * 2, 1, 1);
	}

	/**
	 * For debugging purpouses, draws the canvas borders.
	 *
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
		backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
		backBufferGraphics.drawLine(screen.getWidth() - 1, 0,
				screen.getWidth() - 1, screen.getHeight() - 1);
		backBufferGraphics.drawLine(0, screen.getHeight() - 1,
				screen.getWidth() - 1, screen.getHeight() - 1);
	}

	/**
	 * For debugging purpouses, draws a grid over the canvas.
	 *
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawGrid(final Screen screen) {
		backBufferGraphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < screen.getHeight() - 1; i += 2)
			backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
		for (int j = 0; j < screen.getWidth() - 1; j += 2)
			backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
	}

	/**
	 * Draws current score on screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param score
	 *            Current score.
	 */
	public void drawScore(final Screen screen, final int score) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String scoreString = String.format("%04d", score);
		backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
	}

	/**
	 * Draws number of remaining lives on screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param lives
	 *            Current lives.
	 */
	public void drawLives(final Screen screen, final int lives) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
		Ship dummyShip = new Ship(0, 0);
		for (int i = 0; i < lives; i++)
			drawEntity(dummyShip, 40 + 35 * i, 10);
	}

	/**
	 * Draws a thick line from side to side of the screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param positionY
	 *            Y coordinate of the line.
	 */
	public void drawHorizontalLine(final Screen screen, final int positionY) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
		backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
				positionY + 1);
	}

	/**
	 * Draws game title.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "Invaders";
		String instructionsString =
				"select with w+s / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() /2 - fontRegularMetrics.getHeight()*3/2-fontRegularMetrics.getHeight()*2);

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, titleString, screen.getHeight() / 3-fontRegularMetrics.getHeight()*2);
	}

	/**
	 * Draws main menu.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param option
	 *            Option selected.
	 */
	public void drawMenu(final Screen screen, final int option) {
		String playString = "Play";
		String highScoresString = "High scores";
		String shopString = "Shop";
		String settingString = "Setting";
		String exitString = "exit";
		String achievementString = "Achievements";

		if (option == 2)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2 -fontRegularMetrics.getHeight()*3-fontRegularMetrics.getHeight()*2);
		if (option == 3)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, highScoresString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight()-fontRegularMetrics.getHeight()*2-fontRegularMetrics.getHeight()*2);

		if (option == 4)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, shopString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 3-fontRegularMetrics.getHeight()*2-fontRegularMetrics.getHeight()*2);

		if (option == 5)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, settingString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 3-fontRegularMetrics.getHeight()*2);
		if (option == 6)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, achievementString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 5-fontRegularMetrics.getHeight()*2);
		if (option == 0)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, exitString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 7-fontRegularMetrics.getHeight()*2);
	}

	/**
	 * Draws game results.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param score
	 *            Score obtained.
	 * @param livesRemaining
	 *            Lives remaining when finished.
	 * @param shipsDestroyed
	 *            Total ships destroyed.
	 * @param accuracy
	 *            Total accuracy.
	 * @param isNewRecord
	 *            If the score is a new high score.
	 */
	public void drawResults(final Screen screen, final int score,
			final int livesRemaining, final int shipsDestroyed,
			final float accuracy, final boolean isNewRecord) {
		String scoreString = String.format("score %04d", score);
		String livesRemainingString = "lives remaining " + livesRemaining;
		String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
		String accuracyString = String
				.format("accuracy %.2f%%", accuracy * 100);

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, scoreString, screen.getHeight()
				/ height);
		drawCenteredRegularString(screen, livesRemainingString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 2);
		drawCenteredRegularString(screen, shipsDestroyedString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 4);
		drawCenteredRegularString(screen, accuracyString, screen.getHeight()
				/ height + fontRegularMetrics.getHeight() * 6);
	}

	/**
	 * Draws interactive characters for name input.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param name
	 *            Current name selected.
	 * @param nameCharSelected
	 *            Current character selected for modification.
	 */
	public void drawNameInput(final Screen screen, final char[] name,
			final int nameCharSelected) {
		String newRecordString = "New Record!";
		String introduceNameString = "Introduce name:";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredRegularString(screen, newRecordString, screen.getHeight()
				/ 4 + fontRegularMetrics.getHeight() * 10);
		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, introduceNameString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 12);

		// 3 letters name.
		int positionX = screen.getWidth()
				/ 2
				- (fontRegularMetrics.getWidths()[name[0]]
						+ fontRegularMetrics.getWidths()[name[1]]
						+ fontRegularMetrics.getWidths()[name[2]]
								+ fontRegularMetrics.getWidths()[' ']) / 2;

		for (int i = 0; i < 3; i++) {
			if (i == nameCharSelected)
				backBufferGraphics.setColor(Color.GREEN);
			else
				backBufferGraphics.setColor(Color.WHITE);

			positionX += fontRegularMetrics.getWidths()[name[i]] / 2;
			positionX = i == 0 ? positionX
					: positionX
							+ (fontRegularMetrics.getWidths()[name[i - 1]]
									+ fontRegularMetrics.getWidths()[' ']) / 2;

			backBufferGraphics.drawString(Character.toString(name[i]),
					positionX,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight()
							* 14);
		}
	}

	/**
	 * Draws basic content of game over screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param acceptsInput
	 *            If the screen accepts input.
	 * @param isNewRecord
	 *            If the score is a new high score.
	 */
	public void drawGameOver(final Screen screen, final boolean acceptsInput,
			final boolean isNewRecord) {
		String gameOverString = "Game Over";
		String continueOrExitString =
				"Press Space to play again, Escape to exit";

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws high score screen title and instructions.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawHighScoreMenu(final Screen screen) {
		String highScoreString = "High Scores";
		String instructionsString = "Press Space to return";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5);
	}

	/**
	 * Draws high scores.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param highScores
	 *            List of high scores.
	 */
	public void drawHighScores(final Screen screen,
			final List<Score> highScores) {
		backBufferGraphics.setColor(Color.WHITE);
		int i = 0;
		String scoreString = "";

		for (Score score : highScores) {
			scoreString = String.format("%s        %04d", score.getName(),
					score.getScore());
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
		}
	}

	/**
	 * Draws a centered string on regular font.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public void drawCenteredRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontRegularMetrics.stringWidth(string) / 2, height);
	}
	/**
	 * Draws a leftside string on regular font.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public void drawLeftRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, 0, height);
	}

	/**
	 * Draws a centered string on big font.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public void drawCenteredBigString(final Screen screen, final String string,
			final int height) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontBigMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Countdown to game start.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param level
	 *            Game difficulty level.
	 * @param number
	 *            Countdown number.
	 * @param bonusLife
	 *            Checks if a bonus life is received.
	 */
	public void drawCountDown(final Screen screen, final int level,
			final int number, final boolean bonusLife) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);
		if (number >= 4)
			if (!bonusLife) {
				drawCenteredBigString(screen, "Level " + level,
						screen.getHeight() / 2
						+ fontBigMetrics.getHeight() / 3);
			} else {
				drawCenteredBigString(screen, "Level " + level
						+ " - Bonus life!",
						screen.getHeight() / 2
						+ fontBigMetrics.getHeight() / 3);
			}
		else if (number != 0)
			drawCenteredBigString(screen, Integer.toString(number),
					screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
		else
			drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
					+ fontBigMetrics.getHeight() / 3);
	}
	public void drawOneFifthRegularString(final Screen screen,
										  final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 5
				- fontRegularMetrics.stringWidth(string) / 2 , height);
	}

	public void drawSevenTenthRegularString(final Screen screen,
											final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 10 * 7
				- fontRegularMetrics.stringWidth(string) / 2 , height);
	}

	public void drawStar(final Screen screen,
						 final String string, final int height){
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString("*", screen.getWidth() / 5
				- fontRegularMetrics.stringWidth(string) / 2 -16, height);

	}

	public void drawSetting(final Screen screen, final int option, final boolean selected){
		String settingString = "Setting";
		String instructionsString1 = "Move with UP, DOWN / Select with RIGHT arrow";
		String instructionsString2 = "Press Space to return";

		String volumeString = "Volume";
		String bgmString = "BGM";
		String keysString1 = "1P Keys";
		String keysString2 = "2P Keys";


		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, settingString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString1,
				screen.getHeight() / 5 - fontRegularMetrics.getHeight() / 2);
		drawCenteredRegularString(screen, instructionsString2,
				screen.getHeight() / 5 + fontRegularMetrics.getHeight() / 2);


		if (option == 0) {
			backBufferGraphics.setColor(Color.GREEN);
			if(selected) drawStar(screen, volumeString,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 2);
		}
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawOneFifthRegularString(screen, volumeString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 2);

		if (option == 1) {
			backBufferGraphics.setColor(Color.GREEN);
			if(selected) drawStar(screen, bgmString,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 4);
		}
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawOneFifthRegularString(screen, bgmString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 4);

		if (option == 2) {
			backBufferGraphics.setColor(Color.GREEN);
			if(selected) drawStar(screen, keysString1,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 6);;
		}
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawOneFifthRegularString(screen, keysString1,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 6);

		if (option == 3) {
			backBufferGraphics.setColor(Color.GREEN);
			if(selected) drawStar(screen, keysString2,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 8);;
		}
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawOneFifthRegularString(screen, keysString2,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 8);

		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(screen.getWidth()/5*2 -1,screen.getHeight() / 4,
				screen.getWidth()/5*2 -1,screen.getHeight() / 10 * 9);
		backBufferGraphics.drawLine(screen.getWidth()/5*2,screen.getHeight() / 4,
				screen.getWidth()/5*2,screen.getHeight() / 10 * 9);

	}


	public void drawSettingDetail(final Screen screen, final int option, final boolean selected,
								  int volume, boolean bgmOn, int keyNum) {
		String[] keyString = SettingScreen.getKeySettingString();
		if(option == 0 || option == 1){
			if (option == 0 && selected)
				backBufferGraphics.setColor(Color.GREEN);
			else
				backBufferGraphics.setColor(Color.WHITE);

			backBufferGraphics.drawRect(screen.getWidth() / 2, screen.getHeight() / 4 + fontRegularMetrics.getHeight() / 8 * 12,
					screen.getWidth() / 4, fontRegularMetrics.getHeight());
			backBufferGraphics.fillRect(screen.getWidth() / 2, screen.getHeight() / 4 + fontRegularMetrics.getHeight() / 8 * 12,
					screen.getWidth() / 4 * volume / 100, fontRegularMetrics.getHeight());
			backBufferGraphics.drawString(Integer.toString(volume), screen.getWidth() / 4 * 3
					+ fontRegularMetrics.stringWidth("A") * 2, screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 2);

			if (option == 1 && selected)
				backBufferGraphics.setColor(Color.GREEN);
			else
				backBufferGraphics.setColor(Color.WHITE);

			if(bgmOn) drawSevenTenthRegularString(screen,"ON",screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 4);
			else drawSevenTenthRegularString(screen,"OFF",screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 4);
		}

		if (option == 2){
			drawKeyString(screen,"LEFT", "RIGHT", "ATTACK", "BURST 1", "BURST 2","RELOAD","BOOSTER","ITEM",6);
			drawKeyString(screen,keyString[0],keyString[1],keyString[2],keyString[3],keyString[4],keyString[5],keyString[6],keyString[7], 8);
			if(selected){
				drawGreenKeyString(screen, keyNum);
			}

		}

		if (option == 3){
			drawKeyString(screen,"LEFT", "RIGHT", "ATTACK", "BURST 1", "BURST 2","RELOAD","BOOSTER","ITEM",6);
			drawKeyString(screen,keyString[8],keyString[9],keyString[10],keyString[11],keyString[12],keyString[13],keyString[14],keyString[15], 8);
			if(selected){
				drawGreenKeyString(screen, keyNum);
			}

		}



	}
	private void drawKeyString(Screen screen, String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,int num){
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(s1, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s1) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight());
		backBufferGraphics.drawString(s2, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s2) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 3);
		backBufferGraphics.drawString(s3, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s3) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 5);
		backBufferGraphics.drawString(s4, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s4) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 7);
		backBufferGraphics.drawString(s5, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s5) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 9);
		backBufferGraphics.drawString(s6, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s6) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 11);
		backBufferGraphics.drawString(s7, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s7) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 13);
		backBufferGraphics.drawString(s8, screen.getWidth() / 10 * num
				- fontRegularMetrics.stringWidth(s8) / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 15);
	}

	private  void drawGreenKeyString(Screen screen,int keyNum){
		backBufferGraphics.setFont(fontRegular);
		if(keyNum == 0){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("LEFT", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("LEFT") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight());
		}
		if(keyNum == 1){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("RIGHT", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("RIGHT") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 3);
		}
		if(keyNum == 2){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("ATTACK", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("ATTACK") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 5);
		}
		if(keyNum == 3){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("BURST 1", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("BURST 1") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 7);
		}
		if(keyNum == 4){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("BURST 2", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("BURST 2") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 9);
		}
		if(keyNum == 5){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("RELOAD", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("RELOAD") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 11);
		}
		if(keyNum == 6){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("BOOSTER", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("BOOSTER") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 13);
		}if(keyNum == 7){
			backBufferGraphics.setColor(Color.GREEN);
			backBufferGraphics.drawString("ITEM", screen.getWidth() / 10 * 6
					- fontRegularMetrics.stringWidth("ITEM") / 2 , screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 15);
		}
	}
	public void drawSelect2PModeAndSkillModeScreen(Screen screen, int gameMode, boolean skillModeOn, boolean nextItem)
	{
		String selectString = "Select Mode";
		String instructionsString =
				"select with a+d / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, selectString, screen.getHeight() / 8);
		backBufferGraphics.drawString("Player", screen.getWidth() / 5
				- fontRegularMetrics.stringWidth("Player") / 2 , screen.getHeight() / 8 * 3);
		backBufferGraphics.drawString("Skill Mode", screen.getWidth() / 5
				- fontRegularMetrics.stringWidth("Player") / 2 , screen.getHeight() / 8 * 5);

		if(gameMode == 1) backBufferGraphics.setColor(Color.GREEN);
		else backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("1P", screen.getWidth() / 10 * 6
				- fontRegularMetrics.stringWidth("1P") / 2 , screen.getHeight()
				/ 8 * 3 + fontRegularMetrics.getHeight() * 2);

		if(gameMode == 2) backBufferGraphics.setColor(Color.GREEN);
		else backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("2P", screen.getWidth() / 10 * 8
				- fontRegularMetrics.stringWidth("2P") / 2 , screen.getHeight()
				/ 8 * 3 + fontRegularMetrics.getHeight() * 2);

		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("ON", screen.getWidth() / 10 * 6
				- fontRegularMetrics.stringWidth("ON") / 2 , screen.getHeight()
				/ 8 * 5 + fontRegularMetrics.getHeight() * 2);
		backBufferGraphics.drawString("OFF", screen.getWidth() / 10 * 8
				- fontRegularMetrics.stringWidth("OFF") / 2 , screen.getHeight()
				/ 8 * 5 + fontRegularMetrics.getHeight() * 2);
		if(nextItem){
			backBufferGraphics.setColor(Color.GREEN);
			if(skillModeOn) backBufferGraphics.drawString("ON", screen.getWidth() / 10 * 6
						- fontRegularMetrics.stringWidth("ON") / 2 , screen.getHeight()
						/ 8 * 5 + fontRegularMetrics.getHeight() * 2);
			else backBufferGraphics.drawString("OFF", screen.getWidth() / 10 * 8
					- fontRegularMetrics.stringWidth("OFF") / 2 , screen.getHeight()
					/ 8 * 5 + fontRegularMetrics.getHeight() * 2);
		}
	}

}
