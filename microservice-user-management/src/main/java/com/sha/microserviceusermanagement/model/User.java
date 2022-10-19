package com.sha.microserviceusermanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


//b2 tạo class User nhớ cần có @Data(Setter và getter), @Entity ,@Table(name = "ten bảng")


@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    //Generation Types:
    //Auto: Default one. It does not take any specific action.
    //Identity: Auto increment.
    //Sequence: Oracle or Posgresql creates variable to auto increment.
    //Table: Hibernate uses a database table to simulate a sequence.

//b3 tạo id bảng để tăng tự động cần 2 thứ
// @Id và
// @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//b4 các cột tương ứng thì cần có @Column(name = "tên cột")
    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

// b5 muốn có thuộc tính mô tả vai trò (admin hoac user) can tạo 1 lớp mới
// ở đây tạo ra 1 lớp role có dạng enum -> sang lớp role làm bước 6


// b7 tạo cột @Column(name = "role"), và một biến role có kieu du lieu la Role
// để xác định được lấy d liệu từ Role dùng @Enumerated(value = EnumType.STRING).


//b8 -> resources ->db.changelog-> tạo db.changelog-master.xml và db.changelog-1.0.xml
//sau khi viết truy vấn xong ở db.changelog-0.1.xml   quay lại file cấu hình, cấu hình


    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
}

