package com.example.demo.services.Impl;

import com.example.demo.entities.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.*;
import com.example.demo.repositories.*;
import com.example.demo.services.ImportService;
import com.example.demo.services.WarehouseServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    @Autowired
    private ImportGoodsRepository importGoodsRepository;

    @Autowired
    private ImportDetailRepository importDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseServise warehouseServise;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public LocalDateTime now = LocalDateTime.now();

    @Override
    public void placeImport(CreateImportGoodRequest request) {
        // TODO Auto-generated method stub
        ImportGoods importGoods = new ImportGoods();
        getImport(request, importGoods);
    }

    private ImportGoods getImport(CreateImportGoodRequest request, ImportGoods importGoods){
        importGoods.setNameShipper(request.getNameShipper());
        importGoods.setPhoneShipper(request.getPhoneShipper());
        importGoods.setNote(request.getNote());
        //Date-time
        importGoods.setDateCreated(dtf.format(now));
        importGoodsRepository.save(importGoods);
        long totalPrice = 0;
        for(CreateImportDetailRequest rq: request.getImportDetails()){
            createProductImport(rq);
            totalPrice += rq.getPrice() * rq.getQuantity();
        }
        importGoods.setTotalPrice(totalPrice);
        importGoodsRepository.save(importGoods);
        return importGoods;
    }

    @Override
    public List<ImportGoods> getList() {
        List<ImportGoods> importGoodsList = importGoodsRepository.findAll();
        List<ImportGoods> list = new ArrayList<>();
        for(ImportGoods importGood: importGoodsList){
            if (importGood.getDateDeleted() == null) {
                list.add(importGood);
            }
        }
        return list;
    }

    @Override
    public long totalAllImport(){
        List<ImportGoods> listImport = importGoodsRepository.findAll();
        long totalImport = 0;
        for (ImportGoods importGoods : listImport) {
            totalImport += importGoods.getTotalPrice();
        }
        return totalImport;
    }

    @Override
    public List<ImportDetail> getListDetail(Long ig_id){
        List<ImportDetail> detailList = importDetailRepository.getListDetailById(ig_id);
        ImportGoods importGoods = importGoodsRepository.findById(ig_id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + ig_id));;
        long totalPrice = 0;
        for(ImportDetail rq: detailList){
            totalPrice += rq.getSubTotal();
        }
        importGoods.setTotalPrice(totalPrice);
        importGoodsRepository.save(importGoods);
        return detailList;
    }

    private ImportDetail getImportDetail(CreateImportDetailRequest rq, ImportDetail importDetail){
        ChangeWarehouseRequest changeWarehouseRequest = new ChangeWarehouseRequest(rq.getName(), rq.getQuantity(), rq.getExpiry());
        warehouseServise.addImport(changeWarehouseRequest);
        importDetail.setName(rq.getName());
        importDetail.setPrice(rq.getPrice());
        importDetail.setQuantity(rq.getQuantity());
        importDetail.setExpiry(rq.getExpiry());
        importDetail.setSubTotal(rq.getPrice()* rq.getQuantity());
        ImportGoods importGoods = importGoodsRepository.findById(rq.getImportGoodId()).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + rq.getImportGoodId()));
        importDetail.setImportGoods(importGoods);
        Product product = productRepository.findByProductname(rq.getName()).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + rq.getName()));
//        product.setPrice(rq.getPrice() + 10000L);
        long temp = 0;
        switch (product.getUnit()){
            case "Thùng": {
                temp = 20000L;
                break;
            }
            case "Lốc": {
                temp = 8000L;
                break;
            }
            case "Hộp": {
                temp = 10000L;
                break;
            }
            default: {
                temp = 5000L;
                break;
            }
        }
        product.setPrice(rq.getPrice() + temp);
        product.setExpiry(rq.getExpiry());
        if (product.getQuantity() <= 0) {
            product.setInventoryStatus("OUTOFSTOCK");
        } else if (product.getQuantity() < 10) {
            product.setInventoryStatus("LOWSTOCK");
        } else {
            product.setInventoryStatus("INSTOCK");
        }
        importDetail.setCategory(product.getCategory());
        productRepository.save(product);
        importDetailRepository.save(importDetail);
        return importDetail;
    }

    @Override
    public void createProductImport(CreateImportDetailRequest rq){
        ImportDetail importDetail = new ImportDetail();
        getImportDetail(rq, importDetail);
    }

    @Override
    public ImportDetail updateImportDetail(Long id, CreateImportDetailRequest request){
        ImportDetail importDetail = importDetailRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        return getImportDetail(request, importDetail);
    }

    @Override
    public ImportGoods updateImport(Long id, CreateImportGoodRequest request) {
        ImportGoods importGoods = importGoodsRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        return getImport(request, importGoods);
    }

    @Override
    public void deleteImport(Long id){
        ImportGoods importGoods = importGoodsRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        importGoods.setDateDeleted(dtf.format(now));
        importGoodsRepository.save(importGoods);
    }

    @Override
    public void deteleProductImport(Long id) {
        ImportDetail importDetail = importDetailRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        importDetailRepository.delete(importDetail);
    }
}
