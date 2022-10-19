package com.sha.microservicecousemanagement.repository;

import com.sha.microservicecousemanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
// B5 sau khi tạo CourseRepository thì extends JpaRepository<Tên của thực the trong model, Kiểu dữ liệu của Id trong thực thể>
public interface CourseRepository extends JpaRepository<Course,Long> {
}
// Tiếp tục với TransactionRepository tương tự