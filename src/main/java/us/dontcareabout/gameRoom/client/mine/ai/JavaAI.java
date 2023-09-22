package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.Player;

public interface JavaAI extends Player {
	@Override
	default Type type() { return Type.Java; }
}
