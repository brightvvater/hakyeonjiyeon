package project.hakyeonjiyeon.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.LessonFile;
import project.hakyeonjiyeon.domain.MainStatus;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.domain.TeacherFile;
import project.hakyeonjiyeon.repository.FileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class FileUploadService {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    private final AmazonS3Client amazonS3Client;

    private final FileRepository fileRepository;


    public List<TeacherFile> uploadTeacherFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<TeacherFile> teacherFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                teacherFiles.add(uploadTeacherFile(multipartFile));
            }
        }
        return teacherFiles;
    }


    public TeacherFile uploadTeacherFile(MultipartFile multipartFile) throws IOException {

        //파일 s3에 업로드
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFileName = multipartFile.getOriginalFilename();

        String key = createStoreFileName(originalFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileName = amazonS3Client.getUrl(bucket, key).toString();


        //파일 db저장
        TeacherFile uploadFile = TeacherFile.builder()
                .mainStatus(MainStatus.COMMON)
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();
        fileRepository.saveTeacher(uploadFile);

        return uploadFile;
    }


    public List<LessonFile> uploadLessonFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<LessonFile> lessonFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                lessonFiles.add(uploadLessonFile(multipartFile));
            }
        }
        return lessonFiles;
    }

    public LessonFile uploadLessonFile(MultipartFile multipartFile) throws IOException {

        //파일 s3에 업로드
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFileName = multipartFile.getOriginalFilename();

        String key = createStoreFileName(originalFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileName = amazonS3Client.getUrl(bucket, key).toString();


        //파일 db저장
        LessonFile uploadFile = LessonFile.builder()
                .mainStatus(MainStatus.COMMON)
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();

        fileRepository.saveLesson(uploadFile);

        return uploadFile;
    }



    public String createStoreFileName(String originalFileName) {
        int index = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(index + 1);

        String key =  dir +  UUID.randomUUID() + "." + ext;
        return key;

    }
}