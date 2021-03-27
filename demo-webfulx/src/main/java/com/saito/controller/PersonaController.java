package com.saito.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saito.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {
	private static final Logger log = LoggerFactory.getLogger(PersonaController.class);

	@GetMapping("/mostrar")
	public Mono<Persona> mostrar() {
		return Mono.just(new Persona(1, "Jose"));
	}

	@GetMapping
	public Flux<Persona> listar() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Juan"));
		personas.add(new Persona(1, "Julio"));
		Flux<Persona> personaFlux = Flux.fromIterable(personas);
		return personaFlux;
	}

	@GetMapping("/response")
	public Mono<ResponseEntity<Flux<Persona>>> listarEntity() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Juan"));
		personas.add(new Persona(1, "Julio"));
		Flux<Persona> personaFlux = Flux.fromIterable(personas);
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(personaFlux));
	}

	@DeleteMapping("/{modo}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("modo") Integer modo){
		return buscarPersona(modo)
				.flatMap(p -> {
					return eliminar(p)
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
				}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

	public Mono<Persona> eliminar(Persona p) {
		log.info("Eliminando" + p.getIdPersona() + " - " + p.getNombre());
		return Mono.empty();
	}

	public Mono<Persona> buscarPersona(Integer modo) {
		if (modo == 1) {
			return Mono.just(new Persona(1, "ok"));
		} else {
			return Mono.empty();
		}
	}

}
