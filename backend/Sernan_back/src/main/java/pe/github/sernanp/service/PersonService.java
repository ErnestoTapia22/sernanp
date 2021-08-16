package pe.github.sernanp.service;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.PersonModel;
import pe.github.sernanp.repository.PersonRepository;

@Service
public class PersonService extends BaseService<PersonModel> {

	@Autowired
	private PersonRepository _repository;
	
	/*public int Save(PersonModel person) {
		
		int id2 = 0;

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		Coordinate coord = new Coordinate( 1, 1 );
		Point point2 = geometryFactory.createPoint( coord );
		
		Polygon polygon = (Polygon) point2.buffer(0.1); //(Polygon) reader.read("POLYGON (1 1, 1 2, 2 2, 2 1)"); 
		polygon.setSRID(4326);
		
		person.setGeometry(polygon);
		
		try {
			
			if (person.getId2() == 0)
				id2 = _repository.Insert(person);
			else
				id2 = _repository.Update(person);
			
			return id2;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}*/	
	
	@Override
	public ResponseEntity<PersonModel> list() throws Exception{
		try {
			ResponseEntity<PersonModel> response = new ResponseEntity<PersonModel>();
			List<PersonModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
