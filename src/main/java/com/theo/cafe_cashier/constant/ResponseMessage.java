package com.theo.cafe_cashier.constant;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatusCode;

import java.util.Set;

public class ResponseMessage {
    public static final String ERROR_NOT_FOUND = "Sorry... what you are looking for can't be found";
    public static final String ERROR_INVALID_IMAGE_TYPE = "Invalid image type";
    public static final String SUCCESS_CREATE_MENU = "Successfully create new menu";
    public static final String INTERNAL_SERVER_ERROR = "Sorry internal server Error";
    public static final String SUCCESS_UPDATE_MENU = "Successfully update menu";
    public static final String SUCCESS_GET_ALL_DATA = "Successfully get all Data";
    public static final String SUCCESS_DELETE_DATA = "Successfully delete data";
    public static final String ERROR_MENU_NOT_READY = "Menu Not Ready";
    public static final String SUCCESS_CREATE_NEW_TRANSACTION = "Successfully create new transaction";
}
