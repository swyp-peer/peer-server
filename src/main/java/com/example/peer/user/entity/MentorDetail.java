package com.example.peer.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_detail_id")
	private Long id;

	private String nickname;

	private String position;

	private String introduction;

	private Boolean isAccepted;

	@Builder
	public MentorDetail(String nickname, String position, String introduction) {
		this.nickname = nickname;
		this.position = position;
		this.introduction = introduction;
		this.isAccepted = Boolean.FALSE;
	}
}
