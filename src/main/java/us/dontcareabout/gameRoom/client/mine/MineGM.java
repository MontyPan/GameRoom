package us.dontcareabout.gameRoom.client.mine;

import java.util.Random;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class MineGM {
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

	public static boolean PLAYER_1 = true;
	public static boolean PLAYER_2 = !PLAYER_1;

	private int width;
	private int height;
	private int total;
	private int remainder;

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

	public MineGM() {
		this(16, 16, 51);
	}

	public MineGM(int w, int h, int count) {
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

	/**
	 * @return 是否命中
	 */
	public boolean shoot(XY xy, boolean who) {
		//TODO 應該要炸 exception 才合理
		if (xy.x < 0 || xy.x >= width || xy.y < 0 || xy.y >= height) { return false; }
		if (map[xy.x][xy.y] != UNKNOW){ return false; }

		map[xy.x][xy.y] = count(xy.x, xy.y);

		//踩到空地的連鎖反應
		if (map[xy.x][xy.y] == 0) {
			for (int x = -1; x < 2; x++) {
				if (xy.x + x == width || xy.x + x < 0){ continue; }

				for (int y = -1; y < 2; y++){
					if (xy.y + y == height || xy.y + y < 0){ continue; }
					if (map[xy.x + x][xy.y + y] != -1) {
						continue;
					} else{
						shoot(new XY(xy.x + x, xy.y + y), who);
					}
				}
			}
		}

		//不同人踩到地雷要給不同值
		if (map[xy.x][xy.y] == IS_MINE) {
			remainder--;
			if (who == PLAYER_1) {
				//IS_MINE 也代表 player1 的 flag
				playerHit[0]++;
			} else {
				map[xy.x][xy.y] = P2_FLAG;
				playerHit[1]++;
			}
		}

		return Math.abs(map[xy.x][xy.y]) == IS_MINE;
	}

	private int count(int hitX, int hitY) {
		if (answer[hitX+1][hitY+1]) { return IS_MINE; }

		int c = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (x == 1 && y == 1) {
					continue;
				}
				if (answer[hitX + x][hitY + y]) {
					c++;
				}
			}
		}

		return c;
	}

	public GameInfo getGameInfo() {
		GameInfo result = new GameInfo();
		result.setMap(map);	//雖然不應該把 instance 給出去，不過這裡相信 GM 不會亂搞 XD
		result.setRemainder(remainder);
		result.setTotal(total);
		result.setPlayerHit(playerHit);
		return result;
	}
}
