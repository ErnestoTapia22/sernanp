package pe.gisriv.extension.spatial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Geometry;

import pe.gisriv.extension.spatial.transform.SridReader;


public class ShapeFileReader extends Reader {

	final static int BUFFER = 2048;

	private FeatureIterator<SimpleFeature> _features;
	private File _fileShapeFile;
	public ShapeFileReader(String fileName) throws Exception {
		if (FilenameUtils.getExtension(fileName).equalsIgnoreCase("zip")) {
			this.uncompress(new File(fileName));
		}
		this._fileShapeFile = new File(fileName);
		this.parse();
	}
	public ShapeFileReader(InputStream stream, String pathName)
			throws Exception {
		super(stream);
		File fileInput = super.saveFile(pathName);
		this.uncompress(fileInput);
		this.parse();
	}
	public ShapeFileReader(InputStream stream, String pathName,int targetSrid)
			throws Exception {
		super(stream);
		this._targetSrid=targetSrid;
		File fileInput = super.saveFile(pathName);
		this.uncompress(fileInput);
		this.parse();
	}
	

	private void uncompress(File fileInput) throws Exception {
		ZipFile zipFile = new ZipFile(fileInput);
		String directoryName = FilenameUtils.removeExtension(fileInput
				.getPath());
		File directoryOutput = new File(directoryName);
		if (!directoryOutput.exists() || !directoryOutput.isDirectory()) {
			directoryOutput.mkdir();
		}
		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();

				InputStream is = zipFile.getInputStream(entry);
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				is.close();
				File targetFile = new File(directoryName + File.separator
						+ entry.getName());
				OutputStream stream = new FileOutputStream(targetFile);
				stream.write(buffer);
				stream.close();
			}
			File[] files = directoryOutput.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File directory, String name) {
					return name.endsWith(".shp");
				}
			});
			if (files.length > 0)
				this._fileShapeFile = files[0];
			else
				this._fileShapeFile = null;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			zipFile.close();
			if (fileInput.exists())
				fileInput.delete();
		}
	}

	@Override
	protected void parse() throws Exception {
		DataStore dataStore = null;
		try {
			//this._fileShapeFile.setReadOnly();
			Map<String, Object> map = new HashMap<>();
	        map.put("url", this._fileShapeFile.toURI().toURL());
			dataStore = DataStoreFinder.getDataStore(map);// FileDataStoreFinder.getDataStore(this._fileShapeFile);
			String typeName = dataStore.getTypeNames()[0];
			SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
			SimpleFeatureCollection collection = source.getFeatures();
			this._features = collection.features();
			CoordinateReferenceSystem crs = source.getSchema()
					.getCoordinateReferenceSystem();
			this._sourceSrid = SridReader.getIdByEsriName(crs.getName()
					.toString());
		} finally {
			System.gc ();
			System.runFinalization ();
			//if (dataStore != null)
				//dataStore.dispose();
			//String directoryName = this._fileShapeFile.getParent();
			//FileUtils.deleteDirectory(new File(directoryName));
		}
	}
	

	public List<Geometry> getGeometries() throws Exception {
		List<Geometry> items = new ArrayList<Geometry>();
		FeatureIterator<SimpleFeature> features = this._features;
		try {
			while (features.hasNext()) {
				SimpleFeature feature = features.next();
				Geometry geometry = (Geometry) feature.getDefaultGeometry();
				geometry.setSRID(this._sourceSrid);
				geometry = this.project(geometry);
				items.add(geometry);
			}
		} finally {
			features.close();
		}
		return items;
	}
	public Geometry getGeometry(int index) throws Exception {
		Geometry geometry = null;
		if (index < 0)
			return geometry;
		FeatureIterator<SimpleFeature> features = this._features;
		try {
			int i = 0;
			while (features.hasNext()) {
				SimpleFeature feature = features.next();
				geometry = (Geometry) feature.getDefaultGeometry();
				geometry.setSRID(this._sourceSrid);
				if (i == index)
					break;
				i++;
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		} finally {
			features.close();
		}
		geometry = this.project(geometry);
		return geometry;
	}
	@Override
	public Geometry getGeometry() throws Exception {
		return this.getGeometry(0);
	}
	@Override
	public int getSourceSrid(){
		return this._sourceSrid;
	}

	// public static void main (String argv[]) {
	// try {
	// BufferedOutputStream dest = null;
	// FileInputStream fis = new FileInputStream(argv[0]);
	// ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	// ZipEntry entry;
	// while((entry = zis.getNextEntry()) != null) {
	// System.out.println("Extracting: " +entry);
	// int count;
	// byte data[] = new byte[BUFFER];
	// // write the files to the disk
	// FileOutputStream fos = new FileOutputStream(entry.getName());
	// dest = new BufferedOutputStream(fos, BUFFER);
	// while ((count = zis.read(data, 0, BUFFER))
	// != -1) {
	// dest.write(data, 0, count);
	// }
	// dest.flush();
	// dest.close();
	// }
	// zis.close();
	// } catch(Exception e) {
	// e.printStackTrace();
	// }
	// }
	public void compress(String fileName) {
		String directoryName = FilenameUtils.removeExtension(fileName);
		final String fileName2 = FilenameUtils.removeExtension(FilenameUtils
				.getName(fileName));
		File directoryOutput = new File(directoryName);
		File[] files = directoryOutput.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				return name.startsWith(fileName2);
			}
		});
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(String.format(
					"%s.zip", directoryName));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			// out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			// get a list of files from current directory
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
