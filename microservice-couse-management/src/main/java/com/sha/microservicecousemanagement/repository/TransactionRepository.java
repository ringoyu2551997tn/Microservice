package com.sha.microservicecousemanagement.repository;

import com.sha.microservicecousemanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// B6 sau khi tạo CourseRepository thì extends JpaRepository<Tên của thực the trong model, Kiểu dữ liệu của Id trong thực thể>

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    // B7 tự viết hàm

    // hàm thứ nhất tìm kiếm các user theo userid và trả ra các giao dịch của user đó
    List<Transaction> findAllByUserId(Long userId);

    //tim kiếm các gioa dịch theo Id khóa học
    List<Transaction> findAllByCourseId(Long courseId);
}
//B7 tạo package Service -> tạo interface CourseService