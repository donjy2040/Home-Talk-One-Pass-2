package com.hometalk.onepass.inquiry.service;

import com.hometalk.onepass.auth.entity.User;
import com.hometalk.onepass.auth.repository.UserRepository;
import com.hometalk.onepass.inquiry.dto.ComplaintDto;
import com.hometalk.onepass.inquiry.entity.Complaint;
import com.hometalk.onepass.inquiry.entity.ComplaintAttachment;
import com.hometalk.onepass.inquiry.repository.ComplaintAttachmentRepository;
import com.hometalk.onepass.inquiry.repository.ComplaintRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final ComplaintAttachmentRepository attachmentRepository;

    /*
        민원 등록
     */
    @Transactional
    public Long register(ComplaintDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("작성 유저를 찾을 수 없습니다. ID: " + dto.getUserId()));
        Complaint complaint = Complaint.builder()
                .user(user)
                .title(dto.getTitle())
                .category(dto.getCategory())
                .content(dto.getContent())
                .isSecret(dto.isSecret())
                .status("접수완료") // 초기 상태값
                .build();
        return complaintRepository.save(complaint).getId();
    }

    /*
        전체 민원 조회
     */
    public List<ComplaintDto> findAll() {
        return complaintRepository.findAll().stream()
                .map(ComplaintDto::fromEntity)
                .toList();
    }

    /*
        특정 민원 상세 조회
     */
    public Complaint findOne(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 민원을 찾을 수 없습니다."));
    }

    /*
         관리자 답변 등록 (상태 변경 포함)
     */
    @Transactional
    public void respond(Long id, String response) {
        Complaint complaint = findOne(id);
        complaint.addResponse(response);
    }

    /*
        민원 삭제
     */
    @Transactional
    public void delete(Long id) {
        complaintRepository.deleteById(id);
    }

    /*
        파일 업로드
     */
    // 파일 저장 경로
    private final String uploadPath = "C:/onepass/uploads/";

    @Transactional
    public void saveWithFiles(ComplaintDto dto, List<MultipartFile> files) throws IOException {

       Complaint complaint = dto.toEntity();
        // 1. 민원글 먼저 저장
        complaintRepository.save(complaint);

        // 2. 파일 처리 (if 문이 메서드 안으로 들어와야 해요!)
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 이름 중복 방지용 UUID
                    String uuid = UUID.randomUUID().toString();
                    String savedName = uuid + "_" + file.getOriginalFilename();

                    // 실제 폴더에 파일 저장
                    file.transferTo(new File(uploadPath + savedName));

                    // 3. DB에 파일 정보 기록
                    // (아까 만든 빌더나 생성자를 사용하세요)
                    ComplaintAttachment attach = ComplaintAttachment.builder()
                            .originFileName(file.getOriginalFilename())
                            .storedFileName(savedName)
                            .filePath(uploadPath + savedName)
                            .complaint(complaint)
                            .build();

                    attachmentRepository.save(attach);
                }
            }
        }
    }
}
