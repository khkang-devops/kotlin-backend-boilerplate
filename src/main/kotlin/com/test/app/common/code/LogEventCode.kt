package com.test.app.common.code

enum class LogEventCode(val code: String, val desc: String) {
    // 공통
    COMN001_MAIN_PAGE_ACS("comn001_main_page_acs", "초기화면"),
    COMN002_LOGIN_REQ_CLK("comn002_login_req_clk", "초기화면 > 로그인"),
    COMN007_LOGIN_REQ_CLK("comn007_login_req_clk", "팝업 > 로그인"),
    COMN003_PASSWD_INIT_REQ_CLK("comn003_passwd-init_req_clk", "초기화면 > 비밀번호 초기화"),
    COMN005_LOGOUT_REQ_CLK("comn005_logout_req_clk", "상단바 > 로그아웃"),
    COMN006_TOKEN_REISSUE_REQ_CLK("comn006_token-reissue_req_clk", "토큰 재발급"),
    COMN008_SWITCH_USER_REQ_CLK("comn008_switch-user_req_clk", "사용자 전환"),

    // Quick View
    QUCK001_MAIN_PAGE_ACS("quck001_main_page_acs", "QuickView > 마이페이지(바이어용)"),
    QUCK001_REQUEST_BIZ_SEL("quck001_request_biz_sel", "QuickView > 마이페이지(바이어용) > 업태 선택"),
    QUCK001_REQUEST_CATEGORY_SEL("quck001_request_category_sel", "QuickView > 마이페이지(바이어용) > 카테고리 선택"),
    QUCK001_NOTICE_LINK_CLK("quck001_notice_link_clk", "QuickView > 마이페이지(바이어용) > 공지사항 바로가기"),
    QUCK001_QNA_LINK_CLK("quck001_qna_link_clk", "QuickView > 마이페이지(바이어용) > QNA 바로가기"),
    QUCK001_QNA_REG_CLK("quck001_qna_reg_clk", "QuickView > 마이페이지(바이어용) > QNA 등록"),
    QUCK001_HELP_SCH_CLK("quck001_help_sch_clk", "QuickView > 마이페이지(바이어용) > 도움말"),
    QUCK002_MAIN_PAGE_ACS("quck002_main_page_acs", "QuickView > 마이페이지(일반사용자용)"),
    QUCK002_REQUEST_BIZ_SEL("quck002_request_biz_sel", "QuickView > 마이페이지(일반사용자용) > 업태 선택"),
    QUCK002_TOP50_7D_TREND_INDC_SEL("quck002_top50-7d-trend_indc_sel", "QuickView > 마이페이지(일반사용자용) > 최근 7일 트렌드 Top 50 > 조회지표 선택"),
    QUCK002_AREA_INSIGHT_INDC_SEL("quck002_area-insight_indc_sel", "QuickView > 마이페이지(일반사용자용) > 권역별 Insight > 조회지표 선택"),
    QUCK002_HELP_SCH_CLK("quck002_help_sch_clk", "QuickView > 마이페이지(일반사용자용) > 도움말"),
    QUCK003_MAIN_PAGE_ACS("quck003_main_page_acs", "QuickView > 공공데이터(경제)"),
    QUCK003_PRODUCER_PRICE_INDC_SEL("quck003_producer-price_indc_sel", "QuickView > 공공데이터(경제) > 생산자 물가지수(세부) > 조회지표 선택"),
    QUCK003_CONSUMER_PRICE_INDC1_SEL("quck003_consumer-price_indc1_sel", "QuickView > 공공데이터(경제) > 소비자 물가지수(세부) > 조회지표1 선택"),
    QUCK003_CONSUMER_PRICE_INDC2_SEL("quck003_consumer-price_indc2_sel", "QuickView > 공공데이터(경제) > 소비자 물가지수(세부) > 조회지표2 선택"),
    QUCK003_HELP_SCH_CLK("quck003_help_sch_clk", "QuickView > 공공데이터(경제) > 도움말"),
    QUCK004_MAIN_PAGE_ACS("quck004_main_page_acs", "QuickView > 공공데이터(날씨)"),
    QUCK004_REQUEST_SCH_CLK("quck004_request_sch_clk", "QuickView > 공공데이터(날씨) > 조회"),
    QUCK004_WEATHER_INFO_BIZ_SEL("quck004_weather-info_biz_sel", "QuickView > 공공데이터(날씨) > 지난 날씨 정보 > 업태 선택"),
    QUCK004_SALES_TEMPERATURE_INDC_SEL("quck004_sales-temperature_indc_sel", "QuickView > 공공데이터(날씨) > 매출 추이 및 평균 기온 > 조회지표 선택"),
    QUCK004_HELP_SCH_CLK("quck004_help_sch_clk", "QuickView > 공공데이터(날씨) > 도움말"),

