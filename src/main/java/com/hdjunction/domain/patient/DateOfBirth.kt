package com.hdjunction.domain.patient

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Embeddable


@Embeddable
class DateOfBirth(dateOfBirth: String?) {
    @Column
    private val dateOfBirth: String?

    init {
        if (isNull(dateOfBirth)) {
            DateOfBirth()
        }
        validation(dateOfBirth)
        this.dateOfBirth = dateOfBirth
    }

    constructor() : this(null)


    fun getDateOfBirth(): String {
        return if (isNull(dateOfBirth)) "" else parseDateOfBirth(dateOfBirth)
    }

    private fun parseDateOfBirth(dateOfBirth: String?): String {
        return LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyyMMdd")).toString()
    }

    private fun isNull(dateOfBirth: String?): Boolean {
        return dateOfBirth == null
    }

    companion object {
        const val REGEX_DATE_OF_BIRTH = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$"
        private val pattern: Pattern = Pattern.compile(REGEX_DATE_OF_BIRTH)
        fun from(dateOfBirth: String?): DateOfBirth {
            return DateOfBirth(dateOfBirth)
        }

        private fun validation(dateOfBirth: String?) {
            require(pattern.matcher(dateOfBirth).find()) { "올바르지 않은 생년월입입니다. ex) 20200102" }
        }
    }
}
