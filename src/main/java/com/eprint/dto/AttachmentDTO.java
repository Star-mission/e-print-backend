package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AttachmentDTO {

    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
}