    // 실적분석
    SALE001_MAIN_PAGE_ACS("sale001_main_page_acs", "실적분석 > 업태 통합 분석"),
    SALE001_REQUEST_SCH_CLK("sale001_request_sch_clk", "실적분석 > 업태 통합 분석 > 조회"),
    SALE001_AI_SCH_CLK("sale001_ai_sch_clk", "실적분석 > 업태 통합 분석 > AI"),
    SALE001_HELP_SCH_CLK("sale001_help_sch_clk", "실적분석 > 업태 통합 분석 > 도움말"),
    SALE002_MAIN_PAGE_ACS("sale002_main_page_acs", "실적분석 > 시장 판매 추이"),
    SALE002_REQUEST_SCH_CLK("sale002_request_sch_clk", "실적분석 > 시장 판매 추이 > 조회"),
    SALE002_SALES_TREND_VENDOR_SCH_CLK("sale002_sales-trend-vendor_sch_clk", "실적분석 > 시장 판매 추이 > 벤더별 실결제금액 증감 > 벤더 조회"),
    SALE002_SALES_TREND_WW_SCH_CLK("sale002_sales-trend-ww_sch_clk", "실적분석 > 시장 판매 추이 > 주차별 영향매출 분석 > 벤더 조회"),
    SALE002_PURCHASING_TOP10_INDC_SEL("sale002_purchasing-top10_indc_sel", "실적분석 > 시장 판매 추이 > 최근 N일 재구매율 Top 10 > 조회지표 선택"),
    SALE002_PURCHASING_TOGETHER_SCH_CLK("sale002_purchasing-together_sch_clk", "실적분석 > 시장 판매 추이 > 함께 구매하는 중분류 > 조회"),
    SALE002_HELP_SCH_CLK("sale002_help_sch_clk", "실적분석 > 시장 판매 추이 > 도움말"),
    SALE003_MAIN_PAGE_ACS("sale003_main_page_acs", "실적분석 > 벤더별 분석"),
    SALE003_REQUEST_SCH_CLK("sale003_request_sch_clk", "실적분석 > 벤더별 분석 > 조회"),
    SALE003_GAIN_LOSS_SCH_CLK("sale003_gain-loss_sch_clk", "실적분석 > 벤더별 분석 > 고객 선호도 변화에 따른 Gain/Loss > 조회"),
    SALE003_VENDOR_DETAIL_INDC_SEL("sale003_vendor-detail_indc_sel", "실적분석 > 벤더별 분석 > 벤더별 상세 > 조회지표 선택"),
    SALE003_HELP_SCH_CLK("sale003_help_sch_clk", "실적분석 > 벤더별 분석 > 도움말"),

    // 프로모션 분석
    PRMN001_MAIN_PAGE_ACS("prmn001_main_page_acs", "프로모션 분석 > 프로모션 리스트"),
    PRMN001_REQUEST_SCH_CLK("prmn001_request_sch_clk", "프로모션 분석 > 프로모션 리스트 > 조회"),
    PRMN001_COMPARISON_SCH_CLK("prmn001_comparison_sch_clk", "프로모션 분석 > 프로모션 리스트 > 선택행사 비교분석"),
    PRMN001_COMPARISON_POPUP_INDC1_SEL("prmn001_comparison-popup_indc1_sel", "프로모션 분석 > 프로모션 리스트 > 선택행사 비교분석 > 조회지표1 선택"),
    PRMN001_COMPARISON_POPUP_INDC2_SEL("prmn001_comparison-popup_indc2_sel", "프로모션 분석 > 프로모션 리스트 > 선택행사 비교분석 > 조회지표2 선택"),
    PRMN001_COMPARISON_POPUP_INDC3_SEL("prmn001_comparison-popup_indc3_sel", "프로모션 분석 > 프로모션 리스트 > 선택행사 비교분석 > 행사유형 선택"),
    PRMN001_SALES_STOCK_SCH_CLK("prmn001_sales-stock_sch_clk", "프로모션 분석 > 프로모션 리스트 > 판매추이 및 재고현황"),
    PRMN001_HELP_SCH_CLK("prmn001_help_sch_clk", "프로모션 분석 > 프로모션 리스트 > 도움말"),

