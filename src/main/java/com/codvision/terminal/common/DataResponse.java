package com.codvision.terminal.common;

import lombok.Data;

import java.util.List;
@Data
public class DataResponse<T> {
    private List<T> list;
    private int total;
    private int code;
    private String message;
}
