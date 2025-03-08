package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.menu.CreateMenuRequest;
import com.theo.cafe_cashier.dto.request.menu.SearchMenuRequest;
import com.theo.cafe_cashier.dto.request.menu.UpdateMenuRequest;
import com.theo.cafe_cashier.dto.response.ImageResponse;
import com.theo.cafe_cashier.dto.response.MenuResponse;
import com.theo.cafe_cashier.entity.Image;
import com.theo.cafe_cashier.entity.Menu;
import com.theo.cafe_cashier.repository.MenuRepository;
import com.theo.cafe_cashier.service.ImageService;
import com.theo.cafe_cashier.service.MenuService;
import com.theo.cafe_cashier.specification.MenuSpecification;
import com.theo.cafe_cashier.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ImageService imageService;
    private final ValidatorUtil validatorUtil;

    @Override
    public MenuResponse save(CreateMenuRequest request) {
        validatorUtil.validate(request);
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .readyOrNot(true)
                .description(request.getDescription())
                .build();
        Image image = imageService.create(request.getImage());
        menu.setImage(image);
        menuRepository.saveAndFlush(menu);

        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .readyOrNot(menu.getReadyOrNot())
                .description(menu.getDescription())
                .image(ImageResponse.builder()
                        .name(image.getName())
                        .url(ApiUrl.MENU_IMAGE_DOWNLOAD_API + image.getId())
                        .build())
                .build();
    }

    @Override
    public Page<MenuResponse> findAll(SearchMenuRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Specification<Menu> specification = MenuSpecification.getSpecification(request.getQuery());
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
        Page<Menu> all = menuRepository.findAll(specification, pageable);
        return all.map(MenuServiceImpl::convertToMenuResponse);
    }

    @Override
    public MenuResponse findOne(String id) {
        Menu menu = getOne(id);
        return convertToMenuResponse(menu);
    }

    @Override
    public Menu getOne(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    public Menu findById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public void delete(String id) {
        Menu menu = findById(id);
        if (menu.getImage() != null) {
            imageService.delete(menu.getImage().getId());
        }
        menuRepository.deleteById(menu.getId());
    }

    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        Menu menu = findById(request.getId());
        if (request.getImage() != null) {
            if (menu.getImage() != null) {
                imageService.delete(menu.getImage().getId());
            }
            Image image = imageService.create(request.getImage());
            menu.setImage(image);
        }
        menu.setId(request.getId());
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());

        menuRepository.saveAndFlush(menu);
        return convertToMenuResponse(menu);
    }

    @Override
    public MenuResponse updateStatusReadyOrNot(String id) {
        Menu menu = findById(id);
        menu.setReadyOrNot(!menu.getReadyOrNot());
        return convertToMenuResponse(menu);
    }

    private static MenuResponse convertToMenuResponse(Menu menuResponse) {
        return MenuResponse.builder()
                .id(menuResponse.getId())
                .name(menuResponse.getName())
                .price(menuResponse.getPrice())
                .readyOrNot(menuResponse.getReadyOrNot())
                .description(menuResponse.getDescription())
                .image(ImageResponse.builder()
                        .name(menuResponse.getImage().getName())
                        .url(ApiUrl.MENU_IMAGE_DOWNLOAD_API + menuResponse.getImage().getId())
                        .build())
                .build();
    }
}
