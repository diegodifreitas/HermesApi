package br.com.hermes.api.controllers;

import java.io.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    ServletContext servletContext;
    
    private final String UPLOADED_FOLDER = "/resources/upload_dir/";

    @PostMapping("/")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("files") MultipartFile[] uploadfiles) {

        String uploadedFileName = Arrays.stream(uploadfiles)
                .map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x))
                .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);
    }

    private List<String> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        File fileDir = new File(servletContext.getRealPath(UPLOADED_FOLDER));
        List<String> urlFileList = new ArrayList<>();
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get( fileDir + "/" + file.getOriginalFilename() );
                Files.write(path, bytes);
                urlFileList.add(UPLOADED_FOLDER + file.getOriginalFilename());
            }
        }
        return urlFileList;
    }
}
