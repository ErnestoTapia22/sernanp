package pe.sernanp.simrac.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

public class ResponseEntity<T> {
	private String _extra;
	private String _message;

	private Boolean _success;
	private T _item;
	private List<T> _items;
	private PaginatorEntity _paginator;

	public ResponseEntity() {
		this._success = true;
		this._items = new ArrayList<T>();
	}

	public ResponseEntity(T item) {
		this();
		this._item = item;
	}

	public ResponseEntity(List<T> items) {
		this();
		this._items = items;
	}

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	public String getExtra() {
		return this._extra;
	}

	public String getMessage() {
		return this._message;
	}

	public Boolean getSuccess() {
		return this._success;
	}

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	public T getItem() {
		return _item;
	}
	
	// @JsonInclude(JsonInclude.Include.NON_NULL)
	public List<T> getItems() {
		return _items;
	}
	public int getTotal() {
		if (this._items == null || this._items.size() == 0)
			return 0;
		if (this._paginator != null)
			return this._paginator.getTotal();
		return this._items.size() - 1;
	}

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	public PaginatorEntity getPaginator() {
		return this._paginator;
	}

	public void setExtra(String value) {
		this._extra = value;
	}

	public void setMessage(String value) {
		this._message = value;
	}

	public void setSuccess(Boolean value) {
		this._success = value;
	}

	public void setItem(T value) {
		this._item = value;
	}

	public void setItems(List<T> value) {
		this._items = value;
	}
	public void setPaginator(PaginatorEntity value) {
		this._paginator = value;
	}

	public void setMessage(Exception ex) {
		_success = false;
		_message = ex.getMessage();
	}
}