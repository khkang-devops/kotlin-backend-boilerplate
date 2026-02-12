package com.test.app.common.code

enum class AreaCode(val codeName: String, val dayCode: String, val midTermCode: String, val shortTermX: String, val shortTermY: String) {

    GANGWON("강원", "101", "11D10301", "73", "134"),
    GYEONGGI("경기", "109", "11B20601", "60", "121"),
    GYEONGNAM("경남", "155", "11H20301", "91", "77"),
    GYEONGBUK("경북", "138", "11H10201", "102", "94"),
    GWANGJU("광주", "156", "11F20501", "58", "74"),
    DAEGU("대구", "143", "11H10701", "89", "90"),
    DAEJEON("대전", "133", "11C20401", "67", "100"),
    BUSAN("부산", "159", "11H20201", "98", "76"),
    SEOUL("서울", "108", "11B10101", "60", "127"),
    SEJONG("세종", "1239", "11C20404", "66", "103"),
    ULSAN("울산", "152", "11H20101", "102", "84"),
    INCHEON("인천", "112", "11B20201", "55", "124"),
    JEONNAM("전남", "165", "21F20801", "50", "67"),
    JEONBUK("전북", "146", "11F10201", "63", "89"),
    JEJU("제주", "184", "11G00201", "53", "38"),
    CHUNGNAM("충남", "232", "11C20301", "63", "110"),
    CHUNGBUK("충북", "141", "11C10301", "69", "106");

    companion object {
        fun getByCodeName(codeName: String): AreaCode? =
            AreaCode.entries.find { it.codeName == codeName }
    }
}