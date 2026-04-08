package com.hometalk.onepass.inquiry.entity;

import jakarta.persistence.*;

@Entity
public class ComplaintAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originFileName;
    private String storedFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    public static ComplaintAttachmentBuilder builder() {
        return new ComplaintAttachmentBuilder();
    }

    public static class ComplaintAttachmentBuilder {
        private String originFileName;
        private String storedFileName;
        private String filePath;
        private Complaint complaint;

        public ComplaintAttachmentBuilder originFileName(String originFileName) {
            this.originFileName = originFileName;
            return this;
        }

        public ComplaintAttachmentBuilder storedFileName(String storedFileName) {
            this.storedFileName = storedFileName;
            return this;
        }

        public ComplaintAttachmentBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public ComplaintAttachmentBuilder complaint(Complaint complaint) {
            this.complaint = complaint;
            return this;
        }

        public ComplaintAttachment build() {
            ComplaintAttachment attachment = new ComplaintAttachment();
            attachment.originFileName = this.originFileName;
            attachment.storedFilePath = this.filePath;
            attachment.complaint = this.complaint;
            return attachment;
        }
    }
}
