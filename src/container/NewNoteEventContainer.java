package container;

import event.NewNoteEvent;

public class NewNoteEventContainer {

	private NewNoteEvent noteInfo;

	public NewNoteEvent getNoteInfo() {
		return noteInfo;
	}

	public void setNoteInfo(NewNoteEvent noteInfo) {
		this.noteInfo = noteInfo;
	}
	
}
