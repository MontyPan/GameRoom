package us.dontcareabout.gameRoom.client.mine;

import java.util.Arrays;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gameRoom.client.mine.ai.AiPlayer;
import us.dontcareabout.gameRoom.client.mine.ai.AiRoster;
import us.dontcareabout.gameRoom.client.mine.event.GameEndEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameEndEvent.GameEndHandler;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent.GameMoveHandler;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent;
import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent.GameStartHandler;
import us.dontcareabout.gameRoom.client.mine.ui.BoardView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.Result;
import us.dontcareabout.gameRoom.client.mine.vo.Setting;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class GM {
	interface GameInfoMapper extends ObjectMapper<GameInfo> {};
	public static final GameInfoMapper gameInfoMapper = GWT.create(GameInfoMapper.class);

	private static final SimpleEventBus eventBus = new SimpleEventBus();

	public static Setting setting = new Setting();

	private static MineGM rule;
	private static AiPlayer ai;
	private static String[] playerId;

	public static void start() {
		if (ai != null) { ai.destroy(); }

		ai = new AiPlayer(AiRoster.gen(setting.getAi()));
		playerId = new String[] {BoardView.ID, BoardView.ID};
		playerId[setting.isFirst() ? 1 : 0] = ai.getName();
		rule = new MineGM(Arrays.asList(playerId), setting.getWidth(), setting.getHeight(), setting.getTotal());

		StartInfo startInfo = new StartInfo();
		startInfo.setTotal(setting.getTotal());
		startInfo.setWidth(setting.getWidth());
		startInfo.setHeight(setting.getHeight());
		startInfo.setPlayerId(playerId);

		eventBus.fireEvent(new GameStartEvent(startInfo));
		eventBus.fireEvent(new GameMoveEvent(rule.getGameInfo()));
	}

	public static HandlerRegistration addGameStart(GameStartHandler handler) {
		return eventBus.addHandler(GameStartEvent.TYPE, handler);
	}

	public static Result move(String id, XY xy) {
		Result result = rule.shoot(id, xy);

		if (result.valid) {
			eventBus.fireEvent(rule.isEnd() ? new GameEndEvent(rule.getGameInfo()) : new GameMoveEvent(rule.getGameInfo()));
		}

		return result;
	}

	public static HandlerRegistration addGameMove(GameMoveHandler handler) {
		return eventBus.addHandler(GameMoveEvent.TYPE, handler);
	}

	public static HandlerRegistration addGameEnd(GameEndHandler handler) {
		return eventBus.addHandler(GameEndEvent.TYPE, handler);
	}

	public static GameInfo copy(GameInfo data) {
		String json = gameInfoMapper.write(data);
		return gameInfoMapper.read(json);
	}
}
