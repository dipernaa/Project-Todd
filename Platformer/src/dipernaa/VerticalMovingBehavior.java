package dipernaa;

public abstract class VerticalMovingBehavior {
	
	protected static final int FALLING_SPEED = 1;
	protected static final int JUMPING_COUNT_SPEED = FALLING_SPEED;	
	protected int fallingFrame = 0;	
	protected int jumpingFrame = 0;	
	protected int jumpingLength = 100;	
	protected int jumpingCountFrame = 0;
	
	public abstract void moveVertically(Panel panel, Player player);
	
	public void move(Panel panel, Player player) {
		moveVertically(panel, player);
	}
	
}
