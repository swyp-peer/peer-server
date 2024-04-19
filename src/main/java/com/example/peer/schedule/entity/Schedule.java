package com.example.peer.schedule.entity;

import java.time.LocalDateTime;

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
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long id;

	private LocalDateTime possibleSchedule;

	private Boolean isUsed;

	@Builder
	public Schedule(LocalDateTime possibleSchedule) {
		this.possibleSchedule = possibleSchedule;
		this.isUsed = Boolean.FALSE;
	}
}
