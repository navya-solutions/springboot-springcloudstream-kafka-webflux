package com.ns.os.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Objects;

public class Pagination {
    public static PageRequest getPageRequest(ServerRequest serverRequest) {
        int page = serverRequest.queryParams().containsKey("page") ?
                Integer.parseInt(Objects.requireNonNull(serverRequest.queryParams().getFirst("page"))) : 0;
        int size = serverRequest.queryParams().containsKey("size") ?
                Integer.parseInt(Objects.requireNonNull(serverRequest.queryParams().getFirst("size"))) : 10;
        return PageRequest.of(page, size);
    }
}
