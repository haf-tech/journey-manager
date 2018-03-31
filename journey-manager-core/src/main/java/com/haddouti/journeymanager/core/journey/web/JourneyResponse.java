package com.haddouti.journeymanager.core.journey.web;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a {@link JourneyItem} response.
 *
 */
@XmlRootElement(name = "JourneyResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class JourneyResponse {

	private Status status;

	@XmlElementWrapper(name = "journies")
	@XmlElement(name = "journey")
	private List<JourneyItem> journies;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JourneyResponse [status=").append(status).append(", journies=").append(journies).append("]");
		return builder.toString();
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "JourneyItem", propOrder = {})
	public static class JourneyItem {
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

		private List<JourneyItem> segments;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("JourneyItem [id=").append(id).append(", userId=").append(userId).append(", title=")
					.append(title).append(", description=").append(description).append(", startDate=").append(startDate)
					.append(", endDate=").append(endDate).append(", createdAt=").append(createdAt)
					.append(", updatedAt=").append(updatedAt).append(", positionStart=").append(positionStart)
					.append(", positionEnd=").append(positionEnd).append(", segments=").append(segments).append("]");
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

		public List<JourneyItem> getSegments() {
			return segments;
		}

		public void setSegments(List<JourneyItem> segments) {
			this.segments = segments;
		}

	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<JourneyItem> getJournies() {
		return journies;
	}

	public void setJournies(List<JourneyItem> journies) {
		this.journies = journies;
	}

}
