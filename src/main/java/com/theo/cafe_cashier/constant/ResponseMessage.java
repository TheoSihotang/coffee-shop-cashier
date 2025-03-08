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
    public static final String ERROR_CREATE_TOKEN = "Error while generate token";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String SUCCESS_REGISTRATION = "Successfully registration new account";
    public static final String SUCCESS_LOGIN = "Successfully login";
    public static final String FOREIGN_KEY_CONSTRAINT = "You can't update a column in a parent table (if you have a constraint on it) that has a foreign key linked to it as this will result in orphaned row.";
    public static final String UNIQUE_KEY_CONSTRAINT = "Duplicate data, the data already exists";
    public static final String ERROR_FORBIDDEN = "can't access this resource";
}
