package com.example.peer.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	private Long id;

	private String keyword;

	@Builder
	public Keyword(String keyword) {
		this.keyword = keyword;
	}
}
