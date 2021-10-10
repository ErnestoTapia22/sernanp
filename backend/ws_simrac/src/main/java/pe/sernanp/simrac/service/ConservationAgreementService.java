package pe.sernanp.simrac.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.sernanp.simrac.dto.ConservationAgreementDTO;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.repository.ConservationAgreementRepository;

@Service
public class ConservationAgreementService {

	@Autowired
	private ConservationAgreementRepository _repository;

	public ResponseEntity save(ConservationAgreementModel item) throws Exception {
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (item.getSource().getId() == 0)
				item.setSource(null);
			if (id == 0) {
				SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
				String currentYear = getYearFormat.format(item.getFirm());
				List<ConservationAgreementModel> itemmmm = this._repository
						.searchByDepartment(item.getDistrictId().substring(0, 2));
				item.setCode("AC" + currentYear + item.getDistrictId().substring(0, 2)
						+ String.format("%04d", itemmmm.size() + 1));
				ConservationAgreementModel item2 = this._repository.save(item);
				id = item2.getId();
				message += "Se guardaron sus datos de manera correcta";
			} else {
				message += "Se actualizaron sus datos de manera correcta";
				this._repository.save(item);
			}
			success = true;
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> list() throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			List<ConservationAgreementModel> items = _repository.findAll();
			response.setItems(items);
			return response;

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity delete(int id) throws Exception {
		try {
			this._repository.deleteById(id);
			ResponseEntity response = new ResponseEntity();
			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
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
			ConservationAgreementModel item = _repository.findById(id).get();
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> search(ConservationAgreementDTO item, PaginatorEntity paginator)
			throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
			int value = item.getFirm() == null ? 0 : 1;
			if (item.getFirm() == null)
				item.setFirm(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			if (item.getFirmEnd() == null)
				item.setFirmEnd(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			Page<ConservationAgreementModel> pag = this._repository.search(item.getCode(), item.getName(),
					item.getAnp().getId(), item.getAgreementState().getId(), item.getDepartmentId(),
					item.getProvinceId(), item.getDistrictId(), item.getFirm(), item.getFirmEnd(), value, page);
			List<ConservationAgreementModel> items = pag.getContent();
			paginator.setTotal((int) pag.getTotalElements());
			response.setItems(items);
			response.setPaginator(paginator);
			return response;

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<ConservationAgreementModel> search2(ConservationAgreementDTO item) throws Exception {
		try {
			ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<ConservationAgreementModel>();
			int value = item.getFirm() == null ? 0 : 1;
			if (item.getFirm() == null)
				item.setFirm(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			if (item.getFirmEnd() == null)
				item.setFirmEnd(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			List<ConservationAgreementModel> items = this._repository.search(item.getCode(), item.getName(),
					item.getAnp().getId(), item.getAgreementState().getId(), item.getDepartmentId(),
					item.getProvinceId(), item.getDistrictId(), item.getFirm(), item.getFirmEnd(), value);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}