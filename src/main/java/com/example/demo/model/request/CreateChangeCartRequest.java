package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChangeCartRequest {
    private String username;
    @NotNull(message="Tên sản phẩm rỗng")
    @NotEmpty(message = "Tên sản phẩm rỗng")
    @Size(min=5,max=50,message="Tên sản phẩm từ 5-50 ký tự")
    private String name;

    @NotNull(message = "Số lượng sản phẩm rỗng")
    private int quantity;
    private int quantityAdd;
}
