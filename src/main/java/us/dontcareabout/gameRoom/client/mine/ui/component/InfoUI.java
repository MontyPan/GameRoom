package us.dontcareabout.gameRoom.client.mine.ui.component;

import java.util.List;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gameRoom.client.ImageRS;
import us.dontcareabout.gameRoom.client.mine.ui.SettingView;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gxt.client.draw.LImageSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;

public class InfoUI extends HorizontalLayoutLayer {
	private static final int remainderWidth = 42;
	private static final int gearWidth = 30;

	private TextButton player0 = new TextButton();
	private TextButton player1 = new TextButton();
	private TextButton remainder = new TextButton();
	private ImageLS gear = new ImageLS(new LImageSprite(ImageRS.I.gear()));

	private final List<String> nameList;

	public InfoUI(List<String> nameList, int wBlock, int total) {
		setMargins(new Margins(0, 0, 0, 5));
		setGap(3);

		player0.setBgColor(new RGB(2, 2, 255));
		player0.setTextColor(RGB.WHITE);
		player1.setBgColor(new RGB(255, 9, 9));
		player1.setTextColor(RGB.WHITE);

		gear.addSpriteSelectionHandler(e -> SettingView.pop());

		addChild(gear, gearWidth);
		addChild(player0, 0.5);
		addChild(remainder, remainderWidth);
		addChild(player1, 0.5);

		this.nameList = nameList;

		refresh(0, 0, total);
	}

	public void refresh(GameInfo info) {
		refresh(info.getPlayerHit()[0], info.getPlayerHit()[1], info.getRemainder());
	}

	private void refresh(int hit0, int hit1, int r) {
		player0.setText(nameList.get(0) + " : " + hit0);
		remainder.setText("" + r);
		player1.setText(nameList.get(1) + " : " + hit1);
	}
}
