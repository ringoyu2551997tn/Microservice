package com.sha.microserviceusermanagement.service;

import com.sha.microserviceusermanagement.model.User;
import com.sha.microserviceusermanagement.reposetory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//B26 tạo ra UserDetailServicelmpl implements UserDetailsService
// (UserDetailsService là 1 service của springboot nhớ phải có @Service
@Service
public class UserDetailServicelmpl implements UserDetailsService {


//B27: khoi tao userRepository từ UserRepository và @Autowired- DI nó.
    @Autowired
    private UserRepository userRepository;


//B28 viết hàm tìm người dùng của mình theo tên người dùng từ kho luu trữ ng dùng UserRepositori
// đầu tiên gọi phương thức findByUsername từ kho lưu trữ,
//nếu nguoi dùng là null trả về ngoại lệ nếu không sẽ cấp quyền cho người dùng theo vai trò của người dùng
//sau đó trả lại với sự đóng gói người dùng bảo mật với tên người dùng, mật khẩu và xác thực.
    //- WebSecurityConfig
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);

    }
}
