package com.example.muzi.data

class Time {
    var day:Int= -1
    var hour:Int= -1
    var min:Int= -1
    var sec:Int= -1
    override fun toString(): String {
        var result: String
        var tmpHour :Int = hour
        if (day != -1){
            tmpHour += day*24
        }
        result = if (hour != -1) "$tmpHour:" else ""
        result += if (min != -1 ) (if (min < 10) "0$min:" else "$min:") else "00:"
        result += if (sec != -1 ) (if (sec < 10) "0$sec" else "$sec") else "00"
        return result
    }

}