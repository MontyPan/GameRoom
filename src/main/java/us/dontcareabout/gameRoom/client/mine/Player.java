package us.dontcareabout.gameRoom.client.mine;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

//Refactory AGB
public interface Player {
	public String getName();
	public XY guess(GameInfo info);
}
