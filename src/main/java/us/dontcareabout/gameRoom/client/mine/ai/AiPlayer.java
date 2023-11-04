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
		 * 但是如果考量到這些 data 在（還是幻想中的）多人連線模式，勢必是 server 給
		 * 這就會踩到 GwtJackson 在 deserialize 時，class 必須有 setter 的哏
		 * （雖然這時候 ai 是在 server 上跑... XD）
		 *
		 * 在「不管是什麼 mode，能共用的邏輯都得共用」的偉大前提下，
		 * 不希望多費工弄個 DTO 轉成另外的 immutable class，
		 * 就變成是在傳給 ai 實做前就 copy 一份來解決這個事情。
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
