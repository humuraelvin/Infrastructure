package rw.ac.rca.submission.onlinesubmission.utils;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUploadUtil {
    private static final String UPLOAD_DIRECTORY = "submission_files";
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/zip"
    );
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public static String getUploadDirectory() {
        String baseDir = System.getProperty("catalina.base");
        return Paths.get(baseDir, "webapps", UPLOAD_DIRECTORY).toString();
    }

    public static String saveFile(Part filePart, String studentId, String assignmentId) throws IOException {
        // Validate file type
        String contentType = filePart.getContentType();
        if (!ALLOWED_FILE_TYPES.contains(contentType)) {
            throw new IOException("Invalid file type. Allowed types: PDF, XLSX, PPTX, ZIP");
        }

        // Validate file size
        if (filePart.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds maximum limit of 10MB");
        }

        // Create directories if they don't exist
        String uploadPath = getUploadDirectory();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique filename
        String originalFileName = getSubmittedFileName(filePart);
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = String.format("%s_%s_%s_%s%s",
                studentId,
                assignmentId,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                UUID.randomUUID().toString().substring(0, 8),
                fileExtension);

        // Save file
        Path filePath = Paths.get(uploadPath, uniqueFileName);
        Files.copy(filePart.getInputStream(), filePath);

        return filePath.toString();
    }

    private static String getSubmittedFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    public static void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidFileType(Part part, String allowedTypes) {
        String contentType = part.getContentType();
        String fileExtension = part.getSubmittedFileName()
                .substring(part.getSubmittedFileName().lastIndexOf(".") + 1);

        return allowedTypes.toLowerCase().contains(fileExtension.toLowerCase());
    }

}
