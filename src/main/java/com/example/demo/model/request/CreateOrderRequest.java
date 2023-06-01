package com.example.demo.model.request;

import java.util.List;
import java.util.Set;

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
    private String username;

    @NotNull(message="Tên địa chỉ rỗng")
    @NotEmpty(message="Tên địa chỉ rỗng")
    private String address;

    private String promotionCode;
    private String note;
    private List<CreateOrderDetailRequest> orderDetails;
}

