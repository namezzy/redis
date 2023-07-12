package top.withlevi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Levi Zhao on 7/4/2023 10:56 AM
 *
 * @Author Levi
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private int age;
    private String email;
}
