package pe.sernanp.simrac.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.gisriv.entity.FileEntity;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.service.ConservationAgreementService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/conservationagreement/")
public class ConservationAgreementController extends BaseController<ConservationAgreementModel, ConservationAgreementService>  {
	
	@Autowired
	private ConservationAgreementService _service;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<ConservationAgreementModel> list() {
		ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<ConservationAgreementModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			ConservationAgreementModel item2 = super.fromJson(item, ConservationAgreementModel.class);
			ResponseEntity<ConservationAgreementModel> response = this._service.search(item2, paginador);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody ConservationAgreementModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}

	/*@RequestMapping(value = "/search2")
	@ResponseBody
	public ResponseEntity<EconomicActivityModel> search2(EconomicActivityModel item ) {
		ResponseEntity<EconomicActivityModel> response = new ResponseEntity<>();
		try {
			response = this._service.search(item,null);
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	@RequestMapping(value = "/search")
	@ResponseBody
	public ResponseEntity<EconomicActivityModel> search() {
		ResponseEntity<EconomicActivityModel> response = new ResponseEntity<>();
		try {
			response = this._service.find();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}*/
	
	@RequestMapping(value = "/findby", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> findBy(@RequestBody ConservationAgreementModel item) throws IOException {
		try {
			ResponseEntity<ConservationAgreementModel> response = this._service.findBy(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/savecoordinatesmultiple", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity saveCoordinatesMultiple(@RequestParam("item") String item, @RequestParam(value = "filFileCoordinate", required = true) MultipartFile filFileCoordinate){
		try
        {
			ConservationAgreementModel item2 = super.fromJson(item, ConservationAgreementModel.class);
            FileEntity itemFile = this.getFileCoordinates(filFileCoordinate);
            ResponseEntity response= this._service.saveGeometry(item2,itemFile);
            return response;
        }
        catch (Exception ex){
            return super.getJSON(ex);
        }
    }
	
	private final String[] _fileCoordinateExtensions=new String[]{"zip","xlsx","txt"};
	
	protected FileEntity getFileCoordinates(MultipartFile file) throws Exception{
		if(file==null)
			throw new Exception("No existe ningún archivo");
		String extension= FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension != null && !extension.equals("")) {
			if(!Arrays.asList(this._fileCoordinateExtensions).contains(extension))
				throw new Exception(String.format("La extensión %s no es válida",extension));
		}
		return this.getFile(file);
	}
	
	protected FileEntity getFile(MultipartFile file) throws Exception {
		FileEntity item = new FileEntity();
		// InputStream is = new ByteArrayInputStream(file.getBytes());
		item.setContent(file.getBytes());
		item.setName(file.getOriginalFilename());
		item.setContentType(file.getContentType());
		item.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
		return item;
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<?> response = this._service.delete(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ConservationAgreementModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ConservationAgreementModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
		
}