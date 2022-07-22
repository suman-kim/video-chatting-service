package com.webrtc.videoChattingService.response;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum CommonResponse {
        SUCCESS(1, "성공하였습니다.")
        , FAIL(-1, "실패하였습니다.");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() { return code; }
        public String getMsg() { return msg; }
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메서드
    private void setSuccessResult(CommonResult result) {

        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(CommonResult result) {
        System.out.println("Fail");
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }


    // 단일건 성공 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 단일건 실패 결과를 처리하는 메소드
    public <T> SingleResult<T> FailgetSingleResult(T data,String msg) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }

    // 다중건 결과를 처리하는 메서드
    public <T> ListResult<T> getListResult(List<T> list) {
        System.out.println("getListResult");

        for (T objArr : list) {

            // 형 변환 후 출력
            System.out.println(objArr);

        }
        ListResult<T> result = new ListResult<>();

        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    // 페이징 다중 결과를 처리하는 메서드
    public <T> PageResult<T> getPageListResult(Page<T> list) {

        PageResult<T> result = new PageResult<>();

        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    // 데이터 없이, 성공 결과만 처리하는 메서드
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 데이터 없이, 실패 결과를 정해진 코드 메시지와 함께 처리하는 메서드
    public CommonResult getFailResult(String msg) {
        CommonResult result = new CommonResult();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }


    public CommonResult getFailDefaultResult(int code , String msg){

        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;

    }

}

