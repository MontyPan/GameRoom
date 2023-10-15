package us.dontcareabout.gameRoom.client.mine.ui.component;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gameRoom.client.mine.ui.BoardView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.StartInfo;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;

public class InfoUI extends HorizontalLayoutLayer {
	private static final int remainderWidth = 60;

	//XXX 暫時不考慮玩家自訂名字... XD
	private static final String player = "Player";

	private TextButton player0 = new TextButton();
	private TextButton player1 = new TextButton();
	private TextButton remainder = new TextButton();

	private final String[] name = {player, player};

	public InfoUI(StartInfo data) {
		String[] playerId = data.getPlayerId();

		int aiIndex = playerId[0].equals(BoardView.ID) ? 1 : 0;
		name[aiIndex] = playerId[aiIndex];

		player0.setBgColor(new RGB(2, 2, 255));
		player0.setTextColor(RGB.WHITE);
		player1.setBgColor(new RGB(255, 9, 9));
		player1.setTextColor(RGB.WHITE);

		int width = (data.getWidth() * BoardView.BLOCK_SIZE - remainderWidth) / 2;
		addChild(player0, width);
		addChild(remainder, remainderWidth);
		addChild(player1, width);

		refresh(0, 0, data.getTotal());
	}

	public void refresh(GameInfo info) {
		refresh(info.getPlayerHit()[0], info.getPlayerHit()[1], info.getRemainder());
	}

	private void refresh(int hit0, int hit1, int r) {
		player0.setText(name[0] + " : " + hit0);
		remainder.setText("" + r);
		player1.setText(name[1] + " : " + hit1);
	}
}
