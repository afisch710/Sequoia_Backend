package container;

import event.UserBackpackEvent;

public class UserBackpackEventContainer {

	private UserBackpackEvent backpack;

	public UserBackpackEvent getBackpack() {
		return backpack;
	}

	public void setBackpack(UserBackpackEvent backpack) {
		this.backpack = backpack;
	}
}
