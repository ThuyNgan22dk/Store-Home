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

    @NotNull(message = "Đơn vị tính rỗng")
    @NotEmpty(message="Đơn vị tính rỗng")
    @Schema(description = "Đơn vị tính sản phẩm",example="thùng/lốc/lon")
    @Size(min=3,max=10,message="Đơn vị tính sản phẩm từ 3-10 ký tự")
    private String unit;

//    @NotNull(message = "Giá tiền rỗng")
//    @NotEmpty(message = "Giá tiền rỗng")
//    @Schema(description = "Giá sản phẩm",example = "12")
//    @Size(min=0,message="Giá tiền sản phẩm lớn hơn 0")
//    private long price;

//    @NotNull(message = "Số lượng sản phẩm")
//    @NotEmpty(message="Số lượng sản phẩm")
//    @Schema(description = "Số lượng sản phẩm",example="12")
//    @Size(min=0,message="Số lượng sản phẩm từ 0")
//    private int quantity;

//    @NotNull(message = "Hạn sử dụng rỗng")
//    @NotEmpty(message="Hạn sử dụng rỗng")
//    @Schema(description = "Hạn sử dụng sản phẩm",example="yyyy-MM-dd")
//    @Size(min=5,max=20,message="Hạn sử dụng sản phẩm từ 5-20 ký tự")
//    private String expiry;

    @NotNull(message = "Danh mục rỗng")
    @NotEmpty(message = "Danh mục rỗng")
    @Schema(description = "ID của danh mục",example="1")
    private long categoryId;

    @NotNull(message="Ảnh sản phẩm rỗng")
    @Schema(description="Mảng Id của hình ảnh",example="[1,2,3]")
    private Set<Long> imageIds;

    private List<CreateCommentRequest> comments;
}
