package container;

import event.AddFriendEvent;

public class AddFriendEventContainer {

	private AddFriendEvent friendList;

	public AddFriendEvent getFriendList() {
		return friendList;
	}

	public void setFriendList(AddFriendEvent friendList) {
		this.friendList = friendList;
	}
	
}
