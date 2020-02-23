package cn.scau.springcloud.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginReq {
    /**
     * 用户名
     */
    @NotEmpty(message = "请输入用户名")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "请输入密码")
    private String password;

//    /**
//     * 邮箱
//     */
//    @NotEmpty(message = "请输出邮箱")
//    private String eamil;

//    {"timestamp":"2020-02-23T14:11:53.945+0000",
//            "status":400,
//            "error":"Bad Request",
//            "errors":
//        [
//                {"codes":["NotEmpty.loginReq.password","NotEmpty.password","NotEmpty.java.lang.String","NotEmpty"],
//                    "arguments":[{"codes":["loginReq.password","password"],"arguments":null,"defaultMessage":"password","code":"password"}],
//                    "defaultMessage":"请输入密码",
//                        "objectName":"loginReq",
//                        "field":"password",
//                        "rejectedValue":null,"bindingFailure":false,"code":"NotEmpty"}],"message":"Validation failed for object='loginReq'. Error count: 1","path":"/user/login"}
}
