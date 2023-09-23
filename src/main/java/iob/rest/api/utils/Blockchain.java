package iob.rest.api.utils;

import iob.rest.api.model.SimpleToken;
import iob.rest.api.model.User;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.exception.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Paths;

@Service
//@PropertySource("classpath:application.properties")
public class Blockchain {
    @Resource
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public Credentials getCredentials(User user) {
        try {
            URI folderUri = new ClassPathResource("wallets/" + user.getCredentials()).getURI();
            File file = Paths.get(folderUri).toFile();
            return WalletUtils.loadCredentials(
                    user.getPassword(), file
            );
        } catch (IOException | CipherException e) {
            throw new RuntimeException(e);
        }
    }

    public Web3j getProvider() {
        return Web3j.build(new HttpService(env.getProperty(
                "network.protocol", "http") + "://" +
                env.getProperty("network.hostname", "localhost")
                + ":" + env.getProperty("network.port", "80"
        )));
    }

    public SimpleToken getSimpleToken(Credentials credentials) {
        return SimpleToken.load(
                this.env.getProperty("contracts.SimpleToken.address"),
                this.getProvider(),
                credentials,
                new StaticGasProvider(
                        BigInteger.valueOf(Long.parseLong(env.getProperty("network.gas.price", "0"))),
                        BigInteger.valueOf(Long.parseLong(
                                env.getProperty("network.gas.limit", "8000000")
                        ))
                )
        );
    }
}
