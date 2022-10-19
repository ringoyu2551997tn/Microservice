package com.sha.microservicecousemanagement.intercomm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
// đây là gói liên lạc nội bộ.

//B10 sau khi tạo interface UserClient để kết nối voi MicroserviceUserManagement
// cần @FeignClient("user-service") để xác minh đây là lớp kết nối nội bộ.
@FeignClient("user-service")
public interface UserClient {
    //B11: Sử dụng phương thức @RequestMapping, để lấy đường truyền bên MicroserviceUserManagement
    // các thứ cần xác nhận dữ liệu ở bước 24 bên MicroserviceUserManagement
    //Phương thức : POST
    //đường truyền path :/service/names
    //đường truyền requestbody:@RequestBody List<Long> idList
    // suy ra lắp vào phuong thức bên dưới. Lưu ý ở đường truyền @RequestBody cần xác nhận là từ
    //MicroserviceUserManagement nên idList thay bằng userIdList.
    @RequestMapping(method = RequestMethod.POST,value = "/service/names",consumes = "application/json")
    List<String> getUserName(@RequestBody List<Long> userIdList);
}
// sau khi tạo kết nối nội bộ,ta có thể gọi nó từ lớp controller
//B12 tạo package Controller và tạo ra Course Controller.