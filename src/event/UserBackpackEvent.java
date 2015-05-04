package event;

public class UserBackpackEvent {

	private String userId;
	private String noteIds;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNoteIds() {
		return noteIds;
	}
	public void setNoteIds(String noteIds) {
		this.noteIds = noteIds;
	}
}
