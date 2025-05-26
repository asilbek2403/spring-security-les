package firstsectionblock.controller;


import firstsectionblock.Dto.profileD.ProfileDto;
import firstsectionblock.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDto> create(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PostMapping("")
    public ResponseEntity<ProfileDto> update(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO newDto) {
//        return ResponseEntity.ok(service.update(id, newDto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
//        return ResponseEntity.ok(service.delete(id));
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<CategoryDTO>> getAll() {
//        return ResponseEntity.ok(service.getAllByOrder());
//    }
//
//    // /api/v1/category/lang?language=uz
//    @GetMapping("/lang")
//    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") AppLanguageEnum language) {
//        return ResponseEntity.ok(service.getAllByLang(language));
//    }
}
