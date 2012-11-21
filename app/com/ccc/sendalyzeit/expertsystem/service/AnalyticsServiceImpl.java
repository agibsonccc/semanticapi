package com.ccc.sendalyzeit.expertsystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ccc.nlp.freya.service.UimaService;
import com.ccc.relationextraction.semantic.SemanticObservationService;
import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.AnalyticsService;

@Service("analyticsService")
public class AnalyticsServiceImpl implements AnalyticsService {
	@Inject
	private UimaService uimaService;
	@Inject
	private SemanticObservationService semanticObservations;

	public Collection<Concept> observeConcepts(String text) throws Exception {
		Map<String, Set<String>> map = semanticObservations.map(text);
		List<Concept> concepts = new ArrayList<Concept>();
		Map<String, SemanticEntity> entities = entities(getEntities(map));
		Set<String> get = map.get(SemanticObservationService.CONCEPTS);
		for (String s : get) {
			concepts.add(getConcept(s, entities));
		}
		return concepts;
	}

	private Concept getConcept(String s, Map<String, SemanticEntity> map) {
		Concept ret = new Concept();
		String concept = s.substring(0, s.indexOf('('));
		ret.setName(concept);
		String first = s.substring(s.indexOf('(') + 1, s.indexOf(','));
		String second = s.substring(s.indexOf(',') + 1, s.length() - 1);
		SemanticEntity firstEntity = map.get(first), secondEntity = map
				.get(second);
		if (firstEntity == null) {
			firstEntity = new SemanticEntity();
			firstEntity.setType("what");
			firstEntity.setName(first);
			firstEntity.setId(0);
		}
		if (secondEntity == null) {
			secondEntity = new SemanticEntity(0L, second, "what");
		}

		ret.setFirst(firstEntity);
		ret.setSecond(secondEntity);
		return ret;
	}

	private Collection<SemanticEntity> getEntities(Map<String, Set<String>> map) {
		List<SemanticEntity> entities = new ArrayList<SemanticEntity>();

		String[] keys = { SemanticObservationService.WHO,
				SemanticObservationService.WHEN,
				SemanticObservationService.WHERE,
				SemanticObservationService.WHO };
		for (String s : keys) {
			Set<String> set = map.get(s);
			if (set == null || set.isEmpty())
				continue;
			for (String s2 : set) {
				SemanticEntity entity = new SemanticEntity();
				entity.setType(s);
				entity.setName(s2);
				entities.add(entity);
			}
		}
		return entities;
	}

	private Map<String, SemanticEntity> entities(
			Collection<SemanticEntity> entities) {
		System.out.println("Entities " + entities);
		Map<String, SemanticEntity> map = new HashMap<String, SemanticEntity>();
		for (SemanticEntity e : entities)
			map.put(e.getName(), e);
		return map;
	}

	public Collection<SemanticEntity> observeEntities(String text)
			throws Exception {
		Map<String, Set<String>> map = semanticObservations.map(text);
		return getEntities(map);
	}

}
