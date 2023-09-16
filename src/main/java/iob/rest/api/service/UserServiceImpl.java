package iob.rest.api.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import iob.rest.api.exception.ResourceNotFoundException;
import iob.rest.api.model.User;
import iob.rest.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.exception.CipherException;

import static com.mongodb.client.model.Filters.eq;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(User user) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException {
        URL res = getClass().getClassLoader().getResource("wallets");
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();
        String fileName = WalletUtils.generateNewWalletFile(user.getPassword(), file);
        user.setCredentials(fileName);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional < User > userDb = this.userRepository.findById(user.getId());

        if (userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setId(user.getId());
            userUpdate.setName(user.getName());
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

    @Override
    public User getUserById(long userId) {

        Optional < User > userDb = this.userRepository.findById(userId);

        if (userDb.isPresent()) {
            return userDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + userId);
        }
    }

    @Override
    public User getUserByName(String userName) throws CipherException, IOException {

        Optional < User > userDb = this.userRepository.findByName(userName);

        if (userDb.isPresent()) {
            User user = userDb.get();
            try {
                URL res = getClass().getClassLoader().getResource("wallets/" + user.getCredentials());
                assert res != null;
                File file = Paths.get(res.toURI()).toFile();
                Credentials credentials = WalletUtils.loadCredentials(
                        user.getPassword(), file
                        );
                System.out.println(credentials.getAddress());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CipherException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return user;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + userName);
        }
    }

    @Override
    public void deleteUser(long userId) {
        Optional < User > userDb = this.userRepository.findById(userId);

        if (userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + userId);
        }

    }
}
