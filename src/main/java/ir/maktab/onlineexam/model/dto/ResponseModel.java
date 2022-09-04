package ir.maktab.onlineexam.model.dto;

import lombok.Data;

@Data
public class ResponseModel<T> {
    private T data;

    public ResponseModel(T data) {
        this.data = data;
    }
}
