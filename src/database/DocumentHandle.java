package database;

public class DocumentHandle {

	private String _id;
	private String _rev;

	public DocumentHandle(String _id, String _rev) {
		this._id = _id;
		this._rev = _rev;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}
	
	public boolean documentExists() {
		return (!_id.equals("") && !_rev.equals(""));
	}

}
