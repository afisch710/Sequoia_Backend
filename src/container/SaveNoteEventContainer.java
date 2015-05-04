package container;

import event.NewNoteEvent;

public class SaveNoteEventContainer {

	private NewNoteEvent savedNote;

	public NewNoteEvent getSavedNote() {
		return savedNote;
	}

	public void setSavedNote(NewNoteEvent savedNote) {
		this.savedNote = savedNote;
	}
	
}
