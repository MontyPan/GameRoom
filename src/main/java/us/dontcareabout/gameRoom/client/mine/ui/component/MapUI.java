package us.dontcareabout.gameRoom.client.mine.ui.component;

import com.google.gwt.resources.client.ImageResource;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.ImageRS;
import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.ui.BoardView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;
import us.dontcareabout.gxt.client.draw.LImageSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;

public class MapUI extends VerticalLayoutLayer {
	private final Block[][] map;

	public MapUI(int width, int height) {
		setMargins(1);
		map = new Block[height][width];

		for (int y = 0; y < height; y++) {
			HorizontalLayoutLayer hll = new HorizontalLayoutLayer();
			hll.setMargins(1);
			addChild(hll, BoardView.BLOCK_SIZE);
			for (int x = 0; x < width; x++) {
				map[y][x] = new Block(new LImageSprite(ImageRS.I.unknown()));
				XY xy = new XY(x, y);	//lambda 裡頭得是常數
				map[y][x].addSpriteSelectionHandler(e -> GM.move(BoardView.ID, xy));
				hll.addChild(map[y][x], BoardView.BLOCK_SIZE);
			}
		}
	}

	public void refresh(GameInfo info) {
		int[][] value = info.getMap();

		for (int y = 0; y < info.getHeight(); y++) {
			for (int x = 0; x < info.getWidth(); x++) {
				map[y][x].refresh(value[x][y]);
			}
		}
	}

	//Refactory GF：先有 HasSize 的 LSprite，然後弄個 SimpleLayerSprite 不然煩死人
	private class Block extends LayerSprite {
		private final LImageSprite sprite;

		Block(LImageSprite sprite) {
			this.sprite = sprite;
			add(sprite);
		}

		void refresh(int value) {
			sprite.setResource(mapping(value));
		}

		@Override
		protected void adjustMember() {
			sprite.setWidth(getWidth());
			sprite.setHeight(getHeight());
		}
	}

	private static ImageResource mapping(int x) {
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
