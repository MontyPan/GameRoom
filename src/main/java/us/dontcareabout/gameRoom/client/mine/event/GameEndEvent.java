package us.dontcareabout.gameRoom.client.mine.event;

import com.google.gwt.event.shared.EventHandler;

import us.dontcareabout.gameRoom.client.mine.event.GameEndEvent.GameEndHandler;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gwt.client.data.DataEvent;

public class GameEndEvent extends DataEvent<GameInfo, GameEndHandler> {
	public GameEndEvent(GameInfo data) {
		super(data);
	}

	public static final Type<GameEndHandler> TYPE = new Type<GameEndHandler>();

	@Override
	public Type<GameEndHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameEndHandler handler) {
		handler.onGameEnd(this);
	}

	public interface GameEndHandler extends EventHandler{
		public void onGameEnd(GameEndEvent event);
	}
}
