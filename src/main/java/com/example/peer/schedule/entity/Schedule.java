package com.example.peer.schedule.entity;

import java.time.LocalDateTime;

import com.example.peer.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long id;

	private LocalDateTime possibleSchedule;

	private Boolean isUsed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "user_id", name = "mentor_id")
	private User mentor;

	@Builder
	public Schedule(LocalDateTime possibleSchedule, User mentor) {
		this.possibleSchedule = possibleSchedule;
		this.isUsed = Boolean.FALSE;
	}

	public void UpdateMentor(User mentor) {
		this.mentor = mentor;
		//==연관관계 메서드==//
		mentor.getSchedules().add(this);
	}
}
