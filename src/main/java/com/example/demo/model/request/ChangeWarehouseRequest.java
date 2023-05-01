package com.example.demo.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeWarehouseRequest {
    @NotNull(message = "Sản phẩm rỗng")
    @NotEmpty(message = "Sản phẩm rỗng")
    @Schema(description = "ID của sản phẩm",example="1")
    private String productname;

    @NotNull(message = "Số lượng sản phẩm")
    @NotEmpty(message="Số lượng sản phẩm")
    @Schema(description = "Số lượng sản phẩm",example="1")
    @Size(min=0,message="Số lượng sản phẩm từ 0")
    private int quantity;

    @NotNull(message = "Hạn sử dụng rỗng")
    @NotEmpty(message="Hạn sử dụng rỗng")
    @Schema(description = "Hạn sử dụng sản phẩm",example="yyyy-MM-dd")
    @Size(min=5,max=20,message="Hạn sử dụng sản phẩm từ 5-20 ký tự")
    private String expiry;
}
