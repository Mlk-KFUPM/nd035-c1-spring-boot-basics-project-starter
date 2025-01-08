package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileView;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    private FileMapper fileMapper;
    private NoteMapper noteMapper;
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public HomeService(FileMapper fileMapper, NoteMapper noteMapper, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    // File methods:
    public List<FileView> getFilesView(Integer userId) {
        return fileMapper.getFilesViewByUserId(userId);
    }
    public Optional<File> findFileByName(String fileName, Integer userId) {
        return fileMapper.findFileByFilenameAndUserId(fileName, userId);
    }

    public void uploadFile(MultipartFile fileUpload, Integer userId) throws IOException {
        File file = new File(
                null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()),
                fileUpload.getBytes(),
                userId
        );
        System.out.println("uploading this file: " + file);
        fileMapper.insertFile(file);
    }


    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

    public File findFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public List<File> findFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    // Notes methods:

    public void createNote(String noteTitle, String noteDescription, Integer userId) {
        noteMapper.insertNote(new Note(null, noteTitle, noteDescription, userId));
    }

    public List<Note> findNotesByUserId(Integer userId) {
        return noteMapper.findByUserId(userId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription) {
        noteMapper.updateNote(noteId, noteTitle, noteDescription);
    }


    // Credential methods:

    // get all credentials:
    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.findByUserId(userId);
    }
    // return the decrypted password for a certain credential
    public String decryptPassword(int credentialId) {
        Credential credential = credentialMapper.findById(credentialId);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }
    // Create credentials
    public void createCredential(String url, String username, String password, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Credential credential = new Credential();
        credential.setPassword(encryptedPassword);
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setKey(encodedKey);
        credential.setUserId(userId);
        credentialMapper.insertCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public void updateCredential(Integer credentialId, String url, String username, String password) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        password = encryptionService.encryptValue(password, encodedKey);

        credentialMapper.updateCredential(new Credential(credentialId, username, password, url, encodedKey, null));
    }
}
