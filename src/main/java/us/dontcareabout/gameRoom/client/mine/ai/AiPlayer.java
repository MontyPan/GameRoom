package us.dontcareabout.gameRoom.client.mine.ai;

import java.util.Arrays;

import com.google.gwt.core.client.Scheduler;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class AiPlayer implements Player {
	private static int idSerial = 0;

	private final String aiId;
	private final JavaAI ai;

	private int index;

	public AiPlayer(JavaAI ai) {
		this.ai = ai;
		this.aiId = ai.name() + "-" + (idSerial++);
		GM.addGameStart(e -> start(e.data));
		GM.addGameMove(e -> move(e.data));
		//game end 不用處理
	}

	protected void start(StartInfo data) {
		index = Arrays.asList(data.getPlayerId()).indexOf(aiId);
	}

	protected void move(GameInfo data) {
		if (!data.isMyTurn(index)) { return; }

		//用 fixed delay 是為了讓 UX 看起來 一點
		//但因為 event flow 的關係，至少得弄個 scheduleDeferred()
		Scheduler.get().scheduleFixedDelay(
			() -> {
				GM.move(aiId, guess(data));
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
		return aiId;
	}

	@Override
	public XY guess(GameInfo info) {
		return ai.guess(info);
	}
}
