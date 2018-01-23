package com.ohgianni.tin.Service;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Book;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.ohgianni.tin.Enum.Gender.MALE;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

import javax.servlet.ServletContext;

@Service
public class ImageService {

    private static final String AVATARS_PATH = "/home/gianni/Downloads/tin/src/main/resources/static/img/avatars/";

    private static final String AVATAR_PATH = "/img/avatars/";

    private static final String JPG_SUFFIX = ".jpg";

//    private static final String BOOKS_PATH = "/home/gianni/Downloads/tin/src/main/resources/static/img/books/";
    private static final String BOOKS_PATH = "/home/janusz/Documents/PJATK/TIN/project/Library/src/main/resources" +
        "/static/img/books/";

    private static final String BOOK_PATH = "/img/books/";

    private static final String FEMALE_AVATAR_URL = "/img/avatars/female.png";

    private static final String MALE_AVATAR_URL = "/img/avatars/male.png";

    public String getAvatarUrl(ClientDTO clientDTO) {
        return saveAvatar(clientDTO);
    }

    private String saveAvatar(ClientDTO clientDTO) {
        String result;
        try {

            byte[] avatar = clientDTO.getAvatar().getBytes();

            String fileName = generateAvatarName(clientDTO) + JPG_SUFFIX;
            createFile(get(AVATARS_PATH + fileName));

            if(avatar.length != 0) {
                write(get(AVATARS_PATH + fileName), avatar);
                result = AVATAR_PATH + fileName;
            } else {
                result = setDefaultAvatar(clientDTO);
            }

            return result;
        }
        catch (Exception e) {
            return setDefaultAvatar(clientDTO);
        }
    }

    public String saveBookCover(MultipartFile file) {
        String result;
        try {
            byte[] cover = file.getBytes();

            String fileName = file.getOriginalFilename();
            createFile(get(BOOKS_PATH + fileName));

            write(get(BOOKS_PATH + fileName), cover);
            result = BOOK_PATH + fileName;

            return result;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    private String generateAvatarName(ClientDTO clientDTO) {
        return Base64.encodeBase64String(clientDTO.getEmail().getBytes());
    }

    private String setDefaultAvatar(ClientDTO clientDTO) {
        return clientDTO.getGender().equals(MALE) ? MALE_AVATAR_URL : FEMALE_AVATAR_URL;
    }

    public String getBookCoverUrl(Book book) {
        return book.getCoverImage();
    }

    private boolean isAvatarPresent(Path path) {
        return exists(path);
    }

    private void createFile(Path path) throws Exception {
        if(!isAvatarPresent(path)) {
            Files.createFile(path);
        }
    }
}
