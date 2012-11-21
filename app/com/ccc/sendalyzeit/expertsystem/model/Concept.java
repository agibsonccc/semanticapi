package com.ccc.sendalyzeit.expertsystem.model;

import java.io.Serializable;
import java.util.Map;

import play.libs.F;
import play.libs.F.Option;
import play.mvc.QueryStringBindable;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.google.gson.Gson;
@Entity("concept")
public class Concept  implements Serializable, QueryStringBindable<Concept> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7354330136901440154L;
	@Id
	private long id=0L;

	private String name;
	@Embedded
	private SemanticEntity first;
	@Embedded
	private SemanticEntity second;


	public Concept(long id, String name, SemanticEntity first,
			SemanticEntity second) {
		super();
		this.id = id;
		this.name = name;
		this.first = first;
		this.second = second;
	}

	public Concept() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SemanticEntity getFirst() {
		return first;
	}

	public void setFirst(SemanticEntity first) {
		this.first = first;
	}

	public SemanticEntity getSecond() {
		return second;
	}

	public void setSecond(SemanticEntity second) {
		this.second = second;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Concept [id=" + id + ", name=" + name + ", first=" + first
				+ ", second=" + second + "]";
	}

	@SuppressWarnings("unchecked")
	public Option<Concept> bind(String key, Map<String, String[]> data) {
		String[] value = data.get(key);
		if (value != null && value.length >= 1) {
			if (key.equals("first")) {
				SemanticEntity e = new SemanticEntity();
				String first = value[0];
				e= new Gson().fromJson(first, SemanticEntity.class);
				setFirst(e);
				return F.Some(this);
			} else if (key.equals("second")) {
				SemanticEntity e = new SemanticEntity();
				String first = value[0];
				e= new Gson().fromJson(first, SemanticEntity.class);
				setSecond(e);
				return F.Some(this);
			}
			else if(key.equals("name")) {
				String first = value[0];
				setName(first);
				return F.Some(this);
			}
			else if(key.equals("id")) {
				String first=value[0];
				setId(Long.parseLong(first));
				return F.Some(this);
			}
		}
		return F.None();
	}

	public String unbind(String key) {
		if (key.equals("first")) {
			if (first != null) {
				SemanticEntity firstNum=getFirst();
				setFirst(null);
				return String.valueOf(firstNum);
			}
		} else if (key.equals("second")) {
			if (second != null) {
				SemanticEntity secondString = getSecond();
				setSecond(null);
				return String.valueOf(secondString);
			}
		} else if (key.equals("name")) {
			if (name != null) {
				String name2 = name;
				setName(null);
				return name2;
			}
		} else if (key.equals("id")) {
			long old = id;
			setId(0);
			return String.valueOf(old);
		}
		return null;
	}

	public String javascriptUnbind() {
		return toString();
	}

}
