package com.tmoreno.mooc.shared.query;

public interface Query<P extends QueryParams, R extends QueryResponse> {
    R execute(P params);
}
