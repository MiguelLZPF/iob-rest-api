package iob.rest.api.service;

import iob.rest.api.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.web3j.crypto.exception.CipherException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);
    void saveUser(User user);

    User createUser(User user) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException;

    User updateUser(User user);

//    List< User > getAllUser();

//    User getUserById(String userId);

    User getUserByName(String userName) throws CipherException, IOException;

    void deleteUser(String id);
}
