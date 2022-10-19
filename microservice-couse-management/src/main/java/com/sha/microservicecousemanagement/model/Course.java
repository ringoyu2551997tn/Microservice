package com.sha.microservicecousemanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

//B2 để tao lop course cần @Data(settergetter) @Entity(xác định đây là 1 thực the
// @Table(name ="course") xác định bảng này có tên là course
//
@Data
@Entity
@Table(name = "course")
public class Course {
    // với id cần có @Id và nếu tự động tăng cần @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //id khoa hoc
    //với các cột còn lại cần @Column(name = "ten cot")
    @Column(name = "tittle")
    private String title;       // tieu de khoa hoc

    @Column(name = "author")
    private String author;          // tac gia khoa hoc

    @Column(name = "category")
    private String category;        // the loai khoa hoc

    @Column(name="publish_date")
    private LocalDate publishDate;      // ngay xuat ban
}
//B3 tạo class transation