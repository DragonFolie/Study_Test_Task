package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Person")
@Setter
@Getter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    @Size(min = 2,max = 48,message = "Size must be between 1 and 48 symbol")
    @NotBlank(message = "Please input data")
    private String name;


    @Column(name = "surname")
    @Size(min = 2,max = 48,message = "Size must be between 2 and 48 symbol")
    @NotBlank(message = "Please input data")
    private String surname;

//    @JsonFormat(pattern="yyyy-MM-dd")
//    @Pattern( regexp = "^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$")
    @Column(name = "date_of_birth")
    @Size(min = 1,max = 10,message = "Size must be between 1 and 10 symbol")
    @NotBlank(message = "Please input years old of person")
    private String date_of_birth;



}
