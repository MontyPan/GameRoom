package us.dontcareabout.gameRoom.client.mine.ui.component;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gameRoom.client.mine.ui.BoardView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;

public class InfoUI extends HorizontalLayoutLayer {
	private static final int remainderWidth = 60;

	private TextButton player0 = new TextButton();
	private TextButton player1 = new TextButton();
	private TextButton remainder = new TextButton();

	public InfoUI(GameInfo data) {
		player0.setBgColor(new RGB(2, 2, 255));
		player0.setTextColor(RGB.WHITE);
		player1.setBgColor(new RGB(255, 9, 9));
		player1.setTextColor(RGB.WHITE);

		int width = (data.getWidth() * BoardView.BLOCK_SIZE - remainderWidth) / 2;
		addChild(player0, width);
		addChild(remainder, remainderWidth);
		addChild(player1, width);
	}

	public void refresh(GameInfo info) {
		//FIXME
		player0.setText("Player : " + info.getPlayerHit()[0]);
		remainder.setText("" + info.getRemainder());
		player1.setText("AI : " + info.getPlayerHit()[1]);
	}
}
