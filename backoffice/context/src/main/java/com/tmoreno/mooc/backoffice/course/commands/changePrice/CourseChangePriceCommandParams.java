package com.tmoreno.mooc.backoffice.course.commands.changePrice;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangePriceCommandParams extends CommandParams {

    private String courseId;
    private double value;
    private String currency;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
