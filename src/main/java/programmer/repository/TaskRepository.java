package programmer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import programmer.entity.TaskEntity;

import java.util.List;


@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, String> {

    List<TaskEntity> findAllByProfileId(String profileId);

}
