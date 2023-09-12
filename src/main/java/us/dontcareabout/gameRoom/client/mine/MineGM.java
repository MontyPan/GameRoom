package us.dontcareabout.gameRoom.client.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;

public class MineGM implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final int UNKNOW = -1;
	public static boolean USER = true;
	public static boolean AI = !USER;

	private int width;
	private int height;
	private int total;
	private int remainder;
	private boolean[][] answer;
	private int[][] map;
	private int[] playerHit = new int[2];
	private ArrayList<int[]> trace;

	public MineGM(boolean[][] array) {
		this.width = array.length;
		this.height = array[0].length;
		this.answer = new boolean[this.width+2][this.height+2];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				answer[x + 1][y + 1] = array[x][y];

				if (array[x][y]) {
					total++;
				}
			}
		}

		this.remainder = total;
		this.map = genMap();
		this.trace = new ArrayList<int[]>();
	}

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
		this.trace = new ArrayList<int[]>();
	}

	public boolean[][] getAnswer() {
		return this.answer;
	}

	private boolean[][] genAnswer() {
		Random random = new Random();
		boolean[][] result = new boolean[width+2][height+2];

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


	public int[][] getMap() {
		return map;
	}

	/**
	 * @param hitX
	 * @param hitY
	 * @param who 值為 {@value #USER}, {@value #Player}
	 * @return 是否命中
	 */
	public boolean shoot(int hitX, int hitY, boolean who) {
		if (hitX < 0 || hitX >= width || hitY < 0 || hitY >= height) { return false; }
		if (map[hitX][hitY] != UNKNOW){ return false; }

		map[hitX][hitY] = count(hitX, hitY);

		//踩到空地的連鎖反應
		if (map[hitX][hitY] == 0) {
			for (int x=-1; x<2; x++) {
				if (hitX + x == width || hitX + x < 0){ continue; }

				for (int y = -1; y < 2; y++){
					if (hitY + y == height || hitY + y < 0){ continue; }
					if (map[hitX + x][hitY + y] != -1) {
						continue;
					} else{
						shoot(hitX + x, hitY + y, who);
					}
				}
			}
		}

		//不同人踩到地雷要給不同值
		if (map[hitX][hitY] == 9) {
			remainder--;
			if (who) {
				playerHit[0]++;
			} else {
				map[hitX][hitY] = -9;
				playerHit[1]++;
			}
		}

		return Math.abs(map[hitX][hitY]) == 9;
	}

	private short count(int hitX, int hitY) {
		if (answer[hitX+1][hitY+1]) { return 9; }

		short c = 0;

		for (int x = 0; x < 3; x++) {
			for (int y=0; y<3; y++) {
				if (x==1 && y==1) {
					continue;
				}
				if (answer[hitX+x][hitY+y]) {
					c++;
				}
			}
		}

		return c;
	}

	public static GameInfo toGameInfo(MineGM server) {
		GameInfo result = new GameInfo();
		result.setMap(server.getMap());
		result.setRemainder(server.remainder);
		result.setTotal(server.total);
		result.setPlayerHit(server.playerHit);
		result.setTrace(server.trace);
		return result;
	}

	public void cleanTrace() {
		trace.clear();
	}

	public void addTrace(int[] xy) {
		trace.add(xy);
	}

	public int getRemainder() {
		return this.remainder;
	}
}
