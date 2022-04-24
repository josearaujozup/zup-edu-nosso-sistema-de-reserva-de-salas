package br.com.zup.edu.nossosistemadereservadesala.controller;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.nossosistemadereservadesala.sala.Sala;
import br.com.zup.edu.nossosistemadereservadesala.sala.SalaRepository;

@RestController
@RequestMapping("/salas")
public class SalaController {
	
	private final SalaRepository repository;

	public SalaController(SalaRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	@PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long idSala){
		
		Sala sala = repository.findById(idSala).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sala não encontrado"));
		
		sala.reservar();
		
		repository.save(sala);
		
		return ResponseEntity.noContent().build();
	}
	
}
