package com.ccc.sendalyzeit.expertsystem.service.api;

import java.util.List;

import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
/**
 * Repository for 
 * @author agibsonccc
 *
 */
public interface EntityRepository {
	
	
	
	public void addSemanticEntity(SemanticEntity entity);
	
	public List<SemanticEntity> entities();
	
	public SemanticEntity findById(Long id);
	
	/**
	 * Find an entity by type
	 * @param type (who,what,where,when,organization,location,event)
	 * @return a list of entities with the given type or an empty list
	 */
	public List<SemanticEntity> findByType(String type);
	/**
	 * Find an entity by name
	 * @param name the name of the entity to find
	 * @return the name of the entity
	 */
	public List<SemanticEntity> findByName(String name);
	/**
	 * Find an entity by name or type
	 * @param name the name of the entity
	 * @param type (who,what,where,when,organization,location,event)
	 * @return entities with the given name and type or an empty list
	 */
	public List<SemanticEntity> findByNameAndType(String name,String type);
	/**
	 * Delete an entity with the given id
	 * @param id the id of the entity to delete
	 */
	public void deleteSemanticEntity(Long id);
}
