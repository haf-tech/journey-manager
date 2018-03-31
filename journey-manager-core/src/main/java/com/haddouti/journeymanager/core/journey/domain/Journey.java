package com.haddouti.journeymanager.core.journey.domain;

import java.util.Date;
import java.util.List;

/**
 * Journey entity represents a Journey wiht all relevant information.
 *
 */
public class Journey {

	private Long id;
	private Long userId;

	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Date createdAt;
	private Date updatedAt;

	private String positionStart;
	private String positionEnd;

	/**
	 * Possible segments to separate a journey in multiple fragments.
	 */
	private List<Journey> segments;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Journey [id=").append(id).append(", userId=").append(userId).append(", title=").append(title)
				.append(", description=").append(description).append(", startDate=").append(startDate)
				.append(", endDate=").append(endDate).append(", createdAt=").append(createdAt).append(", updatedAt=")
				.append(updatedAt).append(", positionStart=").append(positionStart).append(", positionEnd=")
				.append(positionEnd).append(", segments=").append(segments).append("]");
		return builder.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getPositionStart() {
		return positionStart;
	}

	public void setPositionStart(String positionStart) {
		this.positionStart = positionStart;
	}

	public String getPositionEnd() {
		return positionEnd;
	}

	public void setPositionEnd(String positionEnd) {
		this.positionEnd = positionEnd;
	}

	public List<Journey> getSegments() {
		return segments;
	}

	public void setSegments(List<Journey> segments) {
		this.segments = segments;
	}

}
