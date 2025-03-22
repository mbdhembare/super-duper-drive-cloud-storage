package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    /**
     * Retrieves all credentials for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of credentials.
     */
    public List<Credential> getCredentialsByUserId(Integer userId) {
        List<Credential> credentials = credentialMapper.getCredentialsByUserId(userId);
        // Decrypt passwords before returning
        credentials.forEach(this::decryptPassword);
        return credentials;
    }

    /**
     * Adds a new credential for a user.
     *
     * @param credential The credential to add.
     * @return The number of rows affected.
     */
    public int addCredential(Credential credential) {
        // Encrypt the password before saving
        encryptPassword(credential);
        return credentialMapper.insertCredential(credential);
    }

    /**
     * Updates an existing credential.
     *
     * @param credential The credential to update.
     * @return The number of rows affected.
     */
    public int updateCredential(Credential credential) {
        // Encrypt the password before updating
        encryptPassword(credential);
        return credentialMapper.updateCredential(credential);
    }

    /**
     * Deletes a credential by its ID.
     *
     * @param credentialId The ID of the credential to delete.
     * @return The number of rows affected.
     */
    public int deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    /**
     * Encrypts the password of a credential using the encryption key.
     *
     * @param credential The credential to encrypt.
     */
    private void encryptPassword(Credential credential) {
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
    }

    /**
     * Decrypts the password of a credential using the encryption key.
     *
     * @param credential The credential to decrypt.
     */
    private void decryptPassword(Credential credential) {
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setDecryptedPassword(decryptedPassword);
    }
}