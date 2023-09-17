package us.dontcareabout.gameRoom.client.mine.ai;

import java.util.Random;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class DummyAI implements Player {
	private Random random = new Random();

	@Override
	public XY guess(GameInfo info) {
		int x, y;
		do {
			x = random.nextInt(info.getWidth());
			y = random.nextInt(info.getHeight());
		} while(info.getMap()[x][y] != MineGM.UNKNOW);

		return new XY(x, y);
	}

	@Override
	public String getName() {
		return "Dummy Yummy";
	}
}
