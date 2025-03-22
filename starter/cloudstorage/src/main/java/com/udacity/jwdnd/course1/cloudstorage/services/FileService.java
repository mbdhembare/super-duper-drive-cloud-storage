package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public int uploadFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File();
        newFile.setFilename(file.getOriginalFilename());
        newFile.setContenttype(file.getContentType());
        newFile.setFilesize(String.valueOf(file.getSize()));
        newFile.setUserid(userId);
        newFile.setFiledata(file.getBytes());
        return fileMapper.insertFile(newFile);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

    public boolean isFilenameAvailable(String filename, Integer userId) {
        return fileMapper.getFileByNameAndUserId(filename, userId) == null;
    }
}