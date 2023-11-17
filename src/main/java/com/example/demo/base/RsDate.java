package com.example.demo.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RsDate<T> {

  private String resultCode;

  private String msg;

  private T data;

  public boolean isSuccess(){
    return resultCode.startsWith("S-");
  }

  public boolean isFail(){
    return !isSuccess();
  }

  public static <T> RsDate<T> of(String resultCode, String msg, T data){
    return new RsDate<>(resultCode, msg, data);
  }

  public static <T> RsDate<T> of(String resultCode, String msg){
    return of(resultCode, msg, null);
  }
}
