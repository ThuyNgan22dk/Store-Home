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
    @NotNull(message = "Giỏ hàng rỗng")
    @NotEmpty(message = "Giỏ hàng rỗng")
    @Schema(description = "ID của giỏ hàng",example="1")
    private long cartId;

    @Schema(description = "Mã giảm giá sản phẩm",example="abcd1234")
    @Size(min=5,max=20,message="Mã giảm giá sản phẩm từ 5-20 ký tự")
    private String promotionCode;
}
