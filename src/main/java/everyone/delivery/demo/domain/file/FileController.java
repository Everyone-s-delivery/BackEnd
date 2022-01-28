package everyone.delivery.demo.domain.file;

import everyone.delivery.demo.common.configuration.FileConfiguration;
import everyone.delivery.demo.common.response.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/***
 * 원본파일 처리를 위한 컨트롤러
 */
@Api(tags = {"* 파일 처리 API(사용자[모집자 또는 참여자] 권한)"})
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileConfiguration fileConfiguration;

    @PostMapping("")
    @ApiOperation(value = "파일 업로드", notes = "파일을 업로드 할 수 있습니다.")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token(사용자 토큰)", required = true, dataType = "String", paramType = "header")
//    })
    public ResponseEntity upload(
            @RequestPart(value = "attachedFile", required=false) MultipartFile attachedFile) throws IOException {

        String fileUuid = saveMultipartFile(attachedFile);
        return ResponseUtils.out(fileUuid);
    }

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
