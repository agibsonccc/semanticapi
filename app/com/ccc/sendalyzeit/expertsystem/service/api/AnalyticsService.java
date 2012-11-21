package com.ccc.sendalyzeit.expertsystem.service.api;

import java.util.Collection;

import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
/**
 * Semantic text analytics for retrieving entities
 * and concepts from text
 * @author Adam Gibson
 *
 */
public interface AnalyticsService {
	/**
	 * Extract concepts from text
	 * @param text the text to analyze
	 * @return any concepts extracted from the text
	 * @throws Exception
	 */
	public Collection<Concept> observeConcepts(String text) throws Exception;
	
	
	/**
	 * Extract entities from text
	 * @param text the text to analyze
	 * @return a set of named entities extracted from text
	 * @throws Exception
	 */
	public Collection<SemanticEntity> observeEntities(String text) throws Exception;
	
	
	
}
