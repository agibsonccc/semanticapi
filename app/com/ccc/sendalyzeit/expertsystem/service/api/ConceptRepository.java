package com.ccc.sendalyzeit.expertsystem.service.api;

import java.util.Collection;

import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
/**
 * DAO for concepts
 * @author Adam Gibson
 *
 */
public interface ConceptRepository {

	
	public void addConcept(Concept concept);
	
	public void deleteConcept(Long id);
	
	public Collection<Concept> concepts();
	
	public Concept findById(Long id);
	
	public Collection<Concept> findByName(String name);
	
	public Collection<Concept> conceptsForSemanticEntity(SemanticEntity entity);
	
}
