package com.mochu.service;

import com.mochu.vo.ResultVo;

public interface IMobileService {

    ResultVo sendLoginCode(String mobile);
}
