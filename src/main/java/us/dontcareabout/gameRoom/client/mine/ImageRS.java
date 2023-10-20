package us.dontcareabout.gameRoom.client.mine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageRS extends ClientBundle {
	static ImageRS I = GWT.create(ImageRS.class);

	ImageResource b0();
	ImageResource b1();
	ImageResource b2();
	ImageResource b3();
	ImageResource b4();
	ImageResource b5();
	ImageResource b6();
	ImageResource b7();
	ImageResource b8();
	ImageResource flagBlue();
	ImageResource flagRed();
	ImageResource unknown();
}
