package dipernaa;

import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Player extends Rectangle {

	protected HorizontalMovingBehavior horizontalMovingBehavior;
	protected VerticalMovingBehavior verticalMovingBehavior;

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		setVerticalMovingBehavior(new FallingBehavior());
		setHorizontalMovingBehavior(new NoMoveBehavior());
	}

	public void setHorizontalMovingBehavior(HorizontalMovingBehavior horizontalMovingBehavior) {
		this.horizontalMovingBehavior = horizontalMovingBehavior;
	}

	public void setVerticalMovingBehavior(VerticalMovingBehavior verticalMovingBehavior) {
		if (!(this.verticalMovingBehavior instanceof JumpingBehavior))
		if (verticalMovingBehavior.fallingSpeed > 0)
			verticalMovingBehavior.fallingSpeed = 0;
		verticalMovingBehavior.jumpingFrame = 0;
		this.verticalMovingBehavior = verticalMovingBehavior;
	}
	
	public HorizontalMovingBehavior getHorizontalMovingBehavior() {
		return horizontalMovingBehavior;
	}
	
	public VerticalMovingBehavior getVerticalMovingBehavior() {
		return verticalMovingBehavior;
	}
	
	public void move(Panel panel, Player player) {
		horizontalMovingBehavior.moveHorizontally(panel, this);
		verticalMovingBehavior.moveVertically(panel, this);
	}
	
}
