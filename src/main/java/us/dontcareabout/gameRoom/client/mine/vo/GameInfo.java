package us.dontcareabout.gameRoom.client.mine.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import us.dontcareabout.gameRoom.client.mine.MineGM;

public class GameInfo {
	private int total;
	private int remainder;

	/**
	 * 目前輪到的玩家 index
	 */
	//XXX 目前不需要，但是以 AGB 來說應該要開成 List<Integer> 來處理多個玩家均可行動
	private int nowIndex;

	/**
	 * 玩家 or Player 可以看到的地圖資訊。
	 * {@link MineGM#UNKNOW} 表示還沒人踩過的區域。
	 * 0～8 表示九宮格內有的地雷數、9 表示玩家踩到的地雷、-9 表示 Player 踩到的地雷。
	 * <p>
	 * 採取左上角為原點、往右為 x 軸遞增、往下為 y 軸遞增。
	 * 為了讓陣列 index 值與座標值相同，因此設計成 map[x][y]
	 * <pre>
	 * 0 0 0 0 0 1
	 * 0 0 0 0 0 0
	 * 2 0 0 0 0 9
	 * </pre>
	 * 上面這個 map 中 1 的座標 map[5][0]、2 的座標是 map[0][2]、9 的座標是 [5][2]。
	 */
	private int[][] map;

	/**
	 * int[0] 為玩家，int[1] 為 Player。{@link MineGM#shoot(int, int, boolean)}
	 */
	private int[] playerHit = new int[2];

	public int[] getPlayerHit() {
		return playerHit;
	}

	public void setMap(int[][] m) {
		this.map = m;
	}

	public int[][] getMap() {
		return map;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	public int getRemainder() {
		return remainder;
	}

	public int getNowIndex() {
		return nowIndex;
	}

	public void setNowIndex(int nowIndex) {
		this.nowIndex = nowIndex;
	}

	public boolean isMyTurn(int myIndex) {
		return myIndex == nowIndex;
	}

	public void setPlayerHit(int[] playerHit) {
		this.playerHit = playerHit;
	}

	@JsonIgnore
	public int getWidth() {
		return map.length;
	}

	@JsonIgnore
	public int getHeight() {
		return map[0].length;
	}
}
