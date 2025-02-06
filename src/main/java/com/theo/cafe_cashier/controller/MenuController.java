package com.theo.cafe_cashier.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.CreateMenuRequest;
import com.theo.cafe_cashier.dto.request.SearchMenuRequest;
import com.theo.cafe_cashier.dto.request.UpdateMenuRequest;
import com.theo.cafe_cashier.dto.response.CommonResponse;
import com.theo.cafe_cashier.dto.response.MenuResponse;
import com.theo.cafe_cashier.dto.response.PagingResponse;
import com.theo.cafe_cashier.service.MenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = ApiUrl.MENU_API)
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(
            @RequestPart(name = "menu") String jsonMenu,
            @RequestPart(name = "image") MultipartFile image
    ){
        CommonResponse.CommonResponseBuilder<MenuResponse> builder = CommonResponse.builder();
        try {
            CreateMenuRequest request = objectMapper.readValue(jsonMenu, new TypeReference<>() {});
            System.out.println(request.getName());
            System.out.println(request.getPrice());
            System.out.println(request.getDescription());
            request.setImage(image);
            MenuResponse save = menuService.save(request);
            builder.message(ResponseMessage.SUCCESS_CREATE_MENU);
            builder.statusCode(HttpStatus.CREATED.name());
            builder.data(save);
            return  ResponseEntity.status(HttpStatus.CREATED).body(builder.build());
        } catch (Exception e){
            builder.message(ResponseMessage.INTERNAL_SERVER_ERROR);
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenu(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "q", required = false) String query
    ) {
        SearchMenuRequest request = SearchMenuRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .query(query)
                .build();
        Page<MenuResponse> allMenu = menuService.findAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allMenu.getPageable().getPageNumber() + 1)
                .size(allMenu.getPageable().getPageSize())
                .hasNext(allMenu.hasNext())
                .hasPrevious(allMenu.hasPrevious())
                .totalElements(allMenu.getTotalElements())
                .totalPage(allMenu.getTotalPages())
                .build();
        CommonResponse<List<MenuResponse>> build = CommonResponse.<List<MenuResponse>>builder()
                .message(ResponseMessage.SUCCESS_GET_ALL_DATA)
                .statusCode(HttpStatus.OK.name())
                .data(allMenu.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(build);
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(
            @RequestPart(name = "menu", required = false) String jsonMenu,
            @RequestPart(name = "image", required = false) MultipartFile image
    ){
        CommonResponse.CommonResponseBuilder<MenuResponse> builder = CommonResponse.builder();
        try {
            UpdateMenuRequest request = objectMapper.readValue(jsonMenu, new TypeReference<>() {});
            request.setImage(image);
            MenuResponse save = menuService.update(request);
            builder.message(ResponseMessage.SUCCESS_UPDATE_MENU);
            builder.statusCode(HttpStatus.CREATED.name());
            builder.data(save);
            return  ResponseEntity.status(HttpStatus.CREATED).body(builder.build());
        } catch (Exception e){
            builder.message(ResponseMessage.INTERNAL_SERVER_ERROR);
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id){
        menuService.delete(id);
        CommonResponse<String> builder = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.name())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(builder);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenuReadyOrNot(@PathVariable String id){
        MenuResponse menuResponse = menuService.updateStatusReadyOrNot(id);
        CommonResponse<MenuResponse> build = CommonResponse.<MenuResponse>builder()
                .message(ResponseMessage.SUCCESS_UPDATE_MENU)
                .statusCode(HttpStatus.OK.name())
                .data(menuResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(build);
    };
}
