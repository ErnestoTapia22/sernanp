package pe.gisriv.extension.spatial.transform;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.CoordinateOperation;
import org.opengis.referencing.operation.CoordinateOperationFactory;
import org.opengis.referencing.operation.MathTransform;
import com.vividsolutions.jts.geom.Geometry;

public final class Projector {
	public static Geometry execute(Geometry geometry, int targetSrid) throws Exception {
		if(geometry==null) return geometry;
		targetSrid = (targetSrid == 0) ? geometry.getSRID() : targetSrid;
        if (geometry.getSRID() == targetSrid) return geometry;
		String sourceWkt = SridReader.getEpsgNameById(geometry.getSRID());
		if(sourceWkt.equals(""))
			throw new Exception("source srid not valid");
		String targetWkt = SridReader.getEpsgNameById(targetSrid);
		if(targetWkt.equals(""))
			throw new Exception("target srid not valid");
		CoordinateReferenceSystem sourceCRS = ReferencingFactoryFinder
				.getCRSFactory(null).createFromWKT(sourceWkt);
		CoordinateReferenceSystem targetCRS = ReferencingFactoryFinder
				.getCRSFactory(null).createFromWKT(targetWkt);
		CoordinateOperationFactory factory = ReferencingFactoryFinder
				.getCoordinateOperationFactory(null);

		// CoordinateReferenceSystem targetCRS2 = CRS.parseWKT("");
		// MathTransform transform2 = CRS.findMathTransform(sourceCRS,
		// targetCRS, true);

		CoordinateOperation operation = factory.createOperation(sourceCRS,
				targetCRS);
		MathTransform transform = operation.getMathTransform();
		//System.out.println(geometry.toText());
		Geometry g = geometry;//JTS.transform(geometry, transform);=========================> aqui cambio
		g.setSRID(targetSrid);
		//JTS.makeValid(g, false);
		//System.out.println(g.toText());
		return g;
	}
}
