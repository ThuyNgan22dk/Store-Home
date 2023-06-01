package com.example.demo.model.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    @NotNull(message = "Tên sản phẩm rỗng")
    @NotEmpty(message="Tên sản phẩm rỗng")
    @Schema(description = "Tên sản phẩm",example="Product1", required=true)
    @Size(min=5,max=50,message="Tên sản phẩm từ 3-50 ký tự")
    private String productname;

    @NotNull(message = "Mô tả rỗng")
    @NotEmpty(message="Mô tả rỗng")
    @Schema(description = "Mô tả sản phẩm",example="Đây là sản phẩm thứ 1")
    @Size(min=5,max=1000,message="Mô tả sản phẩm từ 5-1000 ký tự")
    private String description;

    @NotNull(message = "Nơi sản xuất rỗng")
    @NotEmpty(message="Nơi sản xuất rỗng")
    @Schema(description = "Nơi sản xuất sản phẩm",example="Đà Nẵng")
    @Size(min=5,max=1000,message="Nơi sản xuất sản phẩm từ 5-1000 ký tự")
    private String origin;

//    @NotNull(message = "Nơi sản xuất rỗng")
//    @NotEmpty(message="Nơi sản xuất rỗng")
//    @Schema(description = "Nơi sản xuất sản phẩm",example="Đà Nẵng")
//    @Size(min=5,max=1000,message="Nơi sản xuất sản phẩm từ 5-1000 ký tự")
    private long price;

    @NotNull(message = "Đơn vị tính rỗng")
    @NotEmpty(message="Đơn vị tính rỗng")
    @Schema(description = "Đơn vị tính sản phẩm",example="thùng/lốc/lon")
    @Size(min=3,max=10,message="Đơn vị tính sản phẩm từ 3-10 ký tự")
    private String unit;

    @NotNull(message = "Danh mục rỗng")
    @NotEmpty(message = "Danh mục rỗng")
    @Schema(description = "ID của danh mục",example="1")
    private long categoryId;

    @NotNull(message="Ảnh sản phẩm rỗng")
    @Schema(description="Mảng Id của hình ảnh",example="[1,2,3]")
    private Set<Long> imageIds;
}
