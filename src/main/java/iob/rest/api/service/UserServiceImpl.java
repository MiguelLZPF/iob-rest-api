package iob.rest.api.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Optional;

import iob.rest.api.exception.ResourceNotFoundException;
import iob.rest.api.model.User;
import iob.rest.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.exception.CipherException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
            user.get().getUsername(),
            user.get().getPassword(),
            new ArrayList<>()
        );
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User createUser(User user) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException {
        String encodedPass = new BCryptPasswordEncoder().encode(user.getPassword());
        URI folderUri = new ClassPathResource("wallets").getURI();
        File file = Paths.get(folderUri).toFile();
        String fileName = WalletUtils.generateNewWalletFile(encodedPass, file);
        user.setCredentials(fileName);
        user.setPassword(encodedPass);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional < User > userDb = this.userRepository.findById(user.getId());

        if (userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setId(user.getId());
            userUpdate.setUsername(user.getUsername());
            userRepository.save(userUpdate);
            return userUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + user.getId());
        }
    }

//    @Override
//    public List < User > getAllUser() {
//        return this.userRepository.findAll();
//    }

//    @Override
//    public User getUserById(String userId) {
//
//        Optional < User > userDb = this.userRepository.findById(userId);
//
//        if (userDb.isPresent()) {
//            return userDb.get();
//        } else {
//            throw new ResourceNotFoundException("Record not found with id : " + userId);
//        }
//    }

    @Override
    public User getUserByName(String userName) {

        Optional < User > userDb = this.userRepository.findByUsername(userName);
        if (userDb.isPresent()) {
            User user = userDb.get();
            try {
                URI folderUri = new ClassPathResource("wallets/" + user.getCredentials()).getURI();
                File file = Paths.get(folderUri).toFile();
                Credentials credentials = WalletUtils.loadCredentials(
                        user.getPassword(), file
                        );
            } catch (IOException | CipherException e) {
                throw new RuntimeException(e);
            }
            return user;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + userName);
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional < User > userDb = this.userRepository.findById(userId);

        if (userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + userId);
        }

    }
}
