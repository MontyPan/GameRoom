package us.dontcareabout.gameRoom.client.mine.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class XY {
	public final int x;
	public final int y;

	@JsonCreator
	public XY(@JsonProperty("x") int x, @JsonProperty("y") int y) {
		this.x = x;
		this.y = y;
	}
}
