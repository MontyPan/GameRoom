package us.dontcareabout.gameRoom.client.mine;

import java.util.Arrays;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gameRoom.client.mine.ai.AiPlayer;
import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
import us.dontcareabout.gameRoom.client.mine.event.GameEndEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameEndEvent.GameEndHandler;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent.GameMoveHandler;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent.GameStartHandler;
import us.dontcareabout.gameRoom.client.mine.ui.BoardView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class GM {
	interface GameInfoMapper extends ObjectMapper<GameInfo> {};
	public static final GameInfoMapper gameInfoMapper = GWT.create(GameInfoMapper.class);

	private static final SimpleEventBus eventBus = new SimpleEventBus();

	private static MineGM rule;
	private static AiPlayer ai = new AiPlayer(new DummyAI());
	private static String[] playerId;

	public static void start() {	//TODO 依起始參數開局
		rule = new MineGM();
		playerId = new String[] {BoardView.ID, ai.getName()};

		//XXX 過渡寫法，之後直接從參數組就好了 XD
		GameInfo info = rule.getGameInfo();
		StartInfo setting = new StartInfo();
		setting.setTotal(info.getTotal());
		setting.setWidth(info.getWidth());
		setting.setHeight(info.getHeight());
		setting.setPlayerId(playerId);

		eventBus.fireEvent(new GameStartEvent(setting));
		eventBus.fireEvent(new GameMoveEvent(cloneGameInfo()));
	}

	public static HandlerRegistration addGameStart(GameStartHandler handler) {
		return eventBus.addHandler(GameStartEvent.TYPE, handler);
	}

	public static void move(String id, XY xy) {
		int index = Arrays.asList(playerId).indexOf(id);

		if (!rule.isYourTurn(index)) { return; }	//TODO 炸 exception？

		rule.shoot(index, xy);
		eventBus.fireEvent(rule.isEnd() ? new GameEndEvent(cloneGameInfo()) : new GameMoveEvent(cloneGameInfo()));
	}

	public static HandlerRegistration addGameMove(GameMoveHandler handler) {
		return eventBus.addHandler(GameMoveEvent.TYPE, handler);
	}

	public static HandlerRegistration addGameEnd(GameEndHandler handler) {
		return eventBus.addHandler(GameEndEvent.TYPE, handler);
	}

	private static GameInfo cloneGameInfo() {
		String json = gameInfoMapper.write(rule.getGameInfo());
		return gameInfoMapper.read(json);
	}
}
