package pe.gisriv.entity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;

@Entity
public class FileEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private byte[] _content;
    private String _contentType;
    private String _extension;
    private String _name;
    private File _file;
    private String _path;
    
    public String getPath() {
		return _path;
	}
	public void setPath(String value) {
		this._path = value;
	}
    public File getFile() {
		return _file;
	}
	public void setFile(File value) {
		this._file = value;
	}
	@SuppressWarnings("unused")
	private byte[] getContentBytes(){
		InputStream content= this.getContentStream();
		if(content==null)return null;
		try {
			return IOUtils.toByteArray(content);
		} catch (IOException e) {
			return null;
		}
	}
	public InputStream getContentStream() {
		byte[] content = this.getContent();
		if(content==null)return null;
		InputStream is = new ByteArrayInputStream(content);
		return is;
	}
	public byte[] getContent() {
		return _content;
	}
	public void setContent(byte[] value) {
		this._content = value;
	}
	public String getContentType() {
		return _contentType;
	}
	public void setContentType(String value) {
		this._contentType = value;
	}
	public String getExtension() {
		this._extension= (_extension.startsWith(".")?_extension:String.format(".%s", _extension));
		return _extension;
	}
	public void setExtension(String value) {
		this._extension = value;
	}
	public String getName() {
		return _name;
	}
	public void setName(String value) {
		this._name = value;
	}
}
