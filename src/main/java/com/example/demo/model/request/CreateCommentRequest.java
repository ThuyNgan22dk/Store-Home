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
public class CreateCommentRequest {

    @NotNull(message = "Bình luận rỗng")
    @NotEmpty(message="Bình luận rỗng")
    @Schema(description = "Bình luận sản phẩm",example="Nội dung bình luận")
    @Size(min=5,max=1000,message="Bình luận sản phẩm từ 5-1000 ký tự")
    private String comment;

    @NotNull(message = "Chấm điểm rỗng")
    @NotEmpty(message="Chấm điểm rỗng")
    @Schema(description = "Chấm điểm sản phẩm",example="1-5")
    private int rate;

    @NotNull(message="User id rỗng")
    @NotEmpty(message="User id rỗng")
    @Size(min=5,max=30,message="User id từ 5-30 ký tự")
    @Schema(description="User id",example="1",required = true)
    private long userId;

    @NotNull(message="Product id rỗng")
    @NotEmpty(message="Product id rỗng")
    @Size(min=5,max=30,message="Product id từ 5-30 ký tự")
    @Schema(description="Product id",example="2",required = true)
    private long productId;
}