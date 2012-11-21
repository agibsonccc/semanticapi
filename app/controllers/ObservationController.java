package controllers;

import static play.libs.Json.toJson;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import play.api.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;

import com.ccc.relationextraction.semantic.SemanticObservationService;
import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.AnalyticsService;
import com.ccc.sendalyzeit.expertsystem.service.api.ConceptRepository;
import com.ccc.sendalyzeit.expertsystem.service.api.EntityRepository;
import com.ccc.sentimentdictionary.SentimentService;

public class ObservationController extends Controller {
	private static SemanticObservationService semanticObservationService;

	private static AnalyticsService analyticsService;

	private static SentimentService sentimentService;

	private static EntityRepository entityRepo;

	private static ConceptRepository conceptRepo;

	public static void initBeans() {
		analyticsService = Spring.getBeanOfType(AnalyticsService.class);
		sentimentService = Spring.getBeanOfType(SentimentService.class);
		semanticObservationService = Spring
				.getBeanOfType(SemanticObservationService.class);
		entityRepo = Spring.getBeanOfType(EntityRepository.class);
		conceptRepo = Spring.getBeanOfType(ConceptRepository.class);
	}

	static {
		initBeans();
	}

	public static Result map() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest(toJson(Collections.singletonMap("error",
					"no text specified")));
		// analyze concepts and entities and put them in to a map.
		Map<String, Collection<? extends Object>> map = new HashMap<String, Collection<? extends Object>>();
		map.put("concepts", analyticsService.observeConcepts(text));
		map.put("entities", analyticsService.observeEntities(text));
		return (Result) ok(toJson(map));
	}

	public static Result entities() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest();
		Collection<SemanticEntity> entities = analyticsService
				.observeEntities(text);
		// strip duplicates
		entities = new HashSet<SemanticEntity>(entities);
		for (SemanticEntity e : entities)
			entityRepo.addSemanticEntity(e);

		return (Result) ok(toJson(entities));
	}

	public static Result concepts() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest(toJson(Collections.singletonMap("error",
					"no text specified")));

		Collection<Concept> concepts = analyticsService.observeConcepts(text);
		// strip duplicates
		concepts = new HashSet<Concept>(concepts);
		// record
		for (Concept c : concepts)
			conceptRepo.addConcept(c);
		return (Result) ok(toJson(concepts));
	}

	public static Result sentiment() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest(toJson(Collections.singletonMap("error",
					"no text specified")));
		edu.berkeley.nlp.util.Pair<String, Double> pair = sentimentService
				.classify(text);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("class", pair.getFirst());
		ret.put("score", String.valueOf(pair.getSecond()));
		return (Result) ok(toJson(ret));
	}

	public static Result synset() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest(toJson(Collections.singletonMap("error",
					"no text specified")));
		return (Result) ok(toJson(semanticObservationService.synset(text)));
	}

	public static Result textinfo() throws Exception {
		String text = request().body().asJson().get("text").asText();
		if (text.isEmpty() || text == null)
			return (Result) badRequest(toJson(Collections.singletonMap("error",
					"no text specified")));
		return (Result) ok(toJson(semanticObservationService.textinfo(text)));
	}

}
