package ru.kpfu.services;

import ru.kpfu.exceptions.FileUploadException;
import ru.kpfu.exceptions.UnknownFileException;
import ru.kpfu.models.Comment;
import ru.kpfu.models.MediaFile;
import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.repositories.MediaRepository;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MediaService {
    private MediaRepository mediaRepository;
    private Path repositoryPath;

    public MediaService(MediaRepository mediaRepository, Path repositoryPath) {
        this.mediaRepository = mediaRepository;
        this.repositoryPath = repositoryPath;
    }

    public void saveUserMedia(User user, Part part) throws IOException {
        long mediaId = uploadMedia(part.getSubmittedFileName(), part.getInputStream());
        mediaRepository.saveUserMedia(user, mediaId);
    }

    public void saveRecipeMedia(Recipe recipe, List<Part> parts) throws IOException {
        for (Part part : parts) {
            long mediaId = uploadMedia(part.getSubmittedFileName(), part.getInputStream());
            mediaRepository.saveRecipeMedia(recipe, mediaId);
        }
    }

    public void saveCommentMedia(Comment comment, List<Part> parts) throws IOException {
        for (Part part : parts) {
            long mediaId = uploadMedia(part.getSubmittedFileName(), part.getInputStream());
            mediaRepository.saveCommentMedia(comment, mediaId);
        }
    }

    public String getUserMedia(User user) {
        MediaFile media = mediaRepository.findByUserId(user.getId());
        if (media != null)
            return downloadMedia(media.getId());
        return null;
    }

    public List<String> getRecipeMedia(Recipe recipe) {
        List<MediaFile> medias = mediaRepository.findByRecipeId(recipe.getId());
        return getMediaPaths(medias);
    }

    public List<String> getCommentMedia(Comment comment) {
        List<MediaFile> medias = mediaRepository.findByCommentId(comment.getId());
        return getMediaPaths(medias);
    }

    private List<String> getMediaPaths(List<MediaFile> medias) {
        if (medias != null) {
            List<String> paths = new ArrayList<>();
            for (MediaFile media : medias) {
                paths.add(downloadMedia(media.getId()));
            }
            return paths;
        }
        return null;
    }

    private long uploadMedia(String fileName, InputStream fileInputStream) {
        try {
            String name = generateFileName();
            String mimeType = getMimeType(Paths.get(fileName).toAbsolutePath());
            String extension = getExtension(mimeType, "/");
            Path path = repositoryPath.resolve(name + '.' + extension);
            Files.copy(fileInputStream, path);

            MediaFile file = MediaFile.builder()
                    .name(name)
                    .mimeType(mimeType)
                    .build();
            return mediaRepository.save(file);
        } catch (IOException e) {
            throw new FileUploadException("Can't upload file.");
        }
    }

    private String downloadMedia(long id) {
        MediaFile file = mediaRepository.findById(id);
        if (file != null) {
            Path path = repositoryPath.resolve(file.getName() + '.' + getExtension(file.getMimeType(), "/"));
            return path.toString().replace("\\", "/");
        } else throw new UnknownFileException("Such file doesn't exist.");
    }

    private String getMimeType(Path path) throws IOException {
        return Files.probeContentType(path);
    }

    private String getExtension(String fileName, String delimiter) {
        int i = fileName.lastIndexOf(delimiter);
        if (i >= 0) {
            return fileName.substring(i + 1);
        }
        return "";
    }

    private String generateFileName() {
        return UUID.randomUUID().toString();
    }
}

