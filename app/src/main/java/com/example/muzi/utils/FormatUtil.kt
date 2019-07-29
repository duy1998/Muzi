package com.example.muzi.utils

import com.example.muzi.data.Time
import java.text.DecimalFormat

class FormatUtil {
    companion object {
        private val suffix = arrayOf("", "K", "M", "B", "T")
        private const val MAX_LENGTH = 4

        fun formatViewCount(number: Long): String {
            var r = DecimalFormat("##0E0").format(number)
            r = r.replace("E[0-9]".toRegex(), suffix[Character.getNumericValue(r[r.length - 1]) / 3])
            while (r.length > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]".toRegex())) {
                r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
            }
            return r
        }

        fun formatDuration(s: String): String {
            val tmp = Time()
            var tmp1 = 0
            var i:Int = if ((s[0] == 'P') && (s[1] == 'T')) {
                2
            } else {
                1
            }
            while (i < s.length) {
                if (s[i].isDigit()){
                    tmp1 =tmp1*10 +(s[i].toInt() - 48)
                } else{
                    if (s[i] == 'S') tmp.sec =tmp1
                    if (s[i] == 'M') tmp.min =tmp1
                    if (s[i] == 'H') tmp.hour =tmp1
                    if (s[i] == 'D') tmp.day = tmp1

                    tmp1 =0
                }
                i++
            }
            return tmp.toString()
        }
    }
}