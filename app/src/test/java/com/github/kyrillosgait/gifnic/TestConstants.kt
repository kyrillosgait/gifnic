package com.github.kyrillosgait.gifnic


const val OFFSET = "0"
const val ANSWER_SHOULD_BE_SUCCESS = "Answer should be Answer.Success, when response is successful."
const val ANSWER_SHOULD_BE_ERROR = "Answer should be Answer.Error, when response is not successful."
const val ERROR_SHOULD_BE_EQUAL =
    "Error should be equal to the GENERIC_ERROR constant, set in ApiResponse."
const val MESSAGE_SHOULD_NOT_BE_NULL = "Error message should not be null if " +
        "response is unsuccessful with code 4xx/5xx."
const val DATA_SHOULD_NOT_BE_NULL = "Data should not be null, when response is successful."
const val DATA_SHOULD_BE_EQUAL = "Data should be equal to the actual data from the response, " +
        "when response is successful."
const val OFFSET_SHOULD_BE_EQUAL = "Offset should be equal to the offset that was requested."
const val PAGE_SIZE_SHOULD_BE_EQUAL = "Count should be equal to the page size that was requested."