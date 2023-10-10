package us.dontcareabout.gameRoom.client.mine.ai;

import com.google.gwt.core.client.Scheduler;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class AiPlayer implements Player {
	public final int index;
	private final JavaAI ai;

	public AiPlayer(JavaAI ai, int index) {
		this.ai = ai;
		this.index = index;
		GM.addGameStart(e -> start(e.data));
		GM.addGameMove(e -> move(e.data));
		//game end 不用處理
	}

	protected void start(GameInfo data) {}

	protected void move(GameInfo data) {
		if (!data.isMyTurn(index)) { return; }

		//用 fixed delay 是為了讓 UX 看起來 一點
		//但因為 event flow 的關係，至少得弄個 scheduleDeferred()
		Scheduler.get().scheduleFixedDelay(
			() -> {
				GM.move(index, guess(data));
				return false;
			},
			500
		);
	}

	@Override
	public final int getIndex() { return index; }

	@Override
	public	final Type type() { return Type.Java; }

	@Override
	public String getName() {
		return ai.name();
	}

	@Override
	public XY guess(GameInfo info) {
		return ai.guess(info);
	}
}
