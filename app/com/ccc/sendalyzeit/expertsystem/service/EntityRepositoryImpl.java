package com.ccc.sendalyzeit.expertsystem.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.EntityRepository;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

@Repository("entityRepo")
//NEEDS TO BE HERE DUE TO ERROR IN PLAY/MORPHIA LOGGING SUPPORT. SETUP FOR LOGGING
//HAPPENS HERE. PUT THIS ON ANY AND ALL DAOS THAT HAVE TO DO WITH MORPHIA.
@DependsOn("morphiainit")
public class EntityRepositoryImpl extends BasicDAO<SemanticEntity, Long>
		implements EntityRepository {
	
	private static Logger log=LoggerFactory.getLogger(EntityRepositoryImpl.class);
	@Inject
	public EntityRepositoryImpl(Morphia morphia, Mongo mongo) {
		super(SemanticEntity.class,mongo, morphia, "semantic");
	}

	@PostConstruct
	public void init() {
		getDatastore().getCollection(SemanticEntity.class);
		log.info("Created database for Entity");

	}
	@Override
	public void addSemanticEntity(SemanticEntity entity) {
		entity.setId((long)Math.random());
		super.save(entity);
	}

	@Override
	public List<SemanticEntity> entities() {
		return super.find().asList();
	}

	@Override
	public SemanticEntity findById(Long id) {
		return ds.get(SemanticEntity.class, id);
	}

	@Override
	public List<SemanticEntity> findByName(String name) {
		return this.ds.find(name, SemanticEntity.class).asList();
	}

	@Override
	public void deleteSemanticEntity(Long id) {
		SemanticEntity e = findById(id);
		if (e != null)
			super.delete(e);
	}

	@Override
	public List<SemanticEntity> findByNameAndType(String name, String type) {
		return ds.find(SemanticEntity.class).filter("name", name).filter("type", type).asList();
	}

	@Override
	public List<SemanticEntity> findByType(String type) {
		return ds.find(SemanticEntity.class).filter("type", type).asList();
	}

}
