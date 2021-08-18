package pe.sernanp.simrac.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

import pe.gisriv.extension.spatial.transform.Projector;

@Entity
public class BaseGeometryEntity extends BaseModel {

	private static final long serialVersionUID = 1L;
	public static int SRIDDEFAULT;
    public static int SRIDPSEUDOMERCATOR;
    
    protected Coordinate _coordinate;
    protected List<Coordinate> _coordinates;
    private double _area;
    private double _length;
    @JsonIgnore
    protected Geometry _geometry;
    protected Geometry _geometryOriginal;
    protected Geometry _geometryViewer;
    protected String _geometryEWKT;
    protected boolean _fromDatabase;
    protected SpatialReferenceEntity _spatialReference;
    protected CustomGeometryType _geometryType;
    
    private HashMap<String, Object> _layerIntersect;

	public BaseGeometryEntity() {
		this._layerIntersect = new HashMap<String, Object>();
		this._spatialReference = new SpatialReferenceEntity();
	}
	
	@JsonIgnore
	public Geometry getGeometryViewer() {
		return this._geometryViewer;
	}
	
	@JsonIgnore
	public Geometry getGeometry() throws Exception {
//		return this._geometry;
		if (this._geometry != null) return this._geometry;
        if (this.getSpatialReference() == null || this.getSpatialReference().getId2() == 0) return null;

        if (this.getGeometryType() == CustomGeometryType.POINT && this._coordinate == null) return null;
        if (this.getGeometryType() != CustomGeometryType.POINT && this._coordinates == null) return null;
        Geometry geometry = null;
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), this.getSpatialReference().getId2());
        switch (this.getGeometryType())
        {
            case POINT:
                geometry = geometryFactory.createPoint(this._coordinate);
                break;

            case POLYLINE:
                geometry = geometryFactory.createLineString(this._coordinates.stream().toArray(Coordinate[]::new));
                break;

            case POLYGON:
                geometry = geometryFactory.createPolygon(this._coordinates.stream().toArray(Coordinate[]::new));
                break;

            default:
                throw new Exception("Geometry type not supported");
        }
        if (geometry == null) return null;
        geometry = Projector.execute(geometry, SRIDDEFAULT);
        return geometry;
	}

	public void setCoordinate(Coordinate value) {
		this._coordinate = value;
	}

	public Coordinate getCoordinate() {
		if (this._fromDatabase == true)
            this.parseCoordinate();
		return this._coordinate;
	}

	public void setCoordinates(List<Coordinate> value) {
		this._coordinates = value;
	}

	public List<Coordinate> getCoordinates() {
		if (this._fromDatabase == true)
            this.parseCoordinates();
		return this._coordinates;
	}

	@JsonIgnore
	protected CustomGeometryType getGeometryType(){
		return this._geometryType;
	}
	@JsonIgnore
	protected void setGeometryType(CustomGeometryType value){
		this._geometryType=value;
	}
	public SpatialReferenceEntity getSpatialReference() {
		return this._spatialReference;
	}

	public void setSpatialReference(SpatialReferenceEntity value) {
		this._spatialReference = value;
	}
	public double getArea() {
		this.calculateArea();
		return this._area;
	}

	public double getLength() {
		this.calculateLength();
		return this._length;
	}
	
	private void calculateArea() {
		if (this._geometryOriginal == null) {
			this._area = 0;
			return;
		}
		String type = this._geometry.getGeometryType();
		switch (type) {
		case "Polygon":
		case "MultiPolygon":
			this._area = this._geometryOriginal.getArea();
			break;
		default:
			break;
		}
	}

	private void calculateLength() {
		if (this._geometryOriginal == null) {
			this._length = 0;
			return;
		}
		String type = this._geometry.getGeometryType();
		switch (type) {
		case "Polygon":
		case "MultiPolygon":
			this._length = this._geometryOriginal.getLength();
			break;
		default:
			break;
		}
	}
	protected void parseCoordinate() {

		if (this._geometryOriginal == null) {
			this._coordinate = null;
			return;
		}
		String type = this._geometryOriginal.getGeometryType();
		switch (type) {
		case "Point":
			this._coordinate = this._geometryOriginal.getCoordinate();
			break;
		default:
			break;
		}
		// if(type=="Polygon" && this._coordinates!=null)
		// this._coordinates.remove(this._coordinates.size()-1);
		// this._geometry = null;
	}
	protected void parseCoordinates() {
		if (this._geometryOriginal == null) {
			this._coordinates = null;
			return;
		}
		String type = this._geometryOriginal.getGeometryType();
		switch (type) {
		case "Polygon":
		case "MultiPolygon":
			this._coordinates = Arrays.asList(this._geometryOriginal.getCoordinates());
			//this._coordinates.remove(this._coordinates.size()-1);
			break;
		default:
			break;
		}
	}	
	@JsonIgnore
	public void setGeometry(Geometry geometry) throws Exception
    {
        this._geometry = geometry;
        if (this._geometry != null && this._geometry.getSRID()>0)
        {
            this._geometryViewer = Projector.execute(this._geometry, SRIDPSEUDOMERCATOR);
            this._geometryOriginal = Projector.execute(this._geometry, this._spatialReference.getId2());
        }
    }
}
