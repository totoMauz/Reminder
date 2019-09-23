package mauz.toto.reminder

data class Reminder(val name: String, val duration: Long) {
    override fun toString(): String {
        var hours: Long = 0
        var minutes: Long = 0

        var _dur: Long = duration
        if (_dur >= TemplateActivity.HOUR_TO_MILLI) {
            hours = (_dur / TemplateActivity.HOUR_TO_MILLI)
            _dur -= hours * TemplateActivity.HOUR_TO_MILLI
        }

        if (_dur >= TemplateActivity.MIN_TO_MILLI) {
            minutes = (_dur / TemplateActivity.MIN_TO_MILLI)
        }

        return "$name\t${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
    }
}