package dipernaa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable {

	private Thread game;
	private Player player;
	private Floor floor;
	private Floor floor2;
	private Floor floor3;
	private Rectangle[] star;
	private List<Floor> floors = new ArrayList<Floor>();

	protected HorizontalMovingBehavior noMoveBehavior = new NoMoveBehavior();
	protected HorizontalMovingBehavior moveLeftBehavior = new MoveLeftBehavior();
	protected HorizontalMovingBehavior moveRightBehavior = new MoveRightBehavior();
	protected VerticalMovingBehavior onGroundBehavior = new OnGroundBehavior();
	protected VerticalMovingBehavior jumpingBehavior = new JumpingBehavior();
	protected VerticalMovingBehavior fallingBehavior = new FallingBehavior();

	private static final int PLAYER_HEIGHT = 40;
	private static final int PLAYER_WIDTH = 20;
	private static final int FLOOR_HEIGHT = 20;
	private static final int FPS = 1000;

	protected int xs = 0;
	protected int ys = 0;
	private int currentStarSize;

	protected boolean playerRunning = false;
	private boolean movingLeft = false;
	private boolean objectsCreated = false;
	private boolean inGame = true;
	private boolean running = true;

	public Panel(gameFrame frame) {
		setBackground(Color.BLACK);
		createObjects();
		frame.addKeyListener(new KeyHandler());
		game = new Thread(this);
		game.start();
	}

	private void createObjects() {
		player = new Player(gameFrame.WIDTH/2 - PLAYER_WIDTH/2, gameFrame.HEIGHT/2 - PLAYER_HEIGHT/2, PLAYER_WIDTH, PLAYER_HEIGHT);
		floor = new Floor(-10, gameFrame.HEIGHT - FLOOR_HEIGHT, gameFrame.WIDTH + 10, FLOOR_HEIGHT);
		floor2 = new Floor(floor.x + floor.width - 20, gameFrame.HEIGHT - 100, 25, 25);
		floor3 = new Floor(floor.x + 50, floor.y - FLOOR_HEIGHT, 75, FLOOR_HEIGHT);
		floors.add(floor);
		floors.add(floor2);
		floors.add(floor3);

		star = new Rectangle[500];
		Random rGen = new Random();
		for(int i=0; i < star.length; i++) {
			currentStarSize = rGen.nextInt(5);
			star[i] = new Rectangle(rGen.nextInt(gameFrame.WIDTH)*2 - gameFrame.WIDTH/2, rGen.nextInt(gameFrame.HEIGHT)*2 - gameFrame.HEIGHT/2, currentStarSize, currentStarSize);
		}

		objectsCreated = true;
		repaint();
	}

	public void updateWorld() {
		int offset = player.x - gameFrame.WIDTH/2 - floor.x;
		floor.setLocation(player.x - gameFrame.WIDTH/2, gameFrame.HEIGHT - FLOOR_HEIGHT);
		for(int i=0; i < star.length; i++) {
			star[i].setLocation((int)(star[i].getX() + offset), (int)star[i].getY());
		}
	}

	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);

		if(objectsCreated == true) {
			updateWorld();
			graphic.setColor(Color.WHITE);
			graphic.fillRect(player.x - xs, player.y - ys, player.width, player.height);
			graphic.setColor(Color.CYAN);
			if(movingLeft) {
				graphic.fillRect(player.x - xs + 2, player.y - ys + 2, 5, 5);
			}else {
				graphic.fillRect(player.x - xs + player.width - 7, player.y - ys + 2, 5, 5);
			}
			graphic.setColor(Color.WHITE);
			graphic.fillRect(floor.x - xs, floor.y - ys, floor.width, floor.height);
			graphic.setColor(Color.RED);
			graphic.fillRect(floor2.x - xs, floor2.y - ys, floor2.width, floor2.height);
			graphic.setColor(Color.WHITE);
			graphic.fillRect(floor3.x - xs, floor3.y - ys, floor3.width, floor3.height);

			for(int i=0; i < star.length; i++) {
				graphic.fill3DRect(star[i].x - xs, star[i].y - ys, star[i].width, star[i].height, false);
			}

			if(inGame == false) {
				graphic.setColor(Color.RED);
				graphic.drawString("PAUSED", 0, 10);
			}
		}
	}

	@Override
	public void run() {
		while(running == true) {
			if(inGame == true) {
				Point bottomLeftPoint = new Point(player.x + 1, player.y + player.height);
				Point bottomRightPoint = new Point(player.x + player.width - 1, player.y + player.height);
				Point leftBottomMovingPoint = new Point(player.x, player.y + player.height - 1);
				Point rightBottomMovingPoint = new Point(player.x + player.width, player.y + player.height - 1);
				Point leftTopMovingPoint = new Point(player.x, player.y + 1);
				Point rightTopMovingPoint = new Point(player.x + player.width, player.y + 1);
				Point topLeftPoint = new Point(player.x + 1, player.y);
				Point topRightPoint = new Point(player.x + player.width - 1, player.y);

				//falling
				if(player.getVerticalMovingBehavior().equals(onGroundBehavior)) {
					for(Floor floorChecker : floors) {
						if(!floorChecker.contains(bottomLeftPoint) || !floorChecker.contains(bottomRightPoint)) {
							player.setVerticalMovingBehavior(fallingBehavior);
						}
					}
				}
				if(!player.getVerticalMovingBehavior().equals(jumpingBehavior)) {
					for(Floor floorChecker : floors) {
						if(floorChecker.contains(bottomLeftPoint) || floorChecker.contains(bottomRightPoint)) {
							player.setVerticalMovingBehavior(onGroundBehavior);
						}
					}
				}

				//jumping
				for(Floor floorChecker : floors) {
					if(floorChecker.contains(topLeftPoint) || floorChecker.contains(topRightPoint)) {
						player.setVerticalMovingBehavior(fallingBehavior);
					}
				}

				//moving left
				for(Floor floorChecker : floors) {
					if(floorChecker.contains(leftBottomMovingPoint) || floorChecker.contains(leftTopMovingPoint)) {
						player.setHorizontalMovingBehavior(noMoveBehavior);
					}
				}
				
				//moving right
				for(Floor floorChecker : floors) {
					if(floorChecker.contains(rightBottomMovingPoint) || floorChecker.contains(rightTopMovingPoint)) {
						player.setHorizontalMovingBehavior(noMoveBehavior);
					}
				}

				player.move(this, player);
				fpsSetter();
				repaint();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void fpsSetter() {
		try {
			game.sleep(FPS/1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private class KeyHandler extends KeyAdapter {

		private static final int KEY_JUMP = KeyEvent.VK_SPACE;
		private static final int KEY_LEFT = KeyEvent.VK_A;
		private static final int KEY_RIGHT = KeyEvent.VK_D;
		private static final int KEY_PAUSE = KeyEvent.VK_P;
		private static final int KEY_SHIFT = KeyEvent.VK_SHIFT;

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KEY_JUMP) {
				if(!player.getVerticalMovingBehavior().equals(fallingBehavior)) {
					player.setVerticalMovingBehavior(jumpingBehavior);
				}
			}
			else if(e.getKeyCode() == KEY_LEFT) {
				player.setHorizontalMovingBehavior(moveLeftBehavior);
				movingLeft = true;
			}
			else if(e.getKeyCode() == KEY_RIGHT) {
				player.setHorizontalMovingBehavior(moveRightBehavior);
				movingLeft = false;
			}
			else if(e.getKeyCode() == KEY_SHIFT) {
				playerRunning = true;
			}
			else if(e.getKeyCode() == KEY_PAUSE) {
				if(inGame == true) {
					inGame = false;
				}
				else {
					inGame = true;
				}
			}
		}

		public void keyReleased(KeyEvent g) {
			if(g.getKeyCode() == KEY_JUMP) {
				player.setVerticalMovingBehavior(fallingBehavior);
			}
			else if(g.getKeyCode() == KEY_LEFT) {
				player.setHorizontalMovingBehavior(noMoveBehavior);
			}
			else if(g.getKeyCode() == KEY_RIGHT) {
				player.setHorizontalMovingBehavior(noMoveBehavior);
			}
			else if(g.getKeyCode() == KEY_SHIFT) {
				playerRunning = false;
			}
		}
	}
}
