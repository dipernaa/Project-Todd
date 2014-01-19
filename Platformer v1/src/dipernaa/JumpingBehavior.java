package dipernaa;

public class JumpingBehavior extends FallingBehavior {

	public void jump() {
		this.fallingSpeed = VerticalMovingBehavior.JUMPING_SPEED;
	}
	
	@Override
	public void moveVertically(Panel panel, Player player) {
		super.moveVertically(panel, player);
		if(jumpingCountFrame >= JUMPING_COUNT_SPEED) {
			if(jumpingFrame <= jumpingLength) {
				player.y -= 2 - (jumpingFrame /jumpingLength * 2);
				panel.ys -= 2 - (jumpingFrame /jumpingLength * 2);
				jumpingFrame++;
			}
			jumpingCountFrame = 0;
		}
		else {
			jumpingCountFrame++;
		}		
	}

}
