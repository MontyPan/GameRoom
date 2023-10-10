package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class DummyAI extends AiPlayer {
	public DummyAI(int index) {
		super(index);
	}

	@Override
	public XY guess(GameInfo info) {
		return AiUtil.blindGuess(info);
	}

	@Override
	public String getName() {
		return "Dummy Yummy";
	}
}
