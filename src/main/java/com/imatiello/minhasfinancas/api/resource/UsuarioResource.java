package com.imatiello.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imatiello.minhasfinancas.api.dto.UsuarioDTO;
import com.imatiello.minhasfinancas.exception.ErroAutenticacao;
import com.imatiello.minhasfinancas.exception.RegraNegocioException;
import com.imatiello.minhasfinancas.model.entity.Usuario;
import com.imatiello.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	private UsuarioService service;
	
	public UsuarioResource(UsuarioService service) {
		
		this.service= service;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar (@RequestBody UsuarioDTO dto) { //olhar novamente 
	try {	
		
		Usuario usuarioAutenticado= service.autenticar(dto.getEmail(), dto.getSenha());
	return ResponseEntity.ok(usuarioAutenticado);
	}catch (ErroAutenticacao e) { //olhar a classe ErroAutenticacao
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	}
	
	
	
	
	@PostMapping
	
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		
		Usuario usuario= Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();
		try {
			Usuario usuarioSalvo=service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}