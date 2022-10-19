package com.sha.microserviceusermanagement.service;

import com.sha.microserviceusermanagement.model.User;
import com.sha.microserviceusermanagement.reposetory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// sau khi tạo UserServiceImpl implements userService
//B15 gọi UserRepository sang và dùng @Autowired để DI nó.sau đó sẽ tao các phương thức.
@Service
public class UserServiceImpl implements UserService {


    // tạo nó trong security config
    @Autowired
    private UserRepository userRepository;

//B17 tạo bean passwordEncoder sau
    @Autowired
    private PasswordEncoder passwordEncoder;

//B16 tạo phương thức saveUser để luu người dùng.
//Chúng ta có thể gọi kho lưu trữ người dùng theo cùng một phương thức
// Nhưng ở đây, chúng ta cần một cái gì đó như mã hóa thành mật khẩu.
//Bạn biết rằng đây sẽ không phải là một cách an toàn. để làm điều đó.
//chúng ta có thể sử dụng PasswordEncoder trong springboot.
    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return (User) userRepository.save(user);

    }
//B18 viết hàm tim kiem Username(findByUsername) theo giá trị nhập vào (String username)
// nếu giá trị nhập vào có trong database(hàm tìm kiếm của user-JpaRepository) trả về User cần tìm
// ngược lại trả về null
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);

    }

//B19 tìm người dùng theo danh sách id
// tìm kiếm user (findUsers) theo danh sách id

    @Override
    public List<String> findUsers(List<Long> idList){
        return userRepository.findByIdList(idList);
    }

}
//sau đó tạo controller- UserController
