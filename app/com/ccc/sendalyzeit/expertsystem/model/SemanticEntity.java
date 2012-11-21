package com.ccc.sendalyzeit.expertsystem.model;

import java.io.Serializable;
import play.libs.F;
import play.libs.F.Option;
import play.mvc.QueryStringBindable;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
@Entity("semanticentity")
public class SemanticEntity  implements QueryStringBindable<SemanticEntity>, Serializable {

	public SemanticEntity() {
		setId((long)Math.random());
		
	}
	
	
	public SemanticEntity(Long id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -6491283819609612445L;
	@Id
	private Long id=0L;

	private String name;

	private String type;


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SemanticEntity other = (SemanticEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{\"name:\"\"" + name + "\",\"type\":\"" + type + "\",\"id\":"
				+ id + "}";
	}

	public String javascriptUnbind() {
		return toString();
	}

	public String unbind(String key, SemanticEntity entity) {
		return key = entity.toString();
	}

	public Option<SemanticEntity> bind(String key, java.util.Map<String, String[]> data) {
		String[] vs = data.get(key);
		if (vs != null && vs.length > 0) {
			String v = vs[0];
			if(key.equals("type"))
				setType(v);
			else if(key.equals("name"))
				setName(v);
			else if(key.equals("id"))
				setId(Long.parseLong(v));
			return F.Some(this);
		}
		return null;
	}

	public String unbind(String key) {
		if(key.equals("type")) {
			String type=getType();
			setType(null);
			return type;
		}
		else if(key.equals("name")) {
			String name=getName();
			setName(null);
			return name;
		}
		else if(key.equals("id")) {
			long id=getId();
			setId(0);
			return String.valueOf(id);
		}
		return "";
	}

}
