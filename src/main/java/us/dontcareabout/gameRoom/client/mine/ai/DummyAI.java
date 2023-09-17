package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class DummyAI implements Player {

	@Override
	public XY guess(GameInfo info) {
		return AiUtil.blindGuess(info);
	}

	@Override
	public String getName() {
		return "Dummy Yummy";
	}
}
