package pe.sernanp.simrac.model;

//import org.geotools.geometry.jts.JTS;
//import org.geotools.referencing.CRS;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBWriter;
//import org.opengis.referencing.crs.CoordinateReferenceSystem;
//import org.opengis.referencing.operation.MathTransform;

public class PersonModel extends BaseModel {
	private String _lastName;
	private String _email;
	private int _age;
	
	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String value) {
		_lastName = value;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String name) {
		_email = name;
	}
	
	public int getAge() {
		return _age;
	}

	public void setAge(int value) {
		_age = value;
	}
	
	private Geometry Geometry;	
	
	
	public Geometry getGeometry() {
		return Geometry;
	}

	public void setGeometry(Geometry geometry) {
		Geometry = geometry;
	}
		
	public byte[] getGeometryWKB() {
		WKBWriter writer = new WKBWriter();
		
		try {
			/*Geometry temp = null;
			CoordinateReferenceSystem sourceCRS = null;
		    CoordinateReferenceSystem targetCRS = null;
		    targetCRS = CRS.decode("EPSG:32718", false);
			
		    
		    
			if (Geometry.getSRID() == 4326) {
			    sourceCRS = CRS.decode(("EPSG:" + Geometry.getSRID()), true);	
			    MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
				temp = JTS.transform(Geometry, transform);
			}
			
			return writer.write(temp);
			*/
			return writer.write(Geometry);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
