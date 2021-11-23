package com.opentext.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dmr_content")
public class DmrContent extends DmSysObject{
		private String parentId;
		@Column(name = "parent_id", nullable = false)
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		
		@Column(name = "content_path", nullable = false)
		public String getDocumentPath() {
			return documentPath;
		}
		public void setDocumentPath(String documentPath) {
			this.documentPath = documentPath;
		}
		@Column(name = "r_version", nullable = false)
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		private long id;
		private String documentPath;
		private String version;
		

		}