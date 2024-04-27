package com.example.peer.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "mentorDetail")
	private List<KeywordMentorDetail> keywordMentorDetails = new ArrayList<>();

	@Builder
	public MentorDetail(String nickname, String position, String introduction) {
		this.nickname = nickname;
		this.position = position;
		this.introduction = introduction;
		this.isAccepted = Boolean.FALSE;
	}

	public void UpdateIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
}
