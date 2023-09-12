package us.dontcareabout.gameRoom.client.mine.ai;

import java.util.Random;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;

public class DummyAI implements Player {
	private Random random = new Random();

	@Override
	public void guess(GameInfo info, int[] xy) {
		do {
			xy[0] = random.nextInt(info.getWidth());
			xy[1] = random.nextInt(info.getHeight());
		} while(info.getMap()[xy[0]][xy[1]] != MineGM.UNKNOW);
	}

	@Override
	public String getName() {
		return "Dummy Yummy";
	}

}
