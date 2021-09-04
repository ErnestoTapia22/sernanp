package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.AnpModel;

public class AnpMapper extends BaseMapper<AnpModel> {
	@Override
	public AnpModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AnpModel item = super.mapRowBase(rs, new AnpModel());		
		return item;
	}
}