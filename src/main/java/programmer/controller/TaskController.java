package programmer.controller;


import io.jsonwebtoken.JwtException;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import programmer.dto.JwtDTO;
import programmer.dto.TaskDto;
import programmer.service.TaskService;
import programmer.util.JwtUtil;


import java.util.List;

@RestController
@RequestMapping("/task")
@EnableMethodSecurity    //(prePostEnabled = true)//defaultda shnday holatda
public class TaskController {
        @Autowired
        private TaskService taskService;

//        @PostMapping("/create")
//        public ResponseEntity<TaskDto> create(@RequestBody TaskDto dto , Principal principal) {
//
//            System.out.println(principal.getName());
//            CustomUserDetails user = (CustomUserDetails) principal;//bu xato cast
//            CustomUserDetails users = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//            TaskDto result = taskService.create(dto);
//
//
//            return ResponseEntity.ok(result);
//        }
//        @PostMapping("/create")
//        public ResponseEntity<TaskDto> create(@RequestBody TaskDto dto , Authentication authentication) {
//
//            UserDetails user = (UserDetails) authentication.getPrincipal();
//            System.out.println(user.getUsername());
//
//            TaskDto result = taskService.create(dto);
//
//
//            return ResponseEntity.ok(result);
//        }



    @PostAuthorize("hasAnyRole('ROLE_USER' , 'ROLE_ADMIN')")
        @PostMapping("/create")
        public ResponseEntity<TaskDto> create(@RequestBody TaskDto dto ) {//Buni jwtda TaskDto

//        String jwt = JwtUtil.encode("menvamen" , "ROLE_ADMIN");
//            System.out.println(principal.getName());
//            CustomUserDetails user = (CustomUserDetails) principal;//bu xato cast
//            CustomUserDetails users = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            TaskDto result = taskService.create(dto);
            return ResponseEntity.ok(result);
        }




        @PermitAll
        @PostMapping("/jwt-create")
        public ResponseEntity<String> create() {
        String jwt = JwtUtil.encode("menvamen" , "ROLE_ADMIN");
        return ResponseEntity.ok(jwt);
        }
//Jwt uchun
    @GetMapping("/parse-jwt/{token}")
    public ResponseEntity<?> jwtsa( @PathVariable("token") String token ) {
    try {
        JwtDTO jwtDTO = JwtUtil.decode(token);
        return ResponseEntity.ok(jwtDTO);
    } catch (JwtException e){
        return ResponseEntity.badRequest().body("Jwt invalid error . ");
    }
    }





        @GetMapping("/all/By") // { " " , "/" } deb 2 ta apini bitta metodga ulaolamiz
        public ResponseEntity<List<TaskDto>> getAll() {
            List<TaskDto> result = taskService.getAll();
            return ResponseEntity.ok(result);
        }

        @GetMapping("/all/ByTasks") // { " " , "/" } deb 2 ta apini bitta metodga ulaolamiz
        public ResponseEntity<List<TaskDto>> getAlL() {
            List<TaskDto> result = taskService.getProfileTasks();
            return ResponseEntity.ok(result);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TaskDto> getById(@PathVariable String id) {
            TaskDto result = taskService.getById(id);
            return ResponseEntity.ok(result);
        }




    @PreAuthorize("hasRole('ROLE_USER')")
        @PutMapping("/{id}")
        public ResponseEntity<Boolean> update(@RequestBody TaskDto student,
                                              @PathVariable("id") String id) {
            Boolean result = taskService.update(student, id);
            return ResponseEntity.ok(result);
        }




//        @DeleteMapping("/delete-s/{id}")
//        public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
//            taskService.delete(id);
//            return ResponseEntity.ok().build();
//        }

        @DeleteMapping("/admin-delete-s/{id}")
        public ResponseEntity<Void> Admindelete(@PathVariable("id") String id) {
            taskService.deleteisAdmin(id);
            return ResponseEntity.ok().build();
        }

}
