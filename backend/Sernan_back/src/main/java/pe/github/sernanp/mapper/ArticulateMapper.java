package pe.github.sernanp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.github.sernanp.model.ArticulateModel;

public class ArticulateMapper extends BaseMapper<ArticulateModel>{

		@Override
		public ArticulateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticulateModel item = super.mapRowBase(rs, new ArticulateModel());
			return item;
		}
	
	
}
