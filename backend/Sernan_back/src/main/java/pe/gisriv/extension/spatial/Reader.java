package pe.gisriv.extension.spatial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import pe.gisriv.extension.spatial.transform.Projector;

public abstract class Reader {
	protected InputStream _stream;	
	protected int _sourceSrid;
	protected int _targetSrid;
	public Reader() {

	}
	public Reader(InputStream stream) throws Exception{
		this._stream = stream;
	}
	public Reader(InputStream stream, int sourceSrid) throws Exception {
		this(stream);
		this._sourceSrid = sourceSrid;
	}
	public Reader(InputStream stream, int sourceSrid, int targetSrid) throws Exception {
		this(stream,sourceSrid);
		this._targetSrid = targetSrid;
	}
	
	protected abstract void parse() throws Exception;

	protected File saveFile(String pathName) throws IOException {
		byte[] buffer = new byte[this._stream.available()];
		this._stream.read(buffer);
		this._stream.close();
		String separator = pathName.endsWith(File.separator) ? ""
				: File.separator;
		File targetFile = new File(String.format("%s%s%s.zip", pathName,
				separator, UUID.randomUUID().toString()));
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(buffer);
		outStream.flush();
		outStream.close();
		return targetFile;
	}
	protected Geometry project(Geometry geometry) throws Exception {
		if(this._targetSrid<=0) return geometry;
		if (geometry != null) {
			geometry = Projector.execute(geometry, this._targetSrid);
		}
		return geometry;
	}
	protected Coordinate[] listToArray(List<Coordinate> items){
		items.add(items.get(0));
		return items.stream().toArray(Coordinate[]::new);
    }
	public Geometry getGeometry() throws Exception {
		throw new Exception("Not implemented");
	}
	public int getSourceSrid(){
		return -1;
	}
}
