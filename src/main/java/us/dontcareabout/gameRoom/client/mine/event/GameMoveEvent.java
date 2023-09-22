package us.dontcareabout.gameRoom.client.mine.event;

import com.google.gwt.event.shared.EventHandler;

import us.dontcareabout.gameRoom.client.mine.event.GameMoveEvent.GameMoveHandler;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gwt.client.data.DataEvent;

public class GameMoveEvent extends DataEvent<GameInfo, GameMoveHandler> {
	public static final Type<GameMoveHandler> TYPE = new Type<GameMoveHandler>();

	public GameMoveEvent(GameInfo data) {
		super(data);
	}

	@Override
	public Type<GameMoveHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameMoveHandler handler) {
		handler.onGameMove(this);
	}

	public interface GameMoveHandler extends EventHandler{
		public void onGameMove(GameMoveEvent event);
	}
}
