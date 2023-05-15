package com.supportportal.service.impl;

import static com.supportportal.constant.UserImplConstant.DEFAULT_USER_IMAGE_PATH;
import static com.supportportal.constant.UserImplConstant.FOUND_USER_BY_USERNAME;
import static com.supportportal.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supportportal.domain.Principal;
import com.supportportal.domain.Usuario;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.UserService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByLogin(username);
        if (usuario == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {            
            Principal userPrincipal = new Principal(usuario);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }    
    
    @Override
    public List<Usuario> getUsuarios() {
    	return (List<Usuario>) userRepository.findAllByOrderByNombreAsc();
        //return userRepository.findAll();
    }
    
    @Override
	public Page<Usuario> findAll(Pageable pageable) {
    	return userRepository.findAllByOrderByNombreAsc(pageable);
		//return userRepository.findAll(pageable); 
	}
    
    @Override
	public List<Usuario> findByNombre(String nombre) {
		
		return userRepository.findByNombre(nombre);
	}
    
    @Override
	public List<Usuario> findByStatus(String status) {
		
		return userRepository.findByStatus(status);
	}
    
    @Override    
    public Optional<Usuario> buscarByLogin(String id) {
        return userRepository.buscarByLogin(id);
    }
    
    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
    	usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }
    
     @Override	  
	  @Transactional public Optional<Usuario> update(Usuario usuario, String login) {
    	 LOGGER.error(NO_USER_FOUND_BY_USERNAME + usuario.toString());
	  Optional<Usuario> o = buscarByLogin(login); Usuario userOptional = null; if          
	  (o.isPresent()) { Usuario userDb = o.orElseThrow();	  	  	
	  userDb.setNombre(usuario.getNombre());
	  userDb.setApellidoMaterno(usuario.getApellidoMaterno());
	  userDb.setApellidoPaterno(usuario.getApellidoPaterno());	  
	  userDb.setCliente(usuario.getCliente());
	  userDb.setLogin(usuario.getLogin()); 
	  userDb.setEmail(usuario.getEmail());	  

	  userOptional = userRepository.save(userDb); } return Optional.ofNullable(userOptional);
	  }
    
    @Override
    @Transactional
    public void remove(String id) {
    	userRepository.remove(id);
    }
    
    @Override
    public Usuario findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    
    private String getTemporaryProfileImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }



}
