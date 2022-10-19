package com.sha.microserviceusermanagement.reposetory;

import com.sha.microserviceusermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// b11 sau khi tạo interface Repository cần có @Repositoy de xác nhận đây là lớp repository

//b12 cho UserRepository extends JpaRepository<Model tương ưng, Kiểu dữ liệu ID>
// JpaRepository là một lớp trừu tượng được triển khai bởi spring jpa repository sẽ tự động
//cập nhap các câu truy vấn cơ bản như crud findbyid,...
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //b13 viết các phương thức mình muốn mà ko có trong JpaRepository

    //+ Optional<User> findByUsername(String username); tìm người dùng theo tên người dùng.
    // tìm username(findByUsername) theo username(String username)
    // có trong database và trả ra tên người dùng.(Optional<user>)
    Optional<User> findByUsername(String username);

    // truy vấn bằng hql
    // tìm kiếm tên của người dùng theo danh sach id

    @Query("select u.name from  User  u where u.id in (:pIdList)")
    List<String> findByIdList(@Param("pIdList") List<Long> idList);

// b14 tạo package service -> tạo interface UserService -> tạo UserServiceImpl implement Userservice
    //-> quay sang UserServiceLmpl
}
