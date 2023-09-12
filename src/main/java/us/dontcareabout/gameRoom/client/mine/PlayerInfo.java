package us.dontcareabout.gameRoom.client.mine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PlayerInfo extends Composite {
	private static PlayerInfoUiBinder uiBinder = GWT.create(PlayerInfoUiBinder.class);
	interface PlayerInfoUiBinder extends UiBinder<Widget, PlayerInfo> {}

	@UiField Label title;
	@UiField Label hitCount;

	public PlayerInfo() {
		initWidget(uiBinder.createAndBindUi(this));
		setHitCount(0);
	}

	public void setHitCount(int i) {
		hitCount.setText(""+i);
	}

	public void setName(String name) {
		title.setText(name+"：");
	}
}
