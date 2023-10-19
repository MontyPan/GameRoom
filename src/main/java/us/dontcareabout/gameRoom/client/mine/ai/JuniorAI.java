package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.ai.AiUtil.Statistic;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

/**
 * 可以把確定是地雷的格子都踩光，沒有的話就會瞎猜。
 */
public class JuniorAI implements JavaAI {
	private static final int USELESS = Integer.MIN_VALUE;
	private GameInfo info;

	/** 就是 {@link GameInfo#getMap()}，純粹為了好寫好讀 */
	private int[][] map;

	@Override
	public String name() {
		return "初級 AI";
	}

	@Override
	public XY guess(GameInfo info) {
		this.info = info;
		this.map = info.getMap();
		markUseless();
		XY mine = findMine();

		if (mine != null) { return mine; }

		return AiUtil.blindGuess(info);
	}

	private void markUseless() {
		for (int x = 0; x < info.getWidth(); x++) {
			for (int y = 0; y < info.getHeight(); y++) {
				if (map[x][y] < 1 || map[x][y] > 8) { continue; }

				Statistic stat = AiUtil.count(info, x, y);

				if (map[x][y] == stat.getMine()) {
					stat.getUnknownList().stream()
						.forEach(xy -> map[xy.x][xy.y] = USELESS);
				}
			}
		}
	}

	private XY findMine() {
		for (int x = 0; x < info.getWidth(); x++) {
			for (int y = 0; y < info.getHeight(); y++) {
				if (map[x][y] < 1 || map[x][y] > 8) { continue; }

				Statistic stat = AiUtil.count(info, x, y);
				int remainder = map[x][y] - stat.getMine();

				if (remainder != 0 && remainder == stat.getUnknown()) {
					return stat.getUnknownList().get(0);
				}
			}
		}

		return null;
	}
}
