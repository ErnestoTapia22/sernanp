package pe.sernanp.simrac.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.gisriv.entity.FileEntity;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.gisriv.extension.spatial.Reader;
import pe.gisriv.extension.spatial.ShapeFileReader;
import pe.sernanp.simrac.model.BaseGeometryEntity;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.EconomicActivityModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.model.SpatialReferenceEntity;
import pe.sernanp.simrac.repository.ConservationAgreementRepository;
import pe.sernanp.simrac.service.CommitmentService;

@Service
public class ConservationAgreementService extends BaseService<ConservationAgreementModel> {

	@Autowired
	private ConservationAgreementRepository _repository;
	@Autowired
	private CommitmentService _commitmentService;

	@Override
	public ResponseEntity<ConservationAgreementModel> list() throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public ResponseEntity<ConservationAgreementModel> search(ConservationAgreementModel item, PaginatorEntity paginator)
			throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = this._repository.search(this._dataSource, item, paginator);
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> find() throws Exception {
		try {
			System.out.println(this._dataSource);
			boolean success = true;
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = this._repository.find(this._dataSource);
			response.setSuccess(success);
			response.setItems(items);
			return response;

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(ConservationAgreementModel item) throws Exception {
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			if (id == 0) {
				id = this._repository.insert(this._dataSource, item);
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			} else {
				id = this._repository.update(this._dataSource, item);
				message += "Se actualizaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			this.transactionManager.commit(status);
			ResponseEntity respuesta = new ResponseEntity();
			respuesta.setExtra(id.toString());
			respuesta.setMessage(message);
			respuesta.setSuccess(success);
			return respuesta;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			if (ex instanceof org.springframework.dao.DuplicateKeyException)
				throw new Exception("El c??digo ingresado ya se encuentra registrado.");
			else
				throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> findBy2(ConservationAgreementModel item) throws Exception {
		try {
			if (item == null) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = this._repository.findBy2(this._dataSource, item);
			// items.forEach(t ->{
			// t.setIsClient(true);
			// });
			// List<DocumentModel> itemsDocument =
			// this._repository.findDocuments(this._dataSource, id);
			// item.setDocuments(itemsDocument);
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> findBy(ConservationAgreementModel item) throws Exception {
		try {
			Boolean state = item.getState();
			if (state == true) {
				item.setState(false);
			} else {
				item.setState(true);
			}
			List<ConservationAgreementModel> items = _repository.findBy(this._dataSource, item);
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity saveGeometry(ConservationAgreementModel item, FileEntity itemFile) throws Exception {
		try {
			// BaseGeometryEntity itemGeometry = this.coordinateProcessing(itemFile,
			// item.getSpatialReference().getId2());
			// item.setGeometry(itemGeometry.getGeometry());
			// item.setSpatialReference(itemGeometry.getSpatialReference());
			ResponseEntity<BaseGeometryEntity> response = new ResponseEntity<BaseGeometryEntity>();
			// response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@SuppressWarnings("unused")
	protected BaseGeometryEntity coordinateProcessing(FileEntity itemFile, int srid) throws Exception {
		Reader reader = null;
		try {
			switch (itemFile.getExtension()) {
				case ".zip":
					reader = new ShapeFileReader(itemFile.getContentStream(), this.getPathTemporal(), 4326);
					break;
				case ".xlsx":
					reader = null;// new
									// SpreadSheetReader(itemFile.getContentStream(),GeometryType.POLYGON,srid,SRIDDEFAULT);
					break;
				case ".txt":
					reader = null;// new
									// TextSheetReader(itemFile.getContentStream(),GeometryType.POLYGON,srid,SRIDDEFAULT);
					break;
				default:
					throw new Exception("El tipo de archivo no es v??lido");
			}
			if (reader == null)
				return null;
			BaseGeometryEntity item = new BaseGeometryEntity();
			item.setGeometry(reader.getGeometry());
			item.setSpatialReference(new SpatialReferenceEntity());
			item.getSpatialReference().setId(reader.getSourceSrid());
			return item;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	protected String getPathTemporal() throws IOException {
		Path temp = Files.createTempFile("", ".tmp");
		String absolutePath = temp.toString();
		System.out.println("Temp file : " + absolutePath);

		String separator = FileSystems.getDefault().getSeparator();
		String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(separator));
		return "D:\\shape";
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity delete(int id) throws Exception {
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			Integer rowsAffected = this._repository.delete(this._dataSource, id);
			this.transactionManager.commit(status);
			ResponseEntity response = new ResponseEntity();

			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			ConservationAgreementModel item = this._repository.detail(this._dataSource, id);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> search2(ConservationAgreementModel item) throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = this._repository.search2(this._dataSource, item);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public org.springframework.http.ResponseEntity<byte[]> generatePdf(int id) throws Exception, JRException {

		ConservationAgreementModel agreementDetail = this._repository.detail(this._dataSource, id);
		ResponseEntity<CommitmentModel> comitments = this._commitmentService.search(id);

		// JRBeanCollectionDataSource beanCollectorDatasource = new
		// JRBeanCollectionDataSource(
		// Collections.singletonList("Invoice"));
		JRBeanCollectionDataSource beanCollectorDatasource = new JRBeanCollectionDataSource(comitments.getItems());
		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/ReportAgreementDetail.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		map.put("code", agreementDetail.getCode());
		map.put("anp", agreementDetail.getAnp().getName());
		map.put("state", agreementDetail.getState() == true?"Activo":"Inactivo");
		map.put("vigency", agreementDetail.getVigency());
		map.put("name", agreementDetail.getName());
		map.put("firm", agreementDetail.getFirm());
		map.put("observations", agreementDetail.getObservation());

		map.put("hombres", agreementDetail.getPartMen());
		map.put("mujeres", agreementDetail.getPartWomen());
		map.put("numfamily", agreementDetail.getNumFamily());
		map.put("famdetalle", agreementDetail.getBenFamily());
		map.put("benedetalle", agreementDetail.getBenPerson());
		map.put("beneindirect", agreementDetail.getBenIndirect());
		map.put("producearea", agreementDetail.getAreaAmbitc());
		map.put("superintervencion", agreementDetail.getProducedArea());
		map.put("supdetalle", agreementDetail.getDetailProduction());
		map.put("suprestauracion", agreementDetail.getRestHect());
		map.put("supcontrol", agreementDetail.getSectHect());
		map.put("supdetallerestauracion", agreementDetail.getRestdet());
		map.put("sectorvc", agreementDetail.getSectNom());
		map.put("supvcdetalle", agreementDetail.getSectDet());
		map.put("modgestionadc", agreementDetail.getTerritoryMod());
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectorDatasource);
		byte[] data = JasperExportManager.exportReportToPdf(report);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=ReporteAcuerdoDetalle.pdf");
		return org.springframework.http.ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(data);

	}
}