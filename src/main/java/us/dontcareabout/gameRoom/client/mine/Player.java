package us.dontcareabout.gameRoom.client.mine;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;

//Refactory AGB
public interface Player {
	public String getName();
	public void guess(GameInfo info, int[] xy);
}
