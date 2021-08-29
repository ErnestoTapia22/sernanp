package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ActivityModel;

public class ActivityMapper extends BaseMapper<ActivityModel> {
	@Override
	public ActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ActivityModel item = super.mapRowBase(rs, new ActivityModel());
		/*item.setAgreementState(new AgreementStateModel());
		super.mapRowWithTable(rs, item.getAgreementState(), "agreementstate");
		
		item.setFirm(ResultSetExtension.getDate2(rs, "firm"));
		item.setVigency(ResultSetExtension.getInt2(rs, "vigency"));
		
		item.setPartMen(ResultSetExtension.getInt2(rs, "partmen"));
		item.setPartWomen(ResultSetExtension.getInt2(rs, "partwomen"));
		item.setBenPerson(ResultSetExtension.getString2(rs, "benperson"));
		item.setBenIndirect(ResultSetExtension.getString2(rs, "benindirect"));
		item.setNumFamily(ResultSetExtension.getInt2(rs, "numfamily"));
		item.setBenFamily(ResultSetExtension.getString2(rs, "benfamily"));
		item.setAreaAmbitc(ResultSetExtension.getDouble2(rs, "areambitc"));
		item.setProducedArea(ResultSetExtension.getDouble2(rs, "producedarea"));
		item.setDetalleProduction(ResultSetExtension.getString2(rs, "detalleproduction"));
		 */
		
		return item;
	}
}