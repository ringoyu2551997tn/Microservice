package com.sha.microservicecousemanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
// B3: cách setup giống  course lưu ý ở *
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // ma gioa dich
// * đây là quan hệ một nhiều ở đây một khóa học (course) có nhiều giao dịch
    // vì vậy cần @ManyToOne(fetch = FetchType.EAGER)
    //và @JoinColumn(name = "course_id",referencedColumnName = "id") có ý nghĩa
    // Id course( đầu 1 ) sẽ nối sang đây và nó được biểu dien bằng private Course course
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;      // khoa hoc lay theo ma khoa hoc tu course

    @Column(name="user_id")
    private Long userId;    // id user nguoi dung

    @Column(name = "date_of_issue")
    private LocalDateTime dateOfIssue;  // ngay cho muon
}
//B4 tọa package repository và tọa CourseRepository