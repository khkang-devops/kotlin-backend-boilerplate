package com.test.app.common.code

enum class ResultCode(val code: String, val message: String) {
    SUCCESS("0000", "성공"),

    INVALID_PARAMETER("1000", "유효하지 않는 요청 파라미터 입니다."),

    INVALID_USER_ID("5001", "접근 권한이 없습니다."),
    INVALID_USER_PASSWORD("5002", "입력하신 비밀번호가 잘못되었습니다."),
    INVALID_ACCESS_TOKEN("5004", "유효 하지 않는 token 입니다"),
    EXPIRED_ACCESS_TOKEN("5005", "유효 하지 않는 token 입니다"),
    UNAUTHORIZED_OPERATION("5006", "해당 기능을 사용할 권한이 없습니다."),

    INSIGHT_MATE_CONNECT_FAIL("6001", "AI 연결 실패하였습니다."),
    INSIGHT_MATE_USAGE_EXCEED("6002", "이용 횟수를 초과하였습니다."),
    INSIGHT_MATE_FAIL("6003", "AI 해석을 실패하였습니다."),
    INSIGHT_MATE_TIMEOUT("6004", "타임아웃이 발생하였습니다."),
    INSIGHT_MATE_STATUS_FAIL("6005", "잠시 후 다시 이용해 주시기 바랍니다."),
    INSIGHT_MATE_RESULT_FAIL("6006", "결과가 없거나 AI 해석을 실패하였습니다."), //(TODO)
    INSIGHT_MATE_RESPONSE_FAIL("6007", "일시적 오류가 발생했습니다."),

    FAIL("9999", "기타오류"),
}
