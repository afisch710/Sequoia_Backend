package model;

public class AlreadyFriendModel {

	private String alreadyFriend;
	private String id;
	private String username;
	private String phoneNumber;
	private long createdTimeStamp;
	
	public String getAlreadyFriend() {
		return alreadyFriend;
	}
	
	public void setAlreadyFriend(String alreadyFriend) {
		this.alreadyFriend = alreadyFriend;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
}
