package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AgreementStateModel;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.EcosystemTypeModel;
import pe.sernanp.simrac.model.SourceModel;

public class ConservationAgreementMapper extends BaseMapper<ConservationAgreementModel> {
	@Override
	public ConservationAgreementModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ConservationAgreementModel item = super.mapRowBase(rs, new ConservationAgreementModel());
		
		item.setEcosystemType(new EcosystemTypeModel());
		super.mapRowWithTable(rs, item.getEcosystemType(), "ecosystemtype");
		
		item.setAnp(new AnpModel());
		super.mapRowWithTable(rs, item.getAnp(), "anp");
				
		item.setAgreementState(new AgreementStateModel());
		super.mapRowWithTable(rs, item.getAgreementState(), "agreementstate");
		
		item.setSource(new SourceModel());
		super.mapRowWithTable(rs, item.getSource(), "source");
		
		item.setDistrictId(ResultSetExtension.getString2(rs, "districtid"));
		item.setDistrictName(ResultSetExtension.getString2(rs, "districtname"));
		item.setFirm(ResultSetExtension.getDate2(rs, "firm"));
		item.setVigency(ResultSetExtension.getInt2(rs, "vigency"));
		item.setPartMen(ResultSetExtension.getInt2(rs, "partmen"));
		item.setPartWomen(ResultSetExtension.getInt2(rs, "partwomen"));
		item.setBenPerson(ResultSetExtension.getString2(rs, "benperson"));
		item.setBenIndirect(ResultSetExtension.getString2(rs, "benindirect"));
		item.setNumFamily(ResultSetExtension.getDouble2(rs, "numfamily"));
		item.setBenFamily(ResultSetExtension.getString2(rs, "benfamily"));
		item.setAreaAmbitc(ResultSetExtension.getDouble2(rs, "areambitc"));
		item.setProducedArea(ResultSetExtension.getDouble2(rs, "producedarea"));
		item.setDetailProduction(ResultSetExtension.getString2(rs, "detalleproduction"));
		item.setRestHect(ResultSetExtension.getDouble2(rs, "resthect"));
		item.setRestdet(ResultSetExtension.getString2(rs, "restdet"));
		item.setSectNom(ResultSetExtension.getString2(rs, "sectnom"));
		item.setSectHect(ResultSetExtension.getDouble2(rs, "secthect"));
		item.setSectDet(ResultSetExtension.getString2(rs, "sectdet"));
		item.setTerritoryMod(ResultSetExtension.getString2(rs, "territorymod"));
		item.setFinanApa(ResultSetExtension.getBoolean2(rs, "finanapa"));
		item.setFinanNum(ResultSetExtension.getDouble2(rs, "finannum"));
		item.setComment(ResultSetExtension.getString2(rs, "comtxt"));
		item.setGenObj(ResultSetExtension.getString2(rs, "genobj"));
		item.setFinanMod(ResultSetExtension.getString2(rs, "finanmod"));
		item.setFondName(ResultSetExtension.getString2(rs, "fondname"));
		item.setAllied(ResultSetExtension.getBoolean2(rs, "allied"));
		
		return item;
	}
}