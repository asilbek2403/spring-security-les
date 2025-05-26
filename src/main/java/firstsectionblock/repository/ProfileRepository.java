package firstsectionblock.repository;

import firstsectionblock.entity.ProfileEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
//@NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue( String username);

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

}
