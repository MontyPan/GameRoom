package us.dontcareabout.gameRoom.client.mine.ui;

import java.util.Arrays;

import com.google.gwt.user.client.Window;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.ui.component.InfoUI;
import us.dontcareabout.gameRoom.client.mine.ui.component.MapUI;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;

public class BoardView extends LayerContainer {
	public static final String ID = "ClientPlayer";
	public static final int BLOCK_SIZE = 32;

	private static final String player = "Player";

	private VerticalLayoutLayer root = new VerticalLayoutLayer();
	private MapUI map;
	private InfoUI info;

	private final String[] name = {player, player};

	public BoardView() {
		GM.addGameStart(e -> init(e.data));
		GM.addGameMove(e -> refresh(e.data));
		GM.addGameEnd(e -> ending(e.data));
		GM.start();
	}

	@Override
	protected void adjustMember(int width, int height) {
		root.resize(width, height);
	}

	private void init(StartInfo data) {
		clear();

		String[] playerId = data.getPlayerId();
		int aiIndex = playerId[0].equals(BoardView.ID) ? 1 : 0;
		name[aiIndex] = playerId[aiIndex];

		info = new InfoUI(Arrays.asList(playerId), data.getWidth(), data.getTotal());
		map = new MapUI(data.getWidth(), data.getHeight());
		root.addChild(info, 36);
		root.addChild(map, 1);
		addLayer(root);
		redrawSurface();
	}

	private void refresh(GameInfo data) {
		map.refresh(data);
		info.refresh(data);
		redrawSurface();
	}

	private void ending(GameInfo info) {
		int winner = info.getPlayerHit()[0] > info.getPlayerHit()[1] ? 0 : 1;
		Window.alert(name[winner] + " 獲勝");
	}
}
