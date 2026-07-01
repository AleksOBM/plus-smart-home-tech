package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/// Сенсор
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "sensors")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor {

	/// Идентификатор сенсора
	@Id
	String id;

	/// Идентификатор хаба
	@Column(nullable = false)
	String hubId;

	// region equals & hashcode

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;

		if (org.hibernate.Hibernate.getClass(this)
				!= org.hibernate.Hibernate.getClass(o)) {
			return false;
		}

		Sensor other = (Sensor) o;

		return id != null && id.equals(other.id);
	}

	@Override
	public final int hashCode() {
		return org.hibernate.Hibernate.getClass(this).hashCode();
	}

	// endregion equals & hashcode
}
