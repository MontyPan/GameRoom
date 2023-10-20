package us.dontcareabout.gameRoom.client.mine.ai;

/**
 * 這個 enum 主要是解決 GWT 在執行期無法動態決定 instance 的 class。
 * 所以要增加一個新的 {@link JavaAI} 除了要增加 enum 名稱之外，
 * {@link #gen(AiRoster)} 也要增加對應的 case 值。
 */
public enum AiRoster {
	Dummy,
	Junior,
	;

	public static JavaAI gen(AiRoster ai) {
		switch(ai) {
		case Dummy: return new DummyAI();
		case Junior: return new JuniorAI();
		default: return new DummyAI();
		}
	}
}
