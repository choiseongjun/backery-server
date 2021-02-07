package com.bakery.pj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/resource")
public class ResourceController {

	@GetMapping(path = "/consume")
	public ResponseEntity consume() {
		return ok("resource here");
	}

}
