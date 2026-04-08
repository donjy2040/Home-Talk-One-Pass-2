package com.hometalk.onepass.inquiry.dto;

import com.hometalk.onepass.inquiry.entity.Complaint;
import com.hometalk.onepass.inquiry.entity.Inquiry;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDto {
    private Long userId;
    private String title;
    private String category;
    private String content;
    private String answer;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InquiryDto fromEntity(Inquiry inquiry) {
        return InquiryDto.builder()
                .userId(inquiry.getUser().getId())
                .title(inquiry.getTitle())
                .category(inquiry.getCategory())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .status(inquiry.getStatus())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .build();

    }

// ... 기존 코드들 ...

    public Inquiry toEntity() {
        return Inquiry.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .status(this.status != null ? this.status : "미답변") // 기본값 세팅
                .build();
    }
}
