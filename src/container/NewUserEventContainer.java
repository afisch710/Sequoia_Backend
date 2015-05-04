package container;

import event.NewUserEvent;

public class NewUserEventContainer {

	private NewUserEvent userInfo;

	public NewUserEvent getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(NewUserEvent userInfo) {
		this.userInfo = userInfo;
	}
	
}
