package com.lithium.esb.compliance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The LswEvent class stores the information relating to the LSW Events that are read from SQS.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "lswevent", type = "lswevent", shards = 1, replicas = 0, indexStoreType = "memory")
public class LswEvent implements Comparable<LswEvent> {
	
	/** The _id. */
	@Id
	private Long _id;
	
	/** The _score. */
	private String _score;
	
	/** The source. */
	@JsonProperty("_source")
	private Source source;

	/**
	 * Gets the _id.
	 *
	 * @return the _id
	 */
	public Long get_id() {
		return _id;
	}

	/**
	 * Sets the _id.
	 *
	 * @param _id the new _id
	 */
	public void set_id(Long _id) {
		this._id = _id;
	}

	/**
	 * Gets the _score.
	 *
	 * @return the _score
	 */
	public String get_score() {
		return _score;
	}

	/**
	 * Sets the _score.
	 *
	 * @param _score the new _score
	 */
	public void set_score(String _score) {
		this._score = _score;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * The Class Source.
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	private class Source {
		
		/**
		 * Instantiates a new source.
		 */
		@SuppressWarnings("unused")
		public Source() {
		}

		/** The id. */
		private Long id;
		
		/** The case id. */
		private Integer caseId;
		
		/** The case uuid. */
		private String caseUuid;
		
		/** The user id. */
		private Integer userId;
		
		/** The team id. */
		private Integer teamId;
		
		/** The company key. */
		private String companyKey;

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * Gets the case id.
		 *
		 * @return the case id
		 */
		public Integer getCaseId() {
			return caseId;
		}

		/**
		 * Sets the case id.
		 *
		 * @param caseId the new case id
		 */
		public void setCaseId(Integer caseId) {
			this.caseId = caseId;
		}

		/**
		 * Gets the case uuid.
		 *
		 * @return the case uuid
		 */
		public String getCaseUuid() {
			return caseUuid;
		}

		/**
		 * Sets the case uuid.
		 *
		 * @param caseUuid the new case uuid
		 */
		public void setCaseUuid(String caseUuid) {
			this.caseUuid = caseUuid;
		}

		/**
		 * Gets the user id.
		 *
		 * @return the user id
		 */
		public Integer getUserId() {
			return userId;
		}

		/**
		 * Sets the user id.
		 *
		 * @param userId the new user id
		 */
		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		/**
		 * Gets the team id.
		 *
		 * @return the team id
		 */
		public Integer getTeamId() {
			return teamId;
		}

		/**
		 * Sets the team id.
		 *
		 * @param teamId the new team id
		 */
		public void setTeamId(Integer teamId) {
			this.teamId = teamId;
		}

		/**
		 * Gets the company key.
		 *
		 * @return the company key
		 */
		public String getCompanyKey() {
			return companyKey;
		}

		/**
		 * Sets the company key.
		 *
		 * @param companyKey the new company key
		 */
		public void setCompanyKey(String companyKey) {
			this.companyKey = companyKey;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Source [id=" + id + ", caseId=" + caseId + ", caseUuid=" + caseUuid + ", userId=" + userId
					+ ", teamId=" + teamId + ", casePriority=" + "companyKey=" + companyKey + "]";
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LswEvent [_id=" + _id + ", _score=" + _score + ", source=" + source + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
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
