package event;

public class AddFriendEvent {

	private String userId;
	private String friendIds;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(String friendIds) {
		this.friendIds = friendIds;
	}
	
}
