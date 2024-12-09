package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.shared.domain.TeacherId;

import java.util.UUID;

public final class TeacherIdMother {

    public static TeacherId random() {
        return new TeacherId(UUID.randomUUID().toString());
    }
}
