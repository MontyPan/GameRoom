package us.dontcareabout.gameRoom.client.mine;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

//Refactory AGB
public interface Player {
	public int getIndex();
	public String getName();
	public XY guess(GameInfo info);
	public Type type();

	public enum Type {
		UI,
		Java,
		;
	}
}
