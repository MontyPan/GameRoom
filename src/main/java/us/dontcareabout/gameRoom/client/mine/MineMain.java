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

import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
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

	private int width = -1;
	private int height = -1;

	private Player player2 = new DummyAI();
	private MineGM server = new MineGM();

	public MineMain() {
		initWidget(uiBinder.createAndBindUi(this));
		setRemainder(-1);
		map.setCellSpacing(0);
		map.setCellPadding(0);
		p1Info.setName("Player");	//FIXME
		p2Info.setName(player2.getName());
		shoot(width, height);	//Refactory ????
	}

	private void setMap(int[][] m) {
		//懶得先傳 x, y 的大小，所以用這招來限定
		if ((width == -1 || height == -1)) {
			initMap(m);
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Image block = getBlockAt(new XY(x, y));
				block.setUrl(mapping(m[x][y]).getSafeUri());
				block.removeStyleName(style.cpuHit());
			}
		}
	}

	private Image getBlockAt(XY xy) {
		return (Image)map.getWidget(xy.y, xy.x);
	}

	private void initMap(int[][] m) {
		height=m[0].length;	//XXX 應該改成 GameInfo.getHeight() 的....
		width=m.length;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int tmpX = x;
				int tmpY = y;
				Image image = new Image(mapping(-1));
				image.addClickHandler(e -> shoot(tmpX, tmpY));
				map.setWidget(tmpY, tmpX, image);
			}
		}
	}

	private void shoot(int x, int y) {
		if (x == -1 || y == -1 || server.getMap()[x][y] != -1) {
			setShootResult(MineGM.toGameInfo(server));
		}

		if (!server.shoot(new XY(x, y), MineGM.PLAYER_1)) {
			server.cleanTrace();
			XY xy;

			do{
				if (server.getRemainder() == 0){ break; }

				xy = player2.guess(MineGM.toGameInfo(server));
				server.addTrace(xy);
			} while (server.shoot(xy, MineGM.PLAYER_2));
		}

		setShootResult(MineGM.toGameInfo(server));
	}

	public void setRemainder(Integer value) {
		remainder.setText("" + value);
	}

	public void setShootResult(GameInfo m) {
		setMap(m.getMap());

		for(XY xy : m.getTrace()) {
			getBlockAt(xy).addStyleName(style.cpuHit());
		}

		setRemainder(m.getRemainder());
		p1Info.setHitCount(m.getPlayerHit()[0]);
		p2Info.setHitCount(m.getPlayerHit()[1]);

		if (m.getPlayerHit()[0] >= (m.getTotal()/2.0)) {
			Window.alert("玩家獲勝");	//FIXME
			Window.open(Window.Location.getHref(), "_self", "");
		}

		if (m.getPlayerHit()[1] >= (m.getTotal()/2.0)) {
			Window.alert(player2.getName() + " 獲勝");
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
