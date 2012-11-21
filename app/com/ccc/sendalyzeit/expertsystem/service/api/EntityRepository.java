package com.ccc.sendalyzeit.expertsystem.service.api;

import java.util.List;

import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;

public interface EntityRepository {
	
	
	
	public void addSemanticEntity(SemanticEntity entity);
	
	public List<SemanticEntity> entities();
	
	public SemanticEntity findById(Long id);
	
	public List<SemanticEntity> findByName(String name);
	
	public void deleteSemanticEntity(Long id);
}
