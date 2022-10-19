package com.sha.microservicecousemanagement.controller;
import com.netflix.discovery.converters.Auto;
import com.sha.microservicecousemanagement.intercomm.UserClient;
import com.sha.microservicecousemanagement.model.Transaction;
import com.sha.microservicecousemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//b13: sau khi tạo Course Controller sẽ cần @RestController để xác minh đây là lớp controller

@RestController
public class CourseController {
//B14 gọi class UserClient vừa tạo sang đây bằng cách @Autowired. sau đó quay lại main.
    @Autowired
    private UserClient userClient;

    //B16 Gọi Course service
    @Autowired
    private CourseService courseService;


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service is working at port : " +env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return ResponseEntity.ok(discoveryClient.getInstances(serviceId));
    }
    //  tạo các phương thức api tham số truyền vào
    //B17
    //từ client qua đường dẫn service/user/{userId} gửi một request lên server để tìm kiếm các giao dịch
    // của user
    // phương thuc này nhận tham số là đường link có chứa userid được gửi từ client và trả về
    //userid tương ứng.
    // ( truyền vào một đường link co userId)
    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(courseService.findTransactionsOfUser(userId));
    }

    //B18: chung ta sẽ nhận mọi request gửi từ client để tìm tất cả các khóa học.
    // vì vậy hàm này tiếp nhận mọi request từ đường link server/all và trả ra tất cả các khóa học.
    @GetMapping("/server/all")
    public ResponseEntity<?>findAllCourses(){
        return ResponseEntity.ok(courseService.allCourse());
    }

    //B19:khi clien gửi một phản hồi lên server về một transaction nào đó được phát sinh
    // nói cách khác một giao dịch nào đó được tạo ra
    // hàm dưới đây sẽ từ server với đường link path là /service/enroll
    // sẽ đưa phương thuc này về client, với tham số dưới dạng link(@requestBody) là một transaction
    // nói cách khác sẽ trả ra một json chức các thuộc tính của một transaction
    // link này sẽ có
    // xét tại thời điểm đó transaction(giao dịch) được thực thi transaction.setDateOfIssue(LocalDateTime.now());
    //và xét khóa học có id là bao nhiêu được tìm thấy trong giao dịch(transaction) đó.
    // sau đó trả ra một thông báo mới khởi tạo thành công khóa học lấy từ db.
    @PostMapping("/service/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction){
        transaction.setDateOfIssue(LocalDateTime.now());
        transaction.setCourse(courseService.findCourseById(transaction.getCourse().getId()));
        return new ResponseEntity<>(courseService.saveTransaction(transaction), HttpStatus.CREATED);
    }

    //B20 từ clien gửi một request lên sever , với đường link /server/course/{courseId} để
    //tìm số học sinh trong một khóa học
    // hàm này sẽ giúp thực hiện điều đó.
    // đầu tiên hàm tìm kiếm số học sinh của khóa hoc sẽ lấy tham số là một đường link @PathVariable
    // có chứa courseID.
    // sau đó tạo một list transaction (danh sách các giao dịch) tương ứng với courseId đã được truền vào từ trên.
    //Neu courseId truyền va là sai hoặc khóa học có corseId khong có học sinh nào thì trả ra rỗng.
    @GetMapping("/service/course/{courseId}")
    public ResponseEntity<?> findStudentOfCourse(@PathVariable Long courseId){
        List<Transaction> transactions = courseService.findTransactionOfCourse(courseId);
        if (CollectionUtils.isEmpty(transactions)){
            return ResponseEntity.notFound().build();
        }
    //Ngược lại Tạo một list userIdList có kiểu dữ liệu Long. cho nhận các gía trị
    //từ vòng lặp  map đã được tiêm transactions vào.
    //sau đó trả về list học sinh
        List<Long> userIdList = transactions.parallelStream().map(t->t.getUserId()).collect(Collectors.toList());
        List<String> students = userClient.getUserName(userIdList);
        return ResponseEntity.ok(students);
    }
}

//B21 quay lại file config để setup project lên eureka service.