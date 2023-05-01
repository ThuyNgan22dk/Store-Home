package com.example.demo.model.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

//    @NotNull(message="Họ khách hàng rỗng")
//    @NotEmpty(message="Họ khách hàng rỗng")
//    @Size(min=3,max=50,message="Họ khách hàng từ 3-50 ký tự")
//    private String firstname;
//
//    @NotNull(message="Tên khách hàng rỗng")
//    @NotEmpty(message="Tên khách hàng rỗng")
//    @Size(min=3,max=50,message="Tên khách hàng từ 3-50 ký tự")
//    private String lastname;

//    private String promotionCode;

//    @NotNull(message = "Email rỗng")
//    @NotEmpty(message = "Email rỗng")
//    @Email(message = "Email không đúng định dạng")
//    private String email;
//
//    @NotNull(message="Số điện thoại rỗng")
//    @NotEmpty(message="Số điện thoại rỗng")
//    private String phone;

    private String note;

    @NotNull(message="Tên địa chỉ rỗng")
    @NotEmpty(message="Tên địa chỉ rỗng")
    private String address;

    private String username;

//    private long totalPrice;

    private List<CreateOrderDetailRequest> orderDetails;

}

