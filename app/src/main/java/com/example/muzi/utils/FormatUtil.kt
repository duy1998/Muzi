package com.example.muzi.utils

import java.text.DecimalFormat

class FormatUtil {
    companion object {
        private val suffix = arrayOf("", "K", "M", "B", "T")
        private val MAX_LENGTH = 4

        fun formatViewCount(number: Long): String {
            var r = DecimalFormat("##0E0").format(number)
            r = r.replace("E[0-9]".toRegex(), suffix[Character.getNumericValue(r.get(r.length - 1)) / 3])
            while (r.length > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]".toRegex())) {
                r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
            }
            return r
        }

        fun formatDuration(s: String): String {
            var tmp = ""
            for (i in 0 until  s.toCharArray().size) {
                if (s[i].isDigit()) {
                    tmp += s[i]
                } else if (s[i].equals('M') || s[i].equals('H')) {
                    tmp += ":"
                } else if (s[i].equals('D')){
                    if(s[i+1].equals('T')){
                        tmp+=":"
                    }
                }
            }
            return tmp
        }
    }
}