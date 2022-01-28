package everyone.delivery.demo.domain.file;

import everyone.delivery.demo.common.configuration.FileConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/***
 * 원본 파일 처리를 위한 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileConfiguration fileConfiguration;

    public String saveMultipartFile(MultipartFile mpFiles) throws IOException {
        String fileUuid = UUID.randomUUID().toString();

        Path fileDirectoryPath = Paths.get(fileConfiguration.getPath()).toAbsolutePath().normalize();
        if(Files.notExists(fileDirectoryPath))
            Files.createDirectories(fileDirectoryPath);
        Path filePath = fileDirectoryPath.resolve(fileUuid).normalize();

        //file uuid가 고유하기 때문에 사실상 덮어쓸 일이 없음(파일은 수정의 개념이 없고 추가 삭제에 대한 개념만 있음)
        Files.copy(mpFiles.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileUuid;
    }
}
