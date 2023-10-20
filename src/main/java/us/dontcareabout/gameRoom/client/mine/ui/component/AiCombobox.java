package us.dontcareabout.gameRoom.client.mine.ui.component;

import java.util.Arrays;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.ComboBox;

import us.dontcareabout.gameRoom.client.mine.ai.AiRoster;

public class AiCombobox extends ComboBox<AiRoster> {
	public AiCombobox() {
		super(
			new ListStore<>(t -> t.name()),
			//XXX 沒辦法，只能直接 new 一個出來娶值... Orz
			t -> AiRoster.gen(t).name()
		);
		getStore().addAll(Arrays.asList(AiRoster.values()));
		setForceSelection(true);
		setEditable(false);
		setTriggerAction(TriggerAction.ALL);
	}
}
