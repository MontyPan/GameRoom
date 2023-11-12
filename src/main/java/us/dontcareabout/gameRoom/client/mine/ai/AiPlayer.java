package us.dontcareabout.gameRoom.client.mine.ai;

import com.google.gwt.core.client.Scheduler;
import com.sencha.gxt.core.shared.event.GroupingHandlerRegistration;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class AiPlayer {
	private static int idSerial = 0;

	private final String aiId;
	private final JavaAI ai;

	private GroupingHandlerRegistration hr = new GroupingHandlerRegistration();

	public AiPlayer(JavaAI ai) {
		this.ai = ai;
		this.aiId = ai.name() + "-" + (idSerial++);
		hr.add(GM.addGameStart(e -> start(e.data)));

		/* 這邊用上 copy/clone 大法，是有一個故事的：
		 *
		 * 首先，因為是 event driven，所有玩家收到的 event 是同一個，
		 * 所以如果 ai 去改變 data 的 field 值，就會造成其他 ai / 玩家的錯亂 or 作弊
		 *
		 * 把 data 弄成 immutable 當然就沒這些問題
		 * 但如何徹底的 immutable 化就會是一個額外的負擔（而且容易忘記 XD）。
		 * 反過來要求 / 檢查「ai 實做不能亂搞 data」也不現實，
		 * 所以變成在傳給 ai 實做前就 copy 一份來解決這個事情。
		 */
		//理論上上面 StartInfo 也要搞，只是這邊就懶惰先跳過 XD
		hr.add(GM.addGameMove(e -> move(GM.copy(e.data))));
	}

	protected void start(StartInfo data) {}

	protected void move(GameInfo data) {
		if (!data.isMyTurn(aiId)) { return; }

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

	public void destroy() {
		hr.removeHandler();
	}

	public String getName() {
		return aiId;
	}

	public XY guess(GameInfo info) {
		return ai.guess(info);
	}
}
