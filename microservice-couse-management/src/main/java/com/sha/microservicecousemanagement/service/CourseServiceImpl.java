package com.sha.microservicecousemanagement.service;


import com.sha.microservicecousemanagement.model.Course;
import com.sha.microservicecousemanagement.model.Transaction;
import com.sha.microservicecousemanagement.repository.CourseRepository;
import com.sha.microservicecousemanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//b8 sau khi tạo class CourseServiceImpl thì implement CoureService
// ở dây viết các phuong thức cho nó
@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private TransactionRepository transactionRepository;

    //tìm kiếm trong database và trả ra tất cả các khóa học Course
    @Override
    public List<Course> allCourse(){
        return courseRepository.findAll();
    }

    //tim kiếm theo đầu vào là id khóa học(Course) kiểm tra có trong db ko? có thì trả ra khóa học ko thì null
    @Override
    public Course findCourseById(Long courseId){
        return courseRepository.findById(courseId).orElse(null);
    }


    // tim kiem theo id người dùng, và trả ra list các giao dịch của người dùng
    @Override
    public List<Transaction> findTransactionsOfUser(Long userId){
        return transactionRepository.findAllByUserId(userId);
    }


    // tim kiem theo mã khóa học và trả ra list các giao dịch
    @Override
    public List<Transaction> findTransactionOfCourse(Long courseId){
        return transactionRepository.findAllByCourseId(courseId);
    }

    // luu giao dich
    public  Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
}
//B9 tạo ket nối với các dịch vụ khác từ dịch vu đám mây kết nối Sprint Could bằng Spind Cloud Open
//Ví dụ: chúng tôi muốn lấy tên người dùng với những người lý tưởng từ dịch vụ người dùng để làm điều đó.
//
//Trước hết, chúng tôi sẽ tạo một gói liên lạc nội bộ.
//
//Sau đó, chúng tôi sẽ thực hiện giao diện dòng nhạc của họ trên gói liên lạc nội bộ.
//
//Sau đó, chúng tôi sẽ thêm những thứ như đổi mới với dịch vụ người dùng để kết nối với dịch vụ người dùng.
//
//Và chúng tôi sẽ gửi thêm yêu cầu đến dịch vụ người dùng để lấy tên người dùng theo các tweet lý tưởng người dùng của chúng tôi.
//
//Tên dịch vụ, vật nuôi.
//
//Sau đó, chúng ta có thể gọi nó từ lớp điều khiển anh chị em để làm điều đó.