package us.dontcareabout.gameRoom.client.mine.ai;

import java.util.ArrayList;
import java.util.List;
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
			x = roll(info.getWidth());
			y = roll(info.getHeight());
		} while(info.getMap()[x][y] != MineGM.UNKNOW);

		return new XY(x, y);
	}

	public static boolean isMine(int[][] map, int x, int y) {
		return Math.abs(map[x][y]) == MineGM.IS_MINE;
	}

	public static boolean isUnknown(int[][] map, int x, int y) {
		return map[x][y] == MineGM.UNKNOW;
	}

	public static Statistic count(GameInfo info, int x, int y) {
		Statistic result = new Statistic();

		for (int x1 = -1; x1 < 2; x1++) {
			for (int y1 = -1; y1 < 2; y1++) {
				if (x1 == 0 && y1 == 0) { continue; }

				int xIndex = x + x1;
				int yIndex = y + y1;

				if (xIndex < 0 || xIndex >= info.getWidth()) { continue; }
				if (yIndex < 0 || yIndex >= info.getHeight()) { continue; }

				if (isMine(info.getMap(), xIndex, yIndex)) { result.addMine(xIndex, yIndex); }
				if (isUnknown(info.getMap(), xIndex, yIndex)) { result.addUnknown(xIndex, yIndex); }
			}
		}

		return result;
	}

	public static class Statistic {
		private List<XY> mineList = new ArrayList<>();
		private List<XY> unknownList = new ArrayList<>();

		public int getMine() { return mineList.size(); }
		public List<XY> getMineList() { return mineList; }
		public int getUnknown() { return unknownList.size(); }
		public List<XY> getUnknownList() { return unknownList; }

		void addMine(int x, int y) {
			mineList.add(new XY(x, y));
		}
		void addUnknown(int x, int y) {
			unknownList.add(new XY(x, y));
		}
	}
}
