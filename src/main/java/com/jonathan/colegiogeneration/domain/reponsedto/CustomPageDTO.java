package com.jonathan.colegiogeneration.domain.reponsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CustomPageDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private Map<String, String> links; // Campo para armazenar links

    // Construtores, getters e setters

    public CustomPageDTO(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages, Map<String, String> links) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.links = links;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}

