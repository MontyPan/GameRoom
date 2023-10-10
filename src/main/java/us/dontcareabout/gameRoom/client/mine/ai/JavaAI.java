package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public interface JavaAI {
	public String name();
	public XY guess(GameInfo info);
}
