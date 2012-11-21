package com.ccc.sendalyzeit.expertsystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.ConceptRepository;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

@Repository("conceptRepo")
// NEEDS TO BE HERE DUE TO ERROR IN PLAY/MORPHIA LOGGING SUPPORT. SETUP FOR
// LOGGING
// HAPPENS HERE. PUT THIS ON ANY AND ALL DAOS THAT HAVE TO DO WITH MORPHIA.
@DependsOn("morphiainit")
public class ConceptRepositoryImpl extends BasicDAO<Concept, Long> implements
		ConceptRepository {

	private static Logger log = LoggerFactory
			.getLogger(ConceptRepository.class);

	@Inject
	public ConceptRepositoryImpl(Morphia morphia, Mongo mongo) {
		super(Concept.class, mongo, morphia, "semantic");
	}

	@PostConstruct
	public void init() {
		this.getDatastore().getCollection(Concept.class);
		log.info("Created database for Concept");

	}

	@Override
	public void addConcept(Concept concept) {
		super.save(concept);
	}

	@Override
	public void deleteConcept(Long id) {
		super.deleteById(id);
	}

	@Override
	public Collection<Concept> concepts() {
		return super.find().asList();
	}

	@Override
	public Concept findById(Long id) {
		return super.findOne("id", id);
	}

	@Override
	public Collection<Concept> findByName(String name) {
		return this.createQuery().filter("name", name).asList();
	}

	@Override
	public Collection<Concept> conceptsForSemanticEntity(SemanticEntity entity) {
		Collection<Concept> l1= ds.find(Concept.class).field("second.id").equal(entity.getId()).asList(),
				l2=ds.find(Concept.class).field("first.id").equal(entity.getId()).asList();
		List<Concept> ret = new ArrayList<Concept>();
		ret.addAll(l1);
		ret.addAll(l2);
		return ret;
	}

}
