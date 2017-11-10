package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.EmployeeType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Employee extends User {

    @Column
    private EmployeeType type;

//    @Column
//    private List<Reservation> reservations;

}
