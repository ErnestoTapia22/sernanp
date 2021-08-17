package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.ArticulateModel;

public class ArticulateMapper extends BaseMapper<ArticulateModel>{

		@Override
		public ArticulateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticulateModel item = super.mapRowBase(rs, new ArticulateModel());
			return item;
		}
	
	
}
