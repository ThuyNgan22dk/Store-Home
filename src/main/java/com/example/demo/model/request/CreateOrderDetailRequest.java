package com.example.demo.model.request;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailRequest {

//    @NotNull(message="Tên sản phẩm rỗng")
//    @NotEmpty(message = "Tên sản phẩm rỗng")
//    @Size(min=5,max=50,message="Tên sản phẩm từ 5-50 ký tự")
//    private String name;
//
//    @NotNull(message="Giá sản phẩm rỗng")
//    @NotEmpty(message="Giá sản phẩm rỗng")
//    @Size(min=0,message ="Giá sản phẩm từ 0 trở lên")
//    private long price;
//
//    @NotNull(message = "Hạn sử dụng rỗng")
//    @NotEmpty(message="Hạn sử dụng rỗng")
//    @Schema(description = "Hạn sử dụng sản phẩm",example="2023-04-24")
//    @Size(min=5,max=20,message="Hạn sử dụng sản phẩm từ 5-20 ký tự")
//    private String expiry;
//
//    @NotNull(message = "Số lượng sản phẩm rỗng")
//    @NotEmpty(message = "Số lượng sản phẩm rỗng")
//    @Size(min = 1,message="Số lượng sản phẩm từ 1 trở lên")
//    private int quantity;

    @NotNull(message = "Giỏ hàng rỗng")
    @NotEmpty(message = "Giỏ hàng rỗng")
    @Schema(description = "ID của giỏ hàng",example="1")
    private long cartId;

//    @NotNull(message = "Mã giảm giá rỗng")
//    @NotEmpty(message="Mã giảm giá rỗng")
    @Schema(description = "Mã giảm giá sản phẩm",example="abcd1234")
    @Size(min=5,max=20,message="Mã giảm giá sản phẩm từ 5-20 ký tự")
    private String promotionCode;

//    private long subTotal;
}
