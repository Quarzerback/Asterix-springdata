package de.quarzerback.asterixspringdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    @Id
    private String id;
    private String name;
    private int age;
    private String profession;
}
