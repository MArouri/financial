package edu.birzeit.financial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Getter
@Setter
public class Subscription {

    private Long id = Math.abs(new Random().nextLong()%10000);
    private String type;

    @JsonIgnore
    private String[] types = new String[]{"Golden", "Silver", "Platinum"};

    public Subscription(){
        setId(Math.abs(new Random().nextLong()%10000));
        setType(this.types[new Random().nextInt(this.types.length)]);
    }

    public Subscription(Long id) {
        setId(id);
        setType(this.types[new Random().nextInt(this.types.length)]);
    }
}