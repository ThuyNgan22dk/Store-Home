package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateImportGoodRequest {
    @NotNull(message="Họ khách hàng rỗng")
    @NotEmpty(message="Họ khách hàng rỗng")
    @Size(min=0,max=50,message="Họ khách hàng từ 3-50 ký tự")
    private String nameShipper;

    @NotNull(message="Số điện thoại rỗng")
    @NotEmpty(message="Số điện thoại rỗng")
    private String phoneShipper;

    private String note;
    private List<CreateImportDetailRequest> importDetails;
}


