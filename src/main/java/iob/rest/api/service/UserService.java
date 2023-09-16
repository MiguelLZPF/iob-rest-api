package iob.rest.api.service;

import iob.rest.api.model.User;
import org.web3j.crypto.exception.CipherException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public interface UserService {
    User createUser(User user) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException;

    User updateUser(User user);

//    List< User > getAllUser();

    User getUserById(long userId);

    User getUserByName(String userName) throws CipherException, IOException;

    void deleteUser(long id);
}
