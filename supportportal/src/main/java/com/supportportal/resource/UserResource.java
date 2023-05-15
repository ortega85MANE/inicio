package com.supportportal.resource;

import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supportportal.domain.Principal;
import com.supportportal.domain.Usuario;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(path = { "/", "/user"})
public class UserResource extends ExceptionHandling {
    
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTTokenProvider jwtTokenProvider;
    List<Usuario> listStatus;
	List<Usuario> listNombres;

    //@Autowired
    public UserResource(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;        
        listStatus = new ArrayList();
    	listNombres = new ArrayList();
    }    
    
    @GetMapping("/all")
    public List<Usuario> getUsuarios() {
        return userService.getUsuarios();
    }
    
    @GetMapping("/pagina")
	public ResponseEntity<?> listar(Pageable pageable){
		return ResponseEntity.ok().body(userService.findAll(pageable));
	}
    
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) { 
        authenticate(usuario.getLogin(), usuario.getPassword());        
        Usuario loginUser = userService.findByLogin(usuario.getLogin());
        Principal userPrincipal = new Principal(loginUser);
        //HttpHeaders jwtHeader = new HttpHeaders();        
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal); 
        
        
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
        
    }
    
    @GetMapping("/por-login/{term}")
    public ResponseEntity<?> showLogin(@PathVariable String term) {
    	
        Optional<Usuario> o = userService.buscarByLogin(term);
        
    	if (o.isPresent()) { 
    			return ResponseEntity.ok(o.orElseThrow()); 
    	} 
    		    return ResponseEntity.notFound().build();
   }
                   
    @GetMapping("/por-nombre/{term}")
    public ResponseEntity<?> showNombre(@PathVariable String term) {
    	
    	try {
    		listNombres = userService.findByNombre(term);
		} catch (Exception e) {
			e.printStackTrace();		   
		}    
    	if (listNombres.size() > 0) {
            return ResponseEntity.ok(listNombres);
    	}
    	       
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/por-status/{term}")
    public ResponseEntity<?> showStatus(@PathVariable String term) {
    	
    	try {
    		listNombres = userService.findByStatus(term);
		} catch (Exception e) {
			e.printStackTrace();		   
		}    
    	if (listNombres.size() > 0) {
            return ResponseEntity.ok(listNombres);
    	}
    	       
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
    	
    	    	
    	usuario.setCliente(1F);
    	usuario.setStatus("A");
    	usuario.setIntentos(0F);
    	usuario.setFechaModificacion(new Date());
    	
        if(result.hasErrors()){
            return validation(result);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(usuario));
    }
    
    @PutMapping("/editar/{id}") 
	  public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable(name = "id") String login) {		 		 
	  if(result.hasErrors()){ return validation(result); } Optional<Usuario> o =
			  userService.update(usuario, login);
	  
	  if (o.isPresent()) { return
	  ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow()); } return
	  ResponseEntity.notFound().build(); 
	  
	  }
    
    @DeleteMapping("/remove/{valor}")
    public ResponseEntity<?> remove(@PathVariable String valor) {
        Optional<Usuario> o = userService.buscarByLogin(valor);

        if (o.isPresent()) {
        	userService.remove(valor);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    private HttpHeaders getJwtHeader(Principal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
