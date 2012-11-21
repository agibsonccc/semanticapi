package com.ccc.sendalyzeit.expertsystem.service.api;

import java.lang.reflect.Method;

import play.Application;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http.Request;

public class Globals extends GlobalSettings {

	@Override
	public void beforeStart(Application app) {
		super.beforeStart(app);
	}

	@Override
	public Action<?> onRequest(Request request, Method actionMethod) {
		return super.onRequest(request, actionMethod);
	}

	@Override
	public void onStart(Application app) {
		super.onStart(app);
	}

	@Override
	public void onStop(Application app) {
		super.onStop(app);
	}

}
