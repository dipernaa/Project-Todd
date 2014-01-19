package dipernaa;

public class FallingBehavior extends VerticalMovingBehavior {

	protected double fallingSpeed = 0;
	
	@Override
	public void moveVertically(Panel panel, Player player) {
		if(fallingFrame >= 1) {
			player.y += fallingSpeed / 10;
			if(fallingSpeed < TERMINAL_VELOCITY) {
				fallingSpeed += 0.5;
			}
			panel.ys += fallingSpeed / 10;
			fallingFrame = 0;
		} 
		else {
			fallingFrame++;
		}
	}

}
