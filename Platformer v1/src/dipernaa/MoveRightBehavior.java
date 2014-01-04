package dipernaa;

public class MoveRightBehavior extends HorizontalMovingBehavior {
	
	@Override
	public void moveHorizontally(Panel panel, Player player) {
		if(panel.playerRunning) {
			movingSpeed = RUNNING_SPEED;
		}else {
			movingSpeed = WALKING_SPEED;
		}
		if(movingFrame >= movingSpeed) {
			player.x++;
			panel.xs++;
			movingFrame = 0;
		}
		else {
			movingFrame++;
		}
	}

}
