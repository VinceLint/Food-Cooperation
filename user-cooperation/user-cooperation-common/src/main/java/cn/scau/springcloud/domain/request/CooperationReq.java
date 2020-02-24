package cn.scau.springcloud.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CooperationReq {
    /** 标题*/
    @NotEmpty(message = "标题不能为空")
    @Length(min=6, max = 30, message = "标题长度必须在{min}到{max}之间")
    private String title;

    /** 公司*/
    @NotEmpty(message = "公司名不能为空")
    @Length(min=6, max = 20, message = "公司名长度必须在{min}到{max}之间")
    private String company;

    /** 省份*/
    @NotEmpty(message = "省份不能为空")
    @Length(min=2, max = 10, message = "省份长度必须在{min}到{max}之间")
    private String province;

    /** 城市*/
    @NotEmpty(message = "城市不能为空")
    @Length(min=2, max = 10, message = "城市长度必须在{min}到{max}之间")
    private String city;

    /** 具体地址*/
    @NotEmpty(message = "地址不能为空")
    @Length(min=2, max = 30, message = "地址长度必须在{min}到{max}之间")
    private String address;

    /** 合作细则*/
    @NotEmpty(message = "合作细则不能为空")
    @Length(min=20, max = 255, message = "合作细则长度必须在{min}到{max}之间")
    private String detail;
}
