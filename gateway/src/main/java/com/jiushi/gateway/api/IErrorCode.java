package com.jiushi.gateway.api;

/**
 * 封装API的错误码
 *
 * @author  [dengmingyang] 2021/3/16
 */
public interface IErrorCode {
  long getCode();

  String getMessage();
}
