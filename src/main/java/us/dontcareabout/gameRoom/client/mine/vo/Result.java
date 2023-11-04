package us.dontcareabout.gameRoom.client.mine.vo;

public enum Result {
	hit(true),
	miss(true),
	not_your_turn,
	out_of_bound,
	not_unknow,
	;

	public final boolean valid;

	private Result() { this(false); }

	private Result(boolean valid) {
		this.valid = valid;
	}
}