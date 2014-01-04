package dipernaa;

public class FallingBehavior extends VerticalMovingBehavior {

	@Override
	public void moveVertically(Panel panel, Player player) {
		if(fallingFrame >= FALLING_SPEED) {
			player.y++;
			panel.ys++;
			fallingFrame = 0;
		} 
		else {
			fallingFrame++;
		}
	}

}
