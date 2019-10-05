package mauz.toto.reminder

data class Reminder(val name: String, val duration: Int) {
    override fun toString(): String {
        return "$name\t${getDuration()}"
    }

    fun getDuration(): String {
        var hours = 0

        var minutes: Int = duration
        if (minutes >= HOUR_TO_MINUTE) {
            hours = (minutes / HOUR_TO_MINUTE)
            minutes -= hours * HOUR_TO_MINUTE
        }
        return formatTime(hours, minutes)
    }
}