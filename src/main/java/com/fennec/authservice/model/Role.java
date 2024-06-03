package com.fennec.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "role")
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "role_name")
	private String name;

	public enum Values {
		ADMIN(1L), BASIC(2L);

		long roleId;

		private Values(long roleId) {
			this.roleId = roleId;
		}
	}
}
