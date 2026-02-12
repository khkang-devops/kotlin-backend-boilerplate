package com.test.app.common.teams.dto

data class TeamsRequest(
    val subject : String = "",
    val messageContent: String = "",
    val activityTitle: String? = "",
    val activityMsg: String? = "",
    val themeColor: String? = "#0000FF"
) {
    fun getPayload(): String {
        return """
            {
                "title": "$subject",
                "text": "$messageContent",
                "themeColor": "$themeColor",
                "sections": [{
                    "activityTitle": "$activityTitle",
                    "text": "$activityMsg",
                }]
            }
        """.trimIndent()
    }
}

data class TeamsEventDto(
    val teamsRequest: TeamsRequest,
    val webhookUrl: String
)