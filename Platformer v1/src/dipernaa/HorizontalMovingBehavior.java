package dipernaa;

public abstract class HorizontalMovingBehavior {
	
	protected int movingFrame = 0;
	protected int movingSpeed = WALKING_SPEED;
	protected static final int WALKING_SPEED = 5;
	protected static final int RUNNING_SPEED = 1;
	
	public abstract void moveHorizontally(Panel panel, Player player);

}
