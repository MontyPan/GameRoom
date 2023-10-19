package us.dontcareabout.gameRoom.client.mine.ui.component;

import com.google.gwt.resources.client.ImageResource;

import us.dontcareabout.gxt.client.draw.LImageSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;

//Refactory GF：先有 HasSize 的 LSprite，然後弄個 SimpleLayerSprite 不然煩死人
class ImageLS extends LayerSprite {
	private final LImageSprite sprite;

	ImageLS(LImageSprite sprite) {
		this.sprite = sprite;
		add(sprite);
	}

	void refresh(ImageResource ir) {
		sprite.setResource(ir);
	}

	@Override
	protected void adjustMember() {
		sprite.setWidth(getWidth());
		sprite.setHeight(getHeight());
	}
}
