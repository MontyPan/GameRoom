package us.dontcareabout.gameRoom.client.mine.ai;

import java.util.Random;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class AiUtil {
	private static Random random = new Random();

	/**
	 * @return 其實就是 {@link Random#nextInt(int)}
	 */
	public static int roll(int max) {
		return random.nextInt(max);
	}

	/**
	 * @return 隨機找一個空格
	 */
	public static XY blindGuess(GameInfo info) {
		int x, y;
		do {
			x = random.nextInt(info.getWidth());
			y = random.nextInt(info.getHeight());
		} while(info.getMap()[x][y] != MineGM.UNKNOW);

		return new XY(x, y);
	}
}
