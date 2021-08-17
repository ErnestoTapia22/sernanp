package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.PersonModel;

public class PersonMapper extends BaseMapper<PersonModel> {
	@Override
	public PersonModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		PersonModel item = super.mapRowBase(rs, new PersonModel());
		return item;
	}
}
