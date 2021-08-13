package pe.github.sernan.service;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.github.sernan.model.Person;
import pe.github.sernan.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personaRepository;
	
	
	public int Save(Person person) {
		
		int id2 = 0;

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		Coordinate coord = new Coordinate( 1, 1 );
		Point point2 = geometryFactory.createPoint( coord );
		
		Polygon polygon = (Polygon) point2.buffer(0.1); //(Polygon) reader.read("POLYGON (1 1, 1 2, 2 2, 2 1)"); 
		polygon.setSRID(4326);
		
		person.setGeometry(polygon);
		
		try {
			
			if (person.getId() == 0)
				id2 = personaRepository.Insert(person);
			else
				id2 = personaRepository.Update(person);
			
			return id2;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public java.util.List<Person> List() {
		return personaRepository.List();
	} 
}
