package pe.github.sernanp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import pe.github.sernanp.extension.ResultSetExtension;
import pe.github.sernanp.model.BaseModel;

public class BaseMapper<TEntity extends BaseModel> implements RowMapper<TEntity> {
	@Override
	public TEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		return null;
	}

	protected TEntity mapRowBase(ResultSet rs, TEntity item) throws SQLException {
		item.setId(ResultSetExtension.getValue2(rs, "id"));
		item.setCode(ResultSetExtension.getString2(rs, "code"));
		item.setName(ResultSetExtension.getString2(rs, "name"));
		item.setDescription(ResultSetExtension.getString2(rs, "description"));
		item.setObservation(StringUtils.trimToNull(ResultSetExtension.getString2(rs, "observation")));
		item.setState(ResultSetExtension.getBoolean2(rs, "state"));
		item.setGUID(ResultSetExtension.getString2(rs, "guid"));
		// sólo para solventar el tema de los refcursor
		item.setRecordsTotal(ResultSetExtension.getInt2(rs, "totalrecords"));
		item.setRowNum(ResultSetExtension.getInt2(rs, "rownum"));
		return item;
	}

	protected <TEntity2 extends BaseModel> TEntity2 mapRowWithTable(ResultSet rs, TEntity2 item, String tableName)
			throws SQLException {
		item.setId(ResultSetExtension.getValue2(rs, tableName + "id"));
		item.setCode(ResultSetExtension.getString2(rs, tableName + "code"));
		item.setName(ResultSetExtension.getString2(rs, tableName + "name"));
		item.setDescription(StringUtils.trimToNull(ResultSetExtension.getString2(rs, tableName + "description")));
		item.setObservation(StringUtils.trimToNull(ResultSetExtension.getString2(rs, tableName + "observation")));
		item.setState(ResultSetExtension.getBoolean2(rs, tableName + "state"));
		item.setGUID(ResultSetExtension.getString2(rs, tableName + "guid"));
		return item;
	}
}
