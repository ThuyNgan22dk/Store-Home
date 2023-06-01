package com.example.demo.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import com.example.demo.entities.User;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entities.Image;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ImageController {
    private static String UPLOAD_DIR  = System.getProperty("user.dir") + "/src/main/resources/static/photos/";

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getList(){
        List<Image> listImage = imageService.getListImage();
        return  ResponseEntity.ok(listImage);
    }

    @GetMapping("/user/{username}")
    @Operation(summary="Lấy ra danh sách hình ảnh của user bằng username")
    public ResponseEntity<?> getListByUser(@PathVariable String username){
        List<Image> listImage = imageService.getListByUser(username);
        return ResponseEntity.ok(listImage);
    }

    @PostMapping("/upload-file")
    @Operation(summary="Upload file lên database cho admin")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
//        File uploadDir = new File(UPLOAD_DIR);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }
//
//        String originalFilename = file.getOriginalFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);;
//        if (originalFilename != null && originalFilename.length() > 0) {
//            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
//                throw new BadRequestException("Không hỗ trợ định dạng file này");
//            }
//            try {
//                Image img = new Image();
//                img.setName(file.getName());
//                img.setSize(file.getSize());
//                img.setType(extension);
//                img.setData(file.getBytes());
//                img.setUploadedBy(imageService.saveUser("admin"));
//                String uid = UUID.randomUUID().toString();
//                String link = UPLOAD_DIR + uid + "." + extension;
//                // Create file
//                File serverFile = new File(link);
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
//                stream.write(file.getBytes());
//                stream.close();
//                imageService.save(img);
//                return ResponseEntity.ok(img);
//            } catch (Exception e) {
//                throw new InternalServerException("Lỗi khi upload file");
//            }
//        }
//        throw new BadRequestException("File không hợp lệ");
        return uploadFileByUser("admin", file);
    }

    @PostMapping("/upload-file/{username}")
    @Operation(summary="Upload file lên database")
    public ResponseEntity<?> uploadFileByUser(@PathVariable String username, @RequestParam("file") MultipartFile file){
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);;
        if (originalFilename != null && originalFilename.length() > 0) {
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                throw new BadRequestException("Không hỗ trợ định dạng file này");
            }
            try {
                Image img = new Image();
                User user = imageService.saveUser(username);
                img.setName(file.getName());
                img.setSize(file.getSize());
                img.setType(extension);
                img.setData(file.getBytes());
                String uid = UUID.randomUUID().toString();
                String link = UPLOAD_DIR + uid + "." + extension;
                // Create file
                File serverFile = new File(link);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                img.setUploadedBy(user);
                imageService.save(img);
                return ResponseEntity.ok(img);
            } catch (Exception e) {
                throw new InternalServerException("Lỗi khi upload file");
            }
        }
        throw new BadRequestException("File không hợp lệ");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa danh mục bằng id")
    public ResponseEntity<?> delete(@PathVariable long id){
        imageService.deleteImage(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
