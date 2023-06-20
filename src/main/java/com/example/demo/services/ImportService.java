package com.example.demo.services;

import com.example.demo.entities.ImportDetail;
import com.example.demo.entities.ImportGoods;
import com.example.demo.model.request.CreateImportDetailRequest;
import com.example.demo.model.request.CreateImportGoodRequest;

import java.util.List;

public interface ImportService {
    void placeImport(CreateImportGoodRequest request);

    List<ImportGoods> getList();

    long getImportForChart(String date);
    List<String> getDatesForChartLine();
    List<Long> getTotalForChartLine(List<String> dates);

    long totalAllImport();

    List<ImportDetail> getListDetail(Long ig_id);

    void createProductImport(CreateImportDetailRequest rq);

    ImportDetail updateImportDetail(Long id, CreateImportDetailRequest request);

    ImportGoods updateImport(Long id, CreateImportGoodRequest request);

    void deleteImport(Long id);

    void deteleProductImport(Long id);

}
