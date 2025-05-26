package firstsectionblock.repository;

import firstsectionblock.Dto.profileD.ProfileFilterDto;
import firstsectionblock.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileCustomRepository {
    Page<ProfileEntity> filter(ProfileFilterDto filter, Pageable pageable);

}

