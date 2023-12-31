package us.dontcareabout.gameRoom.client.mine;

import java.util.List;
import java.util.Random;

import us.dontcareabout.gameRoom.client.agb.RuleBase;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.Result;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class MineGM extends RuleBase {
	/**
	 * {@link #map} 用。還未翻開的格子。
	 */
	public static final int UNKNOW = -1;

	/**
	 * {@link #map} 用。同時也代表 player1 標記的地雷、
	 */
	public static final int IS_MINE = 9;

	/**
	 * {@link #map} 用。player2 標記的地雷。
	 */
	public static final int P2_FLAG = -9;

	private int width;
	private int height;
	private int total;
	private int remainder;
	private int nowIndex;

	/**
	 * 記錄哪一格是地雷，所以 data type 是 boolean。
	 */
	private boolean[][] answer;

	/**
	 * 記錄玩家看到的地圖狀態。
	 * 值域包含：
	 * <ul>
	 * 	<li>0～8：周圍地雷數</li>
	 * 	<li>{@link #UNKNOW}</li>
	 * 	<li>{@link #IS_MINE}</li>
	 * 	<li>{@link #P2_FLAG}</li>
	 * </ul>
	 */
	private int[][] map;
	private int[] playerHit = new int[2];

	public MineGM(List<String> idList) {
		this(idList, 16, 16, 51);
	}

	public MineGM(List<String> idList, int w, int h, int count) {
		super(idList);

		this.width=w;
		this.height=h;
		this.total=count;
		this.remainder=count;
		this.answer = genAnswer();
		this.map = genMap();
	}

	private boolean[][] genAnswer() {
		Random random = new Random();
		boolean[][] result = new boolean[width + 2][height + 2];

		for(int i = 0; i < total; i++) {
			int xVal, yVal;

			do {
				xVal = random.nextInt(width) + 1;
				yVal = random.nextInt(height) + 1;
			} while(result[xVal][yVal]);

			result[xVal][yVal] = true;
		}

		return result;
	}

	private int[][] genMap() {
		int[][] result = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				result[x][y] = UNKNOW;
			}
		}

		return result;
	}

	@Override
	public boolean isYourTurn(String id) {
		return playerList.get(nowIndex).equals(id);
	}

	@Override
	public boolean isEnd() {
		return playerHit[0] >= total / 2.0 || playerHit[1] >= total / 2.0;
	}

	public Result shoot(String id, XY xy) {
		//不考慮炸 exception 是為了預防效率議題
		if (!isYourTurn(id)) { return Result.not_your_turn; }
		if (xy.x < 0 || xy.x >= width || xy.y < 0 || xy.y >= height) { return Result.out_of_bound; }
		if (map[xy.x][xy.y] != UNKNOW){ return Result.not_unknow; }

		count(xy.x, xy.y);

		//不同人踩到地雷要給不同值
		if (map[xy.x][xy.y] == IS_MINE) {
			remainder--;
			playerHit[nowIndex]++;

			if (nowIndex == 1) {
				//IS_MINE 也代表 player1 的 flag
				//所以只有 player2 要重給值
				map[xy.x][xy.y] = P2_FLAG;
			}
		}

		boolean result = Math.abs(map[xy.x][xy.y]) == IS_MINE;
		if (!result) {
			nowIndex = (nowIndex + 1) % 2;
		}
		return result ? Result.hit : Result.miss;
	}

	private void count(int hitX, int hitY) {
		if (answer[hitX+1][hitY+1]) {
			map[hitX][hitY] = IS_MINE;
			return;
		}

		map[hitX][hitY] = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (x == 1 && y == 1) { continue; }
				if (answer[hitX + x][hitY + y]) {
					map[hitX][hitY]++;
				}
			}
		}

		//踩到空地的連鎖反應
		if (map[hitX][hitY] == 0) {
			for (int x = -1; x < 2; x++) {
				if (hitX + x == width || hitX + x < 0){ continue; }

				for (int y = -1; y < 2; y++){
					if (hitY + y == height || hitY + y < 0){ continue; }
					if (map[hitX + x][hitY + y] != UNKNOW) { continue; }

					count(hitX + x, hitY + y);
				}
			}
		}
	}

	public GameInfo getGameInfo() {
		GameInfo result = new GameInfo();
		result.setMap(copy(map));
		result.setRemainder(remainder);
		result.setTotal(total);
		result.setNowId(playerList.get(nowIndex));
		result.setPlayerHit(playerHit);
		return result;
	}

	private static int[][] copy(int[][] target) {
		int[][] result = new int[target.length][];
		for (int i = 0; i < target.length; i++) {
			result[i] = new int[target[i].length];

			for (int i2 = 0; i2 < target[i].length; i2++) {
				result[i][i2] = target[i][i2];
			}
		}
		return result;
	}
}
