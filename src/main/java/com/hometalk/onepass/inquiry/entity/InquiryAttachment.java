package com.hometalk.onepass.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "kjh_inquiry_attachment")
public class InquiryAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry; // Complaint가 아닌 Inquiry와 연결!

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String storedFileName;

    @Column(nullable = false)
    private String filePath;
}