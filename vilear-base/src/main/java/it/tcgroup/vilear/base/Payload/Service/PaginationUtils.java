package it.tcgroup.vilear.base.Payload.Service;


import org.springframework.data.domain.PageRequest;

public class PaginationUtils {

    public static PageRequest buildPageRequest(int page, int pageSize) {
        return PageRequest.of(page, pageSize);
    }

}
