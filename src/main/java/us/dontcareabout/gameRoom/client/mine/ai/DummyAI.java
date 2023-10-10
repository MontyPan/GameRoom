package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;
import us.dontcareabout.gwt.client.Console;

public class DummyAI implements JavaAI {
	@Override
	public XY guess(GameInfo info) {
		return AiUtil.blindGuess(info);
	}

	@Override
	public String name() {
		return "Dummy Yummy";
	}
}
