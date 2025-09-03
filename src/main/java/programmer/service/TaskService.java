package programmer.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programmer.enumL.RoleEnum;
import programmer.exp.AppBadRequestException;
import programmer.exp.ItemNotFoundException;
import programmer.dto.TaskDto;
import programmer.entity.TaskEntity;
import programmer.repository.TaskRepository;
import programmer.util.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;





    // buni vaqtincha ishlatdik Listni
    private List<TaskDto> taskList;

    public TaskService() {
        taskList = new LinkedList<>();

        TaskDto task1 = new TaskDto();
        task1.setId(UUID.randomUUID().toString());
        task1.setTitle("Bozor");
        task1.setContent("Bozorga borib meva-chevalar olib kelish kerak.");
        task1.setCreatedDate(LocalDateTime.now());
        taskList.add(task1);

        TaskDto task2 = new TaskDto();
        task2.setId(UUID.randomUUID().toString());
        task2.setTitle("Spring Security");
        task2.setContent("Dasturlash.uz ga kirib Spring Securityni o'rganishim kerak.");
        task2.setCreatedDate(LocalDateTime.now());
        taskList.add(task2);
    }

    public TaskDto create(TaskDto dto) {

        String profileId = SpringSecurityUtil.getCurrentProfileId();
        System.out.println(profileId);
//        List<String> roles = SpringSecurityUtil.getCarentRoles();
//        if(roles.contains(profileId)){}

        //##
        TaskEntity entity = new TaskEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfileId(profileId);

        taskRepository.save(entity);

//        dto.setId(UUID.randomUUID().toString());
//        dto.setCreatedDate(LocalDateTime.now());
//        taskList.add(dto);

        dto.setId(entity.getId());
        return dto;
    }

    public List<TaskDto> getAll() {
//        return taskList;

        Iterable<TaskEntity> userTask = taskRepository.findAll();

        List<TaskDto> taskDtoList = new LinkedList<>();
        for (TaskEntity task : userTask) {
            taskDtoList.add(toDto(task));
        }
        return taskDtoList;

    }


public List<TaskDto> getProfileTasks() {
        String profileId = SpringSecurityUtil.getCurrentProfileId();
        System.out.println(profileId);
        List<TaskEntity> userTask = taskRepository.findAllByProfileId(profileId);

       List<TaskDto> taskDtoList = new LinkedList<>();
       for (TaskEntity task : userTask) {
           taskDtoList.add(toDto(task));
       }

        return taskDtoList ;
    }

    public TaskDto getById(String id) {
//        for (TaskDto dto : taskList) {
//            if (dto.getId().equals(id)) {
//                return dto;
//            }
//        }
//        return null;


        TaskEntity entity = getbId(id);
        return toDto(entity);
    }

//    public Boolean update(TaskDto dto, String id) {
//        TaskDto exists = getById(id);
//
//        if (exists == null) {
//            return false;
//        }
//
//        exists.setTitle(dto.getTitle());
//        exists.setContent(dto.getContent());
//        return true;
//    }


    public Boolean update(TaskDto dto, String id) {
        TaskEntity exists = getbId(id);
        String profileId = SpringSecurityUtil.getCurrentProfileId();

        List<String> roleList = SpringSecurityUtil.getCarentRoles();
            if(!exists.getProfileId().equals(profileId) && !roleList.contains(RoleEnum.ROLE_ADMIN.name()) ) {
                throw new AppBadRequestException(" Current profile NOt belong VVV");
            }

        exists.setTitle(dto.getTitle());
        exists.setContent(dto.getContent());
        taskRepository.save(exists);

        return true;
    }



    public Boolean deleteisAdmin(String id) {
        TaskEntity exists = getbId(id);
        String profileId = SpringSecurityUtil.getCurrentProfileId();

        List<String> roleList = SpringSecurityUtil.getCarentRoles();
        if(!exists.getProfileId().equals(profileId) && !roleList.contains(RoleEnum.ROLE_ADMIN.name())) {
                throw new AppBadRequestException(" Current profile NOt belong VVV");
                // bu asosiy ish roleni Adminga tekshiradi AKS holda keyingi qadamga >>>
        }

        taskRepository.delete(exists);
        return true;


    }
//public Boolean delete(String id) {
//        return taskList.removeIf(taskDTO -> taskDTO.getId().equals(id));
//    }





    public TaskDto toDto(TaskEntity entity) {
        TaskDto dto = new TaskDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }


    public TaskEntity getbId(String id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (!task.isPresent()){
            throw new ItemNotFoundException("Not found task ");
        }
        return task.get();
    }


//    public Boolean deleteisAdmin(String id) {
//        taskRepository.deleteById(id);
//        return true;
//    }



}

