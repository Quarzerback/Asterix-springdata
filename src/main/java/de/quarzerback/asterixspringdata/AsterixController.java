package de.quarzerback.asterixspringdata;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asterix")
@RequiredArgsConstructor
public class AsterixController {

    private final CharacterRepo characterRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Character saveNewCharacter(@RequestBody Character character){
        return characterRepo.save(character);
    }
    @GetMapping
    public List<Character> getAllCharacters(){
        return characterRepo.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCharacter(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String profession
    ) {
        List<Character> foundCharacters;

        if (id != null) {
            Optional<Character> character = characterRepo.findById(id);
            return character.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else if (name != null) {
            foundCharacters = characterRepo.findByName(name);
        } else if (age != null) {
            foundCharacters = characterRepo.findByAge(age);
        } else if (profession != null) {
            foundCharacters = characterRepo.findByProfession(profession);
        } else {
            return ResponseEntity.badRequest().body("No valid search criteria provided.");
        }

        if (foundCharacters.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundCharacters);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable String id, @RequestBody Character character){
        //find character
        Character existingCharacter = characterRepo.findById(id).orElse(null);

        //update properties if character exists
        if (existingCharacter != null) {
            existingCharacter.setName(character.getName());
            existingCharacter.setAge(character.getAge());
            existingCharacter.setProfession(character.getProfession());

        //save updated character
        Character savedCharacter = characterRepo.save(existingCharacter);

        //return updated character and status code
            return new ResponseEntity<>(savedCharacter, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable String id) {
        if (characterRepo.existsById(id)) {
            characterRepo.deleteById(id);
            return ResponseEntity.ok("The character with the ID " + id + " was deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The character with the ID " + id + " was not found.");
        }
    }


}

