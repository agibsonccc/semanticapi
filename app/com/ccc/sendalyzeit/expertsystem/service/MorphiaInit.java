package com.ccc.sendalyzeit.expertsystem.service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.stereotype.Component;

import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.logging.MorphiaLoggerFactory;
import com.github.jmkgreen.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.Mongo;

@Component("morphiainit")
public class MorphiaInit {
	@Inject
	private Morphia morphia;
	@Inject
	private Mongo mongo;

	@PostConstruct
	public void init() {
		//reset slf4j
		MorphiaLoggerFactory.reset();
		SLF4JBridgeHandler.uninstall();
		//Ensure morphia logger gets registered
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
		//map required classes
		morphia.map(Concept.class, SemanticEntity.class);
		morphia.createDatastore(mongo, "semantic");
		
	}



}
