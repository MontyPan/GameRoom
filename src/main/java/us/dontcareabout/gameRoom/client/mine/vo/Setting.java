package us.dontcareabout.gameRoom.client.mine.vo;

import us.dontcareabout.gameRoom.client.mine.ai.AiRoster;

public class Setting {
	/** 地雷佔全部格子的比例 */
	private static final double MINE_RATIO = 0.2;

	private AiRoster ai = AiRoster.Junior;
	private int width = 16;
	private int height = 16;
	private boolean first;
	public AiRoster getAi() {
		return ai;
	}
	public void setAi(AiRoster ai) {
		this.ai = ai;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public int getTotal() {
		return (int)Math.floor(width * height * MINE_RATIO);
	}
}
