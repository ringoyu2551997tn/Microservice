package com.sha.microserviceusermanagement.controller;


import com.sha.microserviceusermanagement.model.Role;
import com.sha.microserviceusermanagement.model.User;
import com.sha.microserviceusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
//b20 sau khi tạo lớp UserController cần @RestController để hiểu đây la phần controller
@RestController
public class UserController {
//b21 gọi UserService và dùng @Autowired để DI
    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service port number : " +env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId),HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(),HttpStatus.OK);
    }

    //B22
    // phương thức này tiếp nhận một yêu cầu dăng kí với đương dẫn service/registration
    // tiếp nhận yêu cầu này theo biểu mẫu với chú thích @RequestBody
    //Tên người dùng phải là duy nhất , if ... để kiểm tra tên người dùng tồn tại không
    //Nếu đã tồn tại thì trả về trạng thái conflic
    // Nếu chưa tồn tại, gọi service.save(user) để khởi tạo và trả về trạng thái httpstatus. đã tạo

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if (userService.findByUsername(user.getUsername())!= null){
            //status code: 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.save(user),HttpStatus.CREATED);
    }

    //B23
    // ở phía client sẽ gửi giá trị mã để xác minh vào phần mềm theo quyền gì băn đường dẫn/service/login
    // Khi clien đăng nhập ,  chương trình nhận được giá tri mã đó,
    //sẽ thông báo cái mã nhận được có quyền gì(admin hoăc staff chẳng hạn )
    // sau đó nó sẽ cung cấp thông tin xác thực được ủy quyền.
    // phương thức này nhận yêu cầu đó từ client
    // đầu tiên sẽ kiểm traa cái mã đó xem có null ko , hoặc cái mã đó được phân quyền chưa ,
    //nếu ko null sẽ trả về thực the người dùng dưới dạng phản hồi.
    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal){
        if (principal == null || principal.getName() == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    //B24
    //khi người dùng muốn tim kiếm các user
    // phương thức này trả ra danh sách các userName theo các id.
    // phương thức này yêu cầu đưa ra giao diện , nên dưới dạng tham số chúng ta sẽ nhận được
    //idList là @RequestBody
    // đường dẫn sẽ là /service/name
    @PostMapping("/service/names")
    public ResponseEntity<?> getNameOfUsers(@RequestBody List<Long> idList){
        return ResponseEntity.ok(userService.findUsers(idList));
    }

    //B25
    // phuog thức này để test- để kiểm tra các dịch vụ
    //-> Tao lớp UserDetailService
    @GetMapping("/service/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("It is working...");
    }
}

