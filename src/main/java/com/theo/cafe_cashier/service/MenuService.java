package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.dto.request.CreateMenuRequest;
import com.theo.cafe_cashier.dto.request.SearchMenuRequest;
import com.theo.cafe_cashier.dto.request.UpdateMenuRequest;
import com.theo.cafe_cashier.dto.response.MenuResponse;
import com.theo.cafe_cashier.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {
    MenuResponse save(CreateMenuRequest request);
    Page<MenuResponse> findAll(SearchMenuRequest request);
    MenuResponse findOne(String id);
    void delete(String id);
    MenuResponse update(UpdateMenuRequest request);
    MenuResponse updateStatusReadyOrNot(String id);
}
