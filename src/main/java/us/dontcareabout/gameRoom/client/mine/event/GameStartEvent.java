package us.dontcareabout.gameRoom.client.mine.event;

import com.google.gwt.event.shared.EventHandler;

import us.dontcareabout.gameRoom.client.mine.event.GameStartEvent.GameStartHandler;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gwt.client.data.DataEvent;

public class GameStartEvent extends DataEvent<GameInfo, GameStartHandler> {
	public static final Type<GameStartHandler> TYPE = new Type<GameStartHandler>();

	public GameStartEvent(GameInfo data) {
		super(data);
	}

	@Override
	public Type<GameStartHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameStartHandler handler) {
		handler.onGameStart(this);
	}

	public interface GameStartHandler extends EventHandler{
		public void onGameStart(GameStartEvent event);
	}
}
