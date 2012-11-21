package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import play.api.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;

import com.ccc.sendalyzeit.expertsystem.model.Concept;
import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.ConceptRepository;
import com.ccc.sendalyzeit.expertsystem.service.api.EntityRepository;
import com.github.jmkgreen.morphia.logging.MorphiaLoggerFactory;
import com.github.jmkgreen.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConceptController extends Controller {
	
	private static ConceptRepository conceptRepository;
	private static EntityRepository entityRepo;

	public static void initBeans() {
		MorphiaLoggerFactory.reset();
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
		entityRepo=Spring.getBeanOfType(EntityRepository.class);
		conceptRepository=Spring.getBeanOfType(ConceptRepository.class);
	}
	static {
		initBeans();
	}

	public static Result insert() {
		String concept=request().body().asJson().toString();
		Gson gson = new GsonBuilder().create();
		Concept c = gson.fromJson(concept, Concept.class);
		conceptRepository.addConcept(c);
		return (Result) created(toJson(Collections.singletonMap("status", "inserted")));
	}

	public static Result byId(String id) {
		return (Result) ok(toJson(conceptRepository.findById(Long.parseLong(id))));
	}
	public static Result allConcepts() {
		return (Result) ok(toJson(conceptRepository.concepts()));
	}

	public static Result concepts(String name, Long id) {

		if (name != null && id != null) {
			SemanticEntity e=entityRepo.findById(id);
			Collection<Concept> concepts =conceptRepository.conceptsForSemanticEntity(e);
			if (concepts == null)
				return (Result) ok(toJson(Collections.emptyList()));
			List<Concept> ret = new ArrayList<Concept>();
			for (Concept c : concepts) {
				if (c.getName().equals(name))
					ret.add(c);
			}
			return (Result) ok(toJson(ret));
		} else if (name == null)
			return (Result) ok(toJson(conceptRepository.concepts()));
		else if (id != null) {
			SemanticEntity e=entityRepo.findById(id);

			return (Result) ok(toJson(conceptRepository.conceptsForSemanticEntity(e)));
		} else
			return (Result) ok(toJson(conceptRepository.findByName(name)));
	}

	public static Result delete(String id) {
		conceptRepository.deleteConcept(Long.parseLong(id));
		return (Result) ok(toJson(Collections.singletonMap("status", "deleted")));
	}

}
