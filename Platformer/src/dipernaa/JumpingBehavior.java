package dipernaa;

public class JumpingBehavior extends VerticalMovingBehavior {

	@Override
	public void moveVertically(Panel panel, Player player) {
		if(jumpingCountFrame >= JUMPING_COUNT_SPEED) {
			if(jumpingFrame <= jumpingLength) {
				player.y--;
				panel.ys--;
				jumpingFrame++;
			}
			else {
				jumpingFrame = 0;
				player.setVerticalMovingBehavior(panel.fallingBehavior);
			}
			jumpingCountFrame = 0;
		}
		else {
			jumpingCountFrame++;
		}		
	}

}
