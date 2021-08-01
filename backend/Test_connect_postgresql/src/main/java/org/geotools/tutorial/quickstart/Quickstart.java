package org.geotools.tutorial.quickstart;

import java.io.File;
import java.util.ArrayList;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.iso.text.WKTParser;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.WKTReader;


public class Quickstart {
	public static void main(String[] args) throws Exception {
        // display a data store file chooser dialog for shapefiles
        File file = JFileDataStoreChooser.showOpenFile("shp", null);
        if (file == null) {
            return;
        }

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Quickstart");

        test();
        //Style style = SLD.createSimpleStyle(featureSource.getSchema());
        //Layer layer = new FeatureLayer(featureSource, style);
        //map.addLayer(layer);

        // Now display the map
        //JMapFrame.showMap(map);
    }
	
	public static void test() {
		try {

			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
			
			//Crear punto 1
			WKTReader reader = new WKTReader( geometryFactory );
			Point point = (Point) reader.read("POINT (1 1)");
			
			//Crear punto 2
			Coordinate coord = new Coordinate( 1, 1 );
			Point point2 = geometryFactory.createPoint( coord );
			
			
			Polygon polygon = (Polygon) point2.buffer(0.1);//(Polygon) reader.read("POLYGON (1 1, 1 2, 2 2, 2 1)"); 
			polygon.setSRID(32718);
			
			//insert
			WKBWriter writer = new WKBWriter();
			writer.write(polygon);
			
			//select
			//byte[] bytes = null;//r.getBytes("geometry");
			//WKBReader readerWKB = new WKBReader(geometryFactory);
			//polygon = (Polygon) readerWKB.read(bytes);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
