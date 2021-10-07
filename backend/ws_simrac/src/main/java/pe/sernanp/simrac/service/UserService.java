package pe.sernanp.simrac.service;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.sernanp.simrac.dto.RoleDTO;
import pe.sernanp.simrac.dto.UserDTO;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.LoginModel;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.repository.LoginRepository;
import pe.sernanp.simrac.repository.ModuleRepository;
import pe.sernanp.simrac.repository.RoleRepository;
import pe.sernanp.simrac.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository _repository;
	
	@Autowired
	private LoginRepository _repositoryLogin;
	
	@Autowired
	private ModuleRepository _repositoryModule;
	
	@Autowired
	private RoleRepository _repositoryRole;

	public ResponseEntity<UserDTO> validate(String id) throws Exception {
		try {
			boolean success = true;
			ResponseEntity<UserDTO> response = new ResponseEntity<UserDTO>();
			LoginModel login = this._repositoryLogin.validate(id);
			UserModel item = this._repository.findById(login.getUserId()).get();
			List<ModuleModel> items = this._repositoryModule.search(login.getSystemId(), login.getUserId());
			RoleModel role = this._repositoryRole.active(item.getId(), items.get(0).getId());
			UserDTO userDTO = new UserDTO();
			userDTO.setId(item.getId());
			userDTO.setName(item.getUserName());
			userDTO.setSystem(login.getSystemId());
			userDTO.setModules(items);
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			userDTO.setRole(roleDTO);
			//item.setModules(items);
			
			// Add token
			
			//String token = getJWTToken(id);
			//item.setToken(token);
			
			// End add token
			
			response.setSuccess(success);
			response.setItem(userDTO);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<UserModel> search(UserDTO item, PaginatorEntity paginator) throws Exception{
		try {			
			Pageable page = PageRequest.of(paginator.getOffset()-1, paginator.getLimit());
			Page<UserModel> pag = this._repository.search(item.getName(), item.getUserName(), item.getLastName(), item.getSystem(), item.getRole().getId(), page);
			List<UserModel> items = pag.getContent();
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	@Transactional
	public ResponseEntity<UserModel> searchWithoutLogin(String dni, int system) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			List<UserModel> item = this._repository.searchWithoutLogin(dni, system);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	@Transactional
	public ResponseEntity save(UserDTO item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			this._repository.insert(item.getRole().getId(), item.getId(), item.getRegistrationDate());			
			message += " Se guardaron sus datos de manera correcta";
			success = (id == 0) ? false : true;			
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());			
		}
	}
	
	@Transactional
	public ResponseEntity delete(int id) throws Exception  {
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

}