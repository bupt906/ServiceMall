package com.example.demo.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * Set User Attributes
 *
 */
@Data   //Auto add Getter and Setter
@Entity
@ApiModel(description="User Entity")
@NoArgsConstructor
public class User {
    @ApiModelProperty(value = "User Id", position = 1)
    @Id @GeneratedValue
    private Long id;

    @ApiModelProperty(value = "User Name", position = 2)
    @NonNull @Size(min = 2, max = 30)
    private String userName;

    @ApiModelProperty(value = "User Password", position = 3)
    @NonNull
    private String passWord;

    @ApiModelProperty(value = "User Phone", position = 4)
    @NonNull @Size(min = 2, max = 20)
    private String phone;

    @ApiModelProperty(value = "User Email", position = 5)
    @NonNull @Email
    private String email;

    @ApiModelProperty(value = "createTime", position = 6)
    private Date creatTime;

    @ApiModelProperty(value = "updateTime", position = 7)
    private Date updateTime;

    @ApiModelProperty(value = "role", position = 8)
    @NonNull
    private String role;

    @ApiModelProperty(value = "question", position = 9)
    @NonNull
    private String question;

    @ApiModelProperty(value = "answer", position = 10)
    @NonNull
    private String answer;

}

