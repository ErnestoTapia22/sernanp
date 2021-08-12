package pe.github.sernan.model;

//import org.geotools.geometry.jts.JTS;
//import org.geotools.referencing.CRS;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBWriter;
//import org.opengis.referencing.crs.CoordinateReferenceSystem;
//import org.opengis.referencing.operation.MathTransform;

public class Person extends Master {
	private Geometry Geometry;
	

	public Person () {
		
	}
	
	public Person(int id, String name, String lastName, String email) {
		setId(id);
		setName(name);
		setLastName(lastName);
		setEmail(email);
	}
	
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
