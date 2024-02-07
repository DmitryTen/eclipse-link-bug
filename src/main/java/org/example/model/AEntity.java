package org.example.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Abstract base class for all entities.
 * 
 * @author halderm
 *
 */
@MappedSuperclass
@UuidGenerator(name = "ABSTRACT_ENTITY_ID")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder
@NoArgsConstructor
public abstract class AEntity  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ABSTRACT_ENTITY_ID")
	/**
	 * UUID of the JPA Entity
	 */
	protected String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}


	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + uuid;
	}

}
