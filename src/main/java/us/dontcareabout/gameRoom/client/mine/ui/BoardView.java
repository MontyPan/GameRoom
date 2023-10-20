package us.dontcareabout.gameRoom.client.mine.ui;

import java.util.Arrays;

import com.google.gwt.core.client.Scheduler;
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

	private static final int infoHeight = 36;
	private static final String player = "Player";

	private VerticalLayoutLayer root = new VerticalLayoutLayer();
	private MapUI map;
	private InfoUI info;

	private String[] name;

	public BoardView() {
		GM.addGameStart(e -> init(e.data));
		GM.addGameMove(e -> refresh(e.data));
		GM.addGameEnd(e -> ending(e.data));
	}

	@Override
	protected void adjustMember(int width, int height) {
		root.resize(width, height);
	}

	private void init(StartInfo data) {
		clear();

		name = new String[]{player, player};
		String[] playerId = data.getPlayerId();
		int aiIndex = playerId[0].equals(BoardView.ID) ? 1 : 0;
		name[aiIndex] = playerId[aiIndex];

		info = new InfoUI(Arrays.asList(name), data.getWidth(), data.getTotal());
		map = new MapUI(data.getWidth(), data.getHeight());
		root = new VerticalLayoutLayer();
		root.addChild(info, infoHeight);
		root.addChild(map, 1);
		addLayer(root);

		int width = Math.max(data.getWidth(), 10) * BLOCK_SIZE;
		int height = data.getHeight() * BLOCK_SIZE + infoHeight + 4;
		if (width == getOffsetWidth() && height == getOffsetHeight()) {
			adjustMember(width, height);	//沒改變大小就自己觸發 resize 流程
		} else {
			setPixelSize(width, height);
		}
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
		//（疑似）因為還在同一個 event flow 當中，所以得用 finally 大法
		Scheduler.get().scheduleFinally(() -> GM.start());
	}
}
