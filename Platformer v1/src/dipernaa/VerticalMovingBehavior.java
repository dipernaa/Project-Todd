package dipernaa;

public abstract class VerticalMovingBehavior {
	
	protected static final int FALLING_ACCELERATION = 1;
	protected static final int JUMPING_COUNT_SPEED = 1;
	protected static final int JUMPING_SPEED = -10;	
	protected static final int TERMINAL_VELOCITY = 20;	
	protected int fallingSpeed = 0;
	protected int fallingFrame = 0;	
	protected int jumpingFrame = 0;	
	protected int jumpingLength = 100;	
	protected int jumpingCountFrame = 0;
	
	public abstract void moveVertically(Panel panel, Player player);
	
	
}
