package us.dontcareabout.gameRoom.client.mine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

public class MineMain extends Composite {
	private MineMainUiBinder uiBinder = GWT.create(MineMainUiBinder.class);
	interface MineMainUiBinder extends UiBinder<Widget, MineMain> {}

	interface MyStyle2 extends CssResource {
		String cpuHit();
	}

	@UiField Label remainder;
	@UiField FlexTable map;
	@UiField PlayerInfo p1Info;
	@UiField PlayerInfo p2Info;
	@UiField MyStyle2 style;

	public MineMain() {
		initWidget(uiBinder.createAndBindUi(this));
		map.setCellSpacing(0);
		map.setCellPadding(0);
		p1Info.setName("Player");	//FIXME
		p2Info.setName("AI");	//FIXME

		GM.addGameStart(e -> {
			initMap(e.data.getWidth(), e.data.getHeight());
			refresh(e.data);
		});
		GM.addGameMove(e -> refresh(e.data));

		GM.start();	//FIXME
	}

	private Image getBlockAt(XY xy) {
		return (Image)map.getWidget(xy.y, xy.x);
	}

	private void initMap(int width, int height) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int tmpX = x;
				int tmpY = y;
				Image image = new Image(mapping(MineGM.UNKNOW));
				image.addClickHandler(e -> shoot(tmpX, tmpY));
				map.setWidget(tmpY, tmpX, image);
			}
		}
	}

	private void shoot(int x, int y) {
		GM.move(MineGM.PLAYER_1, new XY(x, y));
	}

	private void refresh(GameInfo info) {
		int[][] map = info.getMap();

		//地圖
		for (int y = 0; y < info.getHeight(); y++) {
			for (int x = 0; x < info.getWidth(); x++) {
				Image block = getBlockAt(new XY(x, y));
				block.setUrl(mapping(map[x][y]).getSafeUri());
				block.removeStyleName(style.cpuHit());
			}
		}

		for(XY xy : info.getTrace()) {
			getBlockAt(xy).addStyleName(style.cpuHit());
		}

		remainder.setText("" + info.getRemainder());
		p1Info.setHitCount(info.getPlayerHit()[0]);
		p2Info.setHitCount(info.getPlayerHit()[1]);

		if (info.getPlayerHit()[0] >= (info.getTotal()/2.0)) {
			Window.alert("玩家獲勝");	//FIXME
			Window.open(Window.Location.getHref(), "_self", "");
		}

		if (info.getPlayerHit()[1] >= (info.getTotal()/2.0)) {
			Window.alert("AI 獲勝");	//FIXME
			Window.open(Window.Location.getHref(), "_self", "");
		}
	}

	private ImageResource mapping(int x) {
		switch(x) {
		case 0 : return ImageRS.I.b0();
		case 1 : return ImageRS.I.b1();
		case 2 : return ImageRS.I.b2();
		case 3 : return ImageRS.I.b3();
		case 4 : return ImageRS.I.b4();
		case 5 : return ImageRS.I.b5();
		case 6 : return ImageRS.I.b6();
		case 7 : return ImageRS.I.b7();
		case 8 : return ImageRS.I.b8();
		case MineGM.IS_MINE : return ImageRS.I.flagBlue();
		case MineGM.P2_FLAG : return ImageRS.I.flagRed();
		default: return ImageRS.I.unknown();
		}
	}
}
