package us.dontcareabout.gameRoom.client.mine.ui;

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

	private VerticalLayoutLayer root = new VerticalLayoutLayer();
	private MapUI map;
	private InfoUI info;

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
		info = new InfoUI(data);
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
		if (info.getPlayerHit()[0] >= (info.getTotal()/2.0)) {
			Window.alert("玩家獲勝");	//FIXME
			Window.open(Window.Location.getHref(), "_self", "");
		}

		if (info.getPlayerHit()[1] >= (info.getTotal()/2.0)) {
			Window.alert("AI 獲勝");	//FIXME
			Window.open(Window.Location.getHref(), "_self", "");
		}
	}
}