    // 브랜드 분석
    BRND001_MAIN_PAGE_ACS("brnd001_main_page_acs", "브랜드 분석 > 브랜드 실적 분석"),
    BRND001_REQUEST_SCH_CLK("brnd001_request_sch_clk", "브랜드 분석 > 브랜드 실적 분석 > 조회"),
    BRND001_BRAND_SHARE_GROWTH_INDC_SEL("brnd001_brand-share-growth_indc_sel", "브랜드 분석 > 브랜드 실적 분석 > 브랜드 점유율 X 신장률 > 조회지표 선택"),
    BRND001_TOP20BRAND_SCH_CLK("brnd001_top20brand_sch_clk", "브랜드 분석 > 브랜드 실적 분석 > 브랜드 랭킹 Top 20 > 브랜드명 조회"),
    BRND001_HELP_SCH_CLK("brnd001_help_sch_clk", "브랜드 분석 > 브랜드 실적 분석 > 도움말"),
    BRND002_MAIN_PAGE_ACS("brnd002_main_page_acs", "브랜드 분석 > 브랜드 마켓순위"),
    BRND002_REQUEST_SCH_CLK("brnd002_request_sch_clk", "브랜드 분석 > 브랜드 마켓순위 > 조회"),
    BRND002_BRAND_TREND_INDC_SEL("brnd002_brand-trend_indc_sel", "브랜드 분석 > 브랜드 마켓순위 > 브랜드별 추이 > 조회지표 선택"),
    BRND002_TOP20BRAND_SCH_CLK("brnd002_top20brand_sch_clk", "브랜드 분석 > 브랜드 마켓순위 > 브랜드 랭킹 Top 20 > 브랜드명 조회"),
    BRND002_HELP_SCH_CLK("brnd002_help_sch_clk", "브랜드 분석 > 브랜드 마켓순위 > 도움말"),
    BRND003_MAIN_PAGE_ACS("brnd003_main_page_acs", "브랜드 분석 > 브랜드 고객 선호도"),
    BRND003_REQUEST_SCH_CLK("brnd003_request_sch_clk", "브랜드 분석 > 브랜드 고객 선호도 > 조회"),
    BRND003_BRAND_SALES_INDC_SEL("brnd003_brand-sales_indc_sel", "브랜드 분석 > 브랜드 고객 선호도 > 선택 분류 내 브랜드별 실적 > 조회지표 선택"),
    BRND003_AI_SEG_SCH_CLK("brnd003_ai-seg_sch_clk", "브랜드 분석 > 브랜드 고객 선호도 > 브랜드별 고객 세그먼트 분석 > 브랜드명 조회"),
    BRND003_AI_SEG_TREND_SEG_SEL("brnd003_ai-seg-trend_seg_sel", "브랜드 분석 > 브랜드 고객 선호도 > 브랜드별 고객 세그먼트 분석 > 비중 > AI 세그먼트 선택 "),
    BRND003_HELP_SCH_CLK("brnd003_help_sch_clk", "브랜드 분석 > 브랜드 고객 선호도 > 도움말"),

    // 상품 상세 분석
    PRDT001_MAIN_PAGE_ACS("prdt001_main_page_acs", "상품 상세 분석 > 상품 스카우트"),
    PRDT001_REQUEST_SCH_CLK("prdt001_request_sch_clk", "상품 상세 분석 > 상품 스카우트 > 조회"),
    PRDT001_PRODUCT_GRID_COMPARISON_SCH_CLK("prdt001_product-grid-comparison_sch_clk", "상품 상세 분석 > 상품 스카우트 > 선택 상품 실적 비교"),
    PRDT001_HELP_SCH_CLK("prdt001_help_sch_clk", "상품 상세 분석 > 상품 스카우트 > 도움말"),

    // 고객분석
    OLAP001_MAIN_PAGE_ACS("olap001_main_page_acs", "고객분석 > 이용 고객 분석"),
    OLAP001_REQUEST_SCH_CLK("olap001_request_sch_clk", "고객분석 > 이용 고객 분석 > 조회"),
    OLAP001_HELP_SCH_CLK("olap001_help_sch_clk", "고객분석 > 이용 고객 분석 > 도움말"),
    OLAP002_MAIN_PAGE_ACS("olap002_main_page_acs", "고객분석 > 판매 실적 분석"),
    OLAP002_REQUEST_SCH_CLK("olap002_request_sch_clk", "고객분석 > 판매 실적 분석 > 조회"),
    OLAP002_PROMOTION_SALES_INDC_SEL("olap002_promotion_sales_indc_sel", "고객분석 > 판매 실적 분석 > 행사 진행 여부별 판매실적 > 조회지표 선택"),
    OLAP002_TOP30SKU_SCH_CLK("olap002_top30sku_sch_clk", "고객분석 > 판매 실적 분석 > 선호 상품 TOP 30 SKU > 조회"),
    OLAP002_HELP_SCH_CLK("olap002_help_sch_clk", "고객분석 > 판매 실적 분석 > 도움말"),

    // 인사이트 리포트
    INST001_MAIN_PAGE_ACS("inst001_main_page_acs", "브랜드 인사이트 리포트 > 트렌드 리포트"),
    INST002_MAIN_PAGE_ACS("inst002_main_page_acs", "브랜드 인사이트 리포트 > 카테고리 리포트"),

    // 광고 성과 리포트
    ADPM001_MAIN_PAGE_ACS("adpm001_main_page_acs", "광고 성과 리포트 > 광고 성과 리포트"),

    // 고객 서비스
    CUST001_MAIN_PAGE_ACS("cust001_main_page_acs", "고객 서비스 > 공지사항"),
    CUST002_MAIN_PAGE_ACS("cust002_main_page_acs", "고객 서비스 > FAQ"),
    CUST003_MAIN_PAGE_ACS("cust003_main_page_acs", "고객 서비스 > QNA"),

    // none
    NONE("", "");

    companion object {
        private val map: Map<String, LogEventCode> = entries.associateBy { it.code }

        fun of(code: String): LogEventCode {
            return map.getOrDefault(code, NONE)
        }
    }
}
