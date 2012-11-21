package controllers;

import static play.libs.Json.toJson;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.resource.ResourceInitializationException;

import play.api.modules.spring.Spring;
import play.mvc.Result;
import play.mvc.Controller;

import com.ccc.relationextraction.semantic.SemanticObservationService;
import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.AnalyticsService;
import com.ccc.sendalyzeit.expertsystem.service.api.ConceptRepository;
import com.ccc.sendalyzeit.expertsystem.service.api.EntityRepository;
import com.ccc.sentimentdictionary.SentimentService;
import com.github.jmkgreen.morphia.logging.MorphiaLoggerFactory;
import com.github.jmkgreen.morphia.logging.slf4j.SLF4JLogrImplFactory;

public class ObservationController extends Controller {
	private static SemanticObservationService semanticObservationService;

	private static AnalyticsService analyticsService;

	private static SentimentService sentimentService;

	private static EntityRepository entityRepo;

	private static ConceptRepository conceptRepo;

	public static void initBeans() {
		MorphiaLoggerFactory.reset();
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
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

	public static Result map() throws AnalysisEngineProcessException,
			ResourceInitializationException, CollectionException, IOException,
			Exception {
		String text = request().body().asJson().get("text").asText();
		Map<String, Collection<? extends Object>> map = new HashMap<String, Collection<? extends Object>>();
		map.put("concepts", analyticsService.observeConcepts(text));
		map.put("entities", analyticsService.observeEntities(text));
		return (Result) ok(toJson(map));
	}

	public static Result entities() throws Exception {
		String text = request().body().asJson().get("text").asText();
		Collection<SemanticEntity> entities = analyticsService
				.observeEntities(text);
		entities = new HashSet<SemanticEntity>(entities);
		for (SemanticEntity e : entities)
			entityRepo.addSemanticEntity(e);

		return (Result) ok(toJson(entities));
	}

	public static Result concepts() throws Exception {
		String text = request().body().asJson().get("text").asText();
		Collection<Concept> concepts = analyticsService.observeConcepts(text);
		concepts = new HashSet<Concept>(concepts);
		for (Concept c : concepts)
			conceptRepo.addConcept(c);
		return (Result) ok(toJson(concepts));
	}

	public static Result sentiment() throws Exception {
		String text = request().body().asJson().get("text").asText();

		edu.berkeley.nlp.util.Pair<String, Double> pair = sentimentService
				.classify(text);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("class", pair.getFirst());
		ret.put("score", String.valueOf(pair.getSecond()));
		return (Result) ok(toJson(ret));
	}

	public static Result synset() throws Exception {
		String text = request().body().asJson().get("text").asText();
		return (Result) ok(toJson(semanticObservationService.synset(text)));
	}
	
	public static Result textinfo() throws Exception {
		String text=request().body().asJson().get("text").asText();
		return (Result) ok(toJson(semanticObservationService.textinfo(text)));
	}

}
