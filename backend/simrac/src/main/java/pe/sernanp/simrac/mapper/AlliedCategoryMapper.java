package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.AlliedCategoryModel;

public class AlliedCategoryMapper extends BaseMapper<AlliedCategoryModel> {
	@Override
	public AlliedCategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlliedCategoryModel item = super.mapRowBase(rs, new AlliedCategoryModel());
		return item;
	}
} 