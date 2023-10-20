package us.dontcareabout.gameRoom.client.mine.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;

import us.dontcareabout.gameRoom.client.mine.GM;
import us.dontcareabout.gameRoom.client.mine.ui.component.AiCombobox;
import us.dontcareabout.gameRoom.client.mine.vo.Setting;
import us.dontcareabout.gxt.client.util.PopUtil;
import us.dontcareabout.gxt.client.util.PopUtil.IsDialogWidget;

public class SettingView extends Composite implements IsDialogWidget, Editor<Setting> {
	private static SettingViewUiBinder uiBinder = GWT.create(SettingViewUiBinder.class);
	private static Driver driver = GWT.create(Driver.class);

	@UiField AiCombobox ai;
	@UiField IntegerField width;
	@UiField IntegerField height;
	@UiField CheckBox first;

	private MinNumberValidator<Integer> minVltor = new MinNumberValidator<>(8);
	private MaxNumberValidator<Integer> maxVltor = new MaxNumberValidator<>(25);

	public SettingView() {
		initWidget(uiBinder.createAndBindUi(this));
		width.addValidator(maxVltor);
		width.addValidator(minVltor);
		height.addValidator(maxVltor);
		height.addValidator(minVltor);

		driver.initialize(this);
		driver.edit(GM.setting);
	}

	@Override
	public int dialogWidth() { return 250; }

	@Override
	public int dialogHeight() { return 180; }

	@UiHandler("submit")
	void submit(SelectEvent se) {
		driver.flush();

		if (driver.hasErrors()) { return; }

		PopUtil.closeDialog();
		GM.start();
	}

	private static SettingView instance = new SettingView();

	public static void pop() {
		PopUtil.showDialog(instance);
	}

	interface SettingViewUiBinder extends UiBinder<Widget, SettingView> {}
	interface Driver extends SimpleBeanEditorDriver<Setting, SettingView> {}
}
