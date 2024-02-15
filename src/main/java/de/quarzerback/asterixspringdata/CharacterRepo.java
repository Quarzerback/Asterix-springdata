package de.quarzerback.asterixspringdata;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepo extends MongoRepository<Character, String> {
    List<Character> findByName(String name);
    List<Character> findByAge(int age);
    List<Character> findByProfession(String profession);
}
