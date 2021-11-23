package com.opentext.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dm_document")
public class DmDocument extends DmSysObject{
		
		private String ownerId;
		private long storeId;
		private long dmrContentId;
		@Column(name = "r_content_id", nullable = true)
		public long getDmrContentId() {
			return dmrContentId;
		}
		public void setDmrContentId(long dmrContentId) {
			this.dmrContentId = dmrContentId;
		}
		private String version;
		private String aclId;
		private boolean checkout;
		@Column(name = "checkout", nullable = true)
		public boolean isCheckout() {
			return checkout;
		}
		public void setCheckout(boolean checkout) {
			this.checkout = checkout;
		}
		
		
		
		
		@Column(name = "a_storage_type", nullable = true)
		public long getStoreId() {
			return storeId;
		}
		public void setStoreId(long contentStoreId) {
			this.storeId = contentStoreId;
		}
		
		@Column(name = "acl_id", nullable = true)
		public String getAclId() {
			return aclId;
		}
		public void setAclId(String aclId) {
			this.aclId = aclId;
		}
		

		}
