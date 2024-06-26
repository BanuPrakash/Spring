package com.example.r2app;


import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutorial {

    @Id
    private int id;

    private String title;

    private String description;

    private boolean published;
}