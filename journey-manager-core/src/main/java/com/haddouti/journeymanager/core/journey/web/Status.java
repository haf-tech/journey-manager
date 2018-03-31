package com.haddouti.journeymanager.core.journey.web;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents status.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class Status {

	@XmlElementWrapper(name = "status")
	@XmlElement(name = "item")
	List<StatusItem> status;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Status [status=").append(status).append("]");
		return builder.toString();
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = {})
	public static class StatusItem {

		private String code;
		private String text;
		private StatusLevel level;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("StatusItem [code=").append(code).append(", text=").append(text).append(", level=")
					.append(level).append("]");
			return builder.toString();
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public StatusLevel getLevel() {
			return level;
		}

		public void setLevel(StatusLevel level) {
			this.level = level;
		}

	}

	@XmlEnum
	public static enum StatusLevel {
		OK, WARNING, INFO, ERROR;
	}

	public List<StatusItem> getStatus() {
		return status;
	}

	public void setStatus(List<StatusItem> status) {
		this.status = status;
	}

}
