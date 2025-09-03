package programmer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import programmer.entity.ProfileEntity;

import java.util.Optional;


@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

}
