package us.dontcareabout.gameRoom.client.mine;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent.GameMoveHandler;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent.GameStartHandler;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class GM {
	interface GameInfoMapper extends ObjectMapper<GameInfo> {};
	public static final GameInfoMapper gameInfoMapper = GWT.create(GameInfoMapper.class);

	private static final SimpleEventBus eventBus = new SimpleEventBus();

	private static MineGM rule;
	private static Player player2 = new DummyAI();

	public static void start() {	//TODO 依起始參數開局
		rule = new MineGM();
		eventBus.fireEvent(new GameStartEvent(cloneGameInfo()));
	}

	public static HandlerRegistration addGameStart(GameStartHandler handler) {
		return eventBus.addHandler(GameStartEvent.TYPE, handler);
	}

	public static void move(boolean who, XY xy) {
		if (!rule.shoot(xy, who)) {
			XY foo;

			//TODO AI 也改成呼叫 move()
			do{
				if (rule.getRemainder() == 0){ break; }

				foo = player2.guess(cloneGameInfo());
			} while (rule.shoot(foo, MineGM.PLAYER_2));
		}

		eventBus.fireEvent(new GameMoveEvent(cloneGameInfo()));
	}

	public static HandlerRegistration addGameMove(GameMoveHandler handler) {
		return eventBus.addHandler(GameMoveEvent.TYPE, handler);
	}

	private static GameInfo cloneGameInfo() {
		String json = gameInfoMapper.write(rule.getGameInfo());
		return gameInfoMapper.read(json);
	}
}
