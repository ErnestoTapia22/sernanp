package pe.github.sernanp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.ConservationAgreementModel;
import pe.github.sernanp.model.PersonModel;
import pe.github.sernanp.repository.ConservationAgreementRepository;

@Service
public class ConservationAgreementService extends BaseService<ConservationAgreementModel> {

	@Autowired
	private ConservationAgreementRepository _repository;

	/*
	 * public java.util.List<ConservationAgreementModel> List() { return
	 * conservationAgreementRepository.List(); }
	 */

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

}
