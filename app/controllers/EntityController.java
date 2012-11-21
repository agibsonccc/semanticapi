package controllers;

import static play.libs.Json.toJson;

import java.util.Collections;

import play.api.modules.spring.Spring;
import play.mvc.Result;
import play.mvc.Controller;

import com.ccc.sendalyzeit.expertsystem.model.SemanticEntity;
import com.ccc.sendalyzeit.expertsystem.service.api.EntityRepository;
import com.github.jmkgreen.morphia.logging.MorphiaLoggerFactory;
import com.github.jmkgreen.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EntityController extends Controller {
	

	private static EntityRepository entityRepo;
	
	public static void initBeans() {
		MorphiaLoggerFactory.reset();
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
		entityRepo=Spring.getBeanOfType(EntityRepository.class);
	}
	static {
		initBeans();
	}

	public static Result insert() {
		String entity=request().body().asJson().toString();
		Gson gson=new GsonBuilder().create();
		SemanticEntity e=gson.fromJson(entity, SemanticEntity.class);
		entityRepo.addSemanticEntity(e);
		return (Result) ok(toJson(Collections.singletonMap("status", "inserted")));
	}

	public static Result getById(String id) {
		SemanticEntity e = entityRepo.findById(Long.parseLong(id));
		System.out.println("Entity " + e);
		if (e != null)
			return (Result) ok(toJson(e));
		else return (Result) ok(toJson(Collections.emptyMap()));
	}

	public static Result delete(String id) {
		entityRepo.deleteSemanticEntity(Long.parseLong(id));
		return (Result) ok(toJson(Collections.singletonMap("status", "deleted")));
	}
	public static Result allEntities() {
		return (Result) ok(toJson(entityRepo.entities()));
		
	}
	public static Result entities(String name) {
		if (name == null)
			return (Result) ok(toJson(entityRepo.entities()));
		else
			return (Result) ok(toJson(entityRepo.findByName(name)));
	}

}
