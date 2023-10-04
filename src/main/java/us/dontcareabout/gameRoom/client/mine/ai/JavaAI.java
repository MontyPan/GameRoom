package us.dontcareabout.gameRoom.client.mine.ai;

import us.dontcareabout.gameRoom.client.mine.Player;

public abstract class JavaAI implements Player {
	public final int index;

	public JavaAI(int index) {
		this.index = index;
	}

	@Override
	public final int getIndex() { return index; }

	@Override
	public	final Type type() { return Type.Java; }
}
