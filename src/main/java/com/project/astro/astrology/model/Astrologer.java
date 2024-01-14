package com.project.astro.astrology.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Astrologer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String qualification;
    private String experience;
    private EAstrologerService astrologerService;
    private String fee;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "astrologer_query",
            joinColumns = @JoinColumn(name = "astrologer_id"),
            inverseJoinColumns = @JoinColumn(name = "query_id")
    )
    private List<Query> queries;

    public void addQuery(Query query) {
        this.queries.add(query);
        query.getAstrologers().add(this);
    }

    public void removeQuery(long queryId) {
        Query query = this.queries.stream().filter(t -> t.getId() == queryId).findFirst().orElse(null);
        if (query != null) {
            this.queries.remove(query);
            query.getAstrologers().remove(this);
        }
    }
}
