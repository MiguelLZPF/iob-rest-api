package iob.rest.api.repository;

import iob.rest.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Optional<User> findById(String id);
}
