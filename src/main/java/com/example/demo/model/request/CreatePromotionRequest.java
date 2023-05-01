package com.example.demo.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePromotionRequest {

    @NotNull(message = "Khuyến mãi rỗng")
    @NotEmpty(message="Khuyến mãi rỗng")
    @Schema(description = "Khuyến mãi",example="Promotion1",required=true)
    @Size(min=3,max=50,message="Khuyến mãi từ 3-50 ký tự")
    private String name;

    @NotNull(message = "Khuyến mãi rỗng")
    @NotEmpty(message="Khuyến mãi rỗng")
    @Schema(description = "Khuyến mãi sản phẩm",example="Nội dung khuyến mãi")
    @Size(min=3,max=1000,message="Khuyến mãi sản phẩm từ 5-1000 ký tự")
    private String detail;

    private int quantity;
    private int percent;
}
