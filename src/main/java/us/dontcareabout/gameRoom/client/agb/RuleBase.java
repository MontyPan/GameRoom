package us.dontcareabout.gameRoom.client.agb;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class RuleBase {
	//為了方便開成 protected，child class 想亂搞就去吧 XD
	protected List<String> playerList;

	public RuleBase(List<String> playerList) {
		this.playerList = Lists.newArrayList(playerList);
	}

	//目前還看不出這些 abstract method 先開來放的好處... Orz
	public abstract boolean isYourTurn(String id);
	public abstract boolean isEnd();
}
