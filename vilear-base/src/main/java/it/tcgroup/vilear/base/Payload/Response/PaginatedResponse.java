package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class PaginatedResponse<S> implements Serializable {
    private List<S> data;
    @JsonProperty(value = "page_data")
    private PageData pageData;
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PageData implements Serializable {
        @JsonProperty(value = "total_pages")
        private int totalPages;
        @JsonProperty(value = "total_elements")
        private long totalElements;
        public PageData(int totalPages, long totalElements) {
            this.totalPages = totalPages;
            this.totalElements = totalElements;
        }
    }
    public PaginatedResponse (List<S> data, PageData pageData) {
        this.data = data;
        this.pageData = pageData;
    }
}