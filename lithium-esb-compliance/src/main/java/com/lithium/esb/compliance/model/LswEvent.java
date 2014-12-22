package com.lithium.esb.compliance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "lswevent", type = "lswevent", shards = 1, replicas = 0, indexStoreType = "memory")
public class LswEvent implements Comparable<LswEvent> {
	@Id
	private Long _id;
	private String _score;
	@JsonProperty("_source")
	private Source source;

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String get_score() {
		return _score;
	}

	public void set_score(String _score) {
		this._score = _score;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private class Source {
		@SuppressWarnings("unused")
		public Source() {
		}

		private Long id;
		private Integer caseId;
		private String caseUuid;
		private Integer userId;
		private Integer teamId;
		private String companyKey;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getCaseId() {
			return caseId;
		}

		public void setCaseId(Integer caseId) {
			this.caseId = caseId;
		}

		public String getCaseUuid() {
			return caseUuid;
		}

		public void setCaseUuid(String caseUuid) {
			this.caseUuid = caseUuid;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Integer getTeamId() {
			return teamId;
		}

		public void setTeamId(Integer teamId) {
			this.teamId = teamId;
		}

		public String getCompanyKey() {
			return companyKey;
		}

		public void setCompanyKey(String companyKey) {
			this.companyKey = companyKey;
		}

		@Override
		public String toString() {
			return "Source [id=" + id + ", caseId=" + caseId + ", caseUuid=" + caseUuid + ", userId=" + userId
					+ ", teamId=" + teamId + ", casePriority=" + "companyKey=" + companyKey + "]";
		}

	}

	@Override
	public String toString() {
		return "LswEvent [_id=" + _id + ", _score=" + _score + ", source=" + source + "]";
	}

	@Override
	public int compareTo(LswEvent newLswEvent) {
		if (newLswEvent.get_id() == get_id())
			return 0;
		else if (newLswEvent.get_id() < get_id())
			return -1;
		else
			return 1;
	}

}
